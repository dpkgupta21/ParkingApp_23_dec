package app.parking.com.parkingapp.webservices.handler;

import android.app.Activity;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
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


public class OrderDetailsAPIHandler {
    private Activity mActivity;
    private String TAG = OrderDetailsAPIHandler.class.getSimpleName();
    private WebAPIResponseListener responseListener;
    private String orderno;

    public OrderDetailsAPIHandler(Activity mActivity, String orderno,
                                  WebAPIResponseListener responseListener) {
        this.mActivity = mActivity;
        this.responseListener = responseListener;
        this.orderno = orderno;
        postAPICall();
    }

    private void postAPICall() {
        try {
            JSONObject requestJsonObj = new JSONObject();
            requestJsonObj.put("orderno", orderno);
            String url = (AppConstants.APP_WEBSERVICE_API_URL + GlobalKeys.ORDER_DETAILS).trim();
            JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.POST, url,
                    requestJsonObj,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            AppUtils.showLog(TAG, response.toString());
                            responseListener.onSuccessOfResponse(response.toString());
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
                                JSONObject errorJsonObj= new JSONObject(errorString);
                                WebserviceAPIErrorHandler.getInstance()
                                        .VolleyErrorHandler(volleyError, mActivity);
                                responseListener.onFailOfResponse(errorJsonObj);
                            } catch (UnsupportedEncodingException e) {
                                e.printStackTrace();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }
            ) {
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();
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
                        jsonRequest, GlobalKeys.ORDER_DETAILS_API_KEY);
            }
            // set request time-out
            jsonRequest.setRetryPolicy(new DefaultRetryPolicy(
                    AppConstants.ONE_SECOND * 30, 0,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
