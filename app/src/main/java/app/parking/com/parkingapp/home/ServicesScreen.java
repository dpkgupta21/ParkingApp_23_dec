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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import app.parking.com.parkingapp.R;
import app.parking.com.parkingapp.iClasses.RemoveServices;
import app.parking.com.parkingapp.model.CreateOrderDTO;
import app.parking.com.parkingapp.model.CreateOrderResponseDTO;
import app.parking.com.parkingapp.model.HoldOrderDTO;
import app.parking.com.parkingapp.model.ListOfServicesDTO;
import app.parking.com.parkingapp.preferences.SessionManager;
import app.parking.com.parkingapp.utils.AppConstants;
import app.parking.com.parkingapp.utils.AppUtils;
import app.parking.com.parkingapp.webservices.handler.CreateOrderAPIHandler;
import app.parking.com.parkingapp.webservices.ihelper.WebAPIResponseListener;

public class ServicesScreen extends AppCompatActivity implements View.OnClickListener {

    private RelativeLayout place_order_button, plus_button;
    private Toolbar mToolbar;
    private TextView toolbar_title, toolbar_right_tv, no_service_tv;
    private RelativeLayout toolbar_right_rl;
    private ListView add_services_lv;
    private Activity mActivity;
    private CreateOrderDTO createOrderDTO;
    private CreateOrderResponseDTO createOrderResponseDTO;
    private ArrayList<ListOfServicesDTO> listOfServicesDTO;
    private RemoveServices removeServices;
    private ArrayList<ListOfServicesDTO> mListOfServicesDTOArrayList;
    private ServiceAdapter mServiceAdapter;

    private String TAG = ServicesScreen.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.services_screen);
        initViews();
        assignClicks();

    }

    private void manageServicesRemovedResponse() {

        removeServices = new RemoveServices() {
            @Override
            public void onServicesRemoved(ArrayList<ListOfServicesDTO> updatedlistOfServicesDTO) {
                listOfServicesDTO.clear();
                listOfServicesDTO = updatedlistOfServicesDTO;

            }
        };
    }

    private void initViews() {
        mActivity = this;
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar_title = (TextView) findViewById(R.id.toolbar_title);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle(getResources().getString(R.string.add_services));
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        mToolbar.setNavigationIcon(R.drawable.back_button);
        plus_button = (RelativeLayout) findViewById(R.id.plus_button);
        place_order_button = (RelativeLayout) findViewById(R.id.place_order_button);
        toolbar_right_rl = (RelativeLayout) findViewById(R.id.toolbar_right_rl);
        toolbar_right_tv = (TextView) findViewById(R.id.toolbar_right_tv);
        add_services_lv = (ListView) findViewById(R.id.add_services_lv);
        no_service_tv = (TextView) findViewById(R.id.no_service_tv);
        toolbar_right_rl.setVisibility(View.VISIBLE);
        toolbar_right_tv.setText(R.string.skip);

        mListOfServicesDTOArrayList = new ArrayList<>();
        manageServicesRemovedResponse();
        mServiceAdapter = new ServiceAdapter(mActivity, mListOfServicesDTOArrayList, no_service_tv, removeServices);

//        arrayListArrayAdapter = new ArrayAdapter<String>(this, R.layout.added_services_row, R.id.service_name, addedServicesList);
        add_services_lv.setAdapter(mServiceAdapter);

        mServiceAdapter.notifyDataSetChanged();

        if (getIntent() != null) {
            createOrderDTO = (CreateOrderDTO) getIntent().getSerializableExtra(AppConstants.CREATE_ORDER);
            AppUtils.showLog(TAG, createOrderDTO.getPickUpTime() + " " + createOrderDTO.getDropOffTime());
        } else {
            createOrderDTO = new CreateOrderDTO();
        }

        listOfServicesDTO = new ArrayList<ListOfServicesDTO>();


    }


    private void assignClicks() {

        place_order_button.setOnClickListener(this);
        plus_button.setOnClickListener(this);
        toolbar_right_rl.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.place_order_button:

                submitOrder();
                break;
            case R.id.plus_button:
                listOfServicesDTO.clear();
                mServiceAdapter.notifyDataSetChanged();
                Intent addServiceIntent = new Intent(this, AddServicesScreen.class);
                startActivityForResult(addServiceIntent, 101);
                break;
            case R.id.toolbar_right_rl:

                submitOrder();

                break;
        }
    }

    private void submitOrder() {


        createOrderDTO.setServices(listOfServicesDTO);
        Gson gson = new Gson();
        String orderRequest = gson.toJson(createOrderDTO);
        AppUtils.showLog(TAG, orderRequest);
        String auth = SessionManager.getInstance(mActivity).getAuthToken();
        CreateOrderAPIHandler createOrderAPIHandler = new CreateOrderAPIHandler(mActivity, orderRequest, auth, createOrderResponseListner());
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
                createOrderResponseDTO = new Gson().fromJson(response, CreateOrderResponseDTO.class);


                String orderid = "";
                JSONObject jsonObject = null;
                try {
                    jsonObject = new JSONObject(response);
                    if (jsonObject.has("_orderId")) {
                        orderid = jsonObject.get("_orderId") + "";
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                HoldOrderDTO holdOrderDTO = new HoldOrderDTO();
                holdOrderDTO = new Gson().fromJson(response, HoldOrderDTO.class);
                holdOrderDTO.setOrderId(orderid);
                holdOrderDTO.setUserEmail(SessionManager.getInstance(ServicesScreen.this).getEmail());
                AppUtils.showLog(TAG, response);
                Intent intent = new Intent(ServicesScreen.this, HoldOrderScreen.class);
                intent.putExtra(AppConstants.HOLD_ORDER_KEY, holdOrderDTO);
                intent.putExtra(AppConstants.ORDER_SUMMARY_KEY, createOrderResponseDTO);
                startActivity(intent);


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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && requestCode == 101) {
            listOfServicesDTO.clear();
            listOfServicesDTO = (ArrayList<ListOfServicesDTO>) data.getExtras().getSerializable(AppConstants.SERVICE);

            mListOfServicesDTOArrayList.addAll(listOfServicesDTO);
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    mServiceAdapter.notifyDataSetChanged();

                }
            });
        }

    }


}
