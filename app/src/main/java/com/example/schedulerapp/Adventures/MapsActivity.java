package com.example.schedulerapp.Adventures;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.os.Build;
import android.os.Bundle;
import android.widget.Button;

import com.example.schedulerapp.R;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApi;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.common.internal.GoogleApiAvailabilityCache;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import static com.google.android.gms.common.api.GoogleApiClient.*;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback , LocationListener, GoogleMap.OnMarkerClickListener,
        ConnectionCallbacks, OnConnectionFailedListener {

    private GoogleMap mMap;
    public double currentLatitude;
    public static final int ROUND= 10;
    public double currentLongitude;
    Button hospital;
    private GoogleApiClient googleApiClient;
    private ChildEventListener mChildEventListener;
    private DatabaseReference mUsers;
    Marker marker;
    LocationRequest mLocationRequest;
    SupportMapFragment supportMapFragment;
    public FusedLocationProviderClient fusedLocationProviderClient;
    FusedLocationProviderClient mFusedLocationClient;
    double currentlatitude= 0, currentlongitude= 0;
   @RequiresApi(api = Build.VERSION_CODES.O)

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps2);
//        buildGoogleApiClient();
//        googleApiClient.connect();



        // Obtain the SupportMapFragment and get notified when the map is ready to be used.

        // defining the buttons
//       hospital = findViewById(R.id.hospital);
//        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
//                .findFragmentById(R.id.map);
//        mapFragment.getMapAsync(this);
//        if (ActivityCompat.checkSelfPermission(MapsActivity.this,
//                Manifest.permission.ACCESS_FINE_LOCATION)== PackageManager.PERMISSION_GRANTED){
//            getCurrentLocation();
//        }else {
//            ActivityCompat.requestPermissions(MapsActivity.this,
//                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},45);
//        }

       /*//calling google api's client to establish location connections
       googleApiClient = new GoogleApiClient.Builder(this)
               .addConnectionCallbacks(this)
               .addOnConnectionFailedListener(this)
               .addApi(LocationListener.API)
               .build();
        // fusedlocationProviderClient provides the current location coordinates
       fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
       if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
           NotificationChannel channel = new NotificationChannel(
            "Mynotification","MyNotification", NotificationManager.IMPORTANCE_DEFAULT);
           NotificationManager manager = getSystemService(NotificationManager.class);
           manager.createNotificationChannel(channel);
       }
       //Obtain  the SupportMapFragment and get notified when the map is ready to be used.
       SupportMapFragment mapFragment1 = (SupportMapFragment) getSupportFragmentManager()
               .findFragmentById(R.id.map);
       mapFragment1.getMapAsync(this);*/
//       ChildEventListener mChildEventListener;
//       mUsers = FirebaseDatabase.getInstance().getReference("coordinates");
//       mUsers.push().setValue(marker);
//       onMapReady(mMap);


    }

//    private void getCurrentLocation() {
//        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
//        != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this,Manifest.permission
//        .ACCESS_COARSE_LOCATION) !=PackageManager.PERMISSION_GRANTED){
//            return;
//        }
//        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
//        Task<Location> task = mFusedLocationClient.getLastLocation();
//        task.addOnCompleteListener(new OnSuccessListener<Location>() {
//            @Override
//            public void onSuccess(Location location) {
//                if (location != null){
//                    currentLatitude= location.getLatitude();
//                    currentLongitude = location.getLongitude();
//                    supportMapFragment.getMapAsync(new OnMapReadyCallback() {
//                        @Override
//                        public void onMapReady(GoogleMap googleMap) {
//                            //Add a marker in location and move the camera
//                            mMap= googleMap;
//                            LatLng location = new LatLng(currentLatitude,currentLongitude);
//                            CameraPosition cameraPosition = new CameraPosition.Builder().target(location).zoom(10).build();
//                            googleMap.addMarker(new MarkerOptions().position(location).title("Your Current location"))
//                                    .setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));
//                            googleMap.moveCamera(CameraUpdateFactory.newLatLng(location));
//                            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(location,10));
//                        }
//                    });
//                }
//
//            }
//        });
//    }


    private void buildGoogleApiClient() {
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }

    @Override
    public void onLocationChanged(@NonNull Location location) {

    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        return false;
    }
}
