package com.easv.boldi.yuki.mapme.activities;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.easv.boldi.yuki.mapme.MainActivity;
import com.easv.boldi.yuki.mapme.R;
import com.easv.boldi.yuki.mapme.dal.DatabaseHelper;
import com.easv.boldi.yuki.mapme.entities.Friends;
import com.easv.boldi.yuki.mapme.utils.ChangePhotoDialog;
import com.easv.boldi.yuki.mapme.utils.Init;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class NewFriendActivity extends FriendActivityEditNew {

    private static final String TAG = "NewFriendActivity";
    DatabaseHelper databaseHelper;
    private Button mSaveButton;
    private Button mCancelButton;
    private EditText mNameTxt;
    private EditText mAddressTxt;
    private EditText mEmailText;
    private EditText mWebsiteTxt;
    private EditText mPhoneText;

    private String lat;
    private String lng;
    static final int REQUEST_IMAGE_CAPTURE = 1;
    private static final String CAMERA = Manifest.permission.CAMERA;

//------------------------------------------------------------------------------------------------------------
//------------------------------------ OnCreate
//------------------------------------------------------------------------------------------------------------

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_friend);
        mNameTxt = findViewById(R.id.name_Info);
        mAddressTxt = findViewById(R.id.addressTxt);
        mEmailText = findViewById(R.id.emailTxt);
        mWebsiteTxt = findViewById(R.id.websiteTxt);
        mBirthdayTxt = findViewById(R.id.birthdayTxt);
        mPhoneText = findViewById(R.id.phone_Info);
        mSaveButton = findViewById(R.id.saveBtn);
        mCancelButton = findViewById(R.id.cancelBtn);
        mFriendImage = findViewById(R.id.addFriendImage);

        initOnTextChangedListener();

        mCancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                InputMethodManager imm = (InputMethodManager) getSystemService(
                        Context.INPUT_METHOD_SERVICE);
                try {
                    imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                } catch (NullPointerException e) {
                    Log.d(TAG, "setAppBarState: NullPointerException " + e.getMessage());
                }
                finish();
            }
        });

        mSaveButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: attempting to save a new contact.");
                if (checkStringIfNull(mNameTxt.getText().toString()) && checkStringIfNull(mPhoneText.getText().toString())
                        && checkStringIfNull(mAddressTxt.getText().toString())) {
                    geoLocate();
                    Log.d(TAG, "onClick: saving new contact: " + mNameTxt.getText().toString());
                    DatabaseHelper databaseHelper = new DatabaseHelper(NewFriendActivity.this);
                    Friends friend = new Friends(mNameTxt.getText().toString(),
                            mPhoneText.getText().toString(),
                            mAddressTxt.getText().toString(),
                            mSelectedImagePath,
                            mBirthdayTxt.getText().toString(),
                            mEmailText.getText().toString(),
                            mWebsiteTxt.getText().toString(),
                            lat, lng);
                    if (databaseHelper.addFriend(friend)) {
                        Toast.makeText(NewFriendActivity.this, "Contact Saved", Toast.LENGTH_SHORT).show();
                        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                        try {
                            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                        } catch (NullPointerException e) {
                            Log.d(TAG, "setAppBarState: NullPointerException " + e.getMessage());
                        }

                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(NewFriendActivity.this, "Something went wrong...", Toast.LENGTH_SHORT).show();
                    }


                } else {
                    Toast.makeText(NewFriendActivity.this, "Name, Phone Number and Address are necessary", Toast.LENGTH_LONG).show();
                }

            }
        });
        this.setTitle(null);


        mFriendImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                for (int i = 0; i < Init.PERMISSIONS.length; i++) {
                    String[] permission = {Init.PERMISSIONS[i]};
                    if (getCameraPermission()) {
                        if (i == Init.PERMISSIONS.length - 1) {
                            Log.d(TAG, "onClick: opening the 'image selection dialog box'.");
                            ChangePhotoDialog dialog = new ChangePhotoDialog();
                            dialog.show(getSupportFragmentManager(), getString(R.string.change_photo_dialog));
                        }
                    } else {
                        getCameraPermission();
                    }
                }
            }
        });



    }
//------------------------------------------------------------------------------------------------------------
//------------------------------------ OnCreate END
//------------------------------------------------------------------------------------------------------------


    private void geoLocate() {
        Log.d(TAG, "geoLocate: geoLocating");
        String searchString = mAddressTxt.getText().toString();
        Geocoder geocoder = new Geocoder(this);
        List<Address> list = new ArrayList<>();
        try {
            list = geocoder.getFromLocationName(searchString, 1);
        } catch (IOException e) {
            Log.e(TAG, "geoLocate: IOException" + e.getMessage());
        }
        if (list.size() > 0) {
            Address address = list.get(0);
            Log.d(TAG, "geoLocate: Found a location " + address.toString());

            Double latitude = address.getLatitude();
            Double longitude = address.getLongitude();
            lat = Double.toString(latitude);
            lng = Double.toString(longitude);
        }
    }

    private boolean checkStringIfNull(String string) {
        return !string.equals("");
    }

    private boolean getCameraPermission() {
        Log.d(TAG, "getCameraPermission: Getting CallPermission");
        String[] permission = {Manifest.permission.CAMERA};

        if (ContextCompat.checkSelfPermission(this.getApplicationContext(), CAMERA) == PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(this, permission, Init.CAMERA_REQUEST_CODE);
            return false;
        } else {
            return true;
        }
    }






}