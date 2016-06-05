package app.parking.com.parkingapp.iClasses;

/**
 * Created by Harish on 2/21/2016.
 */
public interface GlobalKeys {

    String HEADER_KEY_CONTENT_TYPE = "Content-Type";
    String HEADER_VALUE_CONTENT_TYPE = "application/json";

    String ACCEPT_KEY_CONTENT_TYPE = "Accept";
    String PASSWORD = "password";
    String EMAIL = "email";
    String LOGIN_API = "users/login";
    String LOGOUT_API = "users/logout";
    String FORGOT_PASSWORD_API = "users/forgetpassword";
    String SIGNUP_API = "users/signup";
    String CREATE_ORDER_API = "order/createOrder";
    String FETCH_SERVICES = "venue/services?name=";
    String HOLD_ORDER_API = "order/holdOrder";
    String PURCHASE_ORDER_API = "order/purchaseOrder";
    String ADD_TOKEN_PUSH = "notification/addtoken";
    String FLIGHT_INFO = "order/flightinfo";
    String ORDER_STATUS_INFO = "order/orderstatus";
    String ORDER_HISTORY = "order/history";
    String ORDER_HISTORY_API_KEY = "order_history";
    String ORDER_DETAILS = "order/details";
    String ORDER_DETAILS_API_KEY = "order_details";

    String PILLION_LOGIN_REQUEST_KEY = "login_api";
    String PILLION_LOGOUT_REQUEST_KEY = "logout_api";
    String PILLION_SIGNUP_REQUEST_KEY = "signup_api";
    String FETCH_SERVICES_REQUEST_KEY = "services_api";

    String MESSAGE = "message";
    String AUTHTOKEN = "authtoken";
    String RESPONSE__USERID = "userId";
    String TAG = "tag";
    String USERID = "userid";
    String FIRSTNAME = "firstname";
    String LASTNAME = "lastname";
    String MOBILE_NO = "mobile_no";

    String REG_TOKEN = "regToken";
    String DEVICE_ID = "deviceId";
    String DEVICE_TYPE = "deviceType";
    String EMAIL_KEY = "email";


    String flightName = "flightName";
    String flightNo = "flightNo";
}
