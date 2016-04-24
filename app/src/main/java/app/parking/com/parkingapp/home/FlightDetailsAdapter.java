package app.parking.com.parkingapp.home;

import android.app.Activity;
import android.app.Dialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

import app.parking.com.parkingapp.R;
import app.parking.com.parkingapp.bookinghistory.BookingHistoryModel;
import app.parking.com.parkingapp.model.FlightDetailsDTO;

public class FlightDetailsAdapter extends BaseAdapter {


    private Activity mActivity;
    private LayoutInflater mLayoutInflater;

    private ArrayList<FlightDetailsDTO> flightDetailsDTOArrayList;
    private Dialog mDialog;

    public FlightDetailsAdapter(Activity mActivity, ArrayList<FlightDetailsDTO> flightDetailsDTOList, Dialog modelDialog) {
        this.mActivity = mActivity;
        this.mDialog = modelDialog;
        try {
            mLayoutInflater = (LayoutInflater) mActivity
                    .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            this.flightDetailsDTOArrayList = flightDetailsDTOList;


        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public int getCount() {
        if (flightDetailsDTOArrayList != null) {
            return flightDetailsDTOArrayList.size();
        } else {
            return 0;
        }
    }

    @Override
    public Object getItem(int position) {

        if (flightDetailsDTOArrayList != null) {
            return flightDetailsDTOArrayList.get(position);
        } else {
            return null;
        }
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        ViewHolder holder;
        if (convertView == null) {
            convertView = mLayoutInflater.inflate(R.layout.flight_details_row_layout, parent, false);
            holder = new ViewHolder();

            holder.airline_value = (TextView) convertView.findViewById(R.id.airline_value);
            holder.from_value = (TextView) convertView.findViewById(R.id.from_value);
            holder.status_value = (TextView) convertView.findViewById(R.id.status_value);
            holder.flight_value = (TextView) convertView.findViewById(R.id.flight_value);
            holder.term_value = (TextView) convertView.findViewById(R.id.term_value);

            convertView.setTag(holder);

        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.airline_value.setText(flightDetailsDTOArrayList.get(position).getAirline());
        holder.flight_value.setText(flightDetailsDTOArrayList.get(position).getFlightNo());
        holder.from_value.setText(flightDetailsDTOArrayList.get(position).getDestination());
        holder.term_value.setText(flightDetailsDTOArrayList.get(position).getTerm());
        holder.status_value.setText(flightDetailsDTOArrayList.get(position).getStatus());

        return convertView;
    }


    public class ViewHolder {
        TextView airline_value, from_value, status_value, flight_value, term_value;
    }
}
