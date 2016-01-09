package app.parking.com.parkingapp.usermodel.home;

import java.io.Serializable;

/**
 * Created by Harish on 1/9/2016.
 */
public class ServicesModel implements Serializable {

    String serviceName = "", serviceCost = "";

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getServiceCost() {
        return serviceCost;
    }

    public void setServiceCost(String serviceCost) {
        this.serviceCost = serviceCost;
    }

}
