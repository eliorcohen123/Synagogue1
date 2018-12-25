package com.eliorcohen1.synagogue.ButtonsPackage;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.eliorcohen1.synagogue.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.maps.DirectionsApi;
import com.google.maps.DirectionsApiRequest;
import com.google.maps.GeoApiContext;
import com.google.maps.model.DirectionsLeg;
import com.google.maps.model.DirectionsResult;
import com.google.maps.model.DirectionsRoute;
import com.google.maps.model.DirectionsStep;
import com.google.maps.model.EncodedPolyline;

import java.util.ArrayList;
import java.util.List;

public class MapFragment extends Fragment implements OnMapReadyCallback {

    private GoogleMap mGoogleMap;
    private MapView mMapView;
    private View mView;
    private MarkerOptions marker;
    private Location location;
    private LocationManager locationManager;
    private Criteria criteria;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.activity_maps, container, false);
        return mView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mMapView = view.findViewById(R.id.map);
        if (mMapView != null) {
            mMapView.onCreate(null);
            mMapView.onResume();
            mMapView.getMapAsync(this);
        }
    }

    @Override
    public void onMapReady(final GoogleMap googleMap) {

        try {
            MapsInitializer.initialize(getContext());
            mGoogleMap = googleMap;
            addMarker();
            googleMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
            if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            googleMap.setMyLocationEnabled(true);
            googleMap.getUiSettings().setZoomControlsEnabled(true);
            locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
            criteria = new Criteria();
            String provider2 = locationManager.getBestProvider(criteria, true);
            if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                    ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
            }
            if (provider2 != null) {
                location = locationManager.getLastKnownLocation(provider2);
                if (location != null) {
                    double lat = location.getLatitude();
                    double lng = location.getLongitude();
                    String lat1 = String.valueOf(lat);
                    String lng1 = String.valueOf(lng);

                    //Define list to get all latLng for the route
                    ArrayList<LatLng> path = new ArrayList<LatLng>();

                    //Execute Directions API request
                    GeoApiContext context = new GeoApiContext.Builder()
                            .apiKey("AIzaSyBrPt88vvoPDDn_imh-RzCXl5Ha2F2LYig")
                            .build();
                    DirectionsApiRequest req = DirectionsApi.getDirections(context, lat1 + ", " + lng1, "31.742462, 34.985447");
                    try {
                        DirectionsResult res = req.await();

                        //Loop through legs and steps to get encoded polyLines of each step
                        if (res.routes != null && res.routes.length > 0) {
                            DirectionsRoute route = res.routes[0];

                            if (route.legs != null) {
                                for (int i = 0; i < route.legs.length; i++) {
                                    DirectionsLeg leg = route.legs[i];
                                    if (leg.steps != null) {
                                        for (int j = 0; j < leg.steps.length; j++) {
                                            DirectionsStep step = leg.steps[j];
                                            if (step.steps != null && step.steps.length > 0) {
                                                for (int k = 0; k < step.steps.length; k++) {
                                                    DirectionsStep step1 = step.steps[k];
                                                    EncodedPolyline points1 = step1.polyline;
                                                    if (points1 != null) {
                                                        //Decode polyline and add points to list of route coordinates
                                                        List<com.google.maps.model.LatLng> coords1 = points1.decodePath();
                                                        for (com.google.maps.model.LatLng coord1 : coords1) {
                                                            path.add(new LatLng(coord1.lat, coord1.lng));
                                                        }
                                                    }
                                                }
                                            } else {
                                                EncodedPolyline points = step.polyline;
                                                if (points != null) {
                                                    //Decode polyline and add points to list of route coordinates
                                                    List<com.google.maps.model.LatLng> coords = points.decodePath();
                                                    for (com.google.maps.model.LatLng coord : coords) {
                                                        path.add(new LatLng(coord.lat, coord.lng));
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    } catch (Exception e) {
                        Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                    }

                    //Draw the polyline
                    if (path.size() > 0) {
                        PolylineOptions opts = new PolylineOptions().addAll(path).color(Color.BLUE).width(5);
                        mGoogleMap.addPolyline(opts);
                    }
                }
                googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(location.getLatitude(), location.getLongitude()), 8));
            }
        } catch (Exception e) {

        }
    }

    public void addMarker() {
        marker = new MarkerOptions().position(new LatLng(31.742462, 34.985447)).title("בית הכנסת - נווה צדק").icon(BitmapDescriptorFactory
                .defaultMarker(BitmapDescriptorFactory.HUE_ORANGE));
        mGoogleMap.addMarker(marker);
        mGoogleMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                Toast.makeText(getContext(), "בית הכנסת - נווה צדק", Toast.LENGTH_SHORT).show();
                locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
                criteria = new Criteria();
                String provider = locationManager.getBestProvider(criteria, true);
                if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                        ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                }
                if (provider != null) {
                    location = locationManager.getLastKnownLocation(provider);
                    if (location != null) {
                        double lat = location.getLatitude();
                        double lng = location.getLongitude();
                        String lat1 = String.valueOf(lat);
                        String lng1 = String.valueOf(lng);

                        //Define list to get all latLng for the route
                        ArrayList<LatLng> path = new ArrayList<LatLng>();

                        //Execute Directions API request
                        GeoApiContext context = new GeoApiContext.Builder()
                                .apiKey("AIzaSyBrPt88vvoPDDn_imh-RzCXl5Ha2F2LYig")
                                .build();
                        DirectionsApiRequest req = DirectionsApi.getDirections(context, lat1 + ", " + lng1, "31.742462, 34.985447");
                        try {
                            DirectionsResult res = req.await();

                            //Loop through legs and steps to get encoded polyLines of each step
                            if (res.routes != null && res.routes.length > 0) {
                                DirectionsRoute route = res.routes[0];

                                if (route.legs != null) {
                                    for (int i = 0; i < route.legs.length; i++) {
                                        DirectionsLeg leg = route.legs[i];
                                        if (leg.steps != null) {
                                            for (int j = 0; j < leg.steps.length; j++) {
                                                DirectionsStep step = leg.steps[j];
                                                if (step.steps != null && step.steps.length > 0) {
                                                    for (int k = 0; k < step.steps.length; k++) {
                                                        DirectionsStep step1 = step.steps[k];
                                                        EncodedPolyline points1 = step1.polyline;
                                                        if (points1 != null) {
                                                            //Decode polyline and add points to list of route coordinates
                                                            List<com.google.maps.model.LatLng> coords1 = points1.decodePath();
                                                            for (com.google.maps.model.LatLng coord1 : coords1) {
                                                                path.add(new LatLng(coord1.lat, coord1.lng));
                                                            }
                                                        }
                                                    }
                                                } else {
                                                    EncodedPolyline points = step.polyline;
                                                    if (points != null) {
                                                        //Decode polyline and add points to list of route coordinates
                                                        List<com.google.maps.model.LatLng> coords = points.decodePath();
                                                        for (com.google.maps.model.LatLng coord : coords) {
                                                            path.add(new LatLng(coord.lat, coord.lng));
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        } catch (Exception e) {
                            Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                        }

                        //Draw the polyline
                        if (path.size() > 0) {
                            PolylineOptions opts = new PolylineOptions().addAll(path).color(Color.BLUE).width(5);
                            mGoogleMap.addPolyline(opts);
                        }
                    }
                }
                return true;
            }
        });
    }

}
