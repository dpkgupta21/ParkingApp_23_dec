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
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
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
    private String userId;
    private String tag;


    private ArrayList<FlightDetailsDTO> flightDetailsDTOArrayList;

    /**
     * API Response Listener
     */
    private WebAPIResponseListener mResponseListener;

    /**
     * @param mActivity
     * @param webAPIResponseListener
     */
    public FlightDetailsAPIHandler(Activity mActivity,
                                   String flightNo, String flightName,
                                   String auth_token, String userId, String tag,
                                   WebAPIResponseListener webAPIResponseListener) {

        this.mActivity = mActivity;
        this.auth_token = auth_token;
        this.userId = userId;
        this.tag = tag;
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
            //mJsonObjectRequest.put(GlobalKeys.flightNo, flightNo);
            mJsonObjectRequest.put(GlobalKeys.TAG, tag);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonRequest<JSONArray> mJsonRequest = new JsonRequest<JSONArray>(
                Request.Method.POST,
                (AppConstants.APP_WEBSERVICE_API_URL + GlobalKeys.FLIGHT_INFO)
                        .trim(), mJsonObjectRequest.toString(),
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray jsonArray) {

                        AppUtils.showInfoLog(TAG, "Response :"
                                + jsonArray.toString());

                        mResponseListener.onSuccessOfResponse(jsonArray.toString());


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
                params.put(GlobalKeys.AUTHTOKEN,
                        auth_token);
                params.put(GlobalKeys.USERID,
                        userId);
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
