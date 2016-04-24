package app.parking.com.parkingapp.home;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import app.parking.com.parkingapp.R;
import app.parking.com.parkingapp.model.CreateOrderDTO;
import app.parking.com.parkingapp.model.VehicleDTO;
import app.parking.com.parkingapp.utils.AppConstants;
import app.parking.com.parkingapp.utils.AppUtils;

public class VehicleDetailsScreen extends AppCompatActivity implements View.OnClickListener {

    private Toolbar mToolbar;
    private TextView toolbar_title, toolbar_right_tv, color_tv, make_tv, make_value_tv, model_value_tv, color_value_tv;
    private RelativeLayout toolbar_right_rl;
    private RelativeLayout color_ll;
    private LinearLayout make_ll;
    private TextView model_et;
    private EditText number_plate_et;

    private static TextView model_color;
    private VehicleDetailsScreen mVehicleDetailsScreen;
    private CreateOrderDTO createOrderDTO;
    private VehicleDTO vehicleDTO;
    private String TAG = VehicleDetailsScreen.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vehicle_detail_screen);
        initViews();
        assignClicks();
    }

    private void assignClicks() {
        make_ll.setOnClickListener(this);
        color_ll.setOnClickListener(this);
        model_et.setOnClickListener(this);
        toolbar_right_rl.setOnClickListener(this);
    }

    private void initViews() {
        mVehicleDetailsScreen = this;
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar_title = (TextView) findViewById(R.id.toolbar_title);
        toolbar_title.setVisibility(View.VISIBLE);
        toolbar_title.setText(R.string.parkforu);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle(" ");
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        mToolbar.setNavigationIcon(R.drawable.back_button);

        toolbar_right_rl = (RelativeLayout) findViewById(R.id.toolbar_right_rl);
        toolbar_right_tv = (TextView) findViewById(R.id.toolbar_right_tv);
        toolbar_right_rl.setVisibility(View.VISIBLE);
        toolbar_right_tv.setText(R.string.next);
        model_color = (TextView) findViewById(R.id.model_color);
        make_ll = (LinearLayout) findViewById(R.id.make_ll);
        color_ll = (RelativeLayout) findViewById(R.id.color_ll);
        color_tv = (TextView) findViewById(R.id.color_tv);
        make_tv = (TextView) findViewById(R.id.make_tv);
        model_et = (TextView) findViewById(R.id.model_et);
        number_plate_et = (EditText) findViewById(R.id.number_plate_et);
        make_value_tv = (TextView) findViewById(R.id.make_value_tv);
        model_value_tv = (TextView) findViewById(R.id.model_value_tv);
        color_value_tv = (TextView) findViewById(R.id.color_value_tv);

       /* makeSelection();
        colorSelecion();*/

        if (getIntent() != null) {
            createOrderDTO = (CreateOrderDTO) getIntent().getSerializableExtra(AppConstants.CREATE_ORDER);
            AppUtils.showLog(TAG, createOrderDTO.getPickUpTime() + " " + createOrderDTO.getDropOffTime());
        } else {
            createOrderDTO = new CreateOrderDTO();
        }

        vehicleDTO = new VehicleDTO();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.toolbar_right_rl:

                vehicleDTO.setColor(color_value_tv.getText().toString().trim());
                vehicleDTO.setMake(make_value_tv.getText().toString().trim());
                vehicleDTO.setModel(model_value_tv.getText().toString().trim());
                vehicleDTO.setPlateNo(number_plate_et.getText().toString().trim());
                createOrderDTO.setVehicle(vehicleDTO);
                Intent intent = new Intent(getApplicationContext(), AddServicesScreen.class);
                startActivity(intent.putExtra(AppConstants.CREATE_ORDER, createOrderDTO));
                break;
            case R.id.make_ll:

                AppDialogs.selectCarMake(this, make_value_tv);
                break;

            case R.id.model_et:

                AppDialogs.selectCarModel(this, model_value_tv);
                break;
            case R.id.color_ll:

                AppDialogs.selectCarColor(this, color_value_tv, model_color);
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
//        ArrayList<String> dummyArrayList = new ArrayList<>();
//        dummyArrayList.add("Audi");
//        dummyArrayList.add("Bentley");
//        dummyArrayList.add("BMW");
//        dummyArrayList.add("Chevrolet");
//        dummyArrayList.add("Daewoo");
//        dummyArrayList.add("Nissan");
//
//
//        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, dummyArrayList);
//        make_tv.setAdapter(adapter);
//        make_tv.setThreshold(1);
    }

    private void colorSelecion() {
//        ArrayList<String> colorList = new ArrayList<>();
//        colorList.add("Black Saphire Metallic");
//        colorList.add("Melbourne Red Pearl");
//
//        colorList.add("Mineral Grey");
//
//        colorList.add("Valencia Orange Metallic");
//        colorList.add("Estoril Blue II");
//        colorList.add("Black (Matt)");
//        ArrayAdapter colorAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, colorList);
//        color_tv.setAdapter(colorAdapter);
//        color_tv.setThreshold(1);
//        final String[] dummyColorArray = getApplicationContext().getResources().getStringArray(R.array.color_bg);
//
//        color_tv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                String color = dummyColorArray[position];
//                GradientDrawable drawable = (GradientDrawable) model_color.getBackground();
//                drawable.setColor(Color.parseColor(color));
//            }
//        });
    }
}
