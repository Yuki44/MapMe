package com.easv.boldi.yuki.mapme.adapters;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.easv.boldi.yuki.mapme.R;
import com.easv.boldi.yuki.mapme.dal.DatabaseHelper;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static android.content.Context.LOCATION_SERVICE;

public class FriendPropertyListAdapter extends ArrayAdapter<String> {

    private static final String TAG = "FriendPropertyListAdap";
    private Context mContext;
    private LayoutInflater mInflater;
    private List<String> mProperties = null;
    private int layoutResource;
    private String mAppend;
    private LocationManager mLocation;
    private DatabaseHelper dbHelper;

    public FriendPropertyListAdapter(@NonNull Context context, int resource, @NonNull List<String> properties) {
        super(context, resource, properties);
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        layoutResource = resource;
        this.mContext = context;
        this.mProperties = properties;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        //ViewHolder Build Patter Start
        final ViewHolder holder;

        if (convertView == null) {
            convertView = mInflater.inflate(layoutResource, parent, false);
            holder = new ViewHolder();

            holder.property = convertView.findViewById(R.id.tvMiddleCardView);
            holder.rightIcon = convertView.findViewById(R.id.iconRightCardView);
            holder.leftIcon = convertView.findViewById(R.id.iconLeftCardView);

            convertView.setTag(holder);

        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        final String property = getItem(position);
        holder.property.setText(property);


        switch (position) {
            case 0:
                holder.leftIcon.setImageResource(mContext.getResources().getIdentifier("@drawable/ic_phone", null, mContext.getPackageName()));
                holder.leftIcon.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        try {
                            Log.d(TAG, "onClick: initiating phone call...");
                            Intent callIntent = new Intent(Intent.ACTION_CALL, Uri.fromParts("tel", property, null));
                            mContext.startActivity(callIntent);
                        } catch (SecurityException e) {
                            Log.e(TAG, "onClick: Error " + e.getMessage());
                        }


                    }
                });
                holder.rightIcon.setImageResource(mContext.getResources().getIdentifier("@drawable/ic_message", null, mContext.getPackageName()));
                holder.rightIcon.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Log.d(TAG, "onClick: initiating text message....");

                        //The number that we want to send SMS
                        Intent smsIntent = new Intent(Intent.ACTION_VIEW, Uri.fromParts("sms", property, null));
                        mContext.startActivity(smsIntent);
                    }
                });
                break;
            case 1:
                if (!property.isEmpty()) {
                    holder.rightIcon.setImageResource(mContext.getResources().getIdentifier("@drawable/ic_email", null, mContext.getPackageName()));
                    holder.rightIcon.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Log.d(TAG, "onClick: opening email.");
                            Intent emailIntent = new Intent(Intent.ACTION_SEND);
                            emailIntent.setType("plain/text");
                            emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{property});
                            mContext.startActivity(emailIntent);

                        }
                    });
                }
                break;
            case 2:
                if (!property.isEmpty()) {
                    holder.rightIcon.setImageResource(mContext.getResources().getIdentifier("@drawable/ic_address", null, mContext.getPackageName()));
                    holder.rightIcon.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Log.d(TAG, "onClick: opening Google Maps.");
                            Uri gmmIntentUri = Uri.parse("geo:0,0?q=" + property);
                            Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                            mapIntent.setPackage("com.google.android.apps.maps");
                            mContext.startActivity(mapIntent);
                        }
                    });
                }
                holder.leftIcon.setImageResource(mContext.getResources().getIdentifier("@drawable/ic_addplace", null, mContext.getPackageName()));
                holder.leftIcon.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Log.d(TAG, "onClick: adding address to your friend.");
                        //TODO
                    }
                });

                break;
            case 3:
                if (!property.isEmpty()) {

                    String birtday = property;
                    DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
                    String[] birtdayInts = birtday.split("/");
                    int[] birthdayNumbs = new int[birtdayInts.length];
                    for (int i = 0; i < birtdayInts.length; i++) {
                        birthdayNumbs[i] = Integer.parseInt(birtdayInts[i]);
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
                            holder.leftIcon.setImageResource(mContext.getResources().getIdentifier("@drawable/ic_cake_orange", null, mContext.getPackageName()));
                        } else {
                            holder.leftIcon.setVisibility(View.GONE);
                        }
                    }

                    holder.leftIcon.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Toast.makeText(v.getContext(), "What does the Facebook profile say?", Toast.LENGTH_SHORT).show();
                        }
                    });

                }
                break;
            case 4:
                if (!property.isEmpty()) {
                    holder.rightIcon.setImageResource(mContext.getResources().getIdentifier("@drawable/ic_website", null, mContext.getPackageName()));
                    holder.rightIcon.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Log.d(TAG, "onClick: opening the Browser.");
                            try {
                                Uri webpage = Uri.parse("http://" + property);
                                Intent myIntent = new Intent(Intent.ACTION_VIEW, webpage);
                                mContext.startActivity(myIntent);
                            } catch (ActivityNotFoundException e) {
                                Toast.makeText(v.getContext(), "No application can handle this request. Please install a web browser or check your URL.", Toast.LENGTH_SHORT).show();
                                e.printStackTrace();
                            }
                        }
                    });


                }
                break;
            default:
                holder.leftIcon.setVisibility(View.GONE);
                holder.rightIcon.setVisibility(View.GONE);
                break;
        }

        return convertView;
    }

    private static class ViewHolder {
        TextView property;
        ImageView rightIcon;
        ImageView leftIcon;

    }
}
