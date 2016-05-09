package app.parking.com.parkingapp.currentbooking;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import app.parking.com.parkingapp.R;
import app.parking.com.parkingapp.model.CreateOrderResponseDTO;

/**
 * Created by Deepak Singh on 06-May-16.
 */
public class CurrentBookingAdapter extends BaseAdapter {

    private Context context;
    private List<CreateOrderResponseDTO> list;

    public CurrentBookingAdapter(Context context, List<CreateOrderResponseDTO> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        CurrentBookingViewHolder holder = null;
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(R.layout.current_booking_row_layout, parent, false);

            holder = new CurrentBookingViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (CurrentBookingViewHolder) convertView.getTag();
        }

        CreateOrderResponseDTO responseDTO = list.get(position);
        holder.vehiclePlate.setText(responseDTO.getVehicleInfo().getVehiclePlateNumber());
        holder.pickUp.setText(responseDTO.getFlightInfo().getArrivalFlight().getFlightArrivalTime());
        holder.dropOff.setText(responseDTO.getFlightInfo()
                .getDestinationFlight().getFlightDepatureTime());
        holder.orderStatus.setText(responseDTO.getOrderStatus().getStatus());

        return convertView;
    }

    public class CurrentBookingViewHolder {
        TextView vehiclePlate;
        TextView pickUp;
        TextView dropOff;
        TextView orderStatus;

        CurrentBookingViewHolder(View view) {
            vehiclePlate = (TextView) view.findViewById(R.id.vehiclePlate);
            pickUp = (TextView) view.findViewById(R.id.txt_pick_up_time);
            dropOff = (TextView) view.findViewById(R.id.txt_drop_time_name);
            orderStatus = (TextView) view.findViewById(R.id.order_status_value);
        }
    }
}
