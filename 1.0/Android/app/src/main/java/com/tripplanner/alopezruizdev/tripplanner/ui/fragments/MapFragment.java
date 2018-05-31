package com.tripplanner.alopezruizdev.tripplanner.ui.fragments;

import android.Manifest;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.tripplanner.alopezruizdev.tripplanner.R;

import java.util.ArrayList;
import java.util.List;

import static android.support.v7.app.AlertDialog.Builder;

public class MapFragment extends Fragment implements OnMapReadyCallback, GoogleMap.OnMyLocationButtonClickListener,
        GoogleMap.OnMyLocationClickListener, ActivityCompat.OnRequestPermissionsResultCallback{

    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;
    private MapView mapView;
    private GoogleMap gmap;
    private FusedLocationProviderClient mFusedLocationClient;
    LocationRequest mLocationRequest;
    Marker mCurrLocationMarker;
    Location mLastLocation;
    private List<Marker> markerList = new ArrayList<Marker>();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_map, container, false);

        // Gets the MapView from the XML layout and creates it
        mapView = (MapView) v.findViewById(R.id.mapView);
        mapView.onCreate(savedInstanceState);

        mapView.getMapAsync(this);

        return v;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        gmap = googleMap;
        gmap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
        gmap.getUiSettings().setMyLocationButtonEnabled(true);
        gmap.setOnMyLocationButtonClickListener(this);
        gmap.setOnMyLocationClickListener(this);

        currentLocationRequest();
        enableMyLocation();

        addStaticMarkers();

    }

    @Override
    public void onResume() {
        mapView.onResume();
        super.onResume();
    }
    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
        //stop location updates when Activity is no longer active
        if (mFusedLocationClient != null) {
            mFusedLocationClient.removeLocationUpdates(mLocationCallback);
        }
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }
    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }
    @Override
    public boolean onMyLocationButtonClick() {
//        Toast.makeText(getActivity(), "MyLocation button clicked", Toast.LENGTH_SHORT).show();
        // Return false so that we don't consume the event and the default behavior still occurs
        // (the camera animates to the user's current position).
        return false;
    }
    @Override
    public void onMyLocationClick(@NonNull Location location) {
//        Toast.makeText(getActivity(), "Current location:\n" + location, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_LOCATION: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (ContextCompat.checkSelfPermission(getActivity(),Manifest.permission.ACCESS_FINE_LOCATION)== PackageManager.PERMISSION_GRANTED) {
                        mFusedLocationClient.requestLocationUpdates(mLocationRequest, mLocationCallback, Looper.myLooper());
                        gmap.setMyLocationEnabled(true);
                    }
                } else {
                    Toast.makeText(getActivity(), "permission denied", Toast.LENGTH_LONG).show();
                }
                return;
            }
        }
    }

    //Private methods

    private void addStaticMarkers(){
        for (Marker m: markerList) {
            if(m!=null){
                m.remove();
            }
        }

        Location loc1 = new Location("");
        loc1.setLatitude(9.979994);
        loc1.setLongitude(-83.849076);
        markerList.add(addMapMarker(loc1,BitmapDescriptorFactory.HUE_GREEN,"Volcán Irazú"));

        Location loc2 = new Location("");
        loc2.setLatitude(10.017012);
        loc2.setLongitude(-83.767969);
        markerList.add(addMapMarker(loc2,BitmapDescriptorFactory.HUE_GREEN,"Volcán Turrialba"));

        Location loc3 = new Location("");
        loc3.setLatitude(10.197793);
        loc3.setLongitude(-84.230554);
        markerList.add(addMapMarker(loc3,BitmapDescriptorFactory.HUE_GREEN,"Volcán Poás"));

        Location loc4 = new Location("");
        loc4.setLatitude(10.452544);
        loc4.setLongitude(-84.721398);
        markerList.add(addMapMarker(loc4,BitmapDescriptorFactory.HUE_GREEN,"Volcán Arenal"));

        Location loc5 = new Location("");
        loc5.setLatitude(10.792612);
        loc5.setLongitude(-85.363032);
        markerList.add(addMapMarker(loc5,BitmapDescriptorFactory.HUE_GREEN,"Volcán Rincón de la Vieja"));

        Location loc6 = new Location("");
        loc6.setLatitude(10.130162);
        loc6.setLongitude(-84.125399);
        markerList.add(addMapMarker(loc6,BitmapDescriptorFactory.HUE_GREEN,"Volcán Barva"));
    }
    private void zoomToLocation(Location location){
        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
        gmap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 11));
    }
    private Marker addMapMarker(Location location, Float markerColor, String title){
        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(latLng);
        markerOptions.title(title);
        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(markerColor));
        return gmap.addMarker(markerOptions);
    }
    private void currentLocationRequest(){
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(getActivity());

        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(120000); // two minute interval
        mLocationRequest.setFastestInterval(120000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
    }
    private void enableMyLocation() {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            if (ContextCompat.checkSelfPermission((AppCompatActivity) getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                mFusedLocationClient.requestLocationUpdates(mLocationRequest,mLocationCallback, Looper.myLooper());
                gmap.setMyLocationEnabled(true);
            } else {
                checkLocationPermission();
            }
        }else {
            mFusedLocationClient.requestLocationUpdates(mLocationRequest, mLocationCallback, Looper.myLooper());
            gmap.setMyLocationEnabled(true);
        }
    }
    private void checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),Manifest.permission.ACCESS_FINE_LOCATION)) {
                new Builder(getActivity())
                        .setTitle("Location Permission Needed")
                        .setMessage("This app needs the Location permission, please accept to use location functionality")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                ActivityCompat.requestPermissions(getActivity(),
                                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                                        MY_PERMISSIONS_REQUEST_LOCATION );
                            }
                        })
                        .create()
                        .show();
            } else {
                ActivityCompat.requestPermissions(getActivity(),
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION );
            }
        }
    }
    private LocationCallback mLocationCallback = new LocationCallback() {
        @Override
        public void onLocationResult(LocationResult locationResult) {
            List<Location> locationList = locationResult.getLocations();
            if (locationList.size() > 0) {
                //The last location in the list is the newest
                Location location = locationList.get(locationList.size() - 1);
                Log.i("MapFragment", "Location: " + location.getLatitude() + " " + location.getLongitude());
                mLastLocation = location;
                if (mCurrLocationMarker != null) {
                    mCurrLocationMarker.remove();
                }

                zoomToLocation(location);
                mCurrLocationMarker = addMapMarker(location,BitmapDescriptorFactory.HUE_AZURE,"Current Position");

            }
        }
    };
}
