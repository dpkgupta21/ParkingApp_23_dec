package app.parking.com.parkingapp.model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Harish on 2/28/2016.
 */
public class CreateOrderResponseDTO implements Serializable {

    public VehicleInfoDTO getVehicleInfo() {
        return vehicleInfo;
    }

    public void setVehicleInfo(VehicleInfoDTO vehicleInfo) {
        this.vehicleInfo = vehicleInfo;
    }

    public OrderStatusDTO getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(OrderStatusDTO orderStatus) {
        this.orderStatus = orderStatus;
    }

    public OrderConfirmationDTO getOrderConfirmation() {
        return orderConfirmation;
    }

    public void setOrderConfirmation(OrderConfirmationDTO orderConfirmation) {
        this.orderConfirmation = orderConfirmation;
    }

    public ServiceInfoDTO getServiceInfo() {
        return serviceInfo;
    }

    public void setServiceInfo(ServiceInfoDTO serviceInfo) {
        this.serviceInfo = serviceInfo;
    }

//    public FlightInfoDTO getFlightInfo() {
//        return flightInfo;
//    }
//
//    public void setFlightInfo(FlightInfoDTO flightInfo) {
//        this.flightInfo = flightInfo;
//    }

    private VehicleInfoDTO vehicleInfo;
    private OrderStatusDTO orderStatus;
    private OrderConfirmationDTO orderConfirmation;
    private ServiceInfoDTO serviceInfo;
//    private FlightInfoDTO flightInfo;
}
