package com.easv.boldi.yuki.mapme.TabActivities;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.easv.boldi.yuki.mapme.MainActivity;
import com.easv.boldi.yuki.mapme.R;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;
import static com.easv.boldi.yuki.mapme.MainActivity.mLocationPermissionGranted;

public class Tab2MapActivity extends SupportMapFragment implements
        OnMapReadyCallback {

    private static final String ARG_SECTION_NUMBER = "section_number";
    private static final String FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION;
    private static final String COURSE_LOCATION = Manifest.permission.ACCESS_COARSE_LOCATION;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1234;
    private final LatLng HAMBURG = new LatLng(53.558, 9.927);
    private final LatLng KIEL = new LatLng(53.551, 9.993);
    private GoogleMap mMap;
    private Marker marker;
    private FusedLocationProviderClient mFusedLocationProviderClient;
    private static final float DEFAULT_ZOOM = 15;

    private EditText etFriendSearch;

    public Tab2MapActivity() {

    }
//    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
//        View view = inflater.inflate(R.layout.snippet_searchtoolbar, container, true);
//        etFriendSearch = view.findViewById(R.id.etSearchFriends);
//        return view;
//    }
//    private void inIt(){
//        Log.d(TAG, "inIt: Initializing");
//        etFriendSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
//            @Override
//            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
//            if (actionId == EditorInfo.IME_ACTION_SEARCH
//                || actionId == EditorInfo.IME_ACTION_DONE
//                || event.getAction() ==KeyEvent.ACTION_DOWN
//                || event.getAction() == KeyEvent.KEYCODE_ENTER){
//                    geoLocate();
//                }return false;
//            }
//        });
//    }
    private void geoLocate(){
        Log.d(TAG, "geoLocate: geLocatinf");
        String searchString = etFriendSearch.getText().toString();
        Geocoder geocoder = new Geocoder(getContext());
        List<Address> list = new ArrayList<>();
        try {
            list = geocoder.getFromLocationName(searchString,1);
        }catch (IOException e) {
            Log.e(TAG, "geoLocate: IOException"+ e.getMessage());
        }
        if(list.size()>0){
            Address address = list.get(0);
            Log.d(TAG, "geoLocate: Found a location "+ address.toString());
        }
    }
    private void getDeviceLocation(){
        mFusedLocationProviderClient= LocationServices.getFusedLocationProviderClient(getActivity());
        try {
            if (mLocationPermissionGranted){
                Task location = mFusedLocationProviderClient.getLastLocation();
                location.addOnCompleteListener(new OnCompleteListener() {
                    @Override
                    public void onComplete(@NonNull Task task) {
                        if (task.isSuccessful()){
                            Log.d(TAG, "onComplete: Found Location");
                            Location currentLocation = (Location)task.getResult();
                            moveCamera(new LatLng(currentLocation.getLatitude(),currentLocation.getLongitude()),DEFAULT_ZOOM);
                        }else{
                            Log.d(TAG, "onComplete: current location is null");
                            Toast.makeText(getActivity(),"Unable to get current location",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        }catch(SecurityException e){
            Log.e(TAG, "getDeviceLocation: Security Exeption"+ e.getMessage());
        }

    }

    private void moveCamera(LatLng latLng, float zoom){
        Log.d(TAG, "moveCamera: moving the camera to lat:"+latLng.latitude+"lng:"+latLng.longitude);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng,zoom));
    }
@Override
public void onResume() {
    super.onResume();

    Log.d("MyMap", "onResume");
    setUpMapIfNeeded();
}

    private void setUpMapIfNeeded() {

        if (mMap == null) {

            Log.d("MyMap", "setUpMapIfNeeded");

            getMapAsync(this);
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        Log.d("MyMap", "Map is ready");
        mMap =  googleMap;
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        if (MainActivity.mLocationPermissionGranted) {
            getDeviceLocation();
            if (ActivityCompat.checkSelfPermission(getActivity(),Manifest.permission.ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED
                    &&ActivityCompat.checkSelfPermission(getActivity(),Manifest.permission.ACCESS_COARSE_LOCATION) !=PackageManager.PERMISSION_GRANTED){
                return;
            }
            mMap.setMyLocationEnabled(true);
        }
       //inIt();
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        Log.d(TAG, "onRequestPermissionsResult: this was called");
        mLocationPermissionGranted = false;
        switch (requestCode){
            case LOCATION_PERMISSION_REQUEST_CODE: {
                if (grantResults.length > 0 ) {
                    for (int i = 0; i< grantResults.length; i++){
                        if (grantResults[0]== PackageManager.PERMISSION_GRANTED){
                            mLocationPermissionGranted = false;
                            return;
                        }
                    }
                    mLocationPermissionGranted = true;
                }
            }
        }
    }

}