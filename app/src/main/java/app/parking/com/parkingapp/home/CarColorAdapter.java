package app.parking.com.parkingapp.home;

import android.app.Activity;
import android.app.Dialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import app.parking.com.parkingapp.R;
import app.parking.com.parkingapp.bookinghistory.BookingHistoryModel;

/**
 * Created by Harish on 12/16/2015.
 */
public class CarColorAdapter extends BaseAdapter {


    private Activity mActivity;
    private LayoutInflater mLayoutInflater;

    private ArrayList<BookingHistoryModel> mBookingHistoryList;
    private ArrayList<String> dummyArrayList;
    private TextView mTextView;

    private Dialog mDialog;

    public CarColorAdapter(Activity mActivity, TextView textView, Dialog modelDialog) {
        this.mActivity = mActivity;
        this.mTextView = textView;
        this.mDialog = modelDialog;
        try {
            mLayoutInflater = (LayoutInflater) mActivity
                    .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            dummyArrayList = new ArrayList<>();
            dummyArrayList.add("Black Saphire Metallic");
            dummyArrayList.add("Melbourne Red Pearl");

            dummyArrayList.add("Mineral Grey");

            dummyArrayList.add("Valencia Orange Metallic");
            dummyArrayList.add("Estoril Blue II");
            dummyArrayList.add("Black (Matt)");


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

        ViewHolder holder = null;
        if (convertView == null) {
            convertView = mLayoutInflater.inflate(R.layout.car_model_row_layout, parent, false);
            holder = new ViewHolder();

            holder.model_name = (TextView) convertView.findViewById(R.id.model_name);
            holder.model_ll = (LinearLayout) convertView.findViewById(R.id.model_ll);
            holder.selected_img = (ImageView) convertView.findViewById(R.id.selected_img);
            convertView.setTag(holder);

        } else {
            holder = (ViewHolder) convertView.getTag();
        }

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
        TextView model_name;
        ImageView selected_img;
        LinearLayout model_ll;
    }
}
