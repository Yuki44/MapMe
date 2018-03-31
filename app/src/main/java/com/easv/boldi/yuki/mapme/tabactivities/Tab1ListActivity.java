package com.easv.boldi.yuki.mapme.tabactivities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.easv.boldi.yuki.mapme.R;
import com.easv.boldi.yuki.mapme.activities.FriendActivity;
import com.easv.boldi.yuki.mapme.adapters.FriendsListAdapter;
import com.easv.boldi.yuki.mapme.dal.DAL;
import com.easv.boldi.yuki.mapme.entities.Friends;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by boldi on 2018. 03. 22..
 */

public class Tab1ListActivity extends android.support.v4.app.Fragment {
    private static final String TAG = "Tab1Fragment";
    public ListView mFriendList;
    public FriendsListAdapter friendsListAdapter;
    private List<Friends> friendsList;
    private DAL dal;
    private String testImageURL = "cnet4.cbsistatic.com/img/QJcTT2ab-sYWwOGrxJc0MXSt3UI=/2011/10/27/a66dfbb7-fdc7-11e2-8c7c-d4ae52e62bcc/android-wallpaper5_2560x1600_1.jpg";


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.tab1_list_fragment, container, false);
        mFriendList = view.findViewById(R.id.friendsList);
        setupFriendsList();

//        mFriendList = view.findViewById(R.id.friendsList);
//        ArrayList<Friends> friends = new ArrayList<>();
//        DAL.setContext(getContext());
//        dal = DAL.getInstance();
//        friends = dal.getAllInfo();
//        for (Friends f : friends) {
//            Log.d("memap", "Friend: " + f.getName());
//
//        }
//
//        friendsListAdapter = new FriendsListAdapter(getActivity(), R.layout.list_item, friends, "https://");
//
//        mFriendList.setAdapter(friendsListAdapter);

        return view;

    }


    private void setupFriendsList() {
        final ArrayList<Friends> friends = new ArrayList<>();
        friends.add(new Friends("Carlos 1", "Kirkevej", "carlos@gmail.com", "website.com", "23242", "50193717", testImageURL, 40.26656, 71.51736));
        friends.add(new Friends("Carlos 2", "Kirkevej", "carlos@gmail.com", "website.com", "23242", "50193717", testImageURL, 7.31616, -91.40302));
        friends.add(new Friends("Carlos 3", "Kirkevej", "carlos@gmail.com", "website.com", "23242", "50193717", testImageURL, 12.37369, -3.26697));
        friends.add(new Friends("Carlos 4", "Kirkevej", "carlos@gmail.com", "website.com", "23242", "50193717", testImageURL, 55.487488, 8.448578));
        friends.add(new Friends("Carlos 5", "Kirkevej", "carlos@gmail.com", "website.com", "23242", "50193717", testImageURL, 55.480410, 8.449680));
        friends.add(new Friends("Carlos 6", "Kirkevej", "carlos@gmail.com", "website.com", "23242", "50193717", testImageURL, 55.486917, 8.451578));
        friends.add(new Friends("Carlos 7", "Kirkevej", "carlos@gmail.com", "website.com", "23242", "50193717", testImageURL, 55.490792, 8.451696));
        friends.add(new Friends("Carlos 8", "Kirkevej", "carlos@gmail.com", "website.com", "23242", "50193717", testImageURL, 55.487284, 8.443706));
        friends.add(new Friends("Carlos 9", "Kirkevej", "carlos@gmail.com", "website.com", "23242", "50193717", testImageURL, 55.491958, 8.452909));
        friends.add(new Friends("Carlos 10", "Kirkevej", "carlos@gmail.com", "website.com", "23242", "50193717", testImageURL, 55.495041, 8.446801));
        friends.add(new Friends("Carlos 11", "Kirkevej", "carlos@gmail.com", "website.com", "23242", "50193717", testImageURL, 55.495477, 8.442538));
        friends.add(new Friends("Carlos 12", "Kirkevej", "carlos@gmail.com", "website.com", "23242", "50193717", testImageURL, 55.488805, 8.456383));

        friendsListAdapter = new FriendsListAdapter(getActivity(), R.layout.list_item, friends, "https://");
        mFriendList.setAdapter(friendsListAdapter);


        mFriendList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d(TAG, "onClick: Navigating to friend activity");
                Intent i = new Intent(getActivity(), FriendActivity.class);
                i.putExtra("friendObj", friends.get(position));
                startActivity(i);
                getActivity().overridePendingTransition(0, 0);

            }
        });

    }


}
