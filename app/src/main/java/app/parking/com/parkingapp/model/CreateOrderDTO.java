package app.parking.com.parkingapp.model;

import java.io.Serializable;

/**
 * Created by Harish on 2/28/2016.
 */
public class CreateOrderDTO implements Serializable {

    public String userEmail, venueName, dropOffTime, pickUpTime;
    public VehicleDTO vehicleDTO;
    public FlightArrivalDTO flightArrivalDTO;
    public FlightDepartureDTO flightDepartureDTO;
    public ListOfServicesDTO listOfServicesDTO;

    public ListOfServicesDTO getListOfServicesDTO() {
        return listOfServicesDTO;
    }

    public void setListOfServicesDTO(ListOfServicesDTO listOfServicesDTO) {
        this.listOfServicesDTO = listOfServicesDTO;
    }


    public FlightArrivalDTO getFlightArrivalDTO() {
        return flightArrivalDTO;
    }

    public void setFlightArrivalDTO(FlightArrivalDTO flightArrivalDTO) {
        this.flightArrivalDTO = flightArrivalDTO;
    }

    public FlightDepartureDTO getFlightDepartureDTO() {
        return flightDepartureDTO;
    }

    public void setFlightDepartureDTO(FlightDepartureDTO flightDepartureDTO) {
        this.flightDepartureDTO = flightDepartureDTO;
    }


    public VehicleDTO getVehicleDTO() {
        return vehicleDTO;
    }

    public void setVehicleDTO(VehicleDTO vehicleDTO) {
        this.vehicleDTO = vehicleDTO;
    }


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
