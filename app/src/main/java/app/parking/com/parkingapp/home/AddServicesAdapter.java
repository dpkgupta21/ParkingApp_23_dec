package app.parking.com.parkingapp.home;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import java.util.ArrayList;

import app.parking.com.parkingapp.R;
import app.parking.com.parkingapp.model.ListOfServicesDTO;

/**
 * Created by Harish on 12/16/2015.
 */
public class AddServicesAdapter extends BaseAdapter {


    private LayoutInflater mLayoutInflater;
    Activity mActivity;
    private ArrayList<ListOfServicesDTO> mSerViceModelList;
    private TextView mTextView;


    public AddServicesAdapter(Activity mActivity, ArrayList<ListOfServicesDTO> list, TextView textView) {
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
            convertView = mLayoutInflater.inflate(R.layout.services_row_layout, parent, false);
            holder = new ViewHolder();

            holder.service_cost = (TextView) convertView.findViewById(R.id.service_cost);
            holder.service_name = (CheckBox) convertView.findViewById(R.id.service_name);
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

            holder.service_name.setText(mSerViceModelList.get(position).getName());
            holder.service_cost.setText(mSerViceModelList.get(position).getPrice());

        }

        holder.service_name.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked) {
                    mSerViceModelList.get(position).setIsAdded(true);
                } else {
                    mSerViceModelList.get(position).setIsAdded(false);

                }
            }
        });

        return convertView;
    }


    public class ViewHolder {
        CheckBox service_name;
        TextView service_cost;
    }


}
