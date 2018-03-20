package com.easv.boldi.yuki.mapme;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by yuki on 20/03/2018.
 */

public class FriendsListAdapter extends RecyclerView.Adapter<FriendsListAdapter.ViewHolder> {

    public List<Friends> friendsList;
    public Context context;

    public FriendsListAdapter(Context context, List<Friends> friendsList) {
        this.friendsList = friendsList;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.nameTxt.setText(friendsList.get(position).getName());
        holder.phoneTxt.setText(friendsList.get(position).getPhone());

        final String friend_id = friendsList.get(position).friendId;

        holder.mView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //TODO
            }
        });
    }

    @Override
    public int getItemCount() {
        return friendsList.size();
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
