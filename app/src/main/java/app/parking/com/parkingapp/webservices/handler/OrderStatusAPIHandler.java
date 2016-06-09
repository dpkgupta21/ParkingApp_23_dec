package app.parking.com.parkingapp.webservices.handler;

import android.app.Activity;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import app.parking.com.parkingapp.application.ParkingAppController;
import app.parking.com.parkingapp.iClasses.GlobalKeys;
import app.parking.com.parkingapp.utils.AppConstants;
import app.parking.com.parkingapp.webservices.control.WebserviceAPIErrorHandler;
import app.parking.com.parkingapp.webservices.ihelper.WebAPIResponseListener;

/**
 * Create hOLDORder API Handler
 */
public class OrderStatusAPIHandler {
    /**
     * Instance object of Create Order API
     */
    private Activity mActivity;
    /**
     * Debug TAG
     */
    private String TAG = OrderStatusAPIHandler.class.getSimpleName();
    /**
     * Request Data
     */
    private String auth_token;
    private String userId;

    private final String parameters;

    /**
     * API Response Listener
     */
    private WebAPIResponseListener responseListener;

    /**
     * @param mActivity
     * @param webAPIResponseListener
     */
    public OrderStatusAPIHandler(Activity mActivity, String parameters,
                                 String auth, String userId,
                                 WebAPIResponseListener webAPIResponseListener) {

        this.mActivity = mActivity;
        this.parameters = parameters;
        this.auth_token = auth;
        this.userId = userId;
        this.responseListener = webAPIResponseListener;
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

        JsonRequest<JSONArray> request = new JsonRequest<JSONArray>(Request.Method.POST,
                (AppConstants.APP_WEBSERVICE_API_URL + GlobalKeys.ORDER_STATUS_INFO).trim(),
                mJsonObjectRequest.toString(),
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray jsonArray) {
                        System.out.println(jsonArray.toString());
                        responseListener.onSuccessOfResponse(jsonArray.toString());
                    }
                },
                new Response.ErrorListener() {
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
                            responseListener.onFailOfResponse(errorJsonObj);
                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }) {
            @Override
            protected Response<JSONArray> parseNetworkResponse(NetworkResponse networkResponse) {
                try {
                    String jsonString = new String(networkResponse.data,
                            HttpHeaderParser
                                    .parseCharset(networkResponse.headers));
                    return Response.success(new JSONArray(jsonString),
                            HttpHeaderParser
                                    .parseCacheHeaders(networkResponse));
                } catch (UnsupportedEncodingException e) {
                    return Response.error(new ParseError(e));
                } catch (JSONException je) {
                    return Response.error(new ParseError(je));
                }
            }


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
                    request, GlobalKeys.ORDER_STATUS_INFO);
        }
        // set request time-out
        request.setRetryPolicy(new DefaultRetryPolicy(
                AppConstants.ONE_SECOND * 20, 0,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
    }

}
