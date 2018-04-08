package com.easv.boldi.yuki.mapme.tabactivities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.easv.boldi.yuki.mapme.R;
import com.easv.boldi.yuki.mapme.activities.FriendActivity;
import com.easv.boldi.yuki.mapme.adapters.FriendsListAdapter;
import com.easv.boldi.yuki.mapme.dal.DatabaseHelper;
import com.easv.boldi.yuki.mapme.entities.Friends;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * Created by yuki on 2018. 03. 22..
 */

public class Tab1ListActivity extends android.support.v4.app.Fragment {
    private static final String TAG = "Tab1Fragment";
    public ListView mFriendList;
    public FriendsListAdapter friendsListAdapter;
    private ArrayList<Friends> friendsList;
    public static ArrayList<Friends> friends;
    private String testImageURL = "cnet4.cbsistatic.com/img/QJcTT2ab-sYWwOGrxJc0MXSt3UI=/2011/10/27/a66dfbb7-fdc7-11e2-8c7c-d4ae52e62bcc/android-wallpaper5_2560x1600_1.jpg";
    private DatabaseHelper databaseHelper;
    private EditText mSearchFriends;
    private AppBarLayout viewFriendsBar, searchBar;
    private TextView mNoFriendsText;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tab1_list_fragment, container, false);
        mFriendList = view.findViewById(R.id.friendsList);
        mNoFriendsText = view.findViewById(R.id.textNoFriends);
        setupFriendsList();
        return view;
    }


    private void setupFriendsList() {

        // Database
        DatabaseHelper databaseHelper = new DatabaseHelper(getActivity());
        friendsList = databaseHelper.getAllInfo();
        for (Friends f : friendsList) {
            Log.d("memap", "Friend: " + f.getName());
        }

        Collections.sort(friendsList, new Comparator<Friends>() {
            @Override
            public int compare(Friends o1, Friends o2) {
                return o1.getName().compareToIgnoreCase(o2.getName());
            }
        });

        if (friendsList.isEmpty()) {
            Toast.makeText(getActivity(), "Add some friends with the green button!", Toast.LENGTH_SHORT).show();
            mNoFriendsText.setText("No friends to show");
        } else {
            mNoFriendsText.setText("");
        }

        mFriendList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d(TAG, "onClick: Navigating to friend activity");
                Intent i = new Intent(getActivity(), FriendActivity.class);
                i.putExtra("friendObj", friendsList.get(position));
                startActivity(i);
                getActivity().overridePendingTransition(0, 0);

            }
        });
        friendsListAdapter = new FriendsListAdapter(getActivity(), R.layout.list_item, friendsList, "");


        mFriendList.setAdapter(friendsListAdapter);

    }


}
