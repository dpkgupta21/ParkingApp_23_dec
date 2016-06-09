package app.parking.com.parkingapp.orderstatus;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import app.parking.com.parkingapp.R;
import app.parking.com.parkingapp.currentbooking.CurrentBookingFragment;
import app.parking.com.parkingapp.customViews.CustomProgressDialog;
import app.parking.com.parkingapp.fragments.BaseFragment;
import app.parking.com.parkingapp.model.CreateOrderResponseDTO;
import app.parking.com.parkingapp.preferences.ParkingPreference;
import app.parking.com.parkingapp.utils.AppConstants;
import app.parking.com.parkingapp.utils.AppUtils;
import app.parking.com.parkingapp.utils.WebserviceResponseConstants;
import app.parking.com.parkingapp.webservices.handler.OrderStatusAPIHandler;
import app.parking.com.parkingapp.webservices.ihelper.WebAPIResponseListener;


public class OrderStausFragment extends BaseFragment {

    private View view;
    private Activity mActivity;

    private Toolbar mToolbar;
    private TextView toolbar_title;
    private RelativeLayout toolbar_right_rl;
    private ListView currentBookingList;

    public static CurrentBookingFragment newInstance(String param1, String param2) {
        CurrentBookingFragment fragment = new CurrentBookingFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_current_booking, container, false);
        mActivity = OrderStausFragment.this.getActivity();

        initViews();
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        CustomProgressDialog.showProgDialog(mActivity, null);
        String param = ParkingPreference.getEmailId(mActivity);
        Map<String, String> params = new HashMap<>();
        params.put("email", param);
        JSONObject jsonObject = new JSONObject(params);
        String auth = ParkingPreference.getKeyAuthtoken(mActivity);
        String userId = ParkingPreference.getUserid(mActivity);
        new OrderStatusAPIHandler(mActivity, jsonObject.toString(), auth,
                userId, manageOrderStatusResponse());
    }

    private void initViews() {

        mToolbar = (Toolbar) mActivity.findViewById(R.id.tool_bar);
        toolbar_title = (TextView) mToolbar.findViewById(R.id.toolbar_title);
        currentBookingList = (ListView) view.findViewById(R.id.currentBookingLIst);
        //submit_button = (RelativeLayout) findViewById(R.id.submit_button);

        //mToolbar.setNavigationIcon(R.drawable.back_button);
        toolbar_right_rl = (RelativeLayout) mToolbar.findViewById(R.id.toolbar_right_rl);
        toolbar_title.setVisibility(View.VISIBLE);
        toolbar_title.setText(getResources().getString(R.string.order_history_title));

        toolbar_right_rl.setVisibility(View.INVISIBLE);


    }

    private WebAPIResponseListener manageOrderStatusResponse() {
        WebAPIResponseListener responseListener = new WebAPIResponseListener() {
            @Override
            public void onSuccessOfResponse(Object... arguments) {
                String response = (String) arguments[0];
                CustomProgressDialog.hideProgressDialog();
                try {
                    Type type = new TypeToken<ArrayList<CreateOrderResponseDTO>>() {
                    }.getType();
                    JSONArray array = new JSONArray(response);
                    List<CreateOrderResponseDTO> createOrderResponseDTOs = new Gson()
                            .fromJson(array.toString(), type);

                    setListAdapter(createOrderResponseDTOs);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                AppUtils.showLog(CurrentBookingFragment.class.getSimpleName(), response);


            }

            @Override
            public void onFailOfResponse(Object... arguments) {
                try {
                    CustomProgressDialog.hideProgressDialog();

                    JSONObject errorJsonObj = (JSONObject) arguments[0];
                    if (AppUtils.getWebServiceErrorCode(errorJsonObj).
                            equalsIgnoreCase(WebserviceResponseConstants.NO_ORDERS_FOUND)) {

                        setViewVisibility(R.id.txt_no_data, view, View.VISIBLE);
                        setViewText(R.id.txt_no_data,
                                AppUtils.getWebServiceErrorMsg(errorJsonObj), view);

                    } else if (AppUtils.getWebServiceErrorCode(errorJsonObj).
                            equalsIgnoreCase(WebserviceResponseConstants.ERROR_TOKEN_EXPIRED)) {


                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };

        return responseListener;
    }

    private void setListAdapter(final List<CreateOrderResponseDTO> orderDTO) {
        OrderStatusAdapter adapter = new OrderStatusAdapter(mActivity, orderDTO);
        currentBookingList.setAdapter(adapter);

        currentBookingList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(mActivity, OrderStatusDetails.class);
                //intent.putExtra("orderno", orderDTO.get(position).getOrderNo());
                intent.putExtra(AppConstants.ORDER_SUMMARY_KEY, orderDTO.get(position));
                mActivity.startActivity(intent);
            }
        });
    }
}
