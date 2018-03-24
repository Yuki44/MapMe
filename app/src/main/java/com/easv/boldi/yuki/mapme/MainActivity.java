package com.easv.boldi.yuki.mapme;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.easv.boldi.yuki.mapme.Adapters.SectionPageAdapter;
import com.easv.boldi.yuki.mapme.Dal.DAL;
import com.easv.boldi.yuki.mapme.Entities.Friends;
import com.easv.boldi.yuki.mapme.TabActivities.NewFriendActivity;
import com.easv.boldi.yuki.mapme.TabActivities.Tab1ListActivity;
import com.easv.boldi.yuki.mapme.TabActivities.Tab2MapActivity;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;

import java.util.List;

public class MainActivity extends AppCompatActivity {


    private static final String TAG = "Main Activity";

    private SectionPageAdapter mSectionPageAdapter;

    private ViewPager mViewPager;

    private DAL dal;

    private static final int ERROR_DIALOG_REQUEST = 9001;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(TAG,"onCreate : Starting");

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionPageAdapter = new SectionPageAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager)findViewById(R.id.container);
        setupViewPager(mViewPager);

        TabLayout tabLayout = (TabLayout)findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);
        tabLayout.refreshDrawableState();

        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager));
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

    private void init(){

    }
    public boolean isServoceOK(){
        Log.d(TAG, "isServoceOK: checking google service version ");

        int available = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(MainActivity.this);

        if (available == ConnectionResult.SUCCESS){
            Log.d(TAG, "isServoceOK: GooglePlay Service is working!!");
            return true;
        }else if (GoogleApiAvailability.getInstance().isUserResolvableError(available)){
            Log.d(TAG, "isServoceOK: an error occured but we can fix it !!!");
            Dialog dialog = GoogleApiAvailability.getInstance().getErrorDialog(MainActivity.this,available,ERROR_DIALOG_REQUEST);
            dialog.show();
        }else{
            Toast.makeText(this,"You can't make map request",Toast.LENGTH_SHORT).show();
        }
        return false;
    }

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
