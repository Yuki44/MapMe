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
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.easv.boldi.yuki.mapme.R;
import com.easv.boldi.yuki.mapme.adapters.FriendPropertyListAdapter;
import com.easv.boldi.yuki.mapme.entities.Friends;
import com.easv.boldi.yuki.mapme.utils.UniversalImageLoader;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by yuki on 30/03/2018.
 */

public class FriendActivity extends AppCompatActivity {

    private static final String TAG = "FriendActivity";

    private Friends mFriend;
    private Toolbar toolbar;
    private TextView mFriendName;
    private CircleImageView mFriendImage;
    private String mSelectedImagePath;
    private int mPreviousKeyStroke;
    private ListView mListView;
    private static final String CALL_PHONE = Manifest.permission.CALL_PHONE;
    private static final int CALL_PHONE_REQUEST_CODE = 1234;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend);
        Log.d(TAG, "onCreate: started Friend Activity view");
        Intent i = getIntent();
        mFriend = (Friends) i.getSerializableExtra("friendObj");
        mListView = findViewById(R.id.lvFriendProperties);
        toolbar = findViewById(R.id.friendToolbar);
        setSupportActionBar(toolbar);
        mFriendName = findViewById(R.id.friendNameDetail);
        mFriendImage = findViewById(R.id.friendImageDetail);


        getCallPermission();
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
                editFriendIntent.putExtra("friendObj", mFriend);
                startActivity(editFriendIntent);
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
        mFriendName.setText(mFriend.getName());
        UniversalImageLoader.setImage(mFriend.getProfileImage(), mFriendImage, null, "");

        ArrayList<String> properties = new ArrayList<>();
        properties.add(mFriend.getPhone());
        properties.add(mFriend.getEmail());
        FriendPropertyListAdapter adapter = new FriendPropertyListAdapter(FriendActivity.this, R.layout.layout_cardview, properties);
        mListView.setAdapter(adapter);
        mListView.setDivider(null);
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

    private void getCallPermission() {
        Log.d(TAG, "getCallPermission: Getting CallPermission");
        String[] permission = {Manifest.permission.CALL_PHONE};

        if (ContextCompat.checkSelfPermission(this.getApplicationContext(), CALL_PHONE) == PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(this, permission, CALL_PHONE_REQUEST_CODE);
        }

    }

}
