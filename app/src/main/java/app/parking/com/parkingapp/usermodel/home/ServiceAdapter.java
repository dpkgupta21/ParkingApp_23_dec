package app.parking.com.parkingapp.usermodel.home;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

import app.parking.com.parkingapp.R;
import app.parking.com.parkingapp.usermodel.bookinghistory.BookingHistoryModel;

/**
 * Created by Harish on 12/16/2015.
 */
public class ServiceAdapter extends BaseAdapter {


    private LayoutInflater mLayoutInflater;
    Activity mActivity;
    private ArrayList<ServicesModel> mSerViceModelList;
    private TextView mTextView;

    private String[] dummyColorArray;

    private Dialog mDialog;

    public ServiceAdapter(Activity mActivity, ArrayList<ServicesModel> list, TextView textView) {
        this.mTextView = textView;
        this.mSerViceModelList = list;
        this.mActivity = mActivity;
        try {
            mLayoutInflater = (LayoutInflater) mActivity
                    .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public int getCount() {
        return mSerViceModelList.size();
    }

    @Override
    public Object getItem(int position) {

        return mSerViceModelList.get(position);

    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        ViewHolder holder = null;
        if (convertView == null) {
            convertView = mLayoutInflater.inflate(R.layout.added_services_row, parent, false);
            holder = new ViewHolder();

            holder.service_name = (TextView) convertView.findViewById(R.id.service_name);
            holder.price_tv = (TextView) convertView.findViewById(R.id.price_tv);
            holder.cross_img = (ImageView) convertView.findViewById(R.id.cross_img);
            convertView.setTag(holder);

        } else {
            holder = (ViewHolder) convertView.getTag();
        }


        if (mSerViceModelList.size() == 0) {
            Log.d("ss", "empty list");


            mTextView.setVisibility(View.VISIBLE);
        } else {
            Log.d("ss", mSerViceModelList.size() + " list");
            mTextView.setVisibility(View.GONE);

            holder.service_name.setText(mSerViceModelList.get(position).getServiceName());
            holder.price_tv.setText(mSerViceModelList.get(position).getServiceCost());

            holder.cross_img.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    mSerViceModelList.remove(position);
                    notifyDataSetChanged();
                }
            });
        }

        return convertView;
    }


    public class ViewHolder {
        TextView service_name;
        TextView price_tv;
        ImageView cross_img;
    }


}
