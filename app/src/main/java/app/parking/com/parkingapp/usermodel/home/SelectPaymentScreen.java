package app.parking.com.parkingapp.usermodel.home;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import app.parking.com.parkingapp.R;

public class SelectPaymentScreen extends AppCompatActivity implements View.OnClickListener {
    private Toolbar mToolbar;
    private TextView toolbar_title;
    private LinearLayout credit_card_ll;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.select_payment_screen);
        initViews();
        assignClicks();
    }

    private void assignClicks() {
        credit_card_ll.setOnClickListener(this);
    }

    private void initViews() {

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar_title = (TextView) findViewById(R.id.toolbar_title);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle(getResources().getString(R.string.select_payment));
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        mToolbar.setNavigationIcon(R.drawable.back_button);
        credit_card_ll = (LinearLayout) findViewById(R.id.credit_card_ll);
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

        switch (v.getId()) {
            case R.id.credit_card_ll:
                startActivity(new Intent(this, CreditCardScreen.class));
                break;
        }

    }
}
