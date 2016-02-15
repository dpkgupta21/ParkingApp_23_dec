package app.parking.com.parkingapp.home;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

import app.parking.com.parkingapp.R;

public class VehicleDetailsScreen extends AppCompatActivity implements View.OnClickListener {

    private Toolbar mToolbar;
    private TextView toolbar_title;
    private RelativeLayout next_button, color_ll;
    private LinearLayout make_ll;
    private AutoCompleteTextView color_tv, make_tv;
    private static TextView model_color;
    private VehicleDetailsScreen mVehicleDetailsScreen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vehicle_detail_screen);
        initViews();
        assignClicks();
    }

    private void assignClicks() {
        next_button.setOnClickListener(this);
        make_ll.setOnClickListener(this);
        color_ll.setOnClickListener(this);
    }

    private void initViews() {
        mVehicleDetailsScreen = this;
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar_title = (TextView) findViewById(R.id.toolbar_title);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle(getResources().getString(R.string.vehicle_details));
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        mToolbar.setNavigationIcon(R.drawable.back_button);
        model_color = (TextView) findViewById(R.id.model_color);
        make_ll = (LinearLayout) findViewById(R.id.make_ll);
        color_ll = (RelativeLayout) findViewById(R.id.color_ll);
        color_tv = (AutoCompleteTextView) findViewById(R.id.color_tv);
        make_tv = (AutoCompleteTextView) findViewById(R.id.make_tv);
        next_button = (RelativeLayout) findViewById(R.id.next_button);
        makeSelection();
        colorSelecion();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.next_button:
                Intent intent = new Intent(getApplicationContext(), ServicesScreen.class);
                startActivity(intent);
                break;
            case R.id.make_ll:

             /*   AppDialogs.selectCarMake(this, make_tv);
*/
                break;

            case R.id.color_ll:

/*
                AppDialogs.selectCarColor(this, color_tv, model_color);
*/
                break;
        }
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
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }


    private void makeSelection() {
        ArrayList<String> dummyArrayList = new ArrayList<>();
        dummyArrayList.add("Audi");
        dummyArrayList.add("Bentley");
        dummyArrayList.add("BMW");
        dummyArrayList.add("Chevrolet");
        dummyArrayList.add("Daewoo");
        dummyArrayList.add("Nissan");


        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, dummyArrayList);
        make_tv.setAdapter(adapter);
        make_tv.setThreshold(1);
    }

    private void colorSelecion() {
        ArrayList<String> colorList = new ArrayList<>();
        colorList.add("Black Saphire Metallic");
        colorList.add("Melbourne Red Pearl");

        colorList.add("Mineral Grey");

        colorList.add("Valencia Orange Metallic");
        colorList.add("Estoril Blue II");
        colorList.add("Black (Matt)");
        ArrayAdapter colorAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, colorList);
        color_tv.setAdapter(colorAdapter);
        color_tv.setThreshold(1);
        final String[] dummyColorArray = getApplicationContext().getResources().getStringArray(R.array.color_bg);

        color_tv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String color = dummyColorArray[position];
                GradientDrawable drawable = (GradientDrawable) model_color.getBackground();
                drawable.setColor(Color.parseColor(color));
            }
        });
    }
}
