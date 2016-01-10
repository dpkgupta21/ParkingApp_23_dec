package app.parking.com.parkingapp.drivermodel.captureImage;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import app.parking.com.parkingapp.R;
import app.parking.com.parkingapp.utils.AppConstants;

/**
 * This class is used to Show full screen image on GUI
 */
public class ShowFullSnapShotOnGUI extends AppCompatActivity {

    private ImageView fullScreenSnap;
    private String TAG = ShowFullSnapShotOnGUI.class.getSimpleName();
    private Toolbar mToolbar;
    private TextView toolbar_title;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_full_snapshot_);
        initViews();
        setImageOnGUI();
    }


    /**
     * Init Views is used to initialize the views in the UI.
     */
    private void initViews() {
        /* Intent get data handler */
        fullScreenSnap = (ImageView) findViewById(R.id.fullScreenSnap);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar_title = (TextView) findViewById(R.id.toolbar_title);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle(AppConstants.imageName);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        mToolbar.setNavigationIcon(R.drawable.back_button);
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

    /**
     * Set Image ON GUI
     */
    private void setImageOnGUI() {

        // Capture position and set to the ImageView
        if (AppConstants.fullScreenBitmap != null) {
            fullScreenSnap.setImageBitmap(AppConstants.fullScreenBitmap);
        }

    }

}
