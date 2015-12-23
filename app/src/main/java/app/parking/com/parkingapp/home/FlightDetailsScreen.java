package app.parking.com.parkingapp.home;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import app.parking.com.parkingapp.R;

public class FlightDetailsScreen extends AppCompatActivity implements View.OnClickListener {
    Toolbar mToolbar;
    TextView toolbar_title;
    RelativeLayout next_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.flight_details_screen);
        initViews();
        assignClicks();
    }

    private void assignClicks() {
        next_button.setOnClickListener(this);
    }

    private void initViews() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar_title = (TextView) findViewById(R.id.toolbar_title);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle(getResources().getString(R.string.flight_details));
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        mToolbar.setNavigationIcon(R.drawable.back_button);

        next_button = (RelativeLayout) findViewById(R.id.next_button);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.next_button:
                startActivity(new Intent(this, VehicleDetailsScreen.class));

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
