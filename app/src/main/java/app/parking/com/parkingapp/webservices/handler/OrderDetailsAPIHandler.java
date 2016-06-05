package app.parking.com.parkingapp.webservices.handler;

import android.app.Activity;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import app.parking.com.parkingapp.application.ParkingAppController;
import app.parking.com.parkingapp.iClasses.GlobalKeys;
import app.parking.com.parkingapp.preferences.ParkingPreference;
import app.parking.com.parkingapp.utils.AppConstants;
import app.parking.com.parkingapp.utils.AppUtils;
import app.parking.com.parkingapp.webservices.control.WebserviceAPIErrorHandler;
import app.parking.com.parkingapp.webservices.ihelper.WebAPIResponseListener;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Request.Method;
import com.android.volley.Response;
import com.android.volley.VolleyError;


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
        Map<String, String> param = new HashMap<>();
        param.put("orderno", orderno);
        try {
            String url = (AppConstants.APP_WEBSERVICE_API_URL + GlobalKeys.ORDER_DETAILS).trim();
            JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.POST, url, param,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            AppUtils.showLog(TAG,response.toString());
                            responseListener.onSuccessOfResponse(response.toString());
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorListener(VolleyError error) {
                            AppUtils.showLog(TAG,error.toString());
                            WebserviceAPIErrorHandler.getInstance()
                                    .VolleyErrorHandler(error, mActivity);
                            responseListener.onFailOfResponse(error);
                        }
                    }
            ) {
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put(GlobalKeys.HEADER_KEY_CONTENT_TYPE,
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
