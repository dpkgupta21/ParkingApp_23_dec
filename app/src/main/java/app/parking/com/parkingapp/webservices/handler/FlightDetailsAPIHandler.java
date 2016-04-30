package app.parking.com.parkingapp.webservices.handler;

import android.app.Activity;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import app.parking.com.parkingapp.application.ParkingAppController;
import app.parking.com.parkingapp.iClasses.GlobalKeys;
import app.parking.com.parkingapp.model.FlightDetailsDTO;
import app.parking.com.parkingapp.model.ListOfServicesDTO;
import app.parking.com.parkingapp.utils.AppConstants;
import app.parking.com.parkingapp.utils.AppUtils;
import app.parking.com.parkingapp.webservices.control.WebserviceAPIErrorHandler;
import app.parking.com.parkingapp.webservices.ihelper.WebAPIResponseListener;

/**
 * Services available for a place API Handler
 */
public class FlightDetailsAPIHandler {
    /**
     * Instance object of ServicesAPIHandler API
     */
    private Activity mActivity;
    /**
     * Debug TAG
     */
    private String TAG = FlightDetailsAPIHandler.class.getSimpleName();
    /**
     * Request Data
     */
    private String flightName, flightNo;
    private String auth_token;

    private ArrayList<FlightDetailsDTO> flightDetailsDTOArrayList;

    /**
     * API Response Listener
     */
    private WebAPIResponseListener mResponseListener;

    /**
     * @param mActivity
     * @param webAPIResponseListener
     */
    public FlightDetailsAPIHandler(Activity mActivity, String flightNo, String flightName, String auth_token, WebAPIResponseListener webAPIResponseListener) {

        this.mActivity = mActivity;
        this.auth_token = auth_token;
        this.flightName = flightName;
        this.flightNo = flightNo;
        this.mResponseListener = webAPIResponseListener;
        flightDetailsDTOArrayList = new ArrayList<>();
        postAPICall();

    }

    /**
     * Making json object request
     */
    public void postAPICall() {

        JSONObject mJsonObjectRequest = new JSONObject();
        try {
            mJsonObjectRequest.put(GlobalKeys.flightName, flightName);
            mJsonObjectRequest.put(GlobalKeys.flightNo, flightNo);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest mJsonRequest = new JsonObjectRequest(
                Request.Method.POST,
                (AppConstants.APP_WEBSERVICE_API_URL + GlobalKeys.FLIGHT_INFO)
                        .trim(), mJsonObjectRequest,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        AppUtils.showInfoLog(TAG, "Response :"
                                + response);

                        mResponseListener.onSuccessOfResponse(response);


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                WebserviceAPIErrorHandler.getInstance()
                        .VolleyErrorHandler(error, mActivity);
                mResponseListener.onFailOfResponse(error);
            }
        }) {
            /*
             * /* (non-Javadoc)
             *
             * @see com.android.volley.Request#getHeaders()
             */
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put(GlobalKeys.HEADER_KEY_CONTENT_TYPE,
                        GlobalKeys.HEADER_VALUE_CONTENT_TYPE);
                return params;
            }

        };
        // Adding request to request queue
        if (ParkingAppController.getInstance() != null) {
            ParkingAppController.getInstance().addToRequestQueue(
                    mJsonRequest, GlobalKeys.PILLION_LOGIN_REQUEST_KEY);
        }
        // set request time-out
        mJsonRequest.setRetryPolicy(new DefaultRetryPolicy(
                AppConstants.ONE_SECOND * 20, 0,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
    }


}
