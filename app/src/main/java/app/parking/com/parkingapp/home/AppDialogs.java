package app.parking.com.parkingapp.home;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import app.parking.com.parkingapp.R;
import app.parking.com.parkingapp.customViews.CustomProgressDialog;
import app.parking.com.parkingapp.iClasses.FlightDetailInterface;
import app.parking.com.parkingapp.model.CreateOrderResponseDTO;
import app.parking.com.parkingapp.model.FlightDetailsDTO;
import app.parking.com.parkingapp.navigationDrawer.UserNavigationDrawerActivity;
import app.parking.com.parkingapp.preferences.ParkingPreference;
import app.parking.com.parkingapp.utils.AppConstants;
import app.parking.com.parkingapp.utils.AppUtils;
import app.parking.com.parkingapp.utils.HelpMe;
import app.parking.com.parkingapp.webservices.handler.FlightDetailsAPIHandler;
import app.parking.com.parkingapp.webservices.ihelper.WebAPIResponseListener;

/**
 * Created by Harish on 12/19/2015.
 */
public class AppDialogs {
    private static final String TAG = AppDialogs.class.getSimpleName();
    /**
     * select country code dialog
     */

    private static Dialog mModelDialog;
    private static ArrayList<FlightDetailsDTO> flightDetailsDTOList = null;

    public static void selectCarMake(final Activity mActivity,
                                     final TextView textView) {

        try {
            if (mModelDialog != null && mModelDialog.isShowing()) {
                mModelDialog.dismiss();
            }
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }

        if (mActivity != null) {
            mModelDialog = new Dialog(mActivity);
            // hide to default title for Dialog
            mModelDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

            // inflate the layout dialog_layout.xml and set it as
            // contentView
            LayoutInflater inflater = (LayoutInflater) mActivity
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = inflater.inflate(R.layout.listview_dialog, null,
                    false);
            mModelDialog.setCanceledOnTouchOutside(false);
            mModelDialog.setContentView(view);
            mModelDialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
            WindowManager.LayoutParams lp = mModelDialog.getWindow().getAttributes();
            lp.dimAmount = 0.8f;
            mModelDialog.getWindow().setAttributes(lp);


            ListView car_make_list = (ListView) mModelDialog
                    .findViewById(R.id.listview);
            final CarMakeAdapter adapter = new CarMakeAdapter(
                    mActivity, textView, mModelDialog);
            car_make_list.setAdapter(adapter);

            try {
                // Display the dialog
                mModelDialog.show();
            } catch (WindowManager.BadTokenException e) {
                // TODO: handle exception
                e.printStackTrace();
            } catch (Exception e) {
                // TODO: handle exception
                e.printStackTrace();
            }
        }

    }


    public static void selectCarColor(final Activity mActivity,
                                      final TextView textView) {
        try {
            if (mModelDialog != null && mModelDialog.isShowing()) {
                mModelDialog.dismiss();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (mActivity != null) {
            mModelDialog = new Dialog(mActivity);
            // hide to default title for Dialog
            mModelDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

            // inflate the layout dialog_layout.xml and set it as
            // contentView
            LayoutInflater inflater = (LayoutInflater) mActivity
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = inflater.inflate(R.layout.listview_dialog, null,
                    false);
            mModelDialog.setCanceledOnTouchOutside(false);
            mModelDialog.setContentView(view);
            mModelDialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
            WindowManager.LayoutParams lp = mModelDialog.getWindow().getAttributes();
            lp.dimAmount = 0.8f;
            mModelDialog.getWindow().setAttributes(lp);


            ListView car_make_list = (ListView) mModelDialog
                    .findViewById(R.id.listview);
            final CarColorAdapter adapter = new CarColorAdapter(
                    mActivity, textView, mModelDialog);
            car_make_list.setAdapter(adapter);

            try {
                // Display the dialog
                mModelDialog.show();
            } catch (WindowManager.BadTokenException e) {
                // TODO: handle exception
                e.printStackTrace();
            } catch (Exception e) {
                // TODO: handle exception
                e.printStackTrace();
            }
        }

    }

    public static void selectCarModel(final Activity mActivity,
                                      final TextView textView) {

        try {
            if (mModelDialog != null && mModelDialog.isShowing()) {
                mModelDialog.dismiss();
            }
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }

        if (mActivity != null) {
            mModelDialog = new Dialog(mActivity);
            // hide to default title for Dialog
            mModelDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

            // inflate the layout dialog_layout.xml and set it as
            // contentView
            LayoutInflater inflater = (LayoutInflater) mActivity
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = inflater.inflate(R.layout.listview_dialog, null,
                    false);
            mModelDialog.setCanceledOnTouchOutside(false);
            mModelDialog.setContentView(view);
            mModelDialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
            WindowManager.LayoutParams lp = mModelDialog.getWindow().getAttributes();
            lp.dimAmount = 0.8f;
            mModelDialog.getWindow().setAttributes(lp);


            ListView car_make_list = (ListView) mModelDialog
                    .findViewById(R.id.listview);
            final CarModelAdapter adapter = new CarModelAdapter(
                    mActivity, textView, mModelDialog);
            car_make_list.setAdapter(adapter);

            try {
                // Display the dialog
                mModelDialog.show();
            } catch (WindowManager.BadTokenException e) {
                // TODO: handle exception
                e.printStackTrace();
            } catch (Exception e) {
                // TODO: handle exception
                e.printStackTrace();
            }
        }

    }


    public static void flightDetailsDialog(final Activity mActivity,
                                           final FlightDetailInterface flightDetailInterface,
                                           final boolean isDepartSelected) {

        try {
            if (mModelDialog != null && mModelDialog.isShowing()) {
                mModelDialog.dismiss();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (mActivity != null) {
            mModelDialog = new Dialog(mActivity);
            // hide to default title for Dialog
            mModelDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

            // inflate the layout dialog_layout.xml and set it as
            // contentView
            LayoutInflater inflater = (LayoutInflater) mActivity
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = inflater.inflate(R.layout.flight_details_dialog, null,
                    false);
            mModelDialog.setCanceledOnTouchOutside(false);
            mModelDialog.setContentView(view);
            mModelDialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
            WindowManager.LayoutParams lp = mModelDialog.getWindow().getAttributes();
            lp.dimAmount = 0.8f;
            mModelDialog.getWindow().setAttributes(lp);


            //final ArrayList<FlightDetailsDTO> flightDetailsDTOList = new ArrayList<FlightDetailsDTO>();
            final ListView flightDetails = (ListView) mModelDialog
                    .findViewById(R.id.listview);
            TextView search_btn = (TextView) mModelDialog.findViewById(R.id.search_btn);
            final EditText edtSearch = (EditText) mModelDialog.findViewById(R.id.edt_search);
            search_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    if (!edtSearch.getText().toString().isEmpty()) {

                        CustomProgressDialog.showProgDialog(mActivity, "Searching...");
                        new FlightDetailsAPIHandler(mActivity,
                                edtSearch.getText().toString().trim(),
                                edtSearch.getText().toString().trim(),
                                ParkingPreference.getKeyAuthtoken(mActivity), new WebAPIResponseListener() {
                            @Override
                            public void onSuccessOfResponse(Object... arguments) {


                                String response = arguments[0].toString();
                                JSONObject responseObject = null;
                                try {
                                    responseObject = new JSONObject(response);

                                    Type type = new TypeToken<List<FlightDetailsDTO>>() {
                                    }.getType();


                                    flightDetailsDTOList = new Gson().
                                            fromJson(responseObject.getJSONArray("flightResponse").toString(), type);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                CustomProgressDialog.hideProgressDialog();
                                AppUtils.showLog(TAG, response);
                                if (flightDetailsDTOList.size() > 0) {

                                    flightDetails.setVisibility(View.VISIBLE);

                                    FlightDetailsAdapter adapter = new FlightDetailsAdapter(mActivity,
                                            flightDetailsDTOList, mModelDialog);
                                    flightDetails.setAdapter(adapter);
                                    adapter.notifyDataSetChanged();


                                    ((TextView) mModelDialog.findViewById(R.id.txt_no_found)).
                                            setVisibility(View.GONE);

                                } else {
                                    ((TextView) mModelDialog.findViewById(R.id.txt_no_found)).
                                            setVisibility(View.VISIBLE);
                                    flightDetails.setVisibility(View.GONE);
                                }

                            }

                            @Override
                            public void onFailOfResponse(Object... arguments) {
                                CustomProgressDialog.hideProgressDialog();
                                AppUtils.showDialog(mActivity, "Alert!",
                                        "Search field cannot be empty");
                            }
                        });
                    } else {
                        AppUtils.showDialog(mActivity, "Alert!",
                                "Search field cannot be empty");
                    }
                }
            });


            flightDetails.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    AppUtils.showLog(TAG, position + "");
                    if (isDepartSelected) {
                        flightDetailInterface.onDepartureDetailsSelected(
                                flightDetailsDTOList.get(position).getAirline(),
                                flightDetailsDTOList.get(position).getDestination(),
                                flightDetailsDTOList.get(position).getStatus(),
                                flightDetailsDTOList.get(position).getFlightNo(),
                                flightDetailsDTOList.get(position).getTerm(),
                                flightDetailsDTOList.get(position).getDeprtTime(),
                                flightDetailsDTOList.get(position).getArrivalTime());
                    } else {
                        flightDetailInterface.onArrivalDetailsSelected(
                                flightDetailsDTOList.get(position).getAirline(),
                                flightDetailsDTOList.get(position).getDestination(),
                                flightDetailsDTOList.get(position).getStatus(),
                                flightDetailsDTOList.get(position).getFlightNo(),
                                flightDetailsDTOList.get(position).getTerm(),
                                flightDetailsDTOList.get(position).getDeprtTime(),
                                flightDetailsDTOList.get(position).getArrivalTime());
                    }
                    if (mModelDialog != null) {
                        mModelDialog.dismiss();
                        mModelDialog = null;
                    }

                }
            });

            try {
                // Display the dialog
                mModelDialog.show();
            } catch (WindowManager.BadTokenException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }


    public static void paymentDialog(final Activity mActivity,
                                     final CreateOrderResponseDTO mCreateOrderResponseDTO) {

        try {
            if (mModelDialog != null && mModelDialog.isShowing()) {
                mModelDialog.dismiss();
                mModelDialog = null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (mActivity != null) {
            mModelDialog = new Dialog(mActivity);
            // hide to default title for Dialog
            mModelDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

            // inflate the layout dialog_layout.xml and set it as
            // contentView
            LayoutInflater inflater = (LayoutInflater) mActivity
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = inflater.inflate(R.layout.dialog_payment_confirmation, null,
                    false);
            mModelDialog.setCanceledOnTouchOutside(false);
            mModelDialog.setContentView(view);
            mModelDialog.setCancelable(false);
            mModelDialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
            WindowManager.LayoutParams lp = mModelDialog.getWindow().getAttributes();
            lp.dimAmount = 0.8f;
            mModelDialog.getWindow().setAttributes(lp);


            ImageView cancel_btn = (ImageView) mModelDialog.findViewById(R.id.cancel_btn);
            TextView txt_order_number_val = (TextView) mModelDialog.
                    findViewById(R.id.txt_order_number_val);
            TextView txt_slot_number_val = (TextView) mModelDialog.
                    findViewById(R.id.txt_slot_number_val);
            TextView txt_duration_val = (TextView) mModelDialog.
                    findViewById(R.id.txt_duration_val);
            TextView txt_amount_val = (TextView) mModelDialog.
                    findViewById(R.id.txt_amount_val);


            txt_order_number_val.setText(mCreateOrderResponseDTO.getOrderStatus().getOrder_id());
            txt_slot_number_val.setText(mCreateOrderResponseDTO.getOrderConfirmation().getSlotId());
            txt_duration_val.setText(HelpMe.getDurationTime(
                    mCreateOrderResponseDTO.getFlightInfo().
                            getDestinationFlight().getFlightDepatureTime(),
                    mCreateOrderResponseDTO.getFlightInfo().
                            getArrivalFlight().getFlightArrivalTime()));
            txt_amount_val.setText(mCreateOrderResponseDTO.getOrderStatus().getOrderTotal());

            Button okButton = (Button) mModelDialog.findViewById(R.id.okButton);

            cancel_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mActivity,
                            UserNavigationDrawerActivity.class);
                    mActivity.startActivity(intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));

                }
            });
            okButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    Intent intent = new Intent(mActivity,
                            CreditCardScreen.class);

                    intent.putExtra(AppConstants.ORDER_SUMMARY_KEY, mCreateOrderResponseDTO);
                    mActivity.startActivity(intent);

                }
            });

            try {
                // Display the dialog
                mModelDialog.show();
            } catch (WindowManager.BadTokenException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }


}
