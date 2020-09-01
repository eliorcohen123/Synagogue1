package com.eliorcohen1.synagogue.PagesPackage;

import android.Manifest;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.eliorcohen1.synagogue.CustomAdaptersPackage.CustomInfoWindowGoogleMap;
import com.eliorcohen1.synagogue.R;
import com.eliorcohen1.synagogue.ModelsPackage.TotalModel;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
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
import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;

public class MapFragment extends Fragment implements OnMapReadyCallback, View.OnClickListener {

    private GoogleMap mGoogleMap;
    private MapView mMapView;
    private View mView;
    private MarkerOptions marker;
    private Location location;
    private LocationManager locationManager;
    private Criteria criteria;
    private String provider;
    private ImageView moovit, gett, waze, btnOpenList;
    private LinearLayout linearList;
    private boolean isClicked;
    private AlphaAnimation anim;
    private static LinearLayout linearLayoutYes, linearLayoutNo;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_map, container, false);

        initUI();
        initListeners();
        initLocation();
        setButtonsEnabledState();

        return mView;
    }

    private void initUI() {
        moovit = mView.findViewById(R.id.imageViewMoovit);
        gett = mView.findViewById(R.id.imageViewGett);
        waze = mView.findViewById(R.id.imageViewWaze);
        btnOpenList = mView.findViewById(R.id.btnOpenList);

        linearList = mView.findViewById(R.id.listAll);

        linearLayoutYes = mView.findViewById(R.id.linYes);
        linearLayoutNo = mView.findViewById(R.id.linNo);

        linearList.setVisibility(View.GONE);

        isClicked = true;
    }

    private void initListeners() {
        moovit.setOnClickListener(this);
        gett.setOnClickListener(this);
        waze.setOnClickListener(this);
        btnOpenList.setOnClickListener(this);
    }

    private void initLocation() {
        locationManager = (LocationManager) Objects.requireNonNull(getActivity()).getSystemService(Context.LOCATION_SERVICE);
        criteria = new Criteria();
        provider = locationManager.getBestProvider(criteria, true);
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
            MapsInitializer.initialize(Objects.requireNonNull(getContext()));
            mGoogleMap = googleMap;
            addMarker();
            mGoogleMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
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
            mGoogleMap.setMyLocationEnabled(true);
            mGoogleMap.getUiSettings().setZoomControlsEnabled(true);
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
                    mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(location.getLatitude(), location.getLongitude()), 8));
                }
            }
        } catch (Exception e) {

        }
    }

    private void addMarker() {
        marker = new MarkerOptions().position(new LatLng(31.742462, 34.985447)).title("בית הכנסת - נווה צדק")
                .icon(BitmapDescriptorFactory.fromBitmap(createCustomMarker(Objects.requireNonNull(getContext()), R.drawable.pic_synagogue)));
        mGoogleMap.addMarker(marker);
        mGoogleMap.setOnMarkerClickListener(marker -> {
            if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION);
            }// TODO: Consider calling
//    ActivityCompat#requestPermissions
// here to request the missing permissions, and then overriding
//   public void onRequestPermissionsResult(int requestCode, String[] permissions,
//                                          int[] grantResults)
// to handle the case where the user grants the permission. See the documentation
// for ActivityCompat#requestPermissions for more details.
            if (provider != null) {
                location = locationManager.getLastKnownLocation(provider);
                if (location != null) {
                    TotalModel info = new TotalModel();
                    double distanceMe;
                    Location locationA = new Location("Point A");
                    locationA.setLatitude(31.742462);
                    locationA.setLongitude(34.985447);
                    Location locationB = new Location("Point B");
                    locationB.setLatitude(location.getLatitude());
                    locationB.setLongitude(location.getLongitude());
                    distanceMe = locationA.distanceTo(locationB) / 1000;

                    String distanceKm1;
                    if (distanceMe < 1) {
                        int dis = (int) (distanceMe * 1000);
                        distanceKm1 = "\n" + "Meters: " + String.valueOf(dis);
                        info.setName(info.getName());
                        info.setImageView("pic_synagogue");
                        info.setDistance(distanceKm1);
                    } else if (distanceMe >= 1) {
                        String disM = String.format("%.2f", distanceMe);
                        distanceKm1 = "\n" + "Km: " + String.valueOf(disM);
                        info.setName(info.getName());
                        info.setImageView("pic_synagogue");
                        info.setDistance(distanceKm1);
                    }

                    CustomInfoWindowGoogleMap customInfoWindow = new CustomInfoWindowGoogleMap(getActivity());
                    mGoogleMap.setInfoWindowAdapter(customInfoWindow);

                    marker.setTag(info);
                    marker.showInfoWindow();

                    double lat = location.getLatitude();
                    double lng = location.getLongitude();
                    String lat1 = String.valueOf(lat);
                    String lng1 = String.valueOf(lng);

                    //Define list to get all latLng for the route
                    ArrayList<LatLng> path = new ArrayList<LatLng>();

                    //Execute Directions API request
                    GeoApiContext context = new GeoApiContext.Builder()
                            .apiKey(getString(R.string.api_key_geo))
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
//                        Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                    }

                    //Draw the polyline
                    if (path.size() > 0) {
                        PolylineOptions opts = new PolylineOptions().addAll(path).color(Color.rgb(153, 153, 102)).width(5);
                        mGoogleMap.addPolyline(opts);
                    }
                }
            }

            Toast.makeText(getContext(), "בית הכנסת - נווה צדק", Toast.LENGTH_SHORT).show();
            return true;
        });
    }

    private static Bitmap createCustomMarker(Context context, @DrawableRes int resource) {
        View marker = ((LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.custom_marker_layout, null);
        CircleImageView markerImage = marker.findViewById(R.id.user_dp);
        markerImage.setImageResource(resource);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        marker.setLayoutParams(new ViewGroup.LayoutParams(52, ViewGroup.LayoutParams.WRAP_CONTENT));
        marker.measure(displayMetrics.widthPixels, displayMetrics.heightPixels);
        marker.layout(0, 0, displayMetrics.widthPixels, displayMetrics.heightPixels);
        marker.buildDrawingCache();
        Bitmap bitmap = Bitmap.createBitmap(marker.getMeasuredWidth(), marker.getMeasuredHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        marker.draw(canvas);

        return bitmap;
    }

    public static void setButtonsEnabledState() {
        if (FragmentActivityMy.getGeofencesAdded()) {
            linearLayoutYes.setVisibility(View.GONE);
            linearLayoutNo.setVisibility(View.VISIBLE);
        } else {
            linearLayoutYes.setVisibility(View.VISIBLE);
            linearLayoutNo.setVisibility(View.GONE);
        }
    }

    private void getGetTaxi() {
        if (isPackageInstalledGetTaxi(Objects.requireNonNull(getContext()))) {
            openLinkGetTaxi((Objects.requireNonNull(getActivity())), "gett://order?pickup=my_location&dropoff_latitude=31.742462&dropoff_longitude=34.985447&product_id=0c1202f8-6c43-4330-9d8a-3b4fa66505fd");
        } else {
            openLinkGetTaxi(Objects.requireNonNull(getActivity()), "https://play.google.com/store/apps/details?id=" + "com.gettaxi.android");
        }
    }

    private static void openLinkGetTaxi(Activity activity, String link) {
        Intent playStoreIntent = new Intent(Intent.ACTION_VIEW);
        playStoreIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        playStoreIntent.setData(Uri.parse(link));
        activity.startActivity(playStoreIntent);
    }

    private static boolean isPackageInstalledGetTaxi(Context context) {
        PackageManager pm = context.getPackageManager();
        try {
            pm.getPackageInfo("com.gettaxi.android", PackageManager.GET_ACTIVITIES);
            return true;
        } catch (PackageManager.NameNotFoundException e) {

        }
        return false;
    }

    private void setFadeAnimationTrue(LinearLayout linearList) {
        anim = new AlphaAnimation(0.0f, 1.0f);
        anim.setDuration(1500);
        linearList.startAnimation(anim);
    }

    private void setFadeAnimationFalse(LinearLayout linearList) {
        anim = new AlphaAnimation(1.0f, 0.0f);
        anim.setDuration(1500);
        linearList.startAnimation(anim);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imageViewMoovit:
                try {
                    PackageManager pm = Objects.requireNonNull(getActivity()).getPackageManager();
                    pm.getPackageInfo("com.tranzmate", PackageManager.GET_ACTIVITIES);
                    String uri = "moovit://directions?dest_lat=31.742462&dest_lon=34.985447&dest_name=בית הכנסת - נווה צדק&orig_lat=" + location.getLatitude() + "&orig_lon=" + location.getLongitude() + "&orig_name=מיקומך הנוכחי&auto_run=true&partner_id=בית הכנסת - נווה צדק";
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse(uri));
                    startActivity(intent);
                } catch (PackageManager.NameNotFoundException e) {
                    String url = "http://app.appsflyer.com/com.tranzmate?pid=DL&c=בית הכנסת - נווה צדק";
                    Intent i = new Intent(Intent.ACTION_VIEW);
                    i.setData(Uri.parse(url));
                    startActivity(i);
                }
                break;
            case R.id.imageViewGett:
                getGetTaxi();
                break;
            case R.id.imageViewWaze:
                try {
                    String url = "https://www.waze.com/ul?ll=31.742462%2C34.985447&navigate=yes&zoom=17";
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                    startActivity(intent);
                } catch (ActivityNotFoundException ex) {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=com.waze"));
                    startActivity(intent);
                }
                break;
            case R.id.btnOpenList:
                if (isClicked) {
                    linearList.setVisibility(View.VISIBLE);
                    setFadeAnimationTrue(linearList);
                    isClicked = false;
                } else {
                    linearList.setVisibility(View.GONE);
                    setFadeAnimationFalse(linearList);
                    isClicked = true;
                }
                break;
        }
    }

}
