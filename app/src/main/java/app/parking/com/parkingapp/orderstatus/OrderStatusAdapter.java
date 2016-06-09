package app.parking.com.parkingapp.orderstatus;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import app.parking.com.parkingapp.R;
import app.parking.com.parkingapp.model.CreateOrderResponseDTO;
import app.parking.com.parkingapp.utils.HelpMe;

/**
 * Created by Deepak Singh on 06-May-16.
 */
public class OrderStatusAdapter extends BaseAdapter {

    private Context context;
    private List<CreateOrderResponseDTO> list;

    public OrderStatusAdapter(Context context, List<CreateOrderResponseDTO> list) {
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
            convertView = inflater.inflate(R.layout.booking_items_layout, parent, false);

            holder = new CurrentBookingViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (CurrentBookingViewHolder) convertView.getTag();
        }


        CreateOrderResponseDTO responseDTO = list.get(position);
        holder.plateNumber.setText(responseDTO.getVehicleInfo().getPlateNo());
        holder.pickDate.setText(HelpMe.getFlightDisplayDateTime(responseDTO
                .getDriverInfo().getPickupDriverInfo()
                .getPickUpTime()));
        holder.dropDate.setText(HelpMe.getFlightDisplayDateTime(responseDTO.getDriverInfo()
                .getDropoffDriverInfo().getDropOffTime()));
        holder.vehicleModel.setText(responseDTO.getVehicleInfo().getVehicleModel());
        holder.orderStatus.setText(responseDTO.getOrderStatus().getStatus());

        return convertView;
    }

    public class CurrentBookingViewHolder {
        TextView plateNumber;
        TextView pickDate;
        TextView dropDate;
        TextView orderStatus;
        TextView vehicleModel;

        CurrentBookingViewHolder(View view) {
            plateNumber = (TextView) view.findViewById(R.id.plate_number_value);
            pickDate = (TextView) view.findViewById(R.id.pick_value);
            dropDate = (TextView) view.findViewById(R.id.drop_date_value);
            orderStatus = (TextView) view.findViewById(R.id.order_status_value);
            vehicleModel = (TextView) view.findViewById(R.id.vehicle_name_value);
        }
    }
}
