package app.parking.com.parkingapp.home;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

import app.parking.com.parkingapp.R;
import app.parking.com.parkingapp.customViews.CustomProgressDialog;
import app.parking.com.parkingapp.model.CreateOrderDTO;
import app.parking.com.parkingapp.model.CreateOrderResponseDTO;
import app.parking.com.parkingapp.model.HoldOrderDTO;
import app.parking.com.parkingapp.model.ListOfServicesDTO;
import app.parking.com.parkingapp.preferences.ParkingPreference;
import app.parking.com.parkingapp.utils.AppConstants;
import app.parking.com.parkingapp.utils.AppUtils;
import app.parking.com.parkingapp.webservices.handler.CreateOrderAPIHandler;
import app.parking.com.parkingapp.webservices.handler.ServicesAPIHandler;
import app.parking.com.parkingapp.webservices.ihelper.WebAPIResponseListener;

public class AddServicesScreen extends AppCompatActivity implements View.OnClickListener {

    private Toolbar mToolbar;
    private TextView toolbar_title, toolbar_right_tv, no_service_tv;
    private RelativeLayout toolbar_right_rl;

    private ListView services_lv;
    private RelativeLayout submit_button;
    private AddServicesAdapter addServicesAdapter;
    private ArrayList<ListOfServicesDTO> listOfServicesDTOArrayList;
    private String TAG = AddServicesScreen.class.getSimpleName();
    private CreateOrderDTO createOrderDTO;
    private ArrayList<ListOfServicesDTO> listOfServicesDTO;
    private Activity mActivity;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_services_screen);
        mActivity = AddServicesScreen.this;
        initViews();
        assignClicks();

        if (getIntent() != null) {
            createOrderDTO = (CreateOrderDTO) getIntent().getSerializableExtra(AppConstants.CREATE_ORDER);
            AppUtils.showLog(TAG, createOrderDTO.getPickUpTime() + " " + createOrderDTO.getDropOffTime());
        } else {
            createOrderDTO = new CreateOrderDTO();
        }

        listOfServicesDTO = new ArrayList<ListOfServicesDTO>();

        listOfServicesDTOArrayList = new ArrayList<>();
        String auth = ParkingPreference.getKeyAuthtoken(mActivity);
        String userId = ParkingPreference.getUserid(mActivity);

        new ServicesAPIHandler(mActivity,
                auth, userId,
                fetchServiceResponseListner());
    }

    private void assignClicks() {
        submit_button.setOnClickListener(this);
        toolbar_right_rl.setOnClickListener(this);

    }

    private void initViews() {


        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar_title = (TextView) findViewById(R.id.toolbar_title);
        services_lv = (ListView) findViewById(R.id.services_lv);
        no_service_tv = (TextView) findViewById(R.id.no_service_tv);
        submit_button = (RelativeLayout) findViewById(R.id.submit_button);


        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle(" ");
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        mToolbar.setNavigationIcon(R.drawable.back_button);
        toolbar_right_rl = (RelativeLayout) findViewById(R.id.toolbar_right_rl);
        toolbar_right_tv = (TextView) findViewById(R.id.toolbar_right_tv);
        toolbar_title.setVisibility(View.VISIBLE);
        toolbar_title.setText(getResources().getString(R.string.parkforu));

        toolbar_right_rl.setVisibility(View.VISIBLE);
        toolbar_right_tv.setText(R.string.skip);



    }

    private WebAPIResponseListener fetchServiceResponseListner() {

        WebAPIResponseListener webAPIResponseListener = new WebAPIResponseListener() {
            @Override
            public void onSuccessOfResponse(Object... arguments) {

                String response = (String) arguments[0];

                Gson gson = new Gson();
                Type listType = new TypeToken<ArrayList<ListOfServicesDTO>>() {
                }.getType();
                listOfServicesDTOArrayList = gson.fromJson(response, listType);

                AppUtils.showLog(TAG, listOfServicesDTOArrayList.size() + "");

                addServicesAdapter = new AddServicesAdapter(AddServicesScreen.this, listOfServicesDTOArrayList, no_service_tv);
                services_lv.setAdapter(addServicesAdapter);

            }

            @Override
            public void onFailOfResponse(Object... arguments) {

            }
        };

        return webAPIResponseListener;


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {


        int id = item.getItemId();
        switch (id) {

            case android.R.id.home:
                finish();
                break;

        }
        return super.onOptionsItemSelected(item);

    }

    @Override
    public void onClick(View v) {


        Intent intent = new Intent();

        switch (v.getId()) {


            case R.id.submit_button:
            case R.id.toolbar_right_rl:


                submitOrder();

                break;
        }

    }


    private void submitOrder() {

        for (int i = 0; i < listOfServicesDTOArrayList.size(); i++) {
            if (listOfServicesDTOArrayList.get(i).isAdded()) {
                listOfServicesDTO.add(listOfServicesDTOArrayList.get(i));
            }
        }
        createOrderDTO.setServices(listOfServicesDTO);
        Gson gson = new Gson();
        String orderRequest = gson.toJson(createOrderDTO);
        AppUtils.showLog(TAG, orderRequest);
        String auth = ParkingPreference.getKeyAuthtoken(mActivity);
        String userId = ParkingPreference.getUserid(mActivity);

        CustomProgressDialog.showProgDialog(mActivity, null);
        CreateOrderAPIHandler createOrderAPIHandler = new CreateOrderAPIHandler(mActivity,
                orderRequest, auth,userId,
                createOrderResponseListner());

    }


    /**
     * Mananging response of orderCreation.
     *
     * @return
     */
    private WebAPIResponseListener createOrderResponseListner() {
        WebAPIResponseListener webAPIResponseListener = new WebAPIResponseListener() {
            @Override
            public void onSuccessOfResponse(Object... arguments) {

                String response = (String) arguments[0];
                CreateOrderResponseDTO createOrderResponseDTO = new Gson().fromJson(response,
                        CreateOrderResponseDTO.class);


                HoldOrderDTO holdOrderDTO = new HoldOrderDTO();
                holdOrderDTO.setUserEmail(ParkingPreference.getEmailId(mActivity));
                holdOrderDTO.setOrderId(createOrderResponseDTO.getOrderStatus().getOrder_id());
                holdOrderDTO.setDropOffTime(createOrderDTO.getDropOffTime());
                holdOrderDTO.setPickUpTime(createOrderDTO.getPickUpTime());
                holdOrderDTO.setVenueName(createOrderDTO.getVenueName());

                AppUtils.showLog(TAG, response);
                Intent intent = new Intent(AddServicesScreen.this, HoldOrderScreen.class);
                intent.putExtra(AppConstants.HOLD_ORDER_KEY, holdOrderDTO);
                intent.putExtra(AppConstants.ORDER_SUMMARY_KEY, createOrderResponseDTO);
                startActivity(intent);

                CustomProgressDialog.hideProgressDialog();

            }

            @Override
            public void onFailOfResponse(Object... arguments) {

            }
        };
        return webAPIResponseListener;

    }
}
