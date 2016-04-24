package app.parking.com.parkingapp.model;

import java.io.Serializable;

/**
 * Created by Harish on 4/24/2016.
 */
public class DestinationFlightInfo implements Serializable {
    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

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

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    String destination = "", flightArrivalTime = "", flightDepatureTime = "", flightName = "", flightNumber = "", origin = "";
}
