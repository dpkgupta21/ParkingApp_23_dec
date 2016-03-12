package app.parking.com.parkingapp.home;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ListView;
import android.widget.TextView;

import app.parking.com.parkingapp.R;

/**
 * Created by Harish on 12/19/2015.
 */
public class AppDialogs {
    /**
     * select country code dialog
     */

    private static Dialog mModelDialog;

    public static void selectCarMake(final Activity mActivity,
                                     final TextView textView) {

        try {
            if (mModelDialog != null && mModelDialog.isShowing()) {
                mModelDialog.dismiss();
            }
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }

        if (mActivity != null) {
            mModelDialog = new Dialog(mActivity);
            // hide to default title for Dialog
            mModelDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

            // inflate the layout dialog_layout.xml and set it as
            // contentView
            LayoutInflater inflater = (LayoutInflater) mActivity
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = inflater.inflate(R.layout.listview_dialog, null,
                    false);
            mModelDialog.setCanceledOnTouchOutside(false);
            mModelDialog.setContentView(view);
            mModelDialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
            WindowManager.LayoutParams lp = mModelDialog.getWindow().getAttributes();
            lp.dimAmount = 0.8f;
            mModelDialog.getWindow().setAttributes(lp);


            ListView car_make_list = (ListView) mModelDialog
                    .findViewById(R.id.listview);
            final CarMakeAdapter adapter = new CarMakeAdapter(
                    mActivity, textView, mModelDialog);
            car_make_list.setAdapter(adapter);

            try {
                // Display the dialog
                mModelDialog.show();
            } catch (WindowManager.BadTokenException e) {
                // TODO: handle exception
                e.printStackTrace();
            } catch (Exception e) {
                // TODO: handle exception
                e.printStackTrace();
            }
        }

    }


    public static void selectCarColor(final Activity mActivity,
                                      final TextView textView, final TextView color_view) {
        try {
            if (mModelDialog != null && mModelDialog.isShowing()) {
                mModelDialog.dismiss();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (mActivity != null) {
            mModelDialog = new Dialog(mActivity);
            // hide to default title for Dialog
            mModelDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

            // inflate the layout dialog_layout.xml and set it as
            // contentView
            LayoutInflater inflater = (LayoutInflater) mActivity
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = inflater.inflate(R.layout.listview_dialog, null,
                    false);
            mModelDialog.setCanceledOnTouchOutside(false);
            mModelDialog.setContentView(view);
            mModelDialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
            WindowManager.LayoutParams lp = mModelDialog.getWindow().getAttributes();
            lp.dimAmount = 0.8f;
            mModelDialog.getWindow().setAttributes(lp);


            ListView car_make_list = (ListView) mModelDialog
                    .findViewById(R.id.listview);
            final CarColorAdapter adapter = new CarColorAdapter(
                    mActivity, textView, color_view, mModelDialog);
            car_make_list.setAdapter(adapter);

            try {
                // Display the dialog
                mModelDialog.show();
            } catch (WindowManager.BadTokenException e) {
                // TODO: handle exception
                e.printStackTrace();
            } catch (Exception e) {
                // TODO: handle exception
                e.printStackTrace();
            }
        }

    }

    public static void selectCarModel(final Activity mActivity,
                                     final TextView textView) {

        try {
            if (mModelDialog != null && mModelDialog.isShowing()) {
                mModelDialog.dismiss();
            }
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }

        if (mActivity != null) {
            mModelDialog = new Dialog(mActivity);
            // hide to default title for Dialog
            mModelDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

            // inflate the layout dialog_layout.xml and set it as
            // contentView
            LayoutInflater inflater = (LayoutInflater) mActivity
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = inflater.inflate(R.layout.listview_dialog, null,
                    false);
            mModelDialog.setCanceledOnTouchOutside(false);
            mModelDialog.setContentView(view);
            mModelDialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
            WindowManager.LayoutParams lp = mModelDialog.getWindow().getAttributes();
            lp.dimAmount = 0.8f;
            mModelDialog.getWindow().setAttributes(lp);


            ListView car_make_list = (ListView) mModelDialog
                    .findViewById(R.id.listview);
            final CarModelAdapter adapter = new CarModelAdapter(
                    mActivity, textView, mModelDialog);
            car_make_list.setAdapter(adapter);

            try {
                // Display the dialog
                mModelDialog.show();
            } catch (WindowManager.BadTokenException e) {
                // TODO: handle exception
                e.printStackTrace();
            } catch (Exception e) {
                // TODO: handle exception
                e.printStackTrace();
            }
        }

    }

}
