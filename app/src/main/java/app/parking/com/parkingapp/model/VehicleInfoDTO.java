package app.parking.com.parkingapp.model;

import java.io.Serializable;

/**
 * Created by Harish on 4/24/2016.
 */
public class VehicleInfoDTO implements Serializable {

    public String getVehicleMake() {
        return vehicleMake;
    }

    public void setVehicleMake(String vehicleMake) {
        this.vehicleMake = vehicleMake;
    }

    public String getVehicleColor() {
        return vehicleColor;
    }

    public void setVehicleColor(String vehicleColor) {
        this.vehicleColor = vehicleColor;
    }

    public String getVehicleModel() {
        return vehicleModel;
    }

    public void setVehicleModel(String vehicleModel) {
        this.vehicleModel = vehicleModel;
    }

    String vehicleMake = "", vehicleColor = "", vehicleModel = "";



}
