package app.parking.com.parkingapp.home;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.Calendar;

import app.parking.com.parkingapp.R;
import app.parking.com.parkingapp.activity.BaseActivity;
import app.parking.com.parkingapp.iClasses.FlightDetailInterface;
import app.parking.com.parkingapp.model.CreateOrderDTO;
import app.parking.com.parkingapp.model.FlightArrivalDTO;
import app.parking.com.parkingapp.model.FlightDepartureDTO;
import app.parking.com.parkingapp.utils.AppConstants;
import app.parking.com.parkingapp.utils.AppUtils;
import app.parking.com.parkingapp.utils.HelpMe;

public class FlightDetailsScreen extends BaseActivity implements View.OnClickListener {
    private Toolbar mToolbar;
    private TextView toolbar_title, arrival_date_tv, arrival_time_tv, depart_time_tv, depart_date_tv, toolbar_right_tv;
    private TextView flight_num_et, flight_number_depart_et, drop_term, pickup_term;
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
    private RelativeLayout toolbar_right_rl;
    private ImageView depart_plane_icon, arrival_plane_icon;

    private FlightDetailInterface flightDetailInterface;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.flight_details_screen);
        initViews();
        assignClicks();

        manageInterface();

    }

    /**
     * showing selected flight details on UI
     *
     * @return
     */
    private FlightDetailInterface manageInterface() {

        flightDetailInterface = new FlightDetailInterface() {
            @Override
            public void onArrivalDetailsSelected(String airline, String from,
                                                 String status, String flightNumber,
                                                 String term, String departTime, String arrivalTime) {
                setViewText(R.id.txt_arrival_flight_origin, from);
                setViewText(R.id.txt_arrival_flight_destination, "TORONTO");
                //setViewText(R.id.arrival_date_tv, HelpMe.getDisplayDate(createOrderDTO.getPickUpTime()));
                setViewText(R.id.arrival_date_tv, HelpMe.getDisplayDate(arrivalTime));
                //setViewText(R.id.arrival_time_tv, HelpMe.getDisplayTime(createOrderDTO.getPickUpTime()));
                setViewText(R.id.arrival_time_tv, HelpMe.getDisplayTime(arrivalTime));

                flight_num_et.setText(flightNumber);
                pickup_term.setText(term);
            }

            @Override
            public void onDepartureDetailsSelected(String airline, String from, String status, String flightNumber, String term, String departTime, String arrivalTime) {
                flight_number_depart_et.setText(flightNumber);

//                setViewText(R.id.depart_date_tv,
//                        HelpMe.getDisplayDate(createOrderDTO.getDropOffTime()));
                setViewText(R.id.depart_date_tv,
                        HelpMe.getDisplayDate(departTime));
                //setViewText(R.id.depart_time_tv, HelpMe.getDisplayTime(createOrderDTO.getDropOffTime()));
                setViewText(R.id.depart_time_tv, HelpMe.getDisplayTime(departTime));
                setViewText(R.id.txt_departure_flight_origin_val, "TORONTO");
                setViewText(R.id.txt_departure_flight_destination_val, from);
                drop_term.setText(term);

            }
        };
        return flightDetailInterface;
    }

    private void assignClicks() {

        arrival_plane_icon.setOnClickListener(this);
        depart_plane_icon.setOnClickListener(this);
        toolbar_right_rl.setOnClickListener(this);


    }

    private void initViews() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar_title = (TextView) findViewById(R.id.toolbar_title);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle(" ");
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        mToolbar.setNavigationIcon(R.drawable.back_button);
        toolbar_title.setVisibility(View.VISIBLE);
        toolbar_title.setText(getResources().getString(R.string.parkforu));

        toolbar_right_rl = (RelativeLayout) findViewById(R.id.toolbar_right_rl);
        toolbar_right_tv = (TextView) findViewById(R.id.toolbar_right_tv);
        toolbar_right_rl.setVisibility(View.VISIBLE);
        toolbar_right_tv.setText(R.string.next);


        arrival_date_tv = (TextView) findViewById(R.id.arrival_date_tv);
        arrival_time_tv = (TextView) findViewById(R.id.arrival_time_tv);
        depart_time_tv = (TextView) findViewById(R.id.depart_time_tv);
        depart_date_tv = (TextView) findViewById(R.id.depart_date_tv);
        flight_num_et = (TextView) findViewById(R.id.flight_num_et);
        flight_number_depart_et = (TextView) findViewById(R.id.flight_number_depart_et);
        pickup_term = (TextView) findViewById(R.id.pickup_term);
        drop_term = (TextView) findViewById(R.id.drop_term);

        depart_plane_icon = (ImageView) findViewById(R.id.depart_plane_icon);
        arrival_plane_icon = (ImageView) findViewById(R.id.arrival_plane_icon);
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
            case R.id.toolbar_right_rl:

                arrivalTime = mArrivalYear + "-" + mArrivalMonth + "-" + mArrivalDay + "T" + mArrivalHour + ":" + mArrivalMinute;
                departureTime = mDepartYear + "-" + mDepartMonth + "-" + mDepartDay + "T" + mDepartHour + ":" + mDepartMinute;
                arrivalTime = departureTime = "2016-02-14T08:11:32.244Z";
                flightArrivalDTO.setFlightArrivalTime(arrivalTime);
                flightArrivalDTO.setFlightDepatureTime(departureTime);
                flightArrivalDTO.setFlightName(" ");
                flightArrivalDTO.setFlightNumber(flight_num_et.getText().toString().trim());
                flightArrivalDTO.setOrigin("Tornoto");
                flightArrivalDTO.setDestination("London");


                flightDepartureDTO.setFlightArrivalTime(arrivalTime);
                flightDepartureDTO.setFlightDepatureTime(departureTime);
                flightDepartureDTO.setFlightName(" ");
                flightDepartureDTO.setFlightNumber(flight_number_depart_et.getText().toString().trim());
                flightDepartureDTO.setOrigin("London");
                flightDepartureDTO.setDestination("Tornoto");
                createOrderDTO.setArrivalFlight(flightArrivalDTO);
                createOrderDTO.setDestinationFlight(flightDepartureDTO);
                startActivity(new Intent(this, VehicleDetailsScreen.class).putExtra(AppConstants.CREATE_ORDER, createOrderDTO));

                break;

            case R.id.arrival_date_tv:

//                isDropDateClicked = false;
//                DatePickerDialog dialog = new DatePickerDialog(this,
//                        mDateSetListener, mYear, mMonth, mDay);
//                dialog.show();

                break;

            case R.id.arrival_time_tv:
//                isDropTimeClicked = false;
//                TimePickerDialog dialogArrivalTime;
//                dialogArrivalTime = new TimePickerDialog(this, mTimeSetListner, mHour, mMinute, true);
//                dialogArrivalTime.show();
                break;

            case R.id.depart_date_tv:
//                isDropDateClicked = true;
//                DatePickerDialog dialogDepartDate = new DatePickerDialog(this,
//                        mDateSetListener, mYear, mMonth, mDay);
//                dialogDepartDate.show();
                break;
            case R.id.depart_time_tv:
//                isDropTimeClicked = true;
//                TimePickerDialog dialogDepartTime;
//                dialogDepartTime = new TimePickerDialog(this, mTimeSetListner, mHour, mMinute, true);
//                dialogDepartTime.show();
                break;

            case R.id.depart_plane_icon:
                AppDialogs.flightDetailsDialog(this, manageInterface(), true);
                break;
            case R.id.arrival_plane_icon:
                AppDialogs.flightDetailsDialog(this, manageInterface(), false);

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
