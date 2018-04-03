package com.easv.boldi.yuki.mapme.adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.easv.boldi.yuki.mapme.R;

import java.util.List;

public class FriendPropertyListAdapter extends ArrayAdapter<String> {

    private static final String TAG = "FriendPropertyListAdap";
    private Context mContext;
    private LayoutInflater mInflater;
    private List<String> mProperties = null;
    private int layoutResource;
    private String mAppend;
    private static final int REQUEST_CALL = 1;

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

        if (property.contains("@")) {
            holder.leftIcon.setImageResource(mContext.getResources().getIdentifier("@drawable/ic_email", null, mContext.getPackageName()));
            holder.leftIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d(TAG, "onClick: opening email.");
                    Intent emailIntent = new Intent(Intent.ACTION_SEND);
                    emailIntent.setType("plain/text");
                    emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{property});
                    mContext.startActivity(emailIntent);

                    /* optional settings for sending emails
                    String email = property;
                    String subject = "subject";
                    String body = "body...";

                    String uriText = "mailto: + Uri.encode(email) + "?subject=" + Uri.encode(subject) +
                    "&body=" + Uri.encode(body);
                    Uri uri = Uri.parse(uriText);

                    emailIntent.setData(uri);
                    mContext.startActivitY(emailIntent);

                     */
                }
            });
        } else if ((property.length() != 0)) {
            //Phone call
            holder.leftIcon.setImageResource(mContext.getResources().getIdentifier("@drawable/ic_phone", null, mContext.getPackageName()));
            holder.leftIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


//                    if(ContextCompat.checkSelfPermission(mContext, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
//                        ActivityCompat.requestPermissions((MainActivity)mContext, new String[] {Manifest.permission.CALL_PHONE}, REQUEST_CALL);
//                    } else {
//                        String dial = "tel" + property;
//                        Intent callIntent = new Intent(Intent.ACTION_CALL, Uri.parse(dial));
//                        mContext.startActivity(callIntent);
//                    }

                    try {
                        Log.d(TAG, "onClick: initiating phone call...");
                        Intent callIntent = new Intent(Intent.ACTION_CALL, Uri.fromParts("tel", property, null));
                        mContext.startActivity(callIntent);
                    } catch (SecurityException e) {
                        Log.e(TAG, "onClick: Error " + e.getMessage());
                    }


                }
            });

            //setup the icon for sending text messages
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
        }


        return convertView;
    }

    private static class ViewHolder {
        TextView property;
        ImageView rightIcon;
        ImageView leftIcon;
    }


}
