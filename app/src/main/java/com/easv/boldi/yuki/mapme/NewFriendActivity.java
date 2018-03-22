package com.easv.boldi.yuki.mapme;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class NewFriendActivity extends AppCompatActivity {

    private Button mSaveButton;
    private Button mCancelButton;
    private EditText mNameTxt;
    private EditText mAddressTxt;
    private EditText mEmailText;
    private EditText mWebsiteTxt;
    private EditText mBirthdayTxt;
    private EditText mPhoneText;
    private Toolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_friend);

        mNameTxt = findViewById(R.id.name_Info);
        mAddressTxt = findViewById(R.id.addressTxt);
        mEmailText = findViewById(R.id.emailTxt);

        mWebsiteTxt = findViewById(R.id.websiteTxt);
        mBirthdayTxt = findViewById(R.id.birthdayTxt);
        mPhoneText = findViewById(R.id.phone_Info);

        mSaveButton = findViewById(R.id.saveBtn);
        mCancelButton = findViewById(R.id.cancelBtn);

        mCancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        mSaveButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Add Friend Feature Under Development", Snackbar.LENGTH_SHORT)
                        .setAction("Action", null).show();

            }
        });
        this.setTitle(null);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);




    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_main, menu);
//        return true;
//    }

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }
//        return super.onOptionsItemSelected(item);
//    }

}
