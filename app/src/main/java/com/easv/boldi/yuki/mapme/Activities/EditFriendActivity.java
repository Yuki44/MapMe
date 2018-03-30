package com.easv.boldi.yuki.mapme.Activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import com.easv.boldi.yuki.mapme.Entities.Friends;
import com.easv.boldi.yuki.mapme.R;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by yuki on 30/03/2018.
 */

public class EditFriendActivity extends AppCompatActivity {

    private static final String TAG = "EditFriendActivity";

    private Friends mFriend;
    private EditText mPhoneNumber, mName, mEmail;
    private CircleImageView mFriendImage;
    private Spinner mSelectDevice;
    private Toolbar toolbar;
    private String mSelectedImagePath;
    private int mPreviousKeyStroke;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editfriend);
        Log.d(TAG, "onCreate: started.");

        ImageView ivBackArrow = findViewById(R.id.ivBackArrow);
        ivBackArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: clicked back arrow.");
                finish();
            }
        });

    }
}
