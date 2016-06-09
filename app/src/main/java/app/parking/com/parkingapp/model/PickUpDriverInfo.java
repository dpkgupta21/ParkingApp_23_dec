package app.parking.com.parkingapp.model;

import java.io.Serializable;

/**
 * Created by Deepak on 2/28/2016.
 */
public class PickUpDriverInfo implements Serializable {

    private String pickUpTime;

    public String getPickUpTime() {
        return pickUpTime;
    }

    public void setPickUpTime(String pickUpTime) {
        this.pickUpTime = pickUpTime;
    }
}
