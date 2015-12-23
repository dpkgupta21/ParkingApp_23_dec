package app.parking.com.parkingapp.home;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import app.parking.com.parkingapp.R;

public class CreditCardScreen extends AppCompatActivity implements AdapterView.OnItemSelectedListener {


    private Toolbar mToolbar;
    private TextView toolbar_title;
    private Spinner month_spinner, year_spinner;
    private static int year_value = 2015;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.credit_card_screen);
        initViews();
    }

    private void initViews() {

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar_title = (TextView) findViewById(R.id.toolbar_title);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle(getResources().getString(R.string.credit_card_details));
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        mToolbar.setNavigationIcon(R.drawable.back_button);


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


}
