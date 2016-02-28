package app.parking.com.parkingapp.model;

import java.io.Serializable;

/**
 * Created by Harish on 2/28/2016.
 */
public class VehicleDTO implements Serializable {

    public String make, model, color, plateNo;

    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getPlateNo() {
        return plateNo;
    }

    public void setPlateNo(String plateNo) {
        this.plateNo = plateNo;
    }

}
