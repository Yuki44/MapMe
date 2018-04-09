package com.easv.boldi.yuki.mapme.adapters;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.easv.boldi.yuki.mapme.MainActivity;
import com.easv.boldi.yuki.mapme.R;
import com.easv.boldi.yuki.mapme.activities.EditFriendActivity;
import com.easv.boldi.yuki.mapme.activities.FriendActivity;
import com.easv.boldi.yuki.mapme.dal.DatabaseHelper;
import com.easv.boldi.yuki.mapme.entities.Friends;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.nostra13.universalimageloader.utils.L;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

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
    private Friends mFriend;
    private DatabaseHelper dbHelper;


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
        ImageView birthdayCake;
        RelativeLayout mRelativeLayout;
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
            holder.birthdayCake = convertView.findViewById(R.id.birthday_cake);
            holder.birthdayCake.setVisibility(View.INVISIBLE);
            holder.mRelativeLayout = convertView.findViewById(R.id.mRelativLayout);

            holder.mProgressBar = convertView.findViewById(R.id.friendProgressbar);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        String name_ = getItem(position).getName();
        String phone_ = getItem(position).getPhone();

        ImageLoader imageLoader = ImageLoader.getInstance();
        String imagePath = getItem(position).getProfileImage();


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

        String birthday = getItem(position).getBirthday();
        if (getItem(position).getBirthday().isEmpty()==false) {
            DateFormat formatter = new SimpleDateFormat("dd/MM");
            String[] birthdayInts = birthday.split("/");
            int[] birthdayNumbs = new int[birthdayInts.length];
            for (int i = 0; i < birthdayInts.length; i++) {
                birthdayNumbs[i] = Integer.parseInt(birthdayInts[i]);
            }
            Date todaysDate = new Date();
            todaysDate.getTime();
            String todaysString = formatter.format(todaysDate);
            String[] todaysInts = todaysString.split("/");
            int[] todaysNumbs = new int[todaysInts.length];
            for (int i = 0; i < todaysInts.length; i++) {
                todaysNumbs[i] = Integer.parseInt(todaysInts[i]);
            }
            if (todaysNumbs[1] == birthdayNumbs[1]) {
                if (todaysNumbs[0] == birthdayNumbs[0]) {
                    holder.birthdayCake.setVisibility(View.VISIBLE);
                }
            }
        }


        holder.name.setText(name_);
        holder.phone.setText(phone_);


        return convertView;

    }

}
