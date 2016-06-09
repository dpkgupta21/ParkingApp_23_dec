package app.parking.com.parkingapp.model;

import java.io.Serializable;

/**
 * Created by Deepak on 2/28/2016.
 */
public class DropOffDriverInfo implements Serializable {

    private String dropOffTime;

    public String getDropOffTime() {
        return dropOffTime;
    }

    public void setDropOffTime(String dropOffTime) {
        this.dropOffTime = dropOffTime;
    }
}
