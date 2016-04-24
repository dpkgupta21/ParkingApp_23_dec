package app.parking.com.parkingapp.iClasses;

/**
 * Created by Harish on 4/20/2016.
 */
public interface FlightDetailInterface {

    void onArrivalDetailsSelected(String airline,String from,String status,String flightNumber,String term,String departTime,String arrivalTime);

    void onDepartureDetailsSelected(String airline,String from,String status,String flightNumber,String term,String departTime,String arrivalTime);
}
