package app.parking.com.parkingapp.iClasses;

/**
 * Created by Harish on 2/21/2016.
 */
public interface GlobalKeys {

    String HEADER_KEY_CONTENT_TYPE = "Content-Type";
    String HEADER_VALUE_CONTENT_TYPE = "application/json";


    String PASSWORD = "password";
    String EMAIL = "email";
    String LOGIN_API = "users/login";
    String LOGOUT_API = "users/logout";
    String SIGNUP_API = "users/signup";
    String CREATE_ORDER_API = "order/createOrder";
    String FETCH_SERVICES = "venue/services?name=";
    String HOLD_ORDER_API = "order/holdOrder";
    String PURCHASE_ORDER_API = "order/purchaseOrder";
    String ADD_TOKEN_PUSH = "notification/addtoken";


    String PILLION_LOGIN_REQUEST_KEY = "login_api";
    String PILLION_LOGOUT_REQUEST_KEY = "logout_api";
    String PILLION_SIGNUP_REQUEST_KEY = "signup_api";
    String FETCH_SERVICES_REQUEST_KEY = "services_api";

    String MESSAGE = "message";
    String AUTHTOKEN = "authtoken";
    String FIRSTNAME = "firstname";
    String LASTNAME = "lastname";

    String REG_TOKEN="regToken";
    String DEVICE_ID="deviceId";
    String DEVICE_TYPE="deviceType";


}
