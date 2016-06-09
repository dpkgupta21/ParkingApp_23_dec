package app.parking.com.parkingapp.webservices.handler;

import android.app.Activity;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request.Method;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import app.parking.com.parkingapp.application.ParkingAppController;
import app.parking.com.parkingapp.iClasses.GlobalKeys;
import app.parking.com.parkingapp.preferences.ParkingPreference;
import app.parking.com.parkingapp.utils.AppConstants;
import app.parking.com.parkingapp.utils.AppUtils;
import app.parking.com.parkingapp.webservices.control.WebserviceAPIErrorHandler;
import app.parking.com.parkingapp.webservices.ihelper.WebAPIResponseListener;

/**
 * Pillion Logout API Handler
 */
public class LogoutAPIHandler {
    /**
     * Instance object of Logout API
     */
    private Activity mActivity;
    /**
     * Debug TAG
     */
    private String TAG = LogoutAPIHandler.class.getSimpleName();
    /**
     * Request Data
     */

    /**
     * API Response Listener
     */
    private WebAPIResponseListener mResponseListener;

    /**
     * @param mActivity
     * @param webAPIResponseListener
     */
    public LogoutAPIHandler(Activity mActivity,
                            WebAPIResponseListener webAPIResponseListener) {

        this.mActivity = mActivity;
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
            mJsonObjectRequest.put(GlobalKeys.EMAIL, ParkingPreference.getEmailId(mActivity));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest mJsonRequest = new JsonObjectRequest(
                Method.POST,
                (AppConstants.APP_WEBSERVICE_API_URL + GlobalKeys.LOGOUT_API)
                        .trim(), mJsonObjectRequest,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        AppUtils.showInfoLog(TAG, "Response :"
                                + response);

                        parseLogoutAPIResponse(response.toString());
                        mResponseListener.onSuccessOfResponse(response);


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                try {
                    Response<JSONObject> errorResponse = Response.error(volleyError);
                    String errorString = new String(errorResponse.error.networkResponse.data,
                            HttpHeaderParser
                                    .parseCharset(errorResponse.error.networkResponse.headers));
                    JSONObject errorJsonObj = new JSONObject(errorString);
                    WebserviceAPIErrorHandler.getInstance()
                            .VolleyErrorHandler(volleyError, mActivity);
                    mResponseListener.onFailOfResponse(errorJsonObj);
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }
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
                params.put(GlobalKeys.AUTHTOKEN, ParkingPreference.getKeyAuthtoken(mActivity));
                params.put(GlobalKeys.USERID, ParkingPreference.getUserid(mActivity));
                return params;
            }

        };
        // Adding request to request queue
        if (ParkingAppController.getInstance() != null) {
            ParkingAppController.getInstance().addToRequestQueue(
                    mJsonRequest, GlobalKeys.PILLION_LOGOUT_REQUEST_KEY);
        }
        // set request time-out
        mJsonRequest.setRetryPolicy(new DefaultRetryPolicy(
                AppConstants.ONE_SECOND * AppConstants.RETRY_SECONDS, AppConstants.NO_OF_RETRY,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
    }

    /**
     * Parse LoginAPI Response
     *
     * @param response
     */
    protected void parseLogoutAPIResponse(String response) {

    }
}
