package app.parking.com.parkingapp.home;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;

import app.parking.com.parkingapp.R;

public class OrderAmountScreen extends AppCompatActivity implements View.OnClickListener {
    private Toolbar mToolbar;
    private RelativeLayout payment_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.order_amount_screen);
        initViews();
        assignClicks();
    }

    private void assignClicks() {
        payment_button.setOnClickListener(this);
    }

    private void initViews() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle(getResources().getString(R.string.order_confirm));
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        mToolbar.setNavigationIcon(R.drawable.back_button);

        payment_button = (RelativeLayout) findViewById(R.id.payment_button);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.payment_button:
                Intent intent = new Intent(this, SelectPaymentScreen.class);
                startActivity(intent);
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
