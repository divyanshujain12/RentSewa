package com.example.divyanshujain.rentsewa.GlobalClasses;


import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;


import com.example.divyanshujain.rentsewa.Interfaces.RecyclerViewClick;
import com.example.divyanshujain.rentsewa.Interfaces.SnackBarCallback;
import com.example.divyanshujain.rentsewa.Interfaces.UpdateUiCallback;
import com.example.divyanshujain.rentsewa.Utils.CallWebService;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by divyanshu on 5/29/2016.
 */
public class BaseActivity extends AppCompatActivity implements CallWebService.ObjectResponseCallBack, SnackBarCallback, UpdateUiCallback, RecyclerViewClick {

    @Override
    public void doAction() {

    }

    @Override
    public void updateUi(String string) {

    }

    public void showDialogFragment(DialogFragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragment.show(fragmentManager, fragment.getClass().getName());
    }

    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);
        if (level > TRIM_MEMORY_MODERATE) {
            // Restart app so data is reloaded
            android.os.Process.killProcess(android.os.Process.myPid());

        }

    }

    @Override
    public void onClickItem(int position, View view) {

    }

    @Override
    public void onJsonObjectSuccess(JSONObject response, int apiType) throws JSONException {

    }

    @Override
    public void onFailure(String str, int apiType) {

    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    protected void onStop() {
        super.onStop();

    }


}
