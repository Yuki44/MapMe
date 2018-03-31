package com.easv.boldi.yuki.mapme.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.easv.boldi.yuki.mapme.R;
import com.easv.boldi.yuki.mapme.entities.Friends;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by yuki on 20/03/2018.
 */

public class FriendsListAdapter extends ArrayAdapter<Friends> {

    public Context mContext;
    private LayoutInflater mInflater;
    private List<Friends> mFriends = null;
    private ArrayList<Friends> arrayList; //Used for the search bar
    private int layoutResource;
    private String mAppend;


    public FriendsListAdapter(@NonNull Context context, int resource, @NonNull List<Friends> friends, String append) {
        super(context, resource, friends);
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        layoutResource = resource;
        this.mContext = context;
        mAppend = append;
        this.mFriends = friends;
        arrayList = new ArrayList<>();
        this.arrayList.addAll(mFriends);
    }

    private static class ViewHolder {
        TextView name;
        TextView phone;
        CircleImageView friendImage;
        ProgressBar mProgressBar;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        //ViewHolder Build Patter Start
        final ViewHolder holder;

        if (convertView == null) {
            convertView = mInflater.inflate(layoutResource, parent, false);
            holder = new ViewHolder();

            holder.name = convertView.findViewById(R.id.name_Info);
            holder.phone = convertView.findViewById(R.id.phone_Info);
            holder.friendImage = convertView.findViewById(R.id.friendImage);

            holder.mProgressBar = convertView.findViewById(R.id.friendProgressbar);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        String name_ = getItem(position).getName();
        String phone_ = getItem(position).getPhone();
        String imagePath = getItem(position).getProfileImage();
        holder.name.setText(name_);
        holder.phone.setText(phone_);

        ImageLoader imageLoader = ImageLoader.getInstance();
        imageLoader.displayImage(mAppend + imagePath, holder.friendImage, new ImageLoadingListener() {
            @Override
            public void onLoadingStarted(String imageUri, View view) {
                holder.mProgressBar.setVisibility(View.VISIBLE);
            }

            @Override
            public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
                holder.mProgressBar.setVisibility(View.GONE);
            }

            @Override
            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                holder.mProgressBar.setVisibility(View.GONE);
            }

            @Override
            public void onLoadingCancelled(String imageUri, View view) {
                holder.mProgressBar.setVisibility(View.GONE);
            }
        });
        return convertView;
    }
}
