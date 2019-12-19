package com.example.enseirb_neudecknicolas_satomidavid;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;

import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ShowRunActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    PolylineOptions polylineOptions;
    private LatLng start;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_run);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        polylineOptions = new PolylineOptions().clickable(false).width(5).color(Color.RED).visible(true);

        JSONObject mJsonObject = null;
        if(getIntent().hasExtra("json")) {
            try {
                mJsonObject = new JSONObject(getIntent().getStringExtra("json"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        Iterator<String> iter = mJsonObject.keys();
        while (iter.hasNext()) {
            String key = iter.next();
            String value = "0";
            try {
               value  = (String) mJsonObject.get(key);
            } catch (JSONException e) {
                // Something went wrong!
            }
            polylineOptions.add(new LatLng(Float.valueOf(key), Float.valueOf(value)));
            start = new LatLng(Float.valueOf(key), Float.valueOf(value));
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(start, 17);
        mMap.animateCamera(cameraUpdate);

        Polyline polyline = googleMap.addPolyline(polylineOptions);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
