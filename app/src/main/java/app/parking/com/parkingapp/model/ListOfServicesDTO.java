package app.parking.com.parkingapp.model;

import java.io.Serializable;

/**
 * Created by Harish on 2/28/2016.
 */
public class ListOfServicesDTO implements Serializable {

    public String name, price;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }


}
