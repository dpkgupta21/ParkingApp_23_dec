package app.parking.com.parkingapp.home;

import android.app.Activity;
import android.app.Dialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

import app.parking.com.parkingapp.R;
import app.parking.com.parkingapp.bookinghistory.BookingHistoryModel;

public class CarModelAdapter extends BaseAdapter {


    private Activity mActivity;
    private LayoutInflater mLayoutInflater;

    private ArrayList<BookingHistoryModel> mBookingHistoryList;
    private ArrayList<String> dummyArrayList;
    private TextView mTextView;

    private Dialog mDialog;

    public CarModelAdapter(Activity mActivity, TextView textView, Dialog modelDialog) {
        this.mActivity = mActivity;
        this.mTextView = textView;
        this.mDialog = modelDialog;
        try {
            mLayoutInflater = (LayoutInflater) mActivity
                    .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            dummyArrayList = new ArrayList<>();
            dummyArrayList.add("Sentra");
            dummyArrayList.add("Accord");
            dummyArrayList.add("Corola");
            dummyArrayList.add("Civic");
            dummyArrayList.add("Verna");

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
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
    public View getView(final int position, View convertView, ViewGroup parent) {

        ViewHolder holder;
        if (convertView == null) {
            convertView = mLayoutInflater.inflate(R.layout.car_model_row_layout, parent, false);
            holder = new ViewHolder();

            holder.model_color = (TextView) convertView.findViewById(R.id.model_color);
            holder.model_name = (TextView) convertView.findViewById(R.id.model_name);
            holder.model_ll = (RelativeLayout) convertView.findViewById(R.id.model_ll);
            holder.selected_img = (ImageView) convertView.findViewById(R.id.selected_img);
            convertView.setTag(holder);

        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.model_color.setVisibility(View.GONE);
        holder.model_name.setText(dummyArrayList.get(position));

        holder.model_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mTextView.setText(dummyArrayList.get(position));
                mDialog.dismiss();
            }
        });
        return convertView;
    }


    public class ViewHolder {
        TextView model_name, model_color;
        ImageView selected_img;
        RelativeLayout model_ll;
    }
}
