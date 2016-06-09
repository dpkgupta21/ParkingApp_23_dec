package app.parking.com.parkingapp.bookinghistory;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

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
import app.parking.com.parkingapp.currentbooking.CurrentBookingAdapter;
import app.parking.com.parkingapp.customViews.CustomProgressDialog;
import app.parking.com.parkingapp.model.CreateOrderResponseDTO;
import app.parking.com.parkingapp.model.OrderHistoryDTO;
import app.parking.com.parkingapp.preferences.ParkingPreference;
import app.parking.com.parkingapp.webservices.handler.OrderStatusAPIHandler;
import app.parking.com.parkingapp.webservices.ihelper.WebAPIResponseListener;

public class ViewBookingHistoryFragment extends Fragment implements View.OnClickListener {

    Toolbar mToolbar;
    View view;

    ListView listView;
    CurrentBookingAdapter mBookingHistoryAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_booking_history_screen, container, false);
        return view;

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        initViews();
        assignClicks();
//        mBookingHistoryAdapter = new BookingHistoryAdapter(getActivity());
//        ArrayList<String> arrayList = new ArrayList<>();
//        arrayList.add("aas");
//        arrayList.add("aas");
//        arrayList.add("aas");
//        arrayList.add("aas");
//        arrayList.add("aas");
//        arrayList.add("aas");
//        arrayList.add("aas");
//
//        mBookingHistoryAdapter.addDataOnList(arrayList);
//
//        listView.setAdapter(mBookingHistoryAdapter);
        CustomProgressDialog.showProgDialog(getActivity(), null);
        String param = ParkingPreference.getEmailId(getActivity());
        Map<String, String> params = new HashMap<>();
        params.put("email", param);
        JSONObject jsonObject = new JSONObject(params);
        String auth = ParkingPreference.getKeyAuthtoken(getActivity());
        OrderStatusAPIHandler orderStatusAPIHandler = new OrderStatusAPIHandler(getActivity(),
                jsonObject.toString(), auth, ParkingPreference.getUserid(getActivity()),
                manageOrderStatusResponse());


    }

    private WebAPIResponseListener manageOrderStatusResponse() {
        WebAPIResponseListener responseListener = new WebAPIResponseListener() {
            @Override
            public void onSuccessOfResponse(Object... arguments) {
                String response = (String) arguments[0];
                JSONObject obj = new JSONObject();
                try {
                    obj.put("response", new JSONArray(response));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                try {
                    Type type = new TypeToken<ArrayList<CreateOrderResponseDTO>>() {
                    }.getType();

                    List<CreateOrderResponseDTO> createOrderResponseDTOs = new Gson()
                            .fromJson(obj.getJSONArray("response").toString(),
                                    type);
                    setListAdapter(createOrderResponseDTOs);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailOfResponse(Object... arguments) {

            }
        };
        return responseListener;
    }

    private void setListAdapter(List<CreateOrderResponseDTO> createOrderResponseDTOs) {
        List<OrderHistoryDTO> dtos = new ArrayList<>();
        for (CreateOrderResponseDTO responseDTO : createOrderResponseDTOs) {
            OrderHistoryDTO historyDTO = new OrderHistoryDTO();
        }
        //mBookingHistoryAdapter = new OrderHistoryListAdapter()
    }


    private void assignClicks() {

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), BookingHistoryDetailScreen.class);
                startActivity(intent);
            }
        });
    }

    private void initViews() {
        listView = (ListView) view.findViewById(R.id.booking_listview);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

        }
    }


}
