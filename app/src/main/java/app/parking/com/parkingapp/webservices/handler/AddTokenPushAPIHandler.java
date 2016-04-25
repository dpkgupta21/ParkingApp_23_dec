package app.parking.com.parkingapp.webservices.handler;

import android.app.Activity;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request.Method;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import app.parking.com.parkingapp.application.ParkingAppController;
import app.parking.com.parkingapp.iClasses.GlobalKeys;
import app.parking.com.parkingapp.preferences.SessionManager;
import app.parking.com.parkingapp.utils.AppConstants;
import app.parking.com.parkingapp.utils.AppUtils;
import app.parking.com.parkingapp.webservices.control.WebserviceAPIErrorHandler;
import app.parking.com.parkingapp.webservices.ihelper.WebAPIResponseListener;

/**
 * Pillion Login API Handler
 */
public class AddTokenPushAPIHandler {
    /**
     * Instance object of Login API
     */
    private Activity mActivity;
    /**
     * Debug TAG
     */
    private String TAG = AddTokenPushAPIHandler.class.getSimpleName();
    /**
     * Request Data
     */
    private String regToken, deviceId, deviceType, email, authToken;

    private String device_token, device_platform;
    /**
     * API Response Listener
     */
    private WebAPIResponseListener mResponseListener;

    /**
     * @param mActivity
     * @param deviceId
     * @param regToken
     * @param webAPIResponseListener
     */
    public AddTokenPushAPIHandler(Activity mActivity, String regToken,
                                  String deviceId, String deviceType, String email,
                                  String authToken, WebAPIResponseListener webAPIResponseListener) {
        AppUtils
                .showProgressDialog(mActivity, "Login...", false);
        this.mActivity = mActivity;
        this.regToken = regToken;
        this.deviceId = deviceId;
        this.deviceType = deviceType;
        this.email = email;
        this.authToken = authToken;
        this.mResponseListener = webAPIResponseListener;
        postAPICall();

    }

    /**
     * Making json object request
     */
    public void postAPICall() {
        /**
         * String Request
         */

        JSONObject mJsonObjectRequest = new JSONObject();
        try {

            mJsonObjectRequest.put(GlobalKeys.REG_TOKEN, regToken);
            mJsonObjectRequest.put(GlobalKeys.DEVICE_ID, deviceId);
            mJsonObjectRequest.put(GlobalKeys.DEVICE_TYPE, deviceType);
            mJsonObjectRequest.put(GlobalKeys.EMAIL_KEY, email);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest mJsonRequest = new JsonObjectRequest(
                Method.POST,
                (AppConstants.APP_WEBSERVICE_API_URL + GlobalKeys.ADD_TOKEN_PUSH)
                        .trim(), mJsonObjectRequest,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        AppUtils.showInfoLog(TAG, "Response :"
                                + response);

                        parseLoginAPIResponse(response.toString());
                        mResponseListener.onSuccessOfResponse(response);
                        AppUtils.hideProgressDialog();

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                WebserviceAPIErrorHandler.getInstance()
                        .VolleyErrorHandler(error, mActivity);
                AppUtils.hideProgressDialog();
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
                params.put(GlobalKeys.AUTHTOKEN, authToken);
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

    /**
     * Parse LoginAPI Response
     *
     * @param response
     */
    protected void parseLoginAPIResponse(String response) {

    }
}
