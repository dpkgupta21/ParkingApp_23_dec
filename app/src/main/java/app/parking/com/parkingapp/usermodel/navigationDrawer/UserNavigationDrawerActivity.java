package app.parking.com.parkingapp.usermodel.navigationDrawer;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import app.parking.com.parkingapp.R;
import app.parking.com.parkingapp.usermodel.bookinghistory.ViewBookingHistoryFragment;
import app.parking.com.parkingapp.usermodel.home.HomeScreenFragment;
import app.parking.com.parkingapp.view.UserProfileScreen;


public class UserNavigationDrawerActivity extends AppCompatActivity {

    private Toolbar mToolbar;
    private DrawerLayout mDrawerLayout;
    private NavigationView mNavigationView;
    private ActionBarDrawerToggle mDrawerToggle;
    private int mCurrentSelectedPosition;
    private View headerView;
    public static Activity mActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_nav_drawer_activity);
        initViews();
        assignClickOnView();
        assignClickonNavigationMenu();
        displayView(0);


    }


    private void initViews() {

        mActivity = this;
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mToolbar = (Toolbar) findViewById(R.id.tool_bar);

        mNavigationView = (NavigationView) findViewById(R.id.navigation_view);
        setSupportActionBar(mToolbar);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, mToolbar, R.string.drawer_open, R.string.drawer_close);
        headerView = mNavigationView.inflateHeaderView(R.layout.drawer_header);
        mDrawerLayout.setDrawerListener(mDrawerToggle);


        mDrawerToggle.syncState();
    }

    private void assignClickOnView() {


        headerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDrawerLayout.closeDrawer(GravityCompat.START);

                Intent user_profile_intent = new Intent(UserNavigationDrawerActivity.this, UserProfileScreen.class);
                startActivity(user_profile_intent);
            }
        });

    }


    private void assignClickonNavigationMenu() {

        mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                item.setChecked(true);
                mDrawerLayout.closeDrawer(GravityCompat.START);
                switch (item.getItemId()) {
                    case R.id.nav_item_home:
                        displayView(0);
                        mCurrentSelectedPosition = 0;
                        return true;
                    case R.id.nav_item_boooking_history:
                        displayView(1);
                        mCurrentSelectedPosition = 1;
                        return true;
                    case R.id.nav_item_order_discount:
                        displayView(2);
                        mCurrentSelectedPosition = 2;
                        return true;
                    case R.id.nav_item_about:
                        displayView(3);
                        mCurrentSelectedPosition = 3;
                        return true;
                    case R.id.nav_item_notifications:
                        displayView(4);
                        mCurrentSelectedPosition = 4;
                        return true;

                    case R.id.nav_item_logout:
                        Toast.makeText(UserNavigationDrawerActivity.this, "clicked", Toast.LENGTH_LONG).show();

                        mCurrentSelectedPosition = 5;

                        return true;


                    default:
                        return true;
                }

            }
        });

    }

    private void displayView(int position) {
        Fragment fragment = null;
        String title = getString(R.string.app_name);
        switch (position) {

            case 0:
                fragment = new HomeScreenFragment();
                title = "Home";
                break;
            case 1:
                fragment = new ViewBookingHistoryFragment();
                title = "Booking History";
                break;
            case 2:
                // fragment = new AlertFragment();
                title = "Order Discount";
                break;
            case 3:
                // fragment = new AlertFragment();
                title = "About Us";
                break;
            case 4:
                // fragment = new AlertFragment();
                title = "Notification";
                break;
            default:
                break;
        }


        if (fragment != null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.body_layout, fragment);
            fragmentTransaction.commit();

            // set the toolbar title
            getSupportActionBar().setTitle(title);
        }
    }


}
