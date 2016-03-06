package app.parking.com.parkingapp.model;

import java.io.Serializable;

/**
 * Created by Harish on 1/9/2016.
 */
public class ListOfServicesDTO implements Serializable {

    String name = "", price = "";

    public boolean isAdded() {
        return isAdded;
    }

    public void setIsAdded(boolean isAdded) {
        this.isAdded = isAdded;
    }

    boolean isAdded = false;

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


}
