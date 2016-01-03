package app.parking.com.parkingapp.usermodel.home;

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

public class ServicesScreen extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemClickListener {

    private RelativeLayout place_order_button, plus_button;
    private Toolbar mToolbar;
    private TextView toolbar_title, toolbar_right_tv;
    private RelativeLayout toolbar_right_rl;
    private ListView add_services_lv;
    ArrayList<String> addedServicesList;

    private ArrayAdapter<String> arrayListArrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.services_screen);
        initViews();
        assignClicks();

    }

    private void initViews() {
        addedServicesList = new ArrayList<String>();
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
        toolbar_right_rl.setVisibility(View.VISIBLE);
        toolbar_right_tv.setText(R.string.skip);

        arrayListArrayAdapter = new ArrayAdapter<String>(this, R.layout.added_services_row, R.id.service_name, addedServicesList);
        add_services_lv.setAdapter(arrayListArrayAdapter);
        arrayListArrayAdapter.notifyDataSetChanged();
    }

    private void assignClicks() {

        place_order_button.setOnClickListener(this);
        plus_button.setOnClickListener(this);
        toolbar_right_rl.setOnClickListener(this);

        add_services_lv.setOnItemClickListener(this);


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
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        addedServicesList.remove(position);
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                arrayListArrayAdapter.notifyDataSetChanged();
            }
        });

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && requestCode == 101) {
            String service = data.getExtras().getString(AppConstants.SERVICE);
            addedServicesList.add(service);
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    arrayListArrayAdapter.notifyDataSetChanged();
                }
            });
        }
    }
}
