package com.easv.boldi.yuki.mapme;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;

import com.easv.boldi.yuki.mapme.Adapters.SectionPageAdapter;
import com.easv.boldi.yuki.mapme.Dal.DAL;
import com.easv.boldi.yuki.mapme.Entities.Friends;
import com.easv.boldi.yuki.mapme.TabActivities.NewFriendActivity;
import com.easv.boldi.yuki.mapme.TabActivities.Tab1ListActivity;
import com.easv.boldi.yuki.mapme.TabActivities.Tab2MapActivity;

import java.util.List;

public class MainActivity extends AppCompatActivity {


    private static final String TAG = "Main Activity";
    private static final int STANDARD_APPBAR = 0;
    private static final int SEARCH_APPBAR = 1;
    private static final int ERROR_DIALOG_REQUEST = 9001;
    private static final String FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION;
    private static final String COURSE_LOCATION = Manifest.permission.ACCESS_COARSE_LOCATION;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1234;
    public static boolean mLocationPermissionGranted = false;
    private SectionPageAdapter mSectionPageAdapter;
    private ViewPager mViewPager;
    private DAL dal;
    private int mAppBarState;
    private AppBarLayout viewFriendsBar, searchBar;
    private EditText mSearchText;

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
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionPageAdapter = new SectionPageAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = findViewById(R.id.container);
        setupViewPager(mViewPager);

        TabLayout tabLayout = findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);
        tabLayout.refreshDrawableState();

        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager));

        getLocationPermission();

        DAL.setContext(this);
        dal = DAL.getInstance();
        List<Friends> friends = dal.getAllInfo();
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
        adapter.addFragment(new Tab1ListActivity(), "Friend List");
        adapter.addFragment(new Tab2MapActivity(), "Map");
       // adapter.addFragment(new Tab3SomethingActivity(), "Something");
        viewPager.setAdapter(adapter);
    }

    //----------------------------------------------------------------------------------------------
    //----------------------------------------------------------------------------------------------
    //----------------------------------------------------------------------------------------------
    //----------------------------------------------------------------------------------------------

//    private void init(){
//
//    }
//    public boolean isServoceOK(){
//        Log.d(TAG, "isServoceOK: checking google service version ");
//
//        int available = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(MainActivity.this);
//
//        if (available == ConnectionResult.SUCCESS){
//            Log.d(TAG, "isServoceOK: GooglePlay Service is working!!");
//            return true;
//        }else if (GoogleApiAvailability.getInstance().isUserResolvableError(available)){
//            Log.d(TAG, "isServoceOK: an error occured but we can fix it !!!");
//            Dialog dialog = GoogleApiAvailability.getInstance().getErrorDialog(MainActivity.this,available,ERROR_DIALOG_REQUEST);
//            dialog.show();
//        }else{
//            Toast.makeText(this,"You can't make map request",Toast.LENGTH_SHORT).show();
//        }
//        return false;
//    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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

//    /**
//     * A placeholder fragment containing a simple view.
//     */
//    public static class PlaceholderFragment extends Fragment {
//        /**
//         * The fragment argument representing the section number for this
//         * fragment.
//         */
//        private static final String ARG_SECTION_NUMBER = "section_number";
//
//        public PlaceholderFragment() {
//        }
//
//        /**
//         * Returns a new instance of this fragment for the given section
//         * number.
//         */
//        public static PlaceholderFragment newInstance(int sectionNumber) {
//            PlaceholderFragment fragment = new PlaceholderFragment();
//            Bundle args = new Bundle();
//            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
//            fragment.setArguments(args);
//            return fragment;
//        }
//
//        @Override
//        public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                                 Bundle savedInstanceState) {
//
//            if (getArguments().getInt(ARG_SECTION_NUMBER) == 1) {
//                View rootView = inflater.inflate(R.layout.tab1_list_fragment, container, false);
//                return rootView;
//            } else if (getArguments().getInt(ARG_SECTION_NUMBER) == 2) {
//                View rootView = inflater.inflate(R.layout.tab2_map_fragment, container, false);
//                return rootView;
//            } else {
//                View rootView = inflater.inflate(R.layout.fragment_main, container, false);
//                TextView textView = rootView.findViewById(R.id.section_label);
//                textView.setText(getString(R.string.section_format, getArguments().getInt(ARG_SECTION_NUMBER)));
//                return rootView;
//            }
//
//        }
//    }

//    /**
//     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
//     * one of the sections/tabs/pages.
//     */
//    public class SectionsPagerAdapter extends FragmentPagerAdapter {
//
//        public SectionsPagerAdapter(FragmentManager fm) {
//            super(fm);
//        }
//
//        @Override
//        public Fragment getItem(int position) {
//            // getItem is called to instantiate the fragment for the given page.
//            // Return a PlaceholderFragment (defined as a static inner class below).
//            return PlaceholderFragment.newInstance(position + 1);
//        }
//
//        @Override
//        public int getCount() {
//            // Show 3 total pages.
//            return 3;
//        }
//    }
}
