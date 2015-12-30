package app.parking.com.parkingapp.drivermodel;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import app.parking.com.parkingapp.R;

public class StartJobFragment extends Fragment implements View.OnClickListener {

    private RelativeLayout start_job_button;
    private View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.driver_start_job_screen, container, false);
        return view;

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initViews();
        assignClicks();
    }


    private void assignClicks() {
        start_job_button.setOnClickListener(this);
    }


    private void initViews() {

        start_job_button = (RelativeLayout) view.findViewById(R.id.start_job_button);
    }


    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.start_job_button:
                startActivity(new Intent(getActivity(), CustomerComments.class));
                break;
        }
    }
}
