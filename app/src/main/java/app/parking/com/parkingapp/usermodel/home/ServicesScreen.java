package app.parking.com.parkingapp.usermodel.home;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import app.parking.com.parkingapp.R;

public class ServicesScreen extends AppCompatActivity implements View.OnClickListener {

    private RelativeLayout place_order_button, plus_button;
    private Toolbar mToolbar;
    private TextView toolbar_title;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.services_screen);
        initViews();
        assignClicks();

    }

    private void initViews() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar_title = (TextView) findViewById(R.id.toolbar_title);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle(getResources().getString(R.string.add_services));
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        mToolbar.setNavigationIcon(R.drawable.back_button);
        plus_button = (RelativeLayout) findViewById(R.id.plus_button);
        place_order_button = (RelativeLayout) findViewById(R.id.place_order_button);
    }

    private void assignClicks() {

        place_order_button.setOnClickListener(this);
        plus_button.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.place_order_button:
                Intent intent = new Intent(this, OrderDetailsScreen.class);
                startActivity(intent);
                break;
            case R.id.plus_button:
                startActivity(new Intent(this, AddServicesScreen.class));
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
