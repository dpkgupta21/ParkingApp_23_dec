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
import app.parking.com.parkingapp.utils.AppConstants;
import app.parking.com.parkingapp.utils.AppUtils;
import app.parking.com.parkingapp.webservices.control.WebserviceAPIErrorHandler;
import app.parking.com.parkingapp.webservices.ihelper.WebAPIResponseListener;

/**
 * Create hOLDORder API Handler
 */
public class HoldOrderAPIHandler {
    /**
     * Instance object of Create Order API
     */
    private Activity mActivity;
    /**
     * Debug TAG
     */
    private String TAG = HoldOrderAPIHandler.class.getSimpleName();
    /**
     * Request Data
     */
    private String auth_token;
    private String userId;

    private final String parameters;

    /**
     * API Response Listener
     */
    private WebAPIResponseListener mResponseListener;

    /**
     * @param mActivity
     * @param webAPIResponseListener
     */
    public HoldOrderAPIHandler(Activity mActivity, String parameters,
                               String auth, String userId, WebAPIResponseListener webAPIResponseListener) {

        this.mActivity = mActivity;
        this.parameters = parameters;
        this.auth_token = auth;
        this.userId = userId;
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

        JSONObject mJsonObjectRequest = null;
        try {
            mJsonObjectRequest = new JSONObject(parameters);
        } catch (JSONException e) {
            e.printStackTrace();
        }


        JsonObjectRequest mJsonRequest = new JsonObjectRequest(
                Method.POST,
                (AppConstants.APP_WEBSERVICE_API_URL + GlobalKeys.HOLD_ORDER_API)
                        .trim(), mJsonObjectRequest,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        mResponseListener.onSuccessOfResponse(response.toString());


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                JSONObject errorJsonObj=null;
                try {
                    Response<JSONObject> errorResponse = Response.error(error);
                    String errorString = new String(errorResponse.error.networkResponse.data,
                            HttpHeaderParser
                                    .parseCharset(errorResponse.error.networkResponse.headers));
                    errorJsonObj = new JSONObject(errorString);
                    WebserviceAPIErrorHandler.getInstance()
                            .VolleyErrorHandler(error, mActivity);
                    mResponseListener.onFailOfResponse(errorJsonObj);
                } catch (UnsupportedEncodingException e) {
                    mResponseListener.onFailOfResponse(errorJsonObj);
                    e.printStackTrace();
                } catch (JSONException e) {
                    mResponseListener.onFailOfResponse(errorJsonObj);
                    e.printStackTrace();
                } catch (Exception e) {
                    mResponseListener.onFailOfResponse(errorJsonObj);
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
                params.put(GlobalKeys.AUTHTOKEN, auth_token);
                params.put(GlobalKeys.USERID, userId);
                return params;
            }

        };
        // Adding request to request queue
        if (ParkingAppController.getInstance() != null) {
            ParkingAppController.getInstance().addToRequestQueue(
                    mJsonRequest, GlobalKeys.HOLD_ORDER_API);
        }
        // set request time-out
        mJsonRequest.setRetryPolicy(new DefaultRetryPolicy(
                AppConstants.ONE_SECOND * 20, 0,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
    }

}
