package app.parking.com.parkingapp.drivermodel;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import app.parking.com.parkingapp.R;
import app.parking.com.parkingapp.drivermodel.captureImage.CapturePicture;

public class VehicleInspectionScreen extends AppCompatActivity implements View.OnClickListener {

    private Toolbar mToolbar;
    private TextView toolbar_title;
    private RelativeLayout take_vehicle_button;
    private static Activity mActivity;

    private TextView rt_frnt_dr_tv, lt_frnt_dr_tv, rt_bck_dr_tv, lt_back_dr_tv, frnt_bmpr_tv, back_bmpr_tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vehicle_inspection_screen);
        initViews();
        assignClicks();
    }

    private void assignClicks() {
        take_vehicle_button.setOnClickListener(this);
        rt_frnt_dr_tv.setOnClickListener(this);
        lt_frnt_dr_tv.setOnClickListener(this);
        rt_bck_dr_tv.setOnClickListener(this);
        lt_back_dr_tv.setOnClickListener(this);
        frnt_bmpr_tv.setOnClickListener(this);
    }


    private void initViews() {

        mActivity = this;
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar_title = (TextView) findViewById(R.id.toolbar_title);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle(getResources().getString(R.string.vehicle_inspection));
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        mToolbar.setNavigationIcon(R.drawable.back_button);
        take_vehicle_button = (RelativeLayout) findViewById(R.id.take_vehicle_button);


        rt_frnt_dr_tv = (TextView) findViewById(R.id.rt_frnt_dr_tv);
        lt_frnt_dr_tv = (TextView) findViewById(R.id.lt_frnt_dr_tv);
        rt_bck_dr_tv = (TextView) findViewById(R.id.rt_bck_dr_tv);
        lt_back_dr_tv = (TextView) findViewById(R.id.lt_back_dr_tv);
        frnt_bmpr_tv = (TextView) findViewById(R.id.frnt_bmpr_tv);
        back_bmpr_tv = (TextView) findViewById(R.id.back_bmpr_tv);


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {


        int id = item.getItemId();
        switch (id) {

            case android.R.id.home:
                finish();
                break;

        }
        return super.onOptionsItemSelected(item);

    }

    @Override
    public void onClick(View v) {

        Intent captureImageIntent = new Intent(this, CapturePicture.class);

        switch (v.getId()) {
            case R.id.take_vehicle_button:
                showMessageChooseDialog();
                break;

            case R.id.rt_frnt_dr_tv:
                captureImageIntent.putExtra("imagepath", "right_front_door");

                startActivityForResult(captureImageIntent, 0);
                break;
            case R.id.lt_frnt_dr_tv:
                captureImageIntent.putExtra("imagepath", "left_front_door");

                startActivityForResult(captureImageIntent, 1);
                break;
            case R.id.rt_bck_dr_tv:
                captureImageIntent.putExtra("imagepath", "right_back_door");

                startActivityForResult(captureImageIntent, 2);
                break;
            case R.id.lt_back_dr_tv:
                captureImageIntent.putExtra("imagepath", "left_front_door");

                startActivityForResult(captureImageIntent, 3);
                break;
            case R.id.frnt_bmpr_tv:
                captureImageIntent.putExtra("imagepath", "front_bumper");

                startActivityForResult(captureImageIntent, 4);
                break;
            case R.id.back_bmpr_tv:
                captureImageIntent.putExtra("imagepath", "back_bumper");
                startActivityForResult(captureImageIntent, 5);
                break;
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if (resultCode == RESULT_OK) {
            Toast.makeText(this, "Image Saved Successfully!!", Toast.LENGTH_LONG).show();

        }

    }


    public void showMessageChooseDialog() {
        final Dialog mDialog = new Dialog(this);
        // hide to default title for Dialog
        mDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

        // inflate the layout dialog_layout.xml and set it as
        // contentView

        LayoutInflater inflater = (LayoutInflater) mActivity
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(
                R.layout.text_and_button_dialog, null, false);
        mDialog.setCanceledOnTouchOutside(false);
        mDialog.setContentView(view);
        mDialog.getWindow().setBackgroundDrawable(
                new ColorDrawable(0));

        // Retrieve views from the inflated dialog layout and update
        // their
        // values
        TextView messageText = (TextView) mDialog
                .findViewById(R.id.messageText);
        TextView buttonMsgText = (TextView) mDialog
                .findViewById(R.id.button_text);
        messageText.setText(R.string.job_completed);
        buttonMsgText.setText(R.string.done);

        RelativeLayout closeButtonView = (RelativeLayout) mDialog
                .findViewById(R.id.closeButtonView);
        closeButtonView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Dismiss the dialog
                mDialog.dismiss();


            }
        });
        try {
            mDialog.show();

        } catch (WindowManager.BadTokenException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
