package app.parking.com.parkingapp.home;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.stripe.android.Stripe;
import com.stripe.android.TokenCallback;
import com.stripe.android.model.Card;
import com.stripe.android.model.Token;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import app.parking.com.parkingapp.R;
import app.parking.com.parkingapp.activity.BaseActivity;
import app.parking.com.parkingapp.customViews.CustomAlert;
import app.parking.com.parkingapp.customViews.CustomProgressDialog;
import app.parking.com.parkingapp.model.CreateOrderResponseDTO;
import app.parking.com.parkingapp.model.PurchaseOrderDTO;
import app.parking.com.parkingapp.preferences.ParkingPreference;
import app.parking.com.parkingapp.utils.AppConstants;
import app.parking.com.parkingapp.utils.AppUtils;
import app.parking.com.parkingapp.utils.WebserviceResponseConstants;
import app.parking.com.parkingapp.view.LoginScreen;
import app.parking.com.parkingapp.webservices.handler.PurchaseOrderAPIHandler;
import app.parking.com.parkingapp.webservices.ihelper.WebAPIResponseListener;

public class CreditCardScreen extends BaseActivity implements AdapterView.OnItemSelectedListener,
        View.OnClickListener {


    private static final String TAG = CreditCardScreen.class.getSimpleName();
    private static final int PURCHASE_ORDER_TOKEN_EXPIRED_FAILURE = 1000;

    private PurchaseOrderDTO purchaseOrderDTO;
    private CreateOrderResponseDTO createOrderResponseDTO;
    private String PUBLISHABLE_KEY = "pk_test_OpA06mOu6bmI6iGZMrahmKkc";
    private Activity mActivity;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.credit_card_screen);
        mActivity = CreditCardScreen.this;

        initViews();
        assignClicks();

        if (getIntent() != null) {
            purchaseOrderDTO = (PurchaseOrderDTO) getIntent().
                    getSerializableExtra(AppConstants.PURCHASE_ORDER_KEY);
            createOrderResponseDTO = (CreateOrderResponseDTO) getIntent().getSerializableExtra(AppConstants.ORDER_SUMMARY_KEY);
        } else {
            purchaseOrderDTO = new PurchaseOrderDTO();
        }
    }

    private void assignClicks() {


        ((EditText) findViewById(R.id.card_num_et))
                .addTextChangedListener(getTextWatcher(R.id.card_num_et2));
        ((EditText) findViewById(R.id.card_num_et2))
                .addTextChangedListener(getTextWatcher(R.id.card_num_et3));
        ((EditText) findViewById(R.id.card_num_et3))
                .addTextChangedListener(getTextWatcher(R.id.card_num_et4));

        ((EditText) findViewById(R.id.edt_month))
                .addTextChangedListener(getTextWatcher(R.id.edt_year));
    }

    private TextWatcher getTextWatcher(final int id) {
        TextWatcher mTextWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (id != R.id.edt_year) {
                    if (start == 3) {
                        ((EditText) findViewById(id)).requestFocus();
                    }
                } else {
                    if (start == 1) {
                        findViewById(id).requestFocus();
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        };
        return mTextWatcher;
    }

    private void initViews() {

        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        TextView toolbar_title = (TextView) findViewById(R.id.toolbar_title);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        mToolbar.setNavigationIcon(R.drawable.back_button);


        RelativeLayout toolbar_right_rl = (RelativeLayout) findViewById(R.id.toolbar_right_rl);
        TextView toolbar_right_tv = (TextView) findViewById(R.id.toolbar_right_tv);
        toolbar_title.setVisibility(View.VISIBLE);
        toolbar_title.setText(getResources().getString(R.string.parkforu));

        toolbar_right_rl.setVisibility(View.VISIBLE);
        toolbar_right_tv.setText(R.string.next);
        toolbar_right_tv.setOnClickListener(this);
        // Spinner element
       // month_spinner = (Spinner) findViewById(R.id.month_spinner);
        //year_spinner = (Spinner) findViewById(R.id.year_spinner);

        // Spinner click listener
       // month_spinner.setOnItemSelectedListener(this);
        //year_spinner.setOnItemSelectedListener(this);
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

        int year_value = 2015;

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
        //month_spinner.setAdapter(monthAdapter);
        //year_spinner.setAdapter(yearAdapter);


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

       /* switch (view.getId()) {
            case R.id.month_spinner:


                break;

            case R.id.year_spinner:
                break;
        }*/

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.toolbar_right_tv:

                generateStripeToken();

                break;
        }
    }


    private void generateStripeToken() {
        String cardNumber = getViewText(R.id.card_num_et).trim()
                + getViewText(R.id.card_num_et2).trim() + getViewText(R.id.card_num_et3).trim()
                + getViewText(R.id.card_num_et4).trim();
        Card card = new Card(
                cardNumber,
                Integer.parseInt(getEditTextText(R.id.edt_month) + ""),
                Integer.parseInt(getEditTextText(R.id.edt_year) + ""),
                getTextViewText(R.id.cvv_et).trim()
        );

        boolean validation = card.validateCard();
        if (validation) {
            CustomProgressDialog.showProgDialog(mActivity, "Validating Card");
            new Stripe().createToken(
                    card,
                    PUBLISHABLE_KEY,
                    new TokenCallback() {
                        public void onSuccess(Token token) {


                            Toast.makeText(CreditCardScreen.this, "Token Id :"
                                    + token.getId(), Toast.LENGTH_SHORT).show();
                            purchaseOrderDTO.setPurchaseStripeToken(token.getId());

                            String auth = ParkingPreference.getKeyAuthtoken(mActivity);
                            String request = new Gson().toJson(purchaseOrderDTO);
                            String userId = ParkingPreference.getUserid(mActivity);

                            new PurchaseOrderAPIHandler(mActivity,
                                    request, auth, userId,
                                    managePurchaseResponse());


                        }

                        public void onError(Exception error) {
                            AppUtils.showErrorLog(TAG, error.getLocalizedMessage());
                            CustomProgressDialog.hideProgressDialog();
                            AppUtils.showDialog(mActivity, "Alert!",
                                    "Please try again.");
                        }
                    });
        } else if (!card.validateNumber()) {
            AppUtils.showDialog(mActivity, "Alert!", "The card number that you entered is invalid");
        } else if (!card.validateExpiryDate()) {
            AppUtils.showDialog(mActivity, "Alert!", "The expiration date that you entered is invalid");
        } else if (!card.validateCVC()) {
            AppUtils.showDialog(mActivity, "Alert!", "The CVC code that you entered is invalid");
        } else {
            AppUtils.showDialog(mActivity, "Alert!", "The card details that you entered are invalid");
        }


    }

    private WebAPIResponseListener managePurchaseResponse() {

        WebAPIResponseListener webAPIResponseListener = new WebAPIResponseListener() {
            @Override
            public void onSuccessOfResponse(Object... arguments) {
                String response = (String) arguments[0];
                createOrderResponseDTO = new Gson().fromJson(response, CreateOrderResponseDTO.class);

                AppUtils.showLog(TAG, response);
                AppDialogs.messageDialog(mActivity,
                        mActivity.getString(R.string.payment_success),
                        createOrderResponseDTO);


            }

            @Override
            public void onFailOfResponse(Object... arguments) {

                try {
                    CustomProgressDialog.hideProgressDialog();

                    if (arguments != null) {
                        JSONObject errorJsonObj = (JSONObject) arguments[0];

                        if (AppUtils.getWebServiceErrorCode(errorJsonObj).
                                equalsIgnoreCase
                                        (WebserviceResponseConstants.ERROR_TOKEN_EXPIRED)) {

                            new CustomAlert(mActivity, mActivity)
                                    .singleButtonAlertDialog(
                                            AppUtils.getWebServiceErrorMsg(errorJsonObj),
                                            getString(R.string.ok),
                                            "singleBtnCallbackResponse",
                                            PURCHASE_ORDER_TOKEN_EXPIRED_FAILURE);
                        }
                    }
                } catch (Exception e) {
                    CustomProgressDialog.hideProgressDialog();
                    AppUtils.showDialog(mActivity,
                            getString(R.string.dialog_title_alert),
                            getString(R.string.network_error_please_try_again));
                    e.printStackTrace();
                }
            }
        };

        return webAPIResponseListener;
    }

    public void singleBtnCallbackResponse(Boolean flag, int code) {
        if (flag) {
            if (code == PURCHASE_ORDER_TOKEN_EXPIRED_FAILURE) {
                ParkingPreference.clearSession(mActivity);
                Intent intent = new Intent(mActivity, LoginScreen.class);
                startActivity(intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                finish();
            }
        }
    }

}
