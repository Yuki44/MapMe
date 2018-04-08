package com.easv.boldi.yuki.mapme.activities;

import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class FriendActivityEditNew extends AppCompatActivity {

    private static final String TAG = "FriendActivityEditNew";
    public static CircleImageView mFriendImage;
    public static String mSelectedImagePath;
    public EditText mBirthdayTxt;
    private int mPreviousKeyStroke;
    private static final int REQUEST_CODE = 8;
    public String lat, lng;
    public EditText mAddressTxt;

    public void initOnTextChangedListener() {

        mBirthdayTxt.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                mPreviousKeyStroke = keyCode;
                return false;
            }
        });
        mBirthdayTxt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String number = s.toString();
                Log.d(TAG, "afterTextChanged: " + number);
                if (number.length() == 4 && !number.contains("/")) {

                    number = String.format("%s/%s", s.toString().substring(0, 2),
                            s.toString().substring(2, 4));
                    mBirthdayTxt.setText(number);
                    mBirthdayTxt.setSelection(number.length());
                    Log.d(TAG, "afterTextChanged: " + number + "<><><><><><><><><><><><><><><><><><>");
                } else if (number.length() == 10 && !number.contains("/")) {

                    number = number.substring(0, 10);
                    mBirthdayTxt.setText(number);
                    mBirthdayTxt.setSelection(number.length());
                }

            }

        });
    }


    /**
     * Generalized method for asking permission. Can pass any array of permissions
     *
     * @param permissions
     */
    public void verifyPermissions(String[] permissions) {
        Log.d(TAG, "verifyPermissions: asking user for permissions.");
        ActivityCompat.requestPermissions(
                FriendActivityEditNew.this,
                permissions,
                REQUEST_CODE
        );
    }

    /**
     * Checks to see if permission was granted for the passed parameters
     * ONLY ONE PERMISSION MAYT BE CHECKED AT A TIME
     *
     * @param permission
     * @return
     */
    public boolean checkPermission(String[] permission) {
        Log.d(TAG, "checkPermission: checking permissions for:" + permission[0]);

        int permissionRequest = ActivityCompat.checkSelfPermission(
                FriendActivityEditNew.this,
                permission[0]);

        if (permissionRequest != PackageManager.PERMISSION_GRANTED) {
            Log.d(TAG, "checkPermission: \n Permissions was not granted for: " + permission[0]);
            return false;
        } else {
            return true;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        Log.d(TAG, "onRequestPermissionsResult: requestCode: " + requestCode);

        switch (requestCode) {
            case REQUEST_CODE:
                for (int i = 0; i < permissions.length; i++) {
                    if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                        Log.d(TAG, "onRequestPermissionsResult: User has allowed permission to access: " + permissions[i]);
                    } else {
                        break;
                    }
                }
                break;
        }
    }

    public boolean checkStringIfNull(String string) {
        return !string.equals("");
    }

    public void geoLocate() {
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

}
