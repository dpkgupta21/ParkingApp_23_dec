package app.parking.com.parkingapp.home;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
import app.parking.com.parkingapp.model.ListOfServicesDTO;
import app.parking.com.parkingapp.preferences.SessionManager;
import app.parking.com.parkingapp.utils.AppConstants;
import app.parking.com.parkingapp.utils.AppUtils;
import app.parking.com.parkingapp.webservices.handler.ServicesAPIHandler;
import app.parking.com.parkingapp.webservices.ihelper.WebAPIResponseListener;

public class AddServicesScreen extends AppCompatActivity implements View.OnClickListener {

    private Toolbar mToolbar;
    private TextView toolbar_title, no_service_tv;
    private ListView services_lv;
    private RelativeLayout submit_button;
    private AddServicesAdapter addServicesAdapter;
    private ArrayList<ListOfServicesDTO> listOfServicesDTOArrayList;
    private String TAG = AddServicesScreen.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_services_screen);
        initViews();
        assignClicks();

    }

    private void assignClicks() {
        submit_button.setOnClickListener(this);
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
        toolbar_title.setVisibility(View.VISIBLE);
        toolbar_title.setText(getResources().getString(R.string.add_services));
        listOfServicesDTOArrayList = new ArrayList<>();
        String auth = SessionManager.getInstance(this).getAuthToken();

        new ServicesAPIHandler(this, auth, fetchServiceResponseListner());

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

                for (int i = 0; i < listOfServicesDTOArrayList.size(); i++) {
                    if (!listOfServicesDTOArrayList.get(0).isAdded()) {
                        listOfServicesDTOArrayList.remove(i);
                    }
                }

                intent.putExtra(AppConstants.SERVICE, listOfServicesDTOArrayList);
                setResult(RESULT_OK, intent);
                finish();

                break;
        }

    }
}
