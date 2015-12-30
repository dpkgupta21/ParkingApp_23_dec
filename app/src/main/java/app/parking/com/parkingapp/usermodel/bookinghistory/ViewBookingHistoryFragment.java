package app.parking.com.parkingapp.usermodel.bookinghistory;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

import app.parking.com.parkingapp.R;

public class ViewBookingHistoryFragment extends Fragment implements View.OnClickListener {

    Toolbar mToolbar;
    View view;

    ListView listView;
    BookingHistoryAdapter mBookingHistoryAdapter;

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
        mBookingHistoryAdapter = new BookingHistoryAdapter(getActivity());
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("aas");
        arrayList.add("aas");
        arrayList.add("aas");
        arrayList.add("aas");
        arrayList.add("aas");
        arrayList.add("aas");
        arrayList.add("aas");

        mBookingHistoryAdapter.addDataOnList(arrayList);

        listView.setAdapter(mBookingHistoryAdapter);

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
