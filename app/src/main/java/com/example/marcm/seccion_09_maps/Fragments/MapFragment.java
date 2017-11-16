package com.example.marcm.seccion_09_maps.Fragments;


import android.content.DialogInterface;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.marcm.seccion_09_maps.R;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class MapFragment extends Fragment implements OnMapReadyCallback, GoogleMap.OnMarkerDragListener, View.OnClickListener {

    private View rootView;
    private GoogleMap gMap;
    private MapView mapView;

    private Geocoder geocoder;
    private List<Address> addresses;

    private MarkerOptions marker;

    private FloatingActionButton fab;

    public static int GPS_DISABLED = 0;
    public static int GPS_ENABLED = 1;

    public MapFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_map, container, false);

        fab = (FloatingActionButton) rootView.findViewById(R.id.fab);
        fab.setOnClickListener(this);

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

    private void checkIfGpsIsEnabled() {
        try {
            int gpsSignal = Settings.Secure.getInt(getActivity().getContentResolver(), Settings.Secure.LOCATION_MODE);

            if (gpsSignal == GPS_DISABLED) {
                showInfoAlert();
            }
        } catch (Settings.SettingNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void showInfoAlert() {
        new AlertDialog.Builder(getContext())
                .setTitle("GPS Signal")
                .setMessage("You don't have GPS signal enabled. Would you like to enable the GPS signal?")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        startActivity(intent);
                    }
                })
                .setNegativeButton("CANCEL", null)
                .show();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        gMap = googleMap;

        /* Create marker */
        LatLng place = new LatLng(37.3890924, -5.9844589);
        /* Set camera update: zoom */
        CameraUpdate zoom = CameraUpdateFactory.zoomTo(15);

        // Customizando el marker
        marker = new MarkerOptions();
        marker.position(place);
        marker.title("Mi marcador");
        marker.draggable(true);
        marker.snippet("Esto es una caja de texto donde modificar los datos");
        marker.icon(BitmapDescriptorFactory.fromResource(android.R.drawable.star_on));

        /* Add marker on map */
        gMap.addMarker(marker);
        /* Set camera position to added marker */
        gMap.moveCamera(CameraUpdateFactory.newLatLng(place));
        /* Set camera animation: zoom */
        gMap.animateCamera(zoom);
        ;

        /* Set OnMarkerDrag Listener*/
        gMap.setOnMarkerDragListener(this);

        /* Este objeto nos permite obtener los datos del mapa tales como calles, paises,...) */
        geocoder = new Geocoder(getContext(), Locale.getDefault());
    }

    @Override
    public void onMarkerDragStart(Marker marker) {
        marker.hideInfoWindow();
    }

    @Override
    public void onMarkerDrag(Marker marker) {

    }

    @Override
    public void onMarkerDragEnd(Marker marker) {
        double latitude = marker.getPosition().latitude;
        double longitude = marker.getPosition().longitude;

        try {
            addresses = geocoder.getFromLocation(latitude, longitude, 1);
        } catch (IOException e) {
            e.printStackTrace();
        }

        Address currentAddress = addresses.get(0);
        String address = currentAddress.getAddressLine(0);
        String city = currentAddress.getLocality();
        String state = currentAddress.getAdminArea();
        String country = currentAddress.getCountryName();
        String postalCode = currentAddress.getPostalCode();

        marker.setSnippet(city + ",  " + country + " (" + postalCode + ")");
        marker.showInfoWindow();

    }

    @Override
    public void onClick(View view) {
        this.checkIfGpsIsEnabled();
    }
}
