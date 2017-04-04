package com.example.divyanshujain.rentsewa;

import android.Manifest;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ScrollView;

import com.example.divyanshujain.rentsewa.Constants.API;
import com.example.divyanshujain.rentsewa.Constants.Constants;
import com.example.divyanshujain.rentsewa.CustomViews.CustomAlertDialogs;
import com.example.divyanshujain.rentsewa.CustomViews.CustomToasts;
import com.example.divyanshujain.rentsewa.GlobalClasses.BaseActivity;
import com.example.divyanshujain.rentsewa.Interfaces.ChangePasswordInterface;
import com.example.divyanshujain.rentsewa.Interfaces.ImagePickDialogInterface;
import com.example.divyanshujain.rentsewa.Models.UserModel;
import com.example.divyanshujain.rentsewa.Models.ValidationModel;
import com.example.divyanshujain.rentsewa.Utils.CallWebService;
import com.example.divyanshujain.rentsewa.Utils.CommonFunctions;
import com.example.divyanshujain.rentsewa.Utils.ImageLoading;
import com.example.divyanshujain.rentsewa.Utils.MySharedPereference;
import com.example.divyanshujain.rentsewa.Utils.PermissionUtil;
import com.example.divyanshujain.rentsewa.Utils.PictureHelper;
import com.example.divyanshujain.rentsewa.Utils.UniversalParser;
import com.example.divyanshujain.rentsewa.Utils.Validation;
import com.example.divyanshujain.rentsewa.fragments.RuntimePermissionHeadlessFragment;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;
import com.neopixl.pixlui.components.edittext.EditText;
import com.neopixl.pixlui.components.textview.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

import static com.example.divyanshujain.rentsewa.Constants.ApiCodes.GET_USER_INFO;
import static com.example.divyanshujain.rentsewa.Constants.ApiCodes.UPDATE_USER;

/**
 * Created by divyanshu.jain on 9/29/2016.
 */

public class UserSettingActivity extends BaseActivity implements ImagePickDialogInterface, RuntimePermissionHeadlessFragment.PermissionCallback, View.OnTouchListener {


    @InjectView(R.id.toolbarView)
    Toolbar toolbarView;
    @InjectView(R.id.userIV)
    ImageView userIV;
    @InjectView(R.id.changeUserImageIV)
    ImageView changeUserImageIV;
    @InjectView(R.id.firstNameET)
    EditText firstNameET;
    @InjectView(R.id.mobileET)
    EditText mobileET;
    @InjectView(R.id.contactUsTV)
    TextView contactUsTV;
    String[] cameraPermission, externalStoragePermission;
    RuntimePermissionHeadlessFragment runtimePermissionHeadlessFragment;
    private static final int CAMERA_REQUEST = 101;
    private static final int GALLERY_REQUEST = 102;
    private static final int VIDEO_RECORDING_REQUEST = 103;
    @InjectView(R.id.updateTV)
    TextView updateTV;
    @InjectView(R.id.changePasswordET)
    EditText changePasswordET;
    @InjectView(R.id.contentSV)
    ScrollView contentSV;
    @InjectView(R.id.logoutTV)
    TextView logoutTV;
    @InjectView(R.id.addressET)
    EditText addressET;
    @InjectView(R.id.websiteET)
    EditText websiteET;
    @InjectView(R.id.changePasswordFL)
    FrameLayout changePasswordFL;
    private UserModel userModel;
    private Validation validation;
    private HashMap<View, String> hashMap;
    private Bitmap bitmap;
    private ImageLoading imageLoading;
    protected String[] mRequiredPermissions = {};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_setting);
        ButterKnife.inject(this);


        InitViews();
    }

    private void InitViews() {

        mRequiredPermissions = new String[]{
                Manifest.permission.CAMERA,
                Manifest.permission.RECORD_AUDIO
        };
        contentSV.setVisibility(View.GONE);
        createPermission();
        CommonFunctions.getInstance().configureToolbarWithBackButton(this, toolbarView, getString(R.string.setting));
        runtimePermissionHeadlessFragment = CommonFunctions.getInstance().addRuntimePermissionFragment(this, this);
        addValidation();
        imageLoading = new ImageLoading(this);
        CallWebService.getInstance(this, true, GET_USER_INFO).hitJsonObjectRequestAPI(CallWebService.POST, API.GET_CUSTOMER_DETAIL, CommonFunctions.customerIdJsonObject(this), this);
    }

    private void addValidation() {
        validation = new Validation();
        validation.addValidationField(new ValidationModel(firstNameET, Validation.TYPE_NAME_VALIDATION, getString(R.string.err_first_name)));
        //validation.addValidationField(new ValidationModel(lastNameET, Validation.TYPE_NAME_VALIDATION, getString(R.string.err_last_name)));
        validation.addValidationField(new ValidationModel(mobileET, Validation.TYPE_NAME_VALIDATION, getString(R.string.err_phone_number)));

    }

    private void createPermission() {
        cameraPermission = new String[]{Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE};
        externalStoragePermission = new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE};
    }

    @OnClick({R.id.contactUsTV, R.id.changeUserImageIV, R.id.updateTV})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.contactUsTV:
                Intent intent1 = new Intent(Intent.ACTION_VIEW, Uri.parse("http://info@smytex.com"));
                startActivity(intent1);
                break;
            case R.id.changeUserImageIV:
                CustomAlertDialogs.showImageSelectDialog(this, this);
                break;
            case R.id.updateTV:
                hashMap = validation.validate(this);
                if (hashMap != null) {
                    CallWebService.getInstance(this, true, UPDATE_USER).hitJsonObjectRequestAPI(CallWebService.POST, API.CHANGE_PASSWORD, createJsonForUpdateUserInfo(), this);
                }
                break;

        }
    }

    @Override
    public void Camera() {
        runtimePermissionHeadlessFragment.addAndCheckPermission(cameraPermission, CAMERA_REQUEST);
    }


    @Override
    public void Gallery() {
        runtimePermissionHeadlessFragment.addAndCheckPermission(externalStoragePermission, GALLERY_REQUEST);
    }

    @Override
    public void onPermissionGranted(int permissionType) {
        switch (permissionType) {
            case CAMERA_REQUEST:
                PictureHelper.getInstance().takeFromCamera(this, getString(R.string.take_picture));
                break;
            case GALLERY_REQUEST:
                PictureHelper.getInstance().takeFromGallery(this, getString(R.string.select_picture));
                break;
        }
    }

    @Override
    public void onPermissionDenied(int permissionType) {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        try {
            HashMap<String, Bitmap> imageMap = PictureHelper.getInstance().retrievePicturePath(this, requestCode, resultCode, data);
            setImageBitmap(imageMap);

        } catch (Exception e) {
            CustomToasts.getInstance(this).showErrorToast(e.getMessage());
            e.printStackTrace();
        }
    }

    private void setImageBitmap(HashMap<String, Bitmap> imageMap) {
        if (imageMap != null)
            for (String path : imageMap.keySet()) {
                bitmap = imageMap.get(path);
                if (bitmap != null) {
                    userIV.setImageBitmap(bitmap);
                    //  bitmap.recycle();
                }
            }
    }

    @Override
    public void onJsonObjectSuccess(JSONObject response, int apiType) throws JSONException {
        super.onJsonObjectSuccess(response, apiType);
        switch (apiType) {
            case GET_USER_INFO:
                userModel = UniversalParser.getInstance().parseJsonObject(response.getJSONObject(Constants.DATA), UserModel.class);
                updateUI();
                break;
            case UPDATE_USER:
                // sendLocalBroadCastForUserProfile();
                MySharedPereference.getInstance().setString(this, Constants.PASSWORD, hashMap.get(changePasswordET));
                CustomToasts.getInstance(this).showSuccessToast(response.getString(Constants.MESSAGE));
                finish();
                break;
        }
    }

    private void updateUI() {
        contentSV.setVisibility(View.VISIBLE);
        imageLoading.LoadImage(userModel.getProfile_image(), userIV, null);
        firstNameET.setText(userModel.getName());
        //lastNameET.setText(userModel.getLast_name());
        mobileET.setText(userModel.getPhone());
        // statusET.setText(userModel.getTimeline_msg());
        if (userModel.getUser_type().equalsIgnoreCase(Constants.VISITOR)) {
            changePasswordFL.setVisibility(View.GONE);
        } else {
            changePasswordET.setText(MySharedPereference.getInstance().getString(this, Constants.PASSWORD));
            changePasswordET.setOnTouchListener(this);
            validation.addValidationField(new ValidationModel(changePasswordET, Validation.TYPE_PASSWORD_VALIDATION, getString(R.string.err_pass)));
        }
    }

    /*public void sendLocalBroadCastForUserProfile() {
        Intent intent = new Intent();
        intent.setAction(Constants.UPDATE_USER_INFO);
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
    }*/

    private JSONObject createJsonForUpdateUserInfo() {
        JSONObject jsonObject = new JSONObject();

        try {
            if (bitmap != null) {
                String encodedImage = PictureHelper.getInstance().convertBitmapToBase64(bitmap);
                jsonObject.put(Constants.PROFILE_IMAGE, encodedImage);
            }
            jsonObject.put(Constants.WEBSITE, websiteET.getText().toString());
            jsonObject.put(Constants.ADDRESS, addressET.getText().toString());
            jsonObject.put(Constants.USER_NAME, hashMap.get(firstNameET));
            //jsonObject.put(Constants.LAST_NAME, hashMaps.get(lastNameET));
            jsonObject.put(Constants.EMAIl, userModel.getEmail());
            //jsonObject.put(Constants.PHONE_NUMBER, hashMaps.get(mobileET));
            jsonObject.put(Constants.DATE_OF_BIRTH, userModel.getDate_of_birth());
            jsonObject.put(Constants.PASSWORD, hashMap.get(changePasswordET));
             jsonObject.put(Constants.USER_TYPE, userModel.getUser_type());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return jsonObject;
    }


    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_UP) {
            CustomAlertDialogs.showChangePasswordDialog(this, new ChangePasswordInterface() {
                @Override
                public void onChangeSuccess(String changedPassword) {
                    changePasswordET.setText(changedPassword);
                }
            });
        }
        return false;
    }

    @Override
    protected void onResume() {
        super.onResume();
        CommonFunctions.getInstance().hideKeyBoard(this, userIV);
    }

    @OnClick(R.id.logoutTV)
    public void onClick() {
        FacebookSdk.sdkInitialize(this);
        LoginManager.getInstance().logOut();
        MySharedPereference.getInstance().clearSharedPreference(this);
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }

    private void checkHasPermissions() {
        if (PermissionUtil.isMNC())
            runtimePermissionHeadlessFragment.addAndCheckPermission(mRequiredPermissions, VIDEO_RECORDING_REQUEST);
    }

}
