package app.parking.com.parkingapp.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Harish on 4/24/2016.
 */
public class ServiceInfoDTO implements Serializable {


    public List<Service> getServices() {
        return services;
    }

    public void setServices(List<Service> services) {
        this.services = services;
    }

    private List<Service> services;
}
