package com.easv.boldi.yuki.mapme.TabActivities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import com.easv.boldi.yuki.mapme.Dal.DAL;
import com.easv.boldi.yuki.mapme.MainActivity;
import com.easv.boldi.yuki.mapme.R;

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
                dal.insert(mNameTxt.getText().toString(),mEmailText.getText().toString(),mWebsiteTxt.getText().toString(),mAddressTxt.getText().toString(),mBirthdayTxt.getText().toString(),mPhoneText.getText().toString());
                InputMethodManager imm = (InputMethodManager) getSystemService(
                        Context.INPUT_METHOD_SERVICE);
                try {
                    imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                } catch (NullPointerException e) {
                    Log.d(TAG, "setAppBarState: NullPointerException " + e.getMessage());
                }
                Snackbar.make(view, "Add" + mNameTxt.getText() + " was succesfully added to your list", Snackbar.LENGTH_SHORT)
                        .setAction("Action", null).show();
                Intent startShapes = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(startShapes);

            }
        });
        this.setTitle(null);




    }


}
