package app.parking.com.parkingapp.model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Harish on 2/28/2016.
 */
public class CreateOrderDTO implements Serializable {

    public String userEmail, venueName, dropOffTime, pickUpTime;
    public VehicleDTO vehicle;

    public VehicleDTO getVehicle() {
        return vehicle;
    }

    public void setVehicle(VehicleDTO vehicle) {
        this.vehicle = vehicle;
    }

    public FlightArrivalDTO getArrivalFlight() {
        return arrivalFlight;
    }

    public void setArrivalFlight(FlightArrivalDTO arrivalFlight) {
        this.arrivalFlight = arrivalFlight;
    }

    public FlightDepartureDTO getDestinationFlight() {
        return destinationFlight;
    }

    public void setDestinationFlight(FlightDepartureDTO destinationFlight) {
        this.destinationFlight = destinationFlight;
    }


    public FlightArrivalDTO arrivalFlight;
    public FlightDepartureDTO destinationFlight;

    public ArrayList<ListOfServicesDTO> getServices() {
        return services;
    }

    public void setServices(ArrayList<ListOfServicesDTO> services) {
        this.services = services;
    }

    public ArrayList<ListOfServicesDTO> services;

    public String getVenueName() {
        return venueName;
    }

    public void setVenueName(String venueName) {
        this.venueName = venueName;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getDropOffTime() {
        return dropOffTime;
    }

    public void setDropOffTime(String dropOffTime) {
        this.dropOffTime = dropOffTime;
    }

    public String getPickUpTime() {
        return pickUpTime;
    }

    public void setPickUpTime(String pickUpTime) {
        this.pickUpTime = pickUpTime;
    }


}
