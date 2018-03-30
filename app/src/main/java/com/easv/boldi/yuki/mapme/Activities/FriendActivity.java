package com.easv.boldi.yuki.mapme.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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

public class FriendActivity extends AppCompatActivity {

    private static final String TAG = "FriendActivity";

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
        setContentView(R.layout.activity_friend);
        Log.d(TAG, "onCreate: started.");

        toolbar = findViewById(R.id.friendToolbar);

        setSupportActionBar(toolbar);


        ImageView ivBackArrow = findViewById(R.id.ivBackArrow);
        ivBackArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: clicked back arrow.");
                finish();
            }
        });

        ImageView ivEdit = findViewById(R.id.ivEdit);
        ivEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: clicked edit button.");
                Intent editFriendIntent = new Intent(FriendActivity.this, EditFriendActivity.class);
                startActivity(editFriendIntent);
            }
        });

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


}
