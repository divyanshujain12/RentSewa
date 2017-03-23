package com.example.divyanshujain.rentsewa.Constants;

/**
 * Created by divyanshu.jain on 1/20/2017.
 */

public interface API {
    String BASE = "http://rentsewa.com/api/";
    String REGISTRATION = BASE + "registrationVendor";
    String LOGIN = BASE + "loginCombine";
    String GET_CATEGORIES = BASE + "getCategories";
    String FILTER_CATEGORY = BASE + "filterCategory";
    String CHANGE_PASSWORD = BASE + "changepassword";
    String GET_CITIES = BASE + "getCurrentCities";
    String REGISTRATION_FB_VISITOR = BASE + "registrationFacebookVisitor";
    String GET_CUSTOMER_DETAIL = BASE + "getUserProfile";
    String GET_PRODUCT_LISTING = BASE + "productListing";
    String GET_PRODUCT_REQUESTS = BASE + "getProductRequests";
    String REQUEST_FOR_A_CALL = BASE + "requestForaCall";
    String PRODUCT_DETAIL = BASE + "productDetail";
    String FCM_ID = BASE+"updateFCMId";
}
