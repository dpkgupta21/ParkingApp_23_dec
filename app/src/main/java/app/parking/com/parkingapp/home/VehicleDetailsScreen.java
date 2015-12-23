package app.parking.com.parkingapp.home;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import app.parking.com.parkingapp.R;

public class VehicleDetailsScreen extends AppCompatActivity implements View.OnClickListener {

    private Toolbar mToolbar;
    private TextView toolbar_title;
    private RelativeLayout next_button;
    private LinearLayout color_ll, make_ll;
    private TextView color_tv, make_tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vehicle_detail_screen);
        initViews();
        assignClicks();
    }

    private void assignClicks() {
        next_button.setOnClickListener(this);
        make_ll.setOnClickListener(this);
        color_ll.setOnClickListener(this);
    }

    private void initViews() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar_title = (TextView) findViewById(R.id.toolbar_title);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle(getResources().getString(R.string.vehicle_details));
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        mToolbar.setNavigationIcon(R.drawable.back_button);

        make_ll = (LinearLayout) findViewById(R.id.make_ll);
        color_ll = (LinearLayout) findViewById(R.id.color_ll);
        color_tv = (TextView) findViewById(R.id.color_tv);
        make_tv = (TextView) findViewById(R.id.make_tv);
        next_button = (RelativeLayout) findViewById(R.id.next_button);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.next_button:
                Intent intent = new Intent(getApplicationContext(), ServicesScreen.class);
                startActivity(intent);
                break;
            case R.id.make_ll:

                AppDialogs.selectCarMake(this, make_tv);

                break;

            case R.id.color_ll:
                AppDialogs.selectCarColor(this, color_tv);
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
}
