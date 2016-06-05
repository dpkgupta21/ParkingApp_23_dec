package app.parking.com.parkingapp.currentbooking;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import app.parking.com.parkingapp.R;
import app.parking.com.parkingapp.model.OrderHistoryDTO;

/**
 * Created by Deepak Singh on 06-May-16.
 */
public class CurrentBookingAdapter extends BaseAdapter {

    private Context context;
    private List<OrderHistoryDTO> list;

    public CurrentBookingAdapter(Context context, List<OrderHistoryDTO> list) {
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

        OrderHistoryDTO responseDTO = list.get(position);
        holder.orderNumber.setText(responseDTO.getOrderNo());
        holder.slotNumber.setText(responseDTO.getSlotNo());
        holder.creationDate.setText(responseDTO.getCreationDate()
                .getDestinationFlight().getFlightDepatureTime());
        holder.orderStatus.setText(responseDTO.getOrderStatus());

        return convertView;
    }

    public class CurrentBookingViewHolder {
        TextView orderNumber;
        TextView slotNumber;
        TextView creationDate;
        TextView orderStatus;

        CurrentBookingViewHolder(View view) {
            orderNumber = (TextView) view.findViewById(R.id.order_number_value);
            slotNumber = (TextView) view.findViewById(R.id.slot_value);
            creationDate = (TextView) view.findViewById(R.id.creationd_date_value);
            orderStatus = (TextView) view.findViewById(R.id.order_status_value);
        }
    }
}
