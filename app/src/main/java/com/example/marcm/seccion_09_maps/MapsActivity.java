package com.example.marcm.seccion_09_maps;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
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

        // Add a marker in Seville
        LatLng sevilla = new LatLng(37.40911491941731, -5.99075691250005);
        mMap.addMarker(new MarkerOptions().position(sevilla).title("Hola desde Sevilla!"));

        /* Creamos objeto camara para configurar la vista
            target = donde enfoca la camara
            zoom = || 1 (mundo) | 5 (continente) | 10 (ciudad) | 15 (calle) | 20 (edificio) ||
            bearing = orientacion de la camara hacia el este
            tilt = inclinacion de la camara
        */
        CameraPosition camera = new CameraPosition.Builder()
                .target(sevilla)
                .zoom(18)           // limit -> 21
                .bearing(0)         // 0 - 360 degrees
                .tilt(45)           // 0 - 90 degree
                .build();

        // ... y la configuramos en el mapa
        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(camera));
        //mMap.moveCamera(CameraUpdateFactory.newLatLng(sevilla));
    }
}
