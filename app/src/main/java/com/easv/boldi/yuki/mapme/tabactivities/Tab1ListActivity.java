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
        //getArguments().get()

//        String query = ((MainActivity)getActivity()).search;

        return view;

    }


    private void setupFriendsList() {

        // MockUp Data

//        friends = new ArrayList<>();
//        friends.add(new Friends("Carlos Ognissanti", "Kirkevej", "carlos@gmail.com", "website.com", "23242", "50193717", testImageURL, 40.26656, 71.51736));
//        friends.add(new Friends("Michal Izdebski", "Kirkevej", "carlos@gmail.com", "website.com", "23242", "50193717", testImageURL, 7.31616, -91.40302));
//        friends.add(new Friends("Boldizsar Koppany", "Kirkevej", "carlos@gmail.com", "website.com", "23242", "50193717", testImageURL, 12.37369, -3.26697));
//        friends.add(new Friends("Daniel Matras", "Kirkevej", "carlos@gmail.com", "website.com", "23242", "50193717", testImageURL, 55.487488, 8.448578));
//        friends.add(new Friends("Anne Chuah", "Kirkevej", "carlos@gmail.com", "website.com", "23242", "50193717", testImageURL, 55.480410, 8.449680));
//        friends.add(new Friends("Aracelys Sosa", "Kirkevej", "carlos@gmail.com", "website.com", "23242", "50193717", testImageURL, 55.486917, 8.451578));
//        friends.add(new Friends("Stig Iversen", "Kirkevej", "carlos@gmail.com", "website.com", "23242", "50193717", testImageURL, 55.490792, 8.451696));
//        friends.add(new Friends("Veronica Pachowsky", "Kirkevej", "carlos@gmail.com", "website.com", "23242", "50193717", testImageURL, 55.487284, 8.443706));
//        friends.add(new Friends("Carlos 9", "Kirkevej", "carlos@gmail.com", "website.com", "23242", "50193717", testImageURL, 55.491958, 8.452909));
//        friends.add(new Friends("Carlos 10", "Kirkevej", "carlos@gmail.com", "website.com", "23242", "50193717", testImageURL, 55.495041, 8.446801));
//        friends.add(new Friends("Carlos 11", "Kirkevej", "carlos@gmail.com", "website.com", "23242", "50193717", testImageURL, 55.495477, 8.442538));
//        friends.add(new Friends("Carlos 12", "Kirkevej", "carlos@gmail.com", "website.com", "23242", "50193717", testImageURL, 55.488805, 8.456383));
////
//      friendsListAdapter = new FriendsListAdapter(getActivity(), R.layout.list_item, friends, "https://");
//
//        mFriendList.setAdapter(friendsListAdapter);
//        mFriendList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Log.d(TAG, "onClick: Navigating to friend activity");
//                Intent i = new Intent(getActivity(), FriendActivity.class);
//                i.putExtra("friendObj", friends.get(position));
//                startActivity(i);
//                getActivity().overridePendingTransition(0, 0);
//
//            }
//        });

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
            Toast.makeText(getActivity(), "There are no contacts to show", Toast.LENGTH_SHORT).show();
            mNoFriendsText.setText("No friends to show");
        } else {
            mNoFriendsText.setText("");
        }

//        Cursor cursor = databaseHelper.getAllFriends();
//        if(!cursor.moveToNext()){
//            Toast.makeText(getActivity(), "There are no contacts to show", Toast.LENGTH_SHORT).show();
//        }
//        while(cursor.moveToNext()){
//            friends.add(new Friends(
//                    cursor.getString(1), // Name
//                    cursor.getString(2), // Address
//                    cursor.getString(3), // Email
//                    cursor.getString(4), // Website
//                    cursor.getString(5), // Birthday
//                    cursor.getString(6), // Phone number
//                    cursor.getString(7), // Profile Image
//                    cursor.getString(8), // Latitude
//                    cursor.getString(9)  // Longitude
//            ));
//        }


//        DatabaseHelper.setContext(getContext());
//        databaseHelper = DatabaseHelper.getInstance();
//        friendsList = databaseHelper.getAllInfo();
//        for (Friends f : friendsList) {
//            Log.d("memap", "Friend: " + f.getName());
//        }
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
