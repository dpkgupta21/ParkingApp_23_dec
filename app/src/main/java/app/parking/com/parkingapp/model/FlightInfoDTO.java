package app.parking.com.parkingapp.model;

import java.io.Serializable;

/**
 * Created by Harish on 4/24/2016.
 */
public class FlightInfoDTO implements Serializable {

    public DestinationFlightInfo getDestinationFlight() {
        return destinationFlight;
    }

    public void setDestinationFlight(DestinationFlightInfo destinationFlight) {
        this.destinationFlight = destinationFlight;
    }

    public DestinationFlightInfo getArrivalFlight() {
        return arrivalFlight;
    }

    public void setArrivalFlight(DestinationFlightInfo arrivalFlight) {
        this.arrivalFlight = arrivalFlight;
    }

    private DestinationFlightInfo destinationFlight;
    private DestinationFlightInfo arrivalFlight;
}
