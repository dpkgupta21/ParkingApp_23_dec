package app.parking.com.parkingapp.model;

import java.io.Serializable;

/**
 * Created by Harish on 4/24/2016.
 */
public class OrderStatusDTO implements Serializable {

    private String status = "";
    private String order_id = "";
    private String orderTotal = "";
    private String orderTax = "";

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

    public String getOrderTotal() {
        return orderTotal;
    }

    public void setOrderTotal(String orderTotal) {
        this.orderTotal = orderTotal;
    }

    public String getOrderTax() {
        return orderTax;
    }

    public void setOrderTax(String orderTax) {
        this.orderTax = orderTax;
    }


}
