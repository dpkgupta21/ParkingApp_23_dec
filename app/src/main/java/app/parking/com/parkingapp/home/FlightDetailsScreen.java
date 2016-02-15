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
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.Calendar;

import app.parking.com.parkingapp.R;

public class FlightDetailsScreen extends AppCompatActivity implements View.OnClickListener {
    Toolbar mToolbar;
    TextView toolbar_title, arrival_date_tv, arrival_time_tv, depart_time_tv, depart_date_tv;
    RelativeLayout next_button;
    private boolean isDropDateClicked = true, isDropTimeClicked = true;
    private Calendar calendar;
    private int mYear;
    private int mMonth, mDay, mHour, mMinute;

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

        calendar = Calendar.getInstance();
        mYear = calendar.get(Calendar.YEAR);
        mMonth = calendar.get(Calendar.MONTH);
        mDay = calendar.get(Calendar.DAY_OF_MONTH);
        mHour = calendar.get(Calendar.HOUR_OF_DAY);
        mMinute = calendar.get(Calendar.MINUTE);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.next_button:
                startActivity(new Intent(this, VehicleDetailsScreen.class));

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
                arrival_date_tv.setText(new StringBuilder()
                        // Month is 0 based so add 1
                        .append(mMonth + 1).append("/ ").append(mDay).append("/ ")
                        .append(mYear).append(" "));
            } else {
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
                depart_time_tv.setText(hour + ":" + min);
            } else {
                arrival_time_tv.setText(hour + ":" + min);
            }

        }
    };

}
