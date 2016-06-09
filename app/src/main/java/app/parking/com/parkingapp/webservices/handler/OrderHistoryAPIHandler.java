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
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonRequest;

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
import app.parking.com.parkingapp.webservices.control.WebserviceAPIErrorHandler;
import app.parking.com.parkingapp.webservices.ihelper.WebAPIResponseListener;


public class OrderHistoryAPIHandler {
    private Activity mActivity;
    private String TAG = OrderHistoryAPIHandler.class.getSimpleName();
    private WebAPIResponseListener responseListener;

    public OrderHistoryAPIHandler(Activity mActivity, WebAPIResponseListener responseListener) {
        this.mActivity = mActivity;
        this.responseListener = responseListener;

        postAPICall();
    }

    private void postAPICall() {
        try {
            String url = (AppConstants.APP_WEBSERVICE_API_URL + GlobalKeys.ORDER_HISTORY).trim();

            JsonRequest<JSONArray> mJsonRequest = new JsonRequest<JSONArray>(Request.Method.GET, url, null,
                    new Response.Listener<JSONArray>() {
                        @Override
                        public void onResponse(JSONArray response) {
                            System.out.println(response.toString());
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
                    params.put(GlobalKeys.AUTHTOKEN, ParkingPreference.getKeyAuthtoken(mActivity));
                    params.put(GlobalKeys.USERID, ParkingPreference.getUserid(mActivity));
                    return params;
                }
            };

            // Adding request to request queue
            if (ParkingAppController.getInstance() != null) {
                ParkingAppController.getInstance().addToRequestQueue(
                        mJsonRequest, GlobalKeys.ORDER_HISTORY_API_KEY);
            }
            // set request time-out
            mJsonRequest.setRetryPolicy(new DefaultRetryPolicy(
                    AppConstants.ONE_SECOND * 30, 0,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
