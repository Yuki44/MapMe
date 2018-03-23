package com.easv.boldi.yuki.mapme.TabActivities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.easv.boldi.yuki.mapme.Dal.DAL;
import com.easv.boldi.yuki.mapme.Entities.Friends;
import com.easv.boldi.yuki.mapme.Adapters.FriendsListAdapter;
import com.easv.boldi.yuki.mapme.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by boldi on 2018. 03. 22..
 */

public class Tab1ListActivity extends android.support.v4.app.Fragment {
    private static final String TAG = "Tab1Fragment";
    public RecyclerView mFriendList;
    public FriendsListAdapter friendsListAdapter;
    public List<Friends> friendsList;
    private DAL dal;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tab1_list_fragment, container, false);
        mFriendList = view.findViewById(R.id.friend_list);
        mFriendList.setHasFixedSize(true);
        mFriendList.setLayoutManager(new LinearLayoutManager(getActivity()));

        friendsList = new ArrayList<>();
        DAL.setContext(getContext());
        dal = DAL.getInstance();
        friendsList = dal.getAllInfo();
        for (Friends f : friendsList) {
            Log.d("memap", "Friend: " + f.getName());

        }
        friendsListAdapter = new FriendsListAdapter(getContext(),friendsList);

        mFriendList.setAdapter(friendsListAdapter);
        return view;

    }


}
