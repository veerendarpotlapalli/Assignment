package com.example.assignment;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationRequest;

import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class maps extends AppCompatActivity implements OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, ResultCallback {

    private GoogleMap mMap;
    RecyclerView recyclerView;
    LinearLayoutManager layoutManager;
    adapter_rec adapter;
    List<model> modelList = new ArrayList<>();

    GoogleApiClient googleApiClient;
    FusedLocationProviderClient fusedLocationProviderClient;
    final int ACCESS_LOCATION_REQUEST_CODE = 1002;
    Geocoder geocoder;
    SupportMapFragment mapFragment; // for search view
    LocationManager locationManager;
    LocationListener locationListener;
    private LocationRequest locationRequest;
    public static final int REQUEST_CHECK_SETTINGS = 1001;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(2000);
        locationRequest.setFastestInterval(1000);

        mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.frag);
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        try {

            SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.frag);
            mapFragment.getMapAsync(this);
            geocoder = new Geocoder(this);
        } catch (Exception e) {
            e.printStackTrace();
        }// catch

        try {
            recyclerView = findViewById(R.id.recview);
            layoutManager = new LinearLayoutManager(this);
            recyclerView.setLayoutManager(layoutManager);
            adapter = new adapter_rec(modelList);
            recyclerView.setAdapter(adapter);

            fetch();

        } catch (Exception e) {
            e.printStackTrace();
        }//catch
    }//oncreate

    private void fetch() {
        try {
            retrofit.apis().getPosts().enqueue(new Callback<List<model>>() {
                @SuppressLint("NotifyDataSetChanged")
                @Override
                public void onResponse(Call<List<model>> call, Response<List<model>> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        modelList.addAll(response.body());
                        adapter.notifyDataSetChanged();
                    }//if
                }

                @Override
                public void onFailure(Call<List<model>> call, Throwable t) {
                    Toast.makeText(maps.this, "TURN ON INTERNET", Toast.LENGTH_SHORT).show();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }//catch
    }


    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {

        try{

        mMap = googleMap;

            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            currentlocation();
            zoomtouserlocation();
        } //check self permission (if)
        else {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)) {
                // we can say why this permission is needed.....
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, ACCESS_LOCATION_REQUEST_CODE);
            } else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, ACCESS_LOCATION_REQUEST_CODE);
            }
        } // else
    }catch (Exception e) {
        e.printStackTrace();
    }//catch
    }//onmapready

    @SuppressLint("MissingPermission")
    private void currentlocation() {
        try{
        //mMap.setMyLocationEnabled(true);
//        try {
//            locationManager = (LocationManager) getApplicationContext().getSystemService(LOCATION_SERVICE);
//            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 1, (LocationListener) maps.this);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }//catch
    }catch (Exception e) {
        e.printStackTrace();
    }//catch
    } // currentlocation

    private void zoomtouserlocation() {

        try{
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        Task<Location> locationTask = fusedLocationProviderClient.getLastLocation();
        locationTask.addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                LatLng latLng3 = new LatLng(location.getLatitude(), location.getLongitude());
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng3, 16));
                mMap.addMarker(new MarkerOptions().position(latLng3).title("location"));

                // mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng3, 16));
            } // onsuccess
        }); //addOnsuccessListner
    }catch (Exception e) {
        e.printStackTrace();
    }//catch
    } // zoomtouserlocation


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        try{
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == ACCESS_LOCATION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) ;
            currentlocation();
            zoomtouserlocation();
        } else {
            // we can say why this permission is needed.......
            if (!ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)) { // this is a block if completely denied permission

                new AlertDialog.Builder(maps.this)
                        .setMessage("you have permanently deneid the permission to allow permisson go to settings ")
                        .setPositiveButton("Go to setting", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                gotosettings();
                            } // onclick
                        }) //positive button
                        .setNegativeButton("cancel", null)
                        .setCancelable(false)
                        .show();
            } //if

        }
        }catch (Exception e) {
            e.printStackTrace();
        }//catch
    } //onRequestPermissionsResult

    private void gotosettings() {
        try {
            Intent intent = new Intent();
            intent.setAction(Settings.ACTION_APPLICATION_SETTINGS);
            Uri uri = Uri.fromParts("package", this.getPackageName(), null);
            intent.setData(uri);
            startActivity(intent);
        }catch (Exception e) {
            e.printStackTrace();
        }//catch
    } // gotosettings


    @Override
    public void onResult(@NonNull Result result) {

        final Status status = result.getStatus();
        switch (status.getStatusCode()){
            case LocationSettingsStatusCodes.SUCCESS:
                break;

            case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                try {
                    status.startResolutionForResult(maps.this,REQUEST_CHECK_SETTINGS);
                } catch (IntentSender.SendIntentException ex) {

                }
                break;

                case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                break;
        }//switch

    }//onresult

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                .addLocationRequest(locationRequest);
        builder.setAlwaysShow(true);
        PendingResult result = LocationServices.SettingsApi.checkLocationSettings(googleApiClient,builder.build());
        result.setResultCallback(this);
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == REQUEST_CHECK_SETTINGS){
            switch(requestCode){
                case Activity.RESULT_OK:
                    Toast.makeText(maps.this,"Location Enabled",Toast.LENGTH_SHORT).show();
                    break;

                case Activity.RESULT_CANCELED:
                    Toast.makeText(maps.this,"Location Disabled.Turn it On",Toast.LENGTH_SHORT).show();

            }//switch
        }
    }

}//main