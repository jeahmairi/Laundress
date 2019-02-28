package com.example.user.laundress2;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;

public class LaundryShopLocation extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    String location, name, contact;
    TextView lsname, lscontact, lslocation;
    double lat, lng;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.laundryshoplocation);
        lsname = findViewById(R.id.lsname);
        lscontact = findViewById(R.id.lscpnum);
        lslocation = findViewById(R.id.lslocation);
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        name = extras.getString("handwasher_name");
        location = extras.getString("handwasher_location");
        contact = extras.getString("handwasher_contact");
        lsname.setText(name);
        lscontact.setText(contact);
        lslocation.setText(location);
        getLocationFromAddress(location);
        Toast.makeText(LaundryShopLocation.this, " " +getLocationFromAddress(location),Toast.LENGTH_SHORT).show();
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
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
        LatLng shop_location = new LatLng(lat, lng);
        mMap.addMarker(new MarkerOptions().position(shop_location).title(location));
        float zoomLevel = 17.0f; //This goes up to 21
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(shop_location, zoomLevel));

    }
    public String getLocationFromAddress(String strAddress) {

        Geocoder coder = new Geocoder(LaundryShopLocation.this);
        List<Address> address;

        try {
            address = coder.getFromLocationName(strAddress, 1);
            if (address == null) {
                return null;
            }
            Address location = address.get(0);
            lat = location.getLatitude();
            lng = location.getLongitude();

            return lat + "," + lng;
        } catch (Exception e) {
            return null;
        }
    }
}
