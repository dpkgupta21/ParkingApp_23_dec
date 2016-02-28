package app.parking.com.parkingapp.home;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.Calendar;

import app.parking.com.parkingapp.R;
import app.parking.com.parkingapp.model.CreateOrderDTO;
import app.parking.com.parkingapp.navigationDrawer.UserNavigationDrawerActivity;
import app.parking.com.parkingapp.utils.AppConstants;
import app.parking.com.parkingapp.utils.AppUtils;

/**
 * A placeholder fragment containing a simple view.
 */
public class HomeScreenFragment extends Fragment {


    private View view;

    private Activity mActivity;

    private CreateOrderDTO createOrderDTO;
    private RelativeLayout pick_time_rl, pick_date_rl, drop_date_rl, drop_time_rl, book_now_button;

    private TextView pick_date_tv, drop_date_tv, drop_time_tv, pick_time_tv;
    private static final int DROP_DATE_DIALOG = 121, PICK_DATE_DIALOG = 122;
    private boolean isDropDateClicked = true, isDropTimeClicked = true;
    private Toolbar mToolbar;

    private Calendar calendar;
    private int mYear, mDropYear, mPickupYear;
    private int mMonth, mDropMonth, mPickupMonth;
    private int mDay, mDropDay, mPickupDay;
    private int mHour, mDropHour, mPickupHour;
    private int mMinute, mDropMinute, mPickupMinute;
    private String dropTime, pickTime;
    private String TAG;

    public HomeScreenFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        TAG = HomeScreenFragment.class.getSimpleName();
        view = inflater.inflate(R.layout.fragment_home_screen, container, false);
        return view;

    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


        mActivity = UserNavigationDrawerActivity.mActivity;
        createOrderDTO = new CreateOrderDTO();
        pick_time_rl = (RelativeLayout) view.findViewById(R.id.pick_time_rl);
        pick_date_rl = (RelativeLayout) view.findViewById(R.id.pick_date_rl);
        drop_date_rl = (RelativeLayout) view.findViewById(R.id.drop_date_rl);
        drop_time_rl = (RelativeLayout) view.findViewById(R.id.drop_time_rl);
        book_now_button = (RelativeLayout) view.findViewById(R.id.book_now_button);
        pick_date_tv = (TextView) view.findViewById(R.id.pick_date_tv);
        drop_date_tv = (TextView) view.findViewById(R.id.drop_date_tv);
        drop_time_tv = (TextView) view.findViewById(R.id.drop_time_tv);
        pick_time_tv = (TextView) view.findViewById(R.id.pick_time_tv);


        calendar = Calendar.getInstance();
        mYear = calendar.get(Calendar.YEAR);
        mMonth = calendar.get(Calendar.MONTH);
        mDay = calendar.get(Calendar.DAY_OF_MONTH);
        mHour = calendar.get(Calendar.HOUR_OF_DAY);
        mMinute = calendar.get(Calendar.MINUTE);

        mDropYear = mPickupYear = mYear;
        mDropMonth = mPickupMonth = mMonth;
        mDropDay = mPickupDay = mDay;
        mDropHour = mPickupHour = mHour;
        mDropMinute = mPickupMinute = mMinute;


        pick_date_rl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                getActivity().showDialog(PICK_DATE_DIALOG);
                isDropDateClicked = false;
                DatePickerDialog dialog = new DatePickerDialog(getContext(),
                        mDateSetListener, mYear, mMonth, mDay);
                dialog.show();
            }
        });

        pick_time_rl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isDropTimeClicked = false;
                TimePickerDialog dialog;
                dialog = new TimePickerDialog(getContext(), mTimeSetListner, mHour, mMinute, true);
                dialog.show();
            }
        });

        drop_date_rl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isDropDateClicked = true;
                DatePickerDialog dialog = new DatePickerDialog(getContext(),
                        mDateSetListener, mYear, mMonth, mDay);
                dialog.show();


            }
        });

        drop_time_rl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isDropTimeClicked = true;
                TimePickerDialog dialog;
                dialog = new TimePickerDialog(getContext(), mTimeSetListner, mHour, mMinute, true);
                dialog.show();
            }
        });


        book_now_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                "dropOffTime": "2016-02-14T08:11:32.244Z",
//                        "pickUpTime": "2016-02-17T08:11:32.244Z",
                pickTime = mPickupYear + "-" + mPickupMonth + "-" + mPickupDay + "T" + mPickupHour + ":" + mPickupMinute;
                dropTime = mDropYear + "-" + mDropMonth + "-" + mDropDay + "T" + mDropHour + ":" + mDropMinute;

                createOrderDTO.setDropOffTime(dropTime);
                createOrderDTO.setPickUpTime(pickTime);
                AppUtils.showLog(TAG, pickTime + " droptime: " + dropTime);
                Intent intent = new Intent(getContext(), FlightDetailsScreen.class);
                intent.putExtra(AppConstants.CREATE_ORDER, createOrderDTO);
                startActivity(intent);
            }
        });

    }

    private DatePickerDialog.OnDateSetListener mDateSetListener = new DatePickerDialog.OnDateSetListener() {

        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            int mYear = year;
            int mMonth = monthOfYear;
            int mDay = dayOfMonth;
            if (!isDropDateClicked) {

                mPickupDay = mDay;
                mPickupMonth = mMonth + 1;
                mPickupYear = mYear;


                pick_date_tv.setText(new StringBuilder()
                        // Month is 0 based so add 1
                        .append(mMonth + 1).append("/ ").append(mDay).append("/ ")
                        .append(mYear).append(" "));
            } else {


                mDropDay = mDay;
                mDropMonth = mMonth + 1;
                mDropYear = mYear;
                drop_date_tv.setText(new StringBuilder()
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
                mDropHour = hour;
                mDropMinute = min;

                drop_time_tv.setText(hour + ":" + min);
            } else {
                mPickupMinute = min;
                mPickupHour = hour;
                pick_time_tv.setText(hour + ":" + min);
            }

        }
    };
}
