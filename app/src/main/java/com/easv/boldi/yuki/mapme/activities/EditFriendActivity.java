package com.easv.boldi.yuki.mapme.activities;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.easv.boldi.yuki.mapme.MainActivity;
import com.easv.boldi.yuki.mapme.R;
import com.easv.boldi.yuki.mapme.dal.DatabaseHelper;
import com.easv.boldi.yuki.mapme.entities.Friends;
import com.easv.boldi.yuki.mapme.utils.ChangePhotoDialog;
import com.easv.boldi.yuki.mapme.utils.Init;
import com.easv.boldi.yuki.mapme.utils.UniversalImageLoader;

/**
 * Created by yuki on 30/03/2018.
 */

public class EditFriendActivity extends FriendActivityEditNew {

    private static final String TAG = "EditFriendActivity";

    private Friends mFriend;
    private EditText mPhoneNumber, mName, mEmail, mWebsite;
    private Toolbar toolbar;


    //------------------------------------------------------------------------------------------------------------
//------------------------------------ OnCreate
//------------------------------------------------------------------------------------------------------------
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editfriend);
        Log.d(TAG, "onCreate: started.");
        Intent i = getIntent();
        mFriend = (Friends) i.getSerializableExtra("friendObj");

        mPhoneNumber = findViewById(R.id.etFriendPhone);
        mName = findViewById(R.id.etFriendName);
        mEmail = findViewById(R.id.etFriendEmail);
        mBirthdayTxt = findViewById(R.id.etFriendBirthday);
        mWebsite = findViewById(R.id.etFriendWebsite);
        mAddressTxt = findViewById(R.id.etFriendAddress);
        mFriendImage = findViewById(R.id.addFriendImage);
        toolbar = findViewById(R.id.editFriendToolbar);
        //required for setting up the toolbar
        setSupportActionBar(toolbar);
        // setHasOptionsMenu(true);



        ImageView ivBackArrow = findViewById(R.id.ivBackArrow);
        ivBackArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: clicked back arrow.");
                finish();
            }
        });

        ImageView ivCheckMark = findViewById(R.id.ivCheckMark);
        ivCheckMark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: saving the edited contact.");
                if (checkStringIfNull(mName.getText().toString()) && checkStringIfNull(mPhoneNumber.getText().toString())
                        && checkStringIfNull(mAddressTxt.getText().toString())) {
                    geoLocate();
                    Log.d(TAG, "onClick: saving edited contact: " + mName.getText().toString());
                    DatabaseHelper databaseHelper = new DatabaseHelper(EditFriendActivity.this);
                    Cursor cursor = databaseHelper.getFriendID(mFriend);
                    int friendID = -1;
                    while (cursor.moveToNext()) {
                        friendID = cursor.getInt(0);
                    }
                    if (friendID > -1) {
                        if (mSelectedImagePath != null) {
                            mFriend.setProfileImage(mSelectedImagePath);
                        }
                        mFriend.setName(mName.getText().toString());
                        mFriend.setPhone(mPhoneNumber.getText().toString());
                        mFriend.setAddress(mAddressTxt.getText().toString());
                        mFriend.setBirthday(mBirthdayTxt.getText().toString());
                        mFriend.setEmail(mEmail.getText().toString());
                        mFriend.setWebsite(mWebsite.getText().toString());
                        mFriend.setLatitude(lat);
                        mFriend.setLongitude(lng);
                    }
                    if (databaseHelper.updateFriend(mFriend, friendID)) {
                        Toast.makeText(EditFriendActivity.this, "Contact Saved", Toast.LENGTH_SHORT).show();
                        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                        try {
                            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                        } catch (NullPointerException e) {
                            Log.d(TAG, "setAppBarState: NullPointerException " + e.getMessage());
                        }
                        Intent i = new Intent(EditFriendActivity.this, FriendActivity.class);
                        i.putExtra("friendObj", mFriend);
                        startActivity(i);
                        finish();
                    } else {
                        Toast.makeText(EditFriendActivity.this, "Something went wrong...", Toast.LENGTH_SHORT).show();
                    }

                } else {
                    Toast.makeText(EditFriendActivity.this, "Name, Phone Number and Address are necessary", Toast.LENGTH_LONG).show();
                }
            }
        });

        ImageView ivCamera = findViewById(R.id.ivCamera);
        ivCamera.setOnClickListener(new View.OnClickListener() {
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

        if (mFriend != null) {
            Log.d(TAG, "onCreate: received contact: " + mFriend.getName());
            init();
        } else {
            Log.d(TAG, "onCreate: I could not get the friend object");
            Toast.makeText(this, "Something went wrong", Toast.LENGTH_SHORT).show();
            finish();
        }

        initOnTextChangedListener();
    }
    //------------------------------------------------------------------------------------------------------------
    //------------------------------------ OnCreate END
    //------------------------------------------------------------------------------------------------------------
    //------------------------------------------------------------------------------------------------------------
    //------------------------------------ Override Methods
    //------------------------------------------------------------------------------------------------------------

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.friend_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menuitem_delete:
                Log.d(TAG, "onOptionsItemSelected: deleting friend");
                DatabaseHelper databaseHelper = new DatabaseHelper(EditFriendActivity.this);
                Cursor cursor = databaseHelper.getFriendID(mFriend);

                int friendID = -1;
                while (cursor.moveToNext()) {
                    friendID = cursor.getInt(0);
                }
                if (friendID > -1) {
                    if (databaseHelper.deleteFriend(friendID) > 0) {
                        Toast.makeText(EditFriendActivity.this, "Contact Deleted", Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(EditFriendActivity.this, MainActivity.class);
                        startActivity(i);
                        finish();
                    } else {
                        Toast.makeText(EditFriendActivity.this, "Database Error", Toast.LENGTH_SHORT).show();
                    }
                }
        }
        return super.onOptionsItemSelected(item);
    }
    //------------------------------------------------------------------------------------------------------------
    //------------------------------------ Override Methods END
    //------------------------------------------------------------------------------------------------------------

    private void init() {
        mName.setText(mFriend.getName());
        mPhoneNumber.setText(mFriend.getPhone());
        mAddressTxt.setText(mFriend.getAddress());
        UniversalImageLoader.setImage(mFriend.getProfileImage(), mFriendImage, null, "");
        mBirthdayTxt.setText(mFriend.getBirthday());
        mWebsite.setText(mFriend.getWebsite());
        mEmail.setText(mFriend.getEmail());
    }


}
