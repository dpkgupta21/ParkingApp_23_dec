package app.parking.com.parkingapp.bookinghistory;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import app.parking.com.parkingapp.R;

public class BookingHistoryDetailScreen extends AppCompatActivity {

    Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.booking_history_detail_screen);
        initView();
    }

    /**
     * initialise the view/widgets of the UI.
     */
    private void initView() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
//        mToolbar.setAlpha(1f);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("#245216/Toronto_Airport");
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        mToolbar.setNavigationIcon(R.drawable.back_button);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.user_profile_menu, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {


        int id = item.getItemId();
        switch (id) {

            case android.R.id.home:
                finish();
                break;
            case R.id.action_edit:
                break;
        }
        return super.onOptionsItemSelected(item);

    }

}
