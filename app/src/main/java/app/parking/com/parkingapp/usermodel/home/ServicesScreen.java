package app.parking.com.parkingapp.usermodel.home;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

import app.parking.com.parkingapp.R;
import app.parking.com.parkingapp.iClasses.AppConstants;

public class ServicesScreen extends AppCompatActivity implements View.OnClickListener {

    private RelativeLayout place_order_button, plus_button;
    private Toolbar mToolbar;
    private TextView toolbar_title, toolbar_right_tv, no_service_tv;
    private RelativeLayout toolbar_right_rl;
    private ListView add_services_lv;
    private Activity mActivity;

    private ArrayList<ServicesModel> mServicesModelArrayList;
    private ServiceAdapter mServiceAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.services_screen);
        initViews();
        assignClicks();

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

        mServicesModelArrayList = new ArrayList<>();
        mServiceAdapter = new ServiceAdapter(mActivity, mServicesModelArrayList, no_service_tv);

//        arrayListArrayAdapter = new ArrayAdapter<String>(this, R.layout.added_services_row, R.id.service_name, addedServicesList);
        add_services_lv.setAdapter(mServiceAdapter);

        mServiceAdapter.notifyDataSetChanged();


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
                Intent intent = new Intent(this, OrderDetailsScreen.class);
                startActivity(intent);
                break;
            case R.id.plus_button:
                Intent addServiceIntent = new Intent(this, AddServicesScreen.class);
                startActivityForResult(addServiceIntent, 101);
                break;
            case R.id.toolbar_right_rl:

                startActivity(new Intent(this, OrderDetailsScreen.class));

                break;
        }
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
            ArrayList<ServicesModel> servicesModelArrayList = new ArrayList<>();
            servicesModelArrayList = (ArrayList<ServicesModel>) data.getExtras().getSerializable(AppConstants.SERVICE);

            mServicesModelArrayList.addAll(servicesModelArrayList);
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    mServiceAdapter.notifyDataSetChanged();

                }
            });
        }

    }
}
