package com.easv.boldi.yuki.mapme;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;

public class NewFriendActivity extends AppCompatActivity {

    private Button mSaveButton;
    private EditText mNameTxt;
    private EditText mAddressTxt;
    private EditText mEmailText;
    private EditText mWebsiteTxt;
    private EditText mBirthdayTxt;
    private EditText mPhoneText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_friend);

        mNameTxt = findViewById(R.id.nameTxt);
        mAddressTxt = findViewById(R.id.addressTxt);
        mEmailText = findViewById(R.id.emailTxt);

        mWebsiteTxt = findViewById(R.id.websiteTxt);
        mBirthdayTxt = findViewById(R.id.birthdayTxt);
        mPhoneText = findViewById(R.id.phoneTxt);

        mSaveButton = findViewById(R.id.saveBtn);

        mSaveButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Add Friend Feature Under Development", Snackbar.LENGTH_LONG)
                     .setAction("Action", null).show();

            }
        });

    }
}
