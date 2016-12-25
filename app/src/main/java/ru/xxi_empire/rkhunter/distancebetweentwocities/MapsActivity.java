package ru.xxi_empire.rkhunter.distancebetweentwocities;

import android.graphics.Color;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import ru.xxi_empire.rkhunter.distancebetweentwocities.helpers.Calculator;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    Calculator _calculator;
    TextView distanceLabel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        _calculator = new Calculator();
        distanceLabel = (TextView) findViewById(R.id.distance_label);
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
        Bundle bundle = getIntent().getParcelableExtra("bundle");

        LatLng HOME_CITY = bundle.getParcelable("HOME_CITY");
        if (HOME_CITY != null) {
            mMap.addMarker(new MarkerOptions().position(HOME_CITY).title("Home city"));
        }


        LatLng DESTINATION_CITY = bundle.getParcelable("DESTINATION_CITY");
        if (DESTINATION_CITY != null) {
            mMap.addMarker(new MarkerOptions().position(DESTINATION_CITY).title("Destination city"));
        }

        if (HOME_CITY != null && DESTINATION_CITY != null) {
            PolylineOptions line = new PolylineOptions().add(HOME_CITY, DESTINATION_CITY).width(20).color(Color.RED);
            mMap.addPolyline(line);
            distanceLabel.setText(Integer.toString((int) _calculator.Distance(HOME_CITY, DESTINATION_CITY)/1000) + "km");
            mMap.moveCamera(CameraUpdateFactory.newLatLng(_calculator.Midpoint(HOME_CITY,DESTINATION_CITY)));
        }
    }
}
