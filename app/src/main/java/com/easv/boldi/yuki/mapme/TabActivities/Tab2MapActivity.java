package com.easv.boldi.yuki.mapme.TabActivities;

import android.Manifest;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.util.Log;

import com.easv.boldi.yuki.mapme.MainActivity;
import com.easv.boldi.yuki.mapme.R;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;

import static android.content.ContentValues.TAG;

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

    public Tab2MapActivity() {

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        Log.d("MyMap", "Map is ready");
        mMap = googleMap;
    }
    private void initMap(){
        Log.d(TAG, "initMap: Initializing Map");
        SupportMapFragment mapFragment = (SupportMapFragment)getActivity().getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(Tab2MapActivity.this);

    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        Log.d(TAG, "onRequestPermissionsResult: this was called");
        MainActivity.mLocationPermissionGranted = false;
        switch (requestCode){
            case LOCATION_PERMISSION_REQUEST_CODE: {
                if (grantResults.length > 0 ) {
                    for (int i = 0; i< grantResults.length; i++){
                        if (grantResults[0]== PackageManager.PERMISSION_GRANTED){
                            MainActivity.mLocationPermissionGranted = false;
                            return;
                        }
                    }
                    MainActivity.mLocationPermissionGranted = true;
                    initMap();
                }
            }
        }
    }





}