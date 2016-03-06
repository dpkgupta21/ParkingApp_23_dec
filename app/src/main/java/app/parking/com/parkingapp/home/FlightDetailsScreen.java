package app.parking.com.parkingapp.home;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.Calendar;

import app.parking.com.parkingapp.R;
import app.parking.com.parkingapp.model.CreateOrderDTO;
import app.parking.com.parkingapp.model.FlightArrivalDTO;
import app.parking.com.parkingapp.model.FlightDepartureDTO;
import app.parking.com.parkingapp.utils.AppConstants;
import app.parking.com.parkingapp.utils.AppUtils;

public class FlightDetailsScreen extends AppCompatActivity implements View.OnClickListener {
    private Toolbar mToolbar;
    private TextView toolbar_title, arrival_date_tv, arrival_time_tv, depart_time_tv, depart_date_tv;
    private RelativeLayout next_button;
    private EditText flight_name_et, flight_num_et, flight_name_depart_et, flight_number_depart_et;
    private boolean isDropDateClicked = true, isDropTimeClicked = true;
    private Calendar calendar;
    private int mYear, mDepartYear, mArrivalYear;
    private int mMonth, mDepartMonth, mArrivalMonth;
    private int mDay, mDepartDay, mArrivalDay;
    private int mHour, mDepartHour, mArrivalHour;
    private int mMinute, mDepartMinute, mArrivalMinute;
    private String departureTime, arrivalTime;
    private CreateOrderDTO createOrderDTO;
    private FlightArrivalDTO flightArrivalDTO;
    private FlightDepartureDTO flightDepartureDTO;
    private String TAG = FlightDetailsScreen.class.getSimpleName();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.flight_details_screen);
        initViews();
        assignClicks();
    }

    private void assignClicks() {

        next_button.setOnClickListener(this);
        arrival_date_tv.setOnClickListener(this);
        arrival_time_tv.setOnClickListener(this);
        depart_time_tv.setOnClickListener(this);
        depart_date_tv.setOnClickListener(this);

    }

    private void initViews() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar_title = (TextView) findViewById(R.id.toolbar_title);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle(getResources().getString(R.string.flight_details));
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        mToolbar.setNavigationIcon(R.drawable.back_button);

        next_button = (RelativeLayout) findViewById(R.id.next_button);

        arrival_date_tv = (TextView) findViewById(R.id.arrival_date_tv);
        arrival_time_tv = (TextView) findViewById(R.id.arrival_time_tv);
        depart_time_tv = (TextView) findViewById(R.id.depart_time_tv);
        depart_date_tv = (TextView) findViewById(R.id.depart_date_tv);
        flight_name_et = (EditText) findViewById(R.id.flight_name_et);
        flight_num_et = (EditText) findViewById(R.id.flight_num_et);
        flight_name_depart_et = (EditText) findViewById(R.id.flight_name_depart_et);
        flight_number_depart_et = (EditText) findViewById(R.id.flight_number_depart_et);


        calendar = Calendar.getInstance();
        mYear = calendar.get(Calendar.YEAR);
        mMonth = calendar.get(Calendar.MONTH);
        mDay = calendar.get(Calendar.DAY_OF_MONTH);
        mHour = calendar.get(Calendar.HOUR_OF_DAY);
        mMinute = calendar.get(Calendar.MINUTE);

        mDepartYear = mArrivalYear = mYear;
        mDepartMonth = mArrivalMonth = mMonth;
        mDepartDay = mArrivalDay = mDay;
        mDepartHour = mArrivalHour = mHour;
        mDepartMinute = mArrivalMinute = mMinute;

        if (getIntent() != null) {
            createOrderDTO = (CreateOrderDTO) getIntent().getSerializableExtra(AppConstants.CREATE_ORDER);
            AppUtils.showLog(TAG, createOrderDTO.getPickUpTime() + " " + createOrderDTO.getDropOffTime());
        } else {
            createOrderDTO = new CreateOrderDTO();
        }

        flightArrivalDTO = new FlightArrivalDTO();
        flightDepartureDTO = new FlightDepartureDTO();

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.next_button:

                arrivalTime = mArrivalYear + "-" + mArrivalMonth + "-" + mArrivalDay + "T" + mArrivalHour + ":" + mArrivalMinute;
                departureTime = mDepartYear + "-" + mDepartMonth + "-" + mDepartDay + "T" + mDepartHour + ":" + mDepartMinute;

                arrivalTime = departureTime = "2016-02-14T08:11:32.244Z";
                flightArrivalDTO.setFlightArrivalTime(arrivalTime);
                flightArrivalDTO.setFlightDepatureTime(departureTime);
                flightArrivalDTO.setFlightName(flight_name_et.getText().toString().trim());
                flightArrivalDTO.setFlightNumber(flight_num_et.getText().toString().trim());
                flightArrivalDTO.setOrigin("Tornoto");
                flightArrivalDTO.setDestination("London");


                flightDepartureDTO.setFlightArrivalTime(arrivalTime);
                flightDepartureDTO.setFlightDepatureTime(departureTime);
                flightDepartureDTO.setFlightName(flight_name_depart_et.getText().toString().trim());
                flightDepartureDTO.setFlightNumber(flight_number_depart_et.getText().toString().trim());
                flightDepartureDTO.setDestination("Tornoto");
                flightDepartureDTO.setOrigin("London");
                createOrderDTO.setArrivalFlight(flightArrivalDTO);
                createOrderDTO.setDestinationFlight(flightDepartureDTO);
                startActivity(new Intent(this, VehicleDetailsScreen.class).putExtra(AppConstants.CREATE_ORDER, createOrderDTO));

                break;

            case R.id.arrival_date_tv:

                isDropDateClicked = false;
                DatePickerDialog dialog = new DatePickerDialog(this,
                        mDateSetListener, mYear, mMonth, mDay);
                dialog.show();

                break;

            case R.id.arrival_time_tv:
                isDropTimeClicked = false;
                TimePickerDialog dialogArrivalTime;
                dialogArrivalTime = new TimePickerDialog(this, mTimeSetListner, mHour, mMinute, true);
                dialogArrivalTime.show();
                break;

            case R.id.depart_date_tv:
                isDropDateClicked = true;
                DatePickerDialog dialogDepartDate = new DatePickerDialog(this,
                        mDateSetListener, mYear, mMonth, mDay);
                dialogDepartDate.show();
                break;
            case R.id.depart_time_tv:
                isDropTimeClicked = true;
                TimePickerDialog dialogDepartTime;
                dialogDepartTime = new TimePickerDialog(this, mTimeSetListner, mHour, mMinute, true);
                dialogDepartTime.show();
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


    private DatePickerDialog.OnDateSetListener mDateSetListener = new DatePickerDialog.OnDateSetListener() {

        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            int mYear = year;
            int mMonth = monthOfYear;
            int mDay = dayOfMonth;
            if (!isDropDateClicked) {
                mArrivalDay = mDay;
                mArrivalMonth = mMonth + 1;
                mArrivalYear = mYear;
                arrival_date_tv.setText(new StringBuilder()
                        // Month is 0 based so add 1
                        .append(mMonth + 1).append("/ ").append(mDay).append("/ ")
                        .append(mYear).append(" "));
            } else {
                mDepartDay = mDay;
                mDepartMonth = mMonth + 1;
                mDepartYear = mYear;
                depart_date_tv.setText(new StringBuilder()
                        // Month is 0 based so add 1
                        .append(mMonth + 1).append("/ ").append(mDay).append("/ ")
                        .append(mYear).append(" "));
            }

        }
    };


    private TimePickerDialog.OnTimeSetListener mTimeSetListner = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            int hour = hourOfDay;
            int min = minute;

            if (isDropTimeClicked) {
                mDepartHour = hour;
                mDepartMinute = min;

                depart_time_tv.setText(hour + ":" + min);
            } else {
                mArrivalMinute = min;
                mArrivalHour = hour;
                arrival_time_tv.setText(hour + ":" + min);
            }

        }
    };

}
