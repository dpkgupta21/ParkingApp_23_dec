package app.parking.com.parkingapp.model;

import java.io.Serializable;

/**
 * Created by Harish on 4/24/2016.
 */
public class OrderConfirmationDTO implements Serializable {


    public String getPaymentTransactionId() {
        return paymentTransactionId;
    }

    public void setPaymentTransactionId(String paymentTransactionId) {
        this.paymentTransactionId = paymentTransactionId;
    }

    public String getVenueName() {
        return venueName;
    }

    public void setVenueName(String venueName) {
        this.venueName = venueName;
    }

    public String getSlotName() {
        return slotName;
    }

    public void setSlotName(String slotName) {
        this.slotName = slotName;
    }

    public String getSlotLevel() {
        return slotLevel;
    }

    public void setSlotLevel(String slotLevel) {
        this.slotLevel = slotLevel;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    String paymentTransactionId = "", venueName = "", slotName = "", slotLevel = "", status = "";
}
