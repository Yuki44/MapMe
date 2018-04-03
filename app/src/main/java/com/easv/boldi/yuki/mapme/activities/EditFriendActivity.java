package com.easv.boldi.yuki.mapme.activities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.easv.boldi.yuki.mapme.R;
import com.easv.boldi.yuki.mapme.entities.Friends;
import com.easv.boldi.yuki.mapme.utils.ChangePhotoDialog;
import com.easv.boldi.yuki.mapme.utils.Init;
import com.easv.boldi.yuki.mapme.utils.UniversalImageLoader;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by yuki on 30/03/2018.
 */

public class EditFriendActivity extends AppCompatActivity {

    private static final String TAG = "EditFriendActivity";

    private Friends mFriend;
    private EditText mPhoneNumber, mName, mEmail;
    private static final String CAMERA = Manifest.permission.CAMERA;
    private Toolbar toolbar;
    private String mSelectedImagePath;
    private int mPreviousKeyStroke;
    public static CircleImageView mFriendImage;
//    private static final int CAMERA_REQUEST_CODE = 5;

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
        mFriendImage = findViewById(R.id.friendImage);
        toolbar = findViewById(R.id.editFriendToolbar);
        //required for setting up the toolbar
        setSupportActionBar(toolbar);
        // setHasOptionsMenu(true);

        mSelectedImagePath = null;


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
                //execute the save method for the database
            }
        });

        ImageView ivCamera = findViewById(R.id.ivCamera);
        ivCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                /*
                Make sure all permissions have been verified before opening the dialog
                 */
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

        if (mFriend != null) {
            Log.d(TAG, "onCreate: received contact: " + mFriend.getName());
            init();
        } else {
            Log.d(TAG, "onCreate: I could not get the friend object");
            Toast.makeText(this, "Something went wrong", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    private void init() {
        mPhoneNumber.setText(mFriend.getPhone());
        mName.setText(mFriend.getName());
        mEmail.setText(mFriend.getEmail());
        UniversalImageLoader.setImage(mFriend.getProfileImage(), mFriendImage, null, "http://");
    }

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
        }
        return super.onOptionsItemSelected(item);
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
