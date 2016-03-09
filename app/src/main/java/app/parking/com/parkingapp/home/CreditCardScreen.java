package app.parking.com.parkingapp.home;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.gson.Gson;
import com.stripe.android.Stripe;
import com.stripe.android.TokenCallback;
import com.stripe.android.model.Card;
import com.stripe.android.model.Token;

import java.util.ArrayList;
import java.util.List;

import app.parking.com.parkingapp.R;
import app.parking.com.parkingapp.model.HoldOrderResponseDTO;
import app.parking.com.parkingapp.model.PurchaseOrderDTO;
import app.parking.com.parkingapp.preferences.SessionManager;
import app.parking.com.parkingapp.utils.AppConstants;
import app.parking.com.parkingapp.utils.AppUtils;
import app.parking.com.parkingapp.webservices.handler.PurchaseOrderAPIHandler;
import app.parking.com.parkingapp.webservices.ihelper.WebAPIResponseListener;

public class CreditCardScreen extends AppCompatActivity implements AdapterView.OnItemSelectedListener, View.OnClickListener {


    private static final String TAG = CreditCardScreen.class.getSimpleName();
    private Toolbar mToolbar;
    private TextView toolbar_title;
    private Spinner month_spinner, year_spinner;
    private static int year_value = 2015;
    private EditText card_num_et, card_holder_name_et, cvv_et;
    private RelativeLayout make_payment;
    private PurchaseOrderDTO purchaseOrderDTO;
    private String PUBLISHABLE_KEY = "pk_test_OpA06mOu6bmI6iGZMrahmKkc";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.credit_card_screen);
        initViews();
        assignClicks();
    }

    private void assignClicks() {
        make_payment.setOnClickListener(this);
    }

    private void initViews() {

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar_title = (TextView) findViewById(R.id.toolbar_title);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle(getResources().getString(R.string.credit_card_details));
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        mToolbar.setNavigationIcon(R.drawable.back_button);


        make_payment = (RelativeLayout) findViewById(R.id.make_payment);
        card_num_et = (EditText) findViewById(R.id.card_num_et);
        card_holder_name_et = (EditText) findViewById(R.id.card_holder_name_et);
        cvv_et = (EditText) findViewById(R.id.cvv_et);
        // Spinner element
        month_spinner = (Spinner) findViewById(R.id.month_spinner);
        year_spinner = (Spinner) findViewById(R.id.year_spinner);

        // Spinner click listener
        month_spinner.setOnItemSelectedListener(this);
        year_spinner.setOnItemSelectedListener(this);
        // Spinner Drop down elements
        List<String> month_list = new ArrayList<String>();
        month_list.add("01");
        month_list.add("02");
        month_list.add("03");
        month_list.add("04");
        month_list.add("06");
        month_list.add("07");
        month_list.add("08");
        month_list.add("09");
        month_list.add("10");
        month_list.add("11");
        month_list.add("12");

        List<Integer> year_list = new ArrayList<Integer>();

        for (int i = 0; i < 51; i++) {

            year_list.add(year_value);
            year_value++;

        }


        // Creating adapter for spinner
        ArrayAdapter<String> monthAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, month_list);
        ArrayAdapter<Integer> yearAdapter = new ArrayAdapter<Integer>(this, android.R.layout.simple_spinner_item, year_list);

        // Drop down layout style - list view with radio button
        monthAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        yearAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // attaching data adapter to spinner
        month_spinner.setAdapter(monthAdapter);
        year_spinner.setAdapter(yearAdapter);


        if (getIntent() != null) {
            purchaseOrderDTO = (PurchaseOrderDTO) getIntent().getSerializableExtra(AppConstants.PURCHASE_ORDER_KEY);
        } else {
            purchaseOrderDTO = new PurchaseOrderDTO();
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
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        switch (view.getId()) {
            case R.id.month_spinner:


                break;

            case R.id.year_spinner:
                break;
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.make_payment:

                generateStripeToken();


                break;
        }
    }


    private void generateStripeToken() {
        Card card = new Card(
                card_num_et.getText().toString().trim(),
                Integer.parseInt(month_spinner.getSelectedItem() + ""),
                Integer.parseInt(year_spinner.getSelectedItem() + ""), cvv_et.getText().toString().trim()
        );

        boolean validation = card.validateCard();
        if (validation) {
            AppUtils.showProgressDialog(CreditCardScreen.this, "Validating Card", false);
            new Stripe().createToken(
                    card,
                    PUBLISHABLE_KEY,
                    new TokenCallback() {
                        public void onSuccess(Token token) {
                            AppUtils.hideProgressDialog();
                            purchaseOrderDTO.setUserEmail(SessionManager.getInstance(CreditCardScreen.this).getEmail());
                            purchaseOrderDTO.setPurchaseStripeToken(token.getId());
                            String auth = SessionManager.getInstance(CreditCardScreen.this).getAuthToken();
                            String request = new Gson().toJson(purchaseOrderDTO);

                            new PurchaseOrderAPIHandler(CreditCardScreen.this, request, auth, managePurchaseResponse());


                        }

                        public void onError(Exception error) {
                            AppUtils.showErrorLog(TAG, error.getLocalizedMessage());
                            AppUtils.hideProgressDialog();
                            AppUtils.showToast(CreditCardScreen.this, "Please Try Again");
                        }
                    });
        } else if (!card.validateNumber()) {
            AppUtils.showToast(CreditCardScreen.this, "The card number that you entered is invalid");
        } else if (!card.validateExpiryDate()) {
            AppUtils.showToast(CreditCardScreen.this, "The expiration date that you entered is invalid");
        } else if (!card.validateCVC()) {
            AppUtils.showToast(CreditCardScreen.this, "The CVC code that you entered is invalid");
        } else {
            AppUtils.showToast(CreditCardScreen.this, "The card details that you entered are invalid");
        }


    }

    private WebAPIResponseListener managePurchaseResponse() {

        WebAPIResponseListener webAPIResponseListener = new WebAPIResponseListener() {
            @Override
            public void onSuccessOfResponse(Object... arguments) {
                String response = (String) arguments[0];
                AppUtils.showLog(TAG, response);
                AppUtils.showToast(CreditCardScreen.this, "Payment Successfull");
            }

            @Override
            public void onFailOfResponse(Object... arguments) {

            }
        };

        return webAPIResponseListener;
    }

}
