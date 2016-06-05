package app.parking.com.parkingapp.currentbooking;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
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
import app.parking.com.parkingapp.customViews.CustomProgressDialog;
import app.parking.com.parkingapp.fragments.BaseFragment;
import app.parking.com.parkingapp.model.CreateOrderResponseDTO;
import app.parking.com.parkingapp.model.OrderHistoryDTO;
import app.parking.com.parkingapp.model.PurchaseOrderDTO;
import app.parking.com.parkingapp.preferences.ParkingPreference;
import app.parking.com.parkingapp.utils.AppUtils;
import app.parking.com.parkingapp.webservices.handler.OrderHistoryAPIHandler;
import app.parking.com.parkingapp.webservices.ihelper.WebAPIResponseListener;


public class CurrentBookingFragment extends BaseFragment {

    private View view;
    private Activity mActivity;

    private Toolbar mToolbar;
    private TextView toolbar_title, toolbar_right_tv, no_service_tv;
    private RelativeLayout toolbar_right_rl;
    private ImageView payment;
    private ListView currentBookingList;
    private CreateOrderResponseDTO createOrderResponseDTO;
    private PurchaseOrderDTO purchaseOrderDTO;
    private boolean isFlight;
    private boolean isVehicle;
    private boolean isService;
    private boolean isPayment;
    private boolean isOrder;
    private boolean isPickUpInfo;
    private boolean isDropOffInfo;

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
        mActivity = CurrentBookingFragment.this.getActivity();

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
        new OrderHistoryAPIHandler(mActivity, manageOrderStatusResponse());
    }

    private void initViews() {

        mToolbar = (Toolbar) mActivity.findViewById(R.id.tool_bar);
        toolbar_title = (TextView) mToolbar.findViewById(R.id.toolbar_title);
        currentBookingList = (ListView) view.findViewById(R.id.currentBookingLIst);
        //submit_button = (RelativeLayout) findViewById(R.id.submit_button);

        //mToolbar.setNavigationIcon(R.drawable.back_button);
        toolbar_right_rl = (RelativeLayout) mToolbar.findViewById(R.id.toolbar_right_rl);
        toolbar_right_tv = (TextView) mToolbar.findViewById(R.id.toolbar_right_tv);
        toolbar_title.setVisibility(View.VISIBLE);
        toolbar_title.setText(getResources().getString(R.string.parkforu));

        toolbar_right_rl.setVisibility(View.INVISIBLE);


    }

    private WebAPIResponseListener manageOrderStatusResponse() {
        WebAPIResponseListener responseListener = new WebAPIResponseListener() {
            @Override
            public void onSuccessOfResponse(Object... arguments) {
                String response = (String) arguments[0];

//                JSONObject obj = new JSONObject();
//
//                try {
//                    obj.put("response", new JSONArray(response));
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
                try {
                    Type type = new TypeToken<ArrayList<CreateOrderResponseDTO>>() {
                    }.getType();
                    JSONArray array = new JSONArray(response);
                    List<OrderHistoryDTO> createOrderResponseDTOs = new Gson()
                            .fromJson(array.toString(), type);

                    setListAdapter(createOrderResponseDTOs);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                AppUtils.showLog(CurrentBookingFragment.class.getSimpleName(), response);

                CustomProgressDialog.hideProgressDialog();
            }

            @Override
            public void onFailOfResponse(Object... arguments) {
                CustomProgressDialog.hideProgressDialog();
            }
        };

        return responseListener;
    }

    private void setListAdapter(final List<OrderHistoryDTO> orderDTO) {
        CurrentBookingAdapter adapter = new CurrentBookingAdapter(mActivity, orderDTO);
        currentBookingList.setAdapter(adapter);

        currentBookingList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(mActivity, CurrentBookingDetails.class);
                intent.putExtra("orderno", orderDTO.get(position).getOrderNo());
                //intent.putExtra(AppConstants.ORDER_SUMMARY_KEY, createOrderResponseDTOs.get(position));
                mActivity.startActivity(intent);
            }
        });
    }
}
