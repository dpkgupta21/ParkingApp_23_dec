package app.parking.com.parkingapp.home;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

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

    private final static String TAG = FlightDetailsScreen.class.getSimpleName();
    private Toolbar mToolbar;
    private CreateOrderDTO createOrderDTO;
    private FlightArrivalDTO flightArrivalDTO;
    private FlightDepartureDTO flightDepartureDTO;
    private FlightDetailInterface flightDetailInterface;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.flight_details_screen);
        initViews();
        assignClicks();

        manageInterface();


        if (getIntent() != null) {
            createOrderDTO = (CreateOrderDTO) getIntent().
                    getSerializableExtra(AppConstants.CREATE_ORDER);
            AppUtils.showLog(TAG, createOrderDTO.getPickUpTime() + " " + createOrderDTO.getDropOffTime());
        } else {
            createOrderDTO = new CreateOrderDTO();
        }
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
                setViewText(R.id.txt_arrival_flight_destination, AppConstants.COUNTRY_FOR_SERVICE);
                setViewText(R.id.arrival_date_tv, HelpMe.getDisplayDate(arrivalTime));
                setViewText(R.id.arrival_time_tv, HelpMe.getDisplayTime(arrivalTime));
                setViewText(R.id.flight_num_et, flightNumber);
                setViewText(R.id.pickup_term, term);

                flightArrivalDTO = new FlightArrivalDTO();
                flightArrivalDTO.setFlightArrivalTime(arrivalTime);
                flightArrivalDTO.setFlightDepatureTime(departTime);
                flightArrivalDTO.setFlightName(airline);
                flightArrivalDTO.setFlightNumber(flightNumber);
                flightArrivalDTO.setOrigin(from);
                flightArrivalDTO.setDestination(AppConstants.COUNTRY_FOR_SERVICE);

            }

            @Override
            public void onDepartureDetailsSelected(String airline,
                                                   String from, String status,
                                                   String flightNumber, String term,
                                                   String departTime, String arrivalTime) {


                setViewText(R.id.depart_time_tv, HelpMe.getDisplayTime(departTime));
                setViewText(R.id.depart_date_tv, HelpMe.getDisplayDate(departTime));
                setViewText(R.id.txt_departure_flight_origin_val, AppConstants.COUNTRY_FOR_SERVICE);
                setViewText(R.id.txt_departure_flight_destination_val, from);
                setViewText(R.id.flight_number_depart_et, flightNumber);
                setViewText(R.id.drop_term, term);

                flightDepartureDTO = new FlightDepartureDTO();
                flightDepartureDTO.setFlightArrivalTime(arrivalTime);
                flightDepartureDTO.setFlightDepatureTime(departTime);
                flightDepartureDTO.setFlightName(airline);
                flightDepartureDTO.setFlightNumber(flightNumber);
                flightDepartureDTO.setOrigin(AppConstants.COUNTRY_FOR_SERVICE);
                flightDepartureDTO.setDestination(from);


            }
        };
        return flightDetailInterface;
    }

    private void assignClicks() {
        setClick(R.id.arrival_plane_icon);
        setClick(R.id.depart_plane_icon);
        setClick(R.id.toolbar_right_rl);
    }

    private void initViews() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        mToolbar.setNavigationIcon(R.drawable.back_button);
        setViewVisibility(R.id.toolbar_title, View.VISIBLE);
        setViewText(R.id.toolbar_title, getResources().getString(R.string.parkforu));

        setViewVisibility(R.id.toolbar_right_rl, View.VISIBLE);

        setViewVisibility(R.id.toolbar_right_tv, View.VISIBLE);
        setViewText(R.id.toolbar_right_tv, getResources().getString(R.string.next));


    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.toolbar_right_rl:

                if (flightArrivalDTO != null && flightDepartureDTO != null) {
                    createOrderDTO.setArrivalFlight(flightArrivalDTO);
                    createOrderDTO.setDestinationFlight(flightDepartureDTO);

                    startActivity(new Intent(this, VehicleDetailsScreen.class).
                            putExtra(AppConstants.CREATE_ORDER, createOrderDTO));
                }
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


}
