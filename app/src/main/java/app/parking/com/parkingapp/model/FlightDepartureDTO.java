package app.parking.com.parkingapp.model;

import java.io.Serializable;

/**
 * Created by Harish on 2/28/2016.
 */
public class FlightDepartureDTO implements Serializable {

    public String flightArrivalTime, flightDepatureTime, origin, destination, flightName, flightNumber;

    public String getFlightArrivalTime() {
        return flightArrivalTime;
    }

    public void setFlightArrivalTime(String flightArrivalTime) {
        this.flightArrivalTime = flightArrivalTime;
    }

    public String getFlightDepatureTime() {
        return flightDepatureTime;
    }

    public void setFlightDepatureTime(String flightDepatureTime) {
        this.flightDepatureTime = flightDepatureTime;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getFlightName() {
        return flightName;
    }

    public void setFlightName(String flightName) {
        this.flightName = flightName;
    }

    public String getFlightNumber() {
        return flightNumber;
    }

    public void setFlightNumber(String flightNumber) {
        this.flightNumber = flightNumber;
    }


}
