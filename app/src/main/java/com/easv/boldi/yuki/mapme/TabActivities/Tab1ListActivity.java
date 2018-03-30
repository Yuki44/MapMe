package com.easv.boldi.yuki.mapme.TabActivities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.easv.boldi.yuki.mapme.Activities.FriendActivity;
import com.easv.boldi.yuki.mapme.Adapters.FriendsListAdapter;
import com.easv.boldi.yuki.mapme.Dal.DAL;
import com.easv.boldi.yuki.mapme.Entities.Friends;
import com.easv.boldi.yuki.mapme.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by boldi on 2018. 03. 22..
 */

public class Tab1ListActivity extends android.support.v4.app.Fragment {
    private static final String TAG = "Tab1Fragment";
    public ListView mFriendList;
    public FriendsListAdapter friendsListAdapter;
    public List<Friends> friendsList;
    private DAL dal;
    private String testImageURL = "cnet4.cbsistatic.com/img/QJcTT2ab-sYWwOGrxJc0MXSt3UI=/2011/10/27/a66dfbb7-fdc7-11e2-8c7c-d4ae52e62bcc/android-wallpaper5_2560x1600_1.jpg";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.tab1_list_fragment, container, false);
        mFriendList = view.findViewById(R.id.friendsList);
        setupFriendsList();
//
//        mFriendList = view.findViewById(R.id.friendsList);
//        mFriendList.setHasFixedSize(true);
//        mFriendList.setLayoutManager(new LinearLayoutManager(getActivity()));
//
//        friendsList = new ArrayList<>();
//        DAL.setContext(getContext());
//        dal = DAL.getInstance();
//        friendsList = dal.getAllInfo();
//        for (Friends f : friendsList) {
//            Log.d("memap", "Friend: " + f.getName());
//
//        }
//        friendsListAdapter = new FriendsListAdapter(getContext(),friendsList);
//
//        mFriendList.setAdapter(friendsListAdapter);
//
        return view;

    }

    private void setupFriendsList() {
        final ArrayList<Friends> friends = new ArrayList<>();
        friends.add(new Friends(20, "Carlos 1", "Kirkevej", "carlos@gmail.com", "website.com", "23242", "50193717", testImageURL));
        friends.add(new Friends(21, "Carlos 2", "Kirkevej", "carlos@gmail.com", "website.com", "23242", "50193717", testImageURL));
        friends.add(new Friends(22, "Carlos 3", "Kirkevej", "carlos@gmail.com", "website.com", "23242", "50193717", testImageURL));
        friends.add(new Friends(23, "Carlos 4", "Kirkevej", "carlos@gmail.com", "website.com", "23242", "50193717", testImageURL));
        friends.add(new Friends(24, "Carlos 5", "Kirkevej", "carlos@gmail.com", "website.com", "23242", "50193717", testImageURL));
        friends.add(new Friends(25, "Carlos 6", "Kirkevej", "carlos@gmail.com", "website.com", "23242", "50193717", testImageURL));
        friends.add(new Friends(26, "Carlos 7", "Kirkevej", "carlos@gmail.com", "website.com", "23242", "50193717", testImageURL));
        friends.add(new Friends(27, "Carlos 8", "Kirkevej", "carlos@gmail.com", "website.com", "23242", "50193717", testImageURL));
        friends.add(new Friends(28, "Carlos 9", "Kirkevej", "carlos@gmail.com", "website.com", "23242", "50193717", testImageURL));
        friends.add(new Friends(29, "Carlos 10", "Kirkevej", "carlos@gmail.com", "website.com", "23242", "50193717", testImageURL));
        friends.add(new Friends(30, "Carlos 11", "Kirkevej", "carlos@gmail.com", "website.com", "23242", "50193717", testImageURL));
        friends.add(new Friends(31, "Carlos 12", "Kirkevej", "carlos@gmail.com", "website.com", "23242", "50193717", testImageURL));

        friendsListAdapter = new FriendsListAdapter(getActivity(), R.layout.list_item, friends, "https://");
        mFriendList.setAdapter(friendsListAdapter);


        mFriendList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d(TAG, "onClick: Navigating to friend activity");
                Intent i = new Intent(getActivity(), FriendActivity.class);
                startActivity(i);
                getActivity().overridePendingTransition(0, 0);
            }
        });

    }


}
