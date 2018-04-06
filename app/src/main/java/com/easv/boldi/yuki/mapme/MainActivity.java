package com.easv.boldi.yuki.mapme;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;

import com.easv.boldi.yuki.mapme.activities.NewFriendActivity;
import com.easv.boldi.yuki.mapme.adapters.FriendsListAdapter;
import com.easv.boldi.yuki.mapme.adapters.SectionPageAdapter;
import com.easv.boldi.yuki.mapme.dal.DatabaseHelper;
import com.easv.boldi.yuki.mapme.entities.Friends;
import com.easv.boldi.yuki.mapme.tabactivities.Tab1ListActivity;
import com.easv.boldi.yuki.mapme.tabactivities.Tab2MapActivity;
import com.easv.boldi.yuki.mapme.utils.UniversalImageLoader;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {


    private static final String TAG = "Main Activity";
    private static final int STANDARD_APPBAR = 0;
    private static final int SEARCH_APPBAR = 1;

    private static final String FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION;
    private static final String COURSE_LOCATION = Manifest.permission.ACCESS_COARSE_LOCATION;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1234;
    public static boolean mLocationPermissionGranted = false;
    private SectionPageAdapter mSectionPageAdapter;
    private ViewPager mViewPager;
    public String search;
    private int mAppBarState;
    private AppBarLayout viewFriendsBar, searchBar;
    private EditText mSearchText;
    SearchView searchView = null;
    FriendsListAdapter friendsListAdapter;
    private static final int REQUEST_CODE = 1;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        viewFriendsBar = findViewById(R.id.friendListToolbar);
        searchBar = findViewById(R.id.searchToolbar);
        mSearchText = findViewById(R.id.etSearchFriends);
        Log.d(TAG,"onCreate : Starting");

        setAppBarState(STANDARD_APPBAR);
        //        Toolbar toolbar = findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//        Create the adapter that will return a fragment for each of the three
//        primary sections of the activity.

        mSectionPageAdapter = new SectionPageAdapter(getSupportFragmentManager());


        // Set up the ViewPager with the sections adapter.
        mViewPager = findViewById(R.id.container);
        setupViewPager(mViewPager);

        TabLayout tabLayout = findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);
        tabLayout.refreshDrawableState();

        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager));

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

            }
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                if (mViewPager.getCurrentItem() == 0) {
                    FloatingActionButton fab = findViewById(R.id.fab);
                    fab.setVisibility(View.INVISIBLE);
                }else if (mViewPager.getCurrentItem()==1){
                    FloatingActionButton fab = findViewById(R.id.fab);
                    fab.setVisibility(View.VISIBLE);
                }
            }
            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        getLocationPermission();

        DatabaseHelper databaseHelper = new DatabaseHelper(this);
//        DatabaseHelper.setContext(this);
//        databaseHelper = DatabaseHelper.getInstance();
        List<Friends> friends = databaseHelper.getAllInfo();
        for (Friends f : friends) {
            Log.d("memap", "Friend: " + f.getName());

        }


        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent addFriendIntent = new Intent(MainActivity.this, NewFriendActivity.class);
                startActivity(addFriendIntent);
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
            }
        });

        ImageView ivSearchContact = findViewById(R.id.ivSearchIcon);
        ivSearchContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleToolbarState();
            }
        });

        ImageView ivBackArrow = findViewById(R.id.ivBackArrow);
        ivBackArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleToolbarState();
            }
        });

        initImageLoader();
        Intent intent = getIntent();
        intent.putExtra("fromMap","0");
    }

    private void toggleToolbarState() {
        Log.d(TAG, "toggleToolbarState: toggling AppBarState");
        if (mAppBarState == STANDARD_APPBAR) {
            setAppBarState(SEARCH_APPBAR);
        } else {
            setAppBarState(STANDARD_APPBAR);
        }
    }

    private void setAppBarState(int state) {
        Log.d(TAG, "setAppBarState: changing app bar state to: " + state);

        mAppBarState = state;
        if (mAppBarState == STANDARD_APPBAR) {
            searchBar.setVisibility(View.GONE);
            viewFriendsBar.setVisibility(View.VISIBLE);
            InputMethodManager imm = (InputMethodManager) getSystemService(
                    Context.INPUT_METHOD_SERVICE);
            try {
                imm.hideSoftInputFromWindow(mSearchText.getWindowToken(), 0);
                mSearchText.setText(null);
            } catch (NullPointerException e) {
                Log.d(TAG, "setAppBarState: NullPointerException " + e.getMessage());
            }

        } else if (mAppBarState == SEARCH_APPBAR) {
            viewFriendsBar.setVisibility(View.GONE);
            searchBar.setVisibility(View.VISIBLE);
            mSearchText.requestFocus();
            InputMethodManager imm = (InputMethodManager) getSystemService(
                    Context.INPUT_METHOD_SERVICE);
            try {
                imm.showSoftInput(mSearchText, 0);
            } catch (NullPointerException e) {
                Log.d(TAG, "setAppBarState: NullPointerException " + e.getMessage());
            }
        }
    }
    private void getLocationPermission() {
        Log.d(TAG, "getLocationPermission: Getting LocationPermission");
        String[] permission = {Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION};

        if (ContextCompat.checkSelfPermission(this.getApplicationContext(), FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            if (ContextCompat.checkSelfPermission(this.getApplicationContext(), COURSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                mLocationPermissionGranted = true;
            }else{
                ActivityCompat.requestPermissions(this, permission, LOCATION_PERMISSION_REQUEST_CODE);
            }
        }else {
            ActivityCompat.requestPermissions(this, permission, LOCATION_PERMISSION_REQUEST_CODE);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        setAppBarState(STANDARD_APPBAR);
        mSearchText.setText(null);
    }


    private void setupViewPager (ViewPager viewPager){
        SectionPageAdapter adapter = new SectionPageAdapter(getSupportFragmentManager());
        Fragment friendListFragment = new Tab1ListActivity();
//        Bundle bundle = new Bundle();
//        bundle.putSerializable("edttext", "From Activity");
//        friendListFragment.setArguments(bundle);
        Fragment mapFragment = new Tab2MapActivity();
        adapter.addFragment(friendListFragment, "Friend List");
        adapter.addFragment(mapFragment, "Map");
       // adapter.addFragment(new Tab3SomethingActivity(), "Something");
        viewPager.setAdapter(adapter);
    }


    @Override
    protected void onRestart() {
        super.onRestart();
        Intent intent = getIntent();
        finish();
        startActivity(intent);
        if(intent.getExtras().get("fromMap")=="1"){
            mViewPager.setCurrentItem(0);
        }
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);

//        SearchView searchView = (SearchView) menu.findItem(R.id.searchToolbar)
//                .getActionView();
//
//        SearchView.OnQueryTextListener queryTextListener = new SearchView.OnQueryTextListener() {
//            public boolean onQueryTextChange(String newText) {
//                return true;
//            }
//
//            public boolean onQueryTextSubmit(String query) {
//                search = query;
//                return true;
//            }
//        };
//        searchView.setOnQueryTextListener(queryTextListener);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void initImageLoader() {
        UniversalImageLoader universalImageLoader = new UniversalImageLoader(MainActivity.this);
        ImageLoader.getInstance().init(universalImageLoader.getConfig());
    }


    /**
     * Generalized method for asking permission. Can pass any array of permissions
     *
     * @param permissions
     */
    public void verifyPermissions(String[] permissions) {
        Log.d(TAG, "verifyPermissions: asking user for permissions.");
        ActivityCompat.requestPermissions(
                MainActivity.this,
                permissions,
                REQUEST_CODE
        );
    }

    /**
     * Checks to see if permission was granted for the passed parameters
     * ONLY ONE PERMISSION MAY BE CHECKED AT A TIME
     *
     * @param permission
     * @return
     */
    public boolean checkPermission(String[] permission) {
        Log.d(TAG, "checkPermission: checking permissions for:" + permission[0]);

        int permissionRequest = ActivityCompat.checkSelfPermission(
                MainActivity.this,
                permission[0]);

        if (permissionRequest != PackageManager.PERMISSION_GRANTED) {
            Log.d(TAG, "checkPermission: \n Permissions was not granted for: " + permission[0]);
            return false;
        } else {
            return true;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        Log.d(TAG, "onRequestPermissionsResult: requestCode: " + requestCode);

        switch (requestCode) {
            case REQUEST_CODE:
                for (int i = 0; i < permissions.length; i++) {
                    if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                        Log.d(TAG, "onRequestPermissionsResult: User has allowed permission to access: " + permissions[i]);
                    } else {
                        break;
                    }
                }
                break;
        }
    }

    public void search() {

        mSearchText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                String text = mSearchText.getText().toString().toLowerCase(Locale.getDefault());
                // mFriendsListAdapter.filter(text);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

}
