package com.example.marcm.seccion_09_maps.Fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.marcm.seccion_09_maps.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapFragment extends Fragment implements OnMapReadyCallback {

    private View rootView;
    private GoogleMap gMap;
    private MapView mapView;

    public MapFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_map, container, false);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mapView = rootView.findViewById(R.id.map);
        if (mapView != null) {
            mapView.onCreate(null);
            mapView.onResume();
            mapView.getMapAsync(this);
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        gMap = googleMap;

        /* Create marker */
        LatLng place = new LatLng(37.3890924, -5.9844589);
        /* Add marker on map */
        gMap.addMarker(new MarkerOptions().position(place).title("Marker in Seville"));

        /* Set camera on added marker */
        //gMap.moveCamera(CameraUpdateFactory.newLatLng(place));

        /* Create camera position */
        CameraPosition camera = new CameraPosition.Builder()
                .target(place)
                .zoom(15)           // limit -> 21
                .bearing(0)         // 0 - 360 degrees
                .tilt(45)           // 0 - 90 degree
                .build();

        /* Set camera position on map */
        gMap.moveCamera(CameraUpdateFactory.newCameraPosition(camera));
    }
}
