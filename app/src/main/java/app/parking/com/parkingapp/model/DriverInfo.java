package app.parking.com.parkingapp.model;

import java.io.Serializable;

/**
 * Created by Deepak on 2/28/2016.
 */
public class DriverInfo implements Serializable {

    public PickUpDriverInfo pickupDriverInfo;
    public DropOffDriverInfo dropoffDriverInfo;

    public PickUpDriverInfo getPickupDriverInfo() {
        return pickupDriverInfo;
    }

    public void setPickupDriverInfo(PickUpDriverInfo pickupDriverInfo) {
        this.pickupDriverInfo = pickupDriverInfo;
    }

    public DropOffDriverInfo getDropoffDriverInfo() {
        return dropoffDriverInfo;
    }

    public void setDropoffDriverInfo(DropOffDriverInfo dropoffDriverInfo) {
        this.dropoffDriverInfo = dropoffDriverInfo;
    }
}
