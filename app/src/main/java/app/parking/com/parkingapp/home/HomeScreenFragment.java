package app.parking.com.parkingapp.home;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import app.parking.com.parkingapp.R;
import app.parking.com.parkingapp.model.CreateOrderDTO;
import app.parking.com.parkingapp.preferences.SessionManager;
import app.parking.com.parkingapp.utils.AppConstants;
import app.parking.com.parkingapp.utils.AppUtils;
import app.parking.com.parkingapp.utils.BaseFragment;

/**
 * A placeholder fragment containing a simple view.
 */
public class HomeScreenFragment extends BaseFragment {


    private View view;
    private Activity mActivity;
    private CreateOrderDTO createOrderDTO;
    private String TAG;
    private boolean isDropDateClicked = true;
    private boolean isDropTimeClicked = true;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        TAG = HomeScreenFragment.class.getSimpleName();
        view = inflater.inflate(R.layout.fragment_home_screen_new, container, false);
        mActivity = HomeScreenFragment.this.getActivity();
        return view;

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        createOrderDTO = new CreateOrderDTO();

        TextView toolbar_title = (TextView) mActivity.findViewById(R.id.toolbar_title);
        toolbar_title.setVisibility(View.VISIBLE);
        toolbar_title.setText(R.string.parkforu);

        showDateTimeAtFirst();

        // applying clicks on views.
        setClick(R.id.drop_date_rl, view);
        setClick(R.id.drop_time_rl, view);
        setClick(R.id.pick_date_rl, view);
        setClick(R.id.pick_time_rl, view);
        setClick(R.id.book_now_tv, view);
    }

    private void showDateTimeAtFirst() {
        // for drop date and time
        Date currentDate = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("MMM d, yyyy");
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");

        setViewText(R.id.drop_date_tv, dateFormat.format(currentDate), view);
        setViewText(R.id.drop_time_tv, timeFormat.format(currentDate), view);

        //for setting pick date and time, need to increase date by one.
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.DATE, 1);
        Date pickDate = calendar.getTime();

        setViewText(R.id.pick_date_tv, dateFormat.format(pickDate), view);
        setViewText(R.id.pick_time_tv, timeFormat.format(pickDate), view);

        getDateDifference();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.drop_date_rl:
                isDropDateClicked = true;
                openDatePicker();
                break;
            case R.id.pick_date_rl:
                isDropDateClicked = false;
                openDatePicker();
                break;
            case R.id.drop_time_rl:
                isDropTimeClicked = true;
                openTimePicker();
                break;
            case R.id.pick_time_rl:
                isDropTimeClicked = false;
                openTimePicker();
                break;
            case R.id.book_now_tv:
                bookNow();
                break;
        }
    }

    private void openDatePicker() {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("MMM d, yyyy");
        try {
            if (isDropDateClicked) {
                cal.setTime(sdf.parse(getViewText(R.id.drop_date_tv, view)));
            } else {
                cal.setTime(sdf.parse(getViewText(R.id.pick_date_tv, view)));
            }
            DatePickerDialog datePicker = new DatePickerDialog(mActivity,
                    dateListener, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH),
                    cal.get(Calendar.DAY_OF_MONTH));
            datePicker.show();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }


    private void openTimePicker() {
        Calendar calendar = Calendar.getInstance();
        TimePickerDialog timePicker = new TimePickerDialog(mActivity, timeListener,
                calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), true);
        timePicker.show();
    }


    TimePickerDialog.OnTimeSetListener timeListener = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker v, int hourOfDay, int minute) {
            String hours = hourOfDay < 10 ? "0" + hourOfDay : hourOfDay + "";
            String minutes = minute < 10 ? "0" + minute : minute + "";
            if (isDropTimeClicked) {
                setViewText(R.id.drop_time_tv, hours + ":" + minutes, view);
            } else {
                setViewText(R.id.pick_time_tv, hours + ":" + minutes, view);
            }
            getDateDifference();
        }
    };

    DatePickerDialog.OnDateSetListener dateListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker v, int year, int monthOfYear, int dayOfMonth) {
            try {
                SimpleDateFormat sdf = new SimpleDateFormat("MMM d, yyyy");
                String month = (monthOfYear + 1) < 10 ? "0" + (monthOfYear + 1) : monthOfYear + "";
                String dateString = month + "/" + dayOfMonth + "/" + year;
                Date date = new SimpleDateFormat("MM/dd/yyyy").parse(dateString);
                if (isDropDateClicked) {
                    setViewText(R.id.drop_date_tv, sdf.format(date), view);
                } else {
                    setViewText(R.id.pick_date_tv, sdf.format(date), view);
                }
                getDateDifference();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };


    private void bookNow() {
        SimpleDateFormat sdf = new SimpleDateFormat("MMM d, yyyy");
        Calendar dropCal = Calendar.getInstance();
        Calendar pickCal = Calendar.getInstance();
        try {
            String dropTime = getViewText(R.id.drop_time_tv, view);
            String pickTime = getViewText(R.id.pick_time_tv, view);

            Date dropDate = sdf.parse(getViewText(R.id.drop_date_tv, view));
            Date pickDate = sdf.parse(getViewText(R.id.pick_date_tv, view));

            Calendar c = Calendar.getInstance();

            //for getting drop off time stamp.
            c.setTime(dropDate);
            dropCal.set(c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH),
                    Integer.valueOf(dropTime.split(":")[0]), Integer.valueOf(dropTime.split(":")[1]));

            //for getting pickup time stamp
            c.setTime(pickDate);
            pickCal.set(c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH),
                    Integer.valueOf(pickTime.split(":")[0]), Integer.valueOf(pickTime.split(":")[1]));

            //date difference in days
            long difference = pickDate.getTime() - dropDate.getTime();

            long days = difference / (24 * 60 * 60 * 1000);

            AppUtils.showLog(TAG, difference + " " + days);

            if (days >= 1) {
                //start of getting pickTime
                String pickMonth = (pickCal.get(Calendar.MONTH) + 1) < 10
                        ? "0" + (pickCal.get(Calendar.MONTH) + 1)
                        : (pickCal.get(Calendar.MONTH) + 1) + "";
                String pickDay = pickCal.get(Calendar.DAY_OF_MONTH) < 10
                        ? "0" + pickCal.get(Calendar.DAY_OF_MONTH)
                        : pickCal.get(Calendar.DAY_OF_MONTH) + "";

                pickTime = pickCal.get(Calendar.YEAR) + "-" + pickMonth + "-" + pickDay
                        + " " + pickTime.split(":")[0] + ":" + pickTime.split(":")[1] + ":00";
                pickTime = AppUtils.convertoUTCFormat(AppUtils.convertInUTCDate(pickTime));
                //end of getting pickTime

                //start of getting dropTime
                String dropMonth = (dropCal.get(Calendar.MONTH) + 1) < 10
                        ? "0" + (dropCal.get(Calendar.MONTH) + 1)
                        : (dropCal.get(Calendar.MONTH) + 1) + "";
                String dropDay = dropCal.get(Calendar.DAY_OF_MONTH) < 10
                        ? "0" + dropCal.get(Calendar.DAY_OF_MONTH)
                        : dropCal.get(Calendar.DAY_OF_MONTH) + "";

                dropTime = dropCal.get(Calendar.YEAR) + "-" + dropMonth + "-" + dropDay + " "
                        + dropTime.split(":")[0] + ":" + dropTime.split(":")[1] + ":00";
                dropTime = AppUtils.convertoUTCFormat(AppUtils.convertInUTCDate(dropTime));
                //end of getting dropTime

                createOrderDTO.setDropOffTime(dropTime);
                createOrderDTO.setPickUpTime(pickTime);
                AppUtils.showLog(TAG, pickTime + " droptime: " + dropTime);
                createOrderDTO.setUserEmail(SessionManager.getInstance(mActivity).getEmail());
                createOrderDTO.setVenueName("Vancouver");
                Intent intent = new Intent(getContext(), FlightDetailsScreen.class);
                intent.putExtra(AppConstants.CREATE_ORDER, createOrderDTO);
                startActivity(intent);
            } else {
                Snackbar.make(view, "Order should be for atleast one day",
                        Snackbar.LENGTH_LONG).show();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void getDateDifference() {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("MMM d, yyyy HH:mm");
            String dropDateTime = getViewText(R.id.drop_date_tv, view) + " "
                    + getViewText(R.id.drop_time_tv, view);
            String pickDateTime = getViewText(R.id.pick_date_tv, view) + " "
                    + getViewText(R.id.pick_time_tv, view);

            Date dropDate = sdf.parse(dropDateTime);
            Date pickDate = sdf.parse(pickDateTime);

            long diff = pickDate.getTime() - dropDate.getTime();

            long diffInDays = diff / (24 * 60 * 60 * 1000);
            long diffInHours = diff / (60 * 60 * 1000) % 24;

            setViewText(R.id.days, diffInDays + " day(s)", view);
            setViewText(R.id.hours, diffInHours + " hour(s)", view);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
