package com.example.marcm.seccion_09_maps.Fragments;


import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

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

public class MapFragment extends Fragment implements OnMapReadyCallback, GoogleMap.OnMarkerDragListener {

    private View rootView;
    private GoogleMap gMap;
    private MapView mapView;

    private Geocoder geocoder;
    private List<Address> addresses;

    private MarkerOptions marker;

    public MapFragment() {
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
        gMap.animateCamera(zoom);;

        /* Set OnMarkerDrag Listener*/
        gMap.setOnMarkerDragListener(this);

        /* Este objeto nos permite obtener los datos del mapa tales como calles, paises,...) */
        geocoder = new Geocoder(getContext(), Locale.getDefault());
    }

    @Override
    public void onMarkerDragStart(Marker marker) {

    }

    @Override
    public void onMarkerDrag(Marker marker) {

    }

    @Override
    public void onMarkerDragEnd(Marker marker) {
        double latitude= marker.getPosition().latitude;
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

        /*Toast.makeText(
                getContext(),
                "Address: " + address + "\n" +
                        "City: " + city + "\n" +
                        "State: " + state + "\n" +
                        "Country: " + country + "\n" +
                        "Postal code: " + postalCode,
                Toast.LENGTH_LONG
        ).show();*/
    }
}
