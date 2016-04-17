package app.parking.com.parkingapp.bookinghistory;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

import app.parking.com.parkingapp.R;

/**
 * Created by Harish on 12/16/2015.
 */
public class BookingHistoryAdapter extends BaseAdapter {


    Activity mActivity;
    LayoutInflater mLayoutInflater;

    ArrayList<BookingHistoryModel> mBookingHistoryList;
    ArrayList<String> dummyArrayList;


    public BookingHistoryAdapter(Activity mActivity) {
        this.mActivity = mActivity;

        try {
            mLayoutInflater = (LayoutInflater) mActivity
                    .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /**
     * Add all user data on GUI
     */
    public void addDataOnList(
            ArrayList<String> bookingHistoryList) {
        this.dummyArrayList = bookingHistoryList;
    }

    @Override
    public int getCount() {
        if (dummyArrayList != null) {
            return dummyArrayList.size();
        } else {
            return 0;
        }
    }

    @Override
    public Object getItem(int position) {

        if (dummyArrayList != null) {
            return dummyArrayList.get(position);
        } else {
            return null;
        }
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder = null;
        if (convertView == null) {
            convertView = mLayoutInflater.inflate(R.layout.booking_history_screen_row, parent, false);
            holder = new ViewHolder();

            holder.ticket_value = (TextView) convertView.findViewById(R.id.ticket_value);
            holder.pickup_value = (TextView) convertView.findViewById(R.id.pickup_value);
            holder.drop_value = (TextView) convertView.findViewById(R.id.drop_value);
            holder.new_rl = (RelativeLayout) convertView.findViewById(R.id.new_rl);
            convertView.setTag(holder);

        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.new_rl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        return convertView;
    }


    public class ViewHolder {
        TextView ticket_value, pickup_value, drop_value;
        RelativeLayout new_rl;
    }
}
