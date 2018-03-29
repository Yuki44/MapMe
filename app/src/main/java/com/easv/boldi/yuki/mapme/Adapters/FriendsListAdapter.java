package com.easv.boldi.yuki.mapme.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.easv.boldi.yuki.mapme.Entities.Friends;
import com.easv.boldi.yuki.mapme.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yuki on 20/03/2018.
 */

public class FriendsListAdapter extends RecyclerView.Adapter<FriendsListAdapter.ViewHolder> {

    public List<Friends> mFriendsList;
    public Context context;
    private ArrayList<Friends> arrayList; //Used for the search bar
    private String mAppend;
    private int layoutResource;

    public FriendsListAdapter(Context context, List<Friends> mFriendsList) {
        this.mFriendsList = mFriendsList;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.nameTxt.setText(mFriendsList.get(position).getName());
        holder.phoneTxt.setText(mFriendsList.get(position).getPhone());

        final String friend_id = mFriendsList.get(position).friendId;

        holder.mView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Toast.makeText(context,"Friend ID : "+ friend_id , Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return mFriendsList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView nameTxt;
        public TextView phoneTxt;
        View mView;


        public ViewHolder(View itemView) {
            super(itemView);
            mView = itemView;

            nameTxt = mView.findViewById(R.id.name_Info);
            phoneTxt = mView.findViewById(R.id.phone_Info);
        }
    }

}
