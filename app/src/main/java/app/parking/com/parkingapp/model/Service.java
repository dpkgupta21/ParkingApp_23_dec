package app.parking.com.parkingapp.model;

import java.io.Serializable;

/**
 * Created by Harish on 4/24/2016.
 */
public class Service implements Serializable {

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

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    String name = "", price = "", _id = "";
}
