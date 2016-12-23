package com.example.divyanshujain.rentsewa.Interfaces;

/**
 * Created by divyanshu on 3/12/16.
 */

public interface InAppCallback {
    void onPurchasedSuccess(String productID, String transactionID);
    void onFailure();
}
