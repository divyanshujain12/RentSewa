package com.example.divyanshujain.rentsewa.Constants;

/**
 * Created by divyanshu.jain on 1/20/2017.
 */

public interface API {
    String BASE = "http://rentsewa.com/api/";
    String REGISTRATION = BASE + "registrationVendor";
    String LOGIN = BASE + "loginCombine";
    String FORGOT_PASSWORD = BASE + "forgetpassword";
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
    String GET_SLIDER = BASE + "getSlider";
    String FCM_ID = BASE + "updateFCMId";
    String GET_PRODUCT_LOCATION = BASE + "getProductLocation";
    String GET_ALL_COUNTRY = BASE + "getAllCountry";
    String GET_ALL_STATE = BASE + "getStateByCountry";
    String GET_ALL_CITIES = BASE + "getCityStateWise";
    String POST_PRODUCT = BASE + "productProcess";
    String PRODUCT_EDIT_PROCESS = BASE + "productEditProcess";
    String DELETE_PRODUCT = BASE + "deleteproduct";
    String VENDOR_PRODUCT_LISTING = BASE + "vendorProductListing";
    String GET_PRODUCT_DETAIL_BY_PRODUCT_ID = BASE + "getproductDetailByProductId";

}
