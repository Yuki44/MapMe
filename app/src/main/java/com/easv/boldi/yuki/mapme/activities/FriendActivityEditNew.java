package com.easv.boldi.yuki.mapme.activities;

import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;

import de.hdodenhof.circleimageview.CircleImageView;

public class FriendActivityEditNew extends AppCompatActivity {

    private static final String TAG = "FriendActivityEditNew";
    public static CircleImageView mFriendImage;
    public static String mSelectedImagePath;
    public EditText mBirthdayTxt;
    private int mPreviousKeyStroke;

    public void initOnTextChangedListener() {

        mBirthdayTxt.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                mPreviousKeyStroke = keyCode;
                return false;
            }
        });
        mBirthdayTxt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String number = s.toString();
                Log.d(TAG, "afterTextChanged: " + number);
                if (number.length() == 4 && mPreviousKeyStroke != KeyEvent.KEYCODE_DEL && !number.contains("/")) {

                    number = String.format("%s/%s/", s.toString().substring(0, 2),
                            s.toString().substring(2, 4));
                    mBirthdayTxt.setText(number);
                    mBirthdayTxt.setSelection(number.length());
                    Log.d(TAG, "afterTextChanged: " + number + "<><><><><><><><><><><><><><><><><><>");
                } else if (number.length() == 10 && mPreviousKeyStroke != KeyEvent.KEYCODE_DEL && !number.contains("/")) {

                    number = number.substring(0, 10);
                    mBirthdayTxt.setText(number);
                    mBirthdayTxt.setSelection(number.length());
                }

            }

        });
    }


}
