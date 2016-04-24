package app.parking.com.parkingapp.model;

import java.io.Serializable;

/**
 * Created by Harish on 4/20/2016.
 */
public class FlightDetailsDTO implements Serializable {

    public String getAirline() {
        return airline;
    }

    public void setAirline(String airline) {
        this.airline = airline;
    }

    public String getFlightNo() {
        return flightNo;
    }

    public void setFlightNo(String flightNo) {
        this.flightNo = flightNo;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getDeprtTime() {
        return deprtTime;
    }

    public void setDeprtTime(String deprtTime) {
        this.deprtTime = deprtTime;
    }

    public String getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(String arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTerm() {
        return term;
    }

    public void setTerm(String term) {
        this.term = term;
    }

    String airline, flightNo, destination, deprtTime, arrivalTime, status, term;


}
