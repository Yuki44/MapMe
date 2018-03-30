package com.easv.boldi.yuki.mapme.activities;

import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import com.easv.boldi.yuki.mapme.MainActivity;
import com.easv.boldi.yuki.mapme.R;
import com.easv.boldi.yuki.mapme.dal.DAL;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class NewFriendActivity extends AppCompatActivity {

    private static final String TAG = "NewFriendActivity";
    DAL dal;
    private Button mSaveButton;
    private Button mCancelButton;
    private EditText mNameTxt;
    private EditText mAddressTxt;
    private EditText mEmailText;
    private EditText mWebsiteTxt;
    private EditText mBirthdayTxt;
    private EditText mPhoneText;
    private double lat;
    private double lng;

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
        dal = DAL.getInstance();
        mSaveButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                startMainActivity();
            }
        });
        this.setTitle(null);




    }
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
            lat = address.getLatitude();
            lng = address.getLongitude();
        }
    }
    public void startMainActivity(){
        geoLocate();
        dal.insert(mNameTxt.getText().toString(),mEmailText.getText().toString(),mWebsiteTxt.getText().toString(),mAddressTxt.getText().toString(),mBirthdayTxt.getText().toString(),mPhoneText.getText().toString(),"",lat,lng);
        InputMethodManager imm = (InputMethodManager) getSystemService(
                Context.INPUT_METHOD_SERVICE);
        try {
            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        } catch (NullPointerException e) {
            Log.d(TAG, "setAppBarState: NullPointerException " + e.getMessage());
        }

        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
    }

}