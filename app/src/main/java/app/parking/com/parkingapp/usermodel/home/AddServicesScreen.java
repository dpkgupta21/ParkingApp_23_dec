package app.parking.com.parkingapp.usermodel.home;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import app.parking.com.parkingapp.R;
import app.parking.com.parkingapp.iClasses.AppConstants;

public class AddServicesScreen extends AppCompatActivity implements View.OnClickListener {

    private Toolbar mToolbar;
    private TextView toolbar_title;
    private LinearLayout refill, car_wash, oil_change;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_services_screen);
        initViews();
        assignClicks();

    }

    private void assignClicks() {
        refill.setOnClickListener(this);
        car_wash.setOnClickListener(this);
        oil_change.setOnClickListener(this);
    }

    private void initViews() {


        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar_title = (TextView) findViewById(R.id.toolbar_title);
        refill = (LinearLayout) findViewById(R.id.refill);
        car_wash = (LinearLayout) findViewById(R.id.car_wash);
        oil_change = (LinearLayout) findViewById(R.id.oil_change);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle(getResources().getString(R.string.add_services));
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        mToolbar.setNavigationIcon(R.drawable.back_button);


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

            case R.id.refill:
                intent.putExtra(AppConstants.SERVICE, "Re-Fill Petrol");
                setResult(RESULT_OK, intent);
                finish();
                break;

            case R.id.oil_change:
                intent.putExtra(AppConstants.SERVICE, "Oil Change");
                setResult(RESULT_OK, intent);
                finish();
                break;

            case R.id.car_wash:
                intent.putExtra(AppConstants.SERVICE, "Car Wash");
                setResult(RESULT_OK, intent);
                finish();

                break;
        }

    }
}
