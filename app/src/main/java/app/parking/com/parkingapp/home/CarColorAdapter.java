package app.parking.com.parkingapp.home;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
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

/**
 * Created by Harish on 12/16/2015.
 */
public class CarColorAdapter extends BaseAdapter {


    private Activity mActivity;
    private LayoutInflater mLayoutInflater;

    private ArrayList<BookingHistoryModel> mBookingHistoryList;
    private ArrayList<String> dummyArrayList;
    private TextView mTextView, mColorView;

    private String[] dummyColorArray;

    private Dialog mDialog;

    public CarColorAdapter(Activity mActivity, TextView textView, TextView colorview, Dialog modelDialog) {
        this.mActivity = mActivity;
        this.mTextView = textView;
        this.mDialog = modelDialog;
        mColorView = colorview;
        dummyColorArray = mActivity.getApplicationContext().getResources().getStringArray(R.array.color_bg);
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

            holder.txt_modelColor = (TextView) convertView.findViewById(R.id.model_color);
            holder.model_name = (TextView) convertView.findViewById(R.id.model_name);
            holder.model_ll = (RelativeLayout) convertView.findViewById(R.id.model_ll);
            convertView.setTag(holder);

        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        String color = dummyColorArray[position % dummyColorArray.length];
        GradientDrawable drawable = (GradientDrawable) holder.txt_modelColor.getBackground();
        drawable.setColor(Color.parseColor(color));


        holder.model_name.setText(dummyArrayList.get(position));

        holder.model_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mTextView.setText(dummyArrayList.get(position));
                String color = dummyColorArray[position % dummyColorArray.length];
                GradientDrawable drawable = (GradientDrawable) mColorView.getBackground();
                drawable.setColor(Color.parseColor(color));
                mDialog.dismiss();
            }
        });
        return convertView;
    }


    public class ViewHolder {
        TextView txt_modelColor;
        TextView model_name;
        RelativeLayout model_ll;
    }


}
