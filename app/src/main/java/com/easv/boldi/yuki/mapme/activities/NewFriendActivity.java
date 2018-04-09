package com.easv.boldi.yuki.mapme.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
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

public class NewFriendActivity extends FriendActivityEditNew {

    private static final String TAG = "NewFriendActivity";
    private Button mSaveButton;
    private Button mCancelButton;
    private EditText mNameTxt;
    private EditText mEmailText;
    private EditText mWebsiteTxt;
    private EditText mPhoneText;

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

        mSaveButton.setOnClickListener(new View.OnClickListener() {
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
        checkImageIfNull();


        mFriendImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int i = 0; i < Init.PERMISSIONS.length; i++) {
                    String[] permission = {Init.PERMISSIONS[i]};
                    if (checkPermission(permission)) {
                        if (i == Init.PERMISSIONS.length - 1) {
                            Log.d(TAG, "onClick: opening the 'image selection dialog box'.");
                            ChangePhotoDialog dialog = new ChangePhotoDialog();
                            dialog.show(getSupportFragmentManager(), getString(R.string.change_photo_dialog));
                        }
                    } else {
                        verifyPermissions(permission);
                    }
                }
            }
        });


    }
    public void checkImageIfNull(){
        if (mSelectedImagePath == null){
            mSelectedImagePath = "drawable/face_1";
        }
    }
///------------------------------------------------------------------------------------------------------------
//------------------------------------ OnCreate END
//------------------------------------------------------------------------------------------------------------


}