package app.parking.com.parkingapp.webservices.handler;

import android.app.Activity;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import app.parking.com.parkingapp.application.ParkingAppController;
import app.parking.com.parkingapp.customViews.CustomProgressDialog;
import app.parking.com.parkingapp.model.ListOfServicesDTO;
import app.parking.com.parkingapp.iClasses.GlobalKeys;
import app.parking.com.parkingapp.utils.AppConstants;
import app.parking.com.parkingapp.utils.AppUtils;
import app.parking.com.parkingapp.webservices.control.WebserviceAPIErrorHandler;
import app.parking.com.parkingapp.webservices.ihelper.WebAPIResponseListener;

/**
 * Services available for a place API Handler
 */
public class ServicesAPIHandler {
    /**
     * Instance object of ServicesAPIHandler API
     */
    private Activity mActivity;
    /**
     * Debug TAG
     */
    private String TAG = ServicesAPIHandler.class.getSimpleName();
    /**
     * Request Data
     */
    private String venueName;
    private String auth_token;
    private String userId;


    private ArrayList<ListOfServicesDTO> listOfServicesDTOs;

    /**
     * API Response Listener
     */
    private WebAPIResponseListener mResponseListener;

    /**
     * @param mActivity
     * @param webAPIResponseListener
     */
    public ServicesAPIHandler(Activity mActivity, String auth_token,
                              String userId,
                              WebAPIResponseListener webAPIResponseListener) {

        this.mActivity = mActivity;
        this.auth_token = auth_token;
        this.userId = userId;
        this.mResponseListener = webAPIResponseListener;
        listOfServicesDTOs = new ArrayList<>();
        postAPICall();

    }

    /**
     * Making json object request
     */
    public void postAPICall() {
        /**
         * String Request
         */

        CustomProgressDialog.showProgDialog(mActivity,null);
        JSONObject mJsonObjectRequest = new JSONObject();

        JsonArrayRequest mJsonArrayRequest = new JsonArrayRequest(
                (AppConstants.APP_WEBSERVICE_API_URL + GlobalKeys.FETCH_SERVICES + "Vancouver")
                .trim(), new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                AppUtils.showInfoLog(TAG, "Response :"
                        + response);
                CustomProgressDialog.hideProgressDialog();
                mResponseListener.onSuccessOfResponse(response.toString());

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                WebserviceAPIErrorHandler.getInstance()
                        .VolleyErrorHandler(error, mActivity);
                CustomProgressDialog.hideProgressDialog();
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
                params.put(GlobalKeys.ACCEPT_KEY_CONTENT_TYPE,
                        GlobalKeys.HEADER_VALUE_CONTENT_TYPE);
                params.put(GlobalKeys.AUTHTOKEN, auth_token);
                params.put(GlobalKeys.USERID, userId);

                return params;
            }

        };
        // Adding request to request queue
        if (ParkingAppController.getInstance() != null) {
            ParkingAppController.getInstance().addToRequestQueue(
                    mJsonArrayRequest, GlobalKeys.FETCH_SERVICES_REQUEST_KEY);
        }
        // set request time-out
        mJsonArrayRequest.setRetryPolicy(new DefaultRetryPolicy(
                AppConstants.ONE_SECOND * 20, 0,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
    }


}
