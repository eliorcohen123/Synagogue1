package com.eliorcohen1.synagogue.ClassesPackage;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.location.Location;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.eliorcohen1.synagogue.R;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.firebase.auth.FirebaseAuth;

import guy4444.smartrate.SmartRate;

public class MainActivity extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener, NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {

    private TextView stripe;
    private Button responsible, worshipers, donates, winterClock, summerClock, mapMe, alarms, events;
    private LinearLayout container;
    private AnimationDrawable anim;
    private Toolbar toolbar;
    private static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;
    private FusedLocationProviderClient mFusedLocationClient;
    private LocationRequest mLocationRequest;
    private LocationCallback mLocationCallback;
    private Location mLastLocation;
    private static final String TAG = "MyLocation";
    private GoogleApiClient mGoogleApiClient;
    private DrawerLayout drawer;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getMyLocation();
        initUI();
        initListeners();
        initAppRater();
        drawerLayout();
        showUI();
    }

    @Override
    public void onStart() {
        super.onStart();

        mAuth.addAuthStateListener(mAuthListener);
        if (!checkPermissions()) {
            Log.i(TAG, "Inside onStart function; requesting permission when permission is not available");
            requestPermissions();
        } else {
            Log.i(TAG, "Inside onStart function; getting location when permission is already available");
            getLastLocation();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (anim != null && !anim.isRunning()) {
            anim.start();
        }

        startLocationUpdates();
    }

    @Override
    protected void onPause() {
        super.onPause();

        if (anim != null && anim.isRunning()) {
            anim.stop();
        }

        stopLocationUpdates();
    }

    private void initUI() {
        responsible = findViewById(R.id.responsible);
        worshipers = findViewById(R.id.worshipers);
        donates = findViewById(R.id.donates);
        winterClock = findViewById(R.id.winterClock);
        summerClock = findViewById(R.id.summerClock);
        mapMe = findViewById(R.id.mapMe);
        alarms = findViewById(R.id.alarms);
        events = findViewById(R.id.events);

        container = findViewById(R.id.container);

        stripe = findViewById(R.id.stripe);
        drawer = findViewById(R.id.drawer_layout);

        toolbar = findViewById(R.id.toolbar);
    }

    private void initListeners() {
        summerClock.setOnClickListener(this);
        winterClock.setOnClickListener(this);
        responsible.setOnClickListener(this);
        worshipers.setOnClickListener(this);
        donates.setOnClickListener(this);
        mapMe.setOnClickListener(this);
        alarms.setOnClickListener(this);
        events.setOnClickListener(this);
    }

    private void initAppRater() {
        SmartRate.Rate(MainActivity.this
                , "Rate Us"
                , "Tell others what you think about this app"
                , "Continue"
                , "Please take a moment and rate us on Google Play"
                , "click here"
                , "Ask me later"
                , "Never ask again"
                , "Cancel"
                , "Thanks for the feedback"
                , Color.parseColor("#2196F3")
                , 5
                , 1
                , 1
        );
    }

    private void drawerLayout() {
        setSupportActionBar(toolbar);

        findViewById(R.id.myButton).setOnClickListener(v -> {
            // open right drawer

            if (drawer.isDrawerOpen(GravityCompat.END)) {
                drawer.closeDrawer(GravityCompat.END);
            } else {
                drawer.openDrawer(GravityCompat.END);
            }
        });

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        toggle.setDrawerIndicatorEnabled(false);
        drawer.addDrawerListener(toggle);

        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    private void showUI() {
        anim = (AnimationDrawable) container.getBackground();
        anim.setEnterFadeDuration(6000);
        anim.setExitFadeDuration(2000);

        stripe.setText("יְבָרֶכְךָ יְהוָה נְוֵה צֶדֶק הַר הַקֹּדֶשׁ");

        mAuth = FirebaseAuth.getInstance();
        mAuthListener = FirebaseAuth::getCurrentUser;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.summerClock:
                Intent intent = new Intent(MainActivity.this, SummerClock.class);
                startActivity(intent);
                break;
            case R.id.winterClock:
                Intent intent2 = new Intent(MainActivity.this, WinterClock.class);
                startActivity(intent2);
                break;
            case R.id.responsible:
                Intent intent3 = new Intent(MainActivity.this, Responsible.class);
                startActivity(intent3);
                break;
            case R.id.worshipers:
                Intent intent4 = new Intent(MainActivity.this, Worshipers.class);
                startActivity(intent4);
                break;
            case R.id.donates:
                Intent intent5 = new Intent(MainActivity.this, Donates.class);
                startActivity(intent5);
                break;
            case R.id.mapMe:
                Intent intent6 = new Intent(MainActivity.this, FragmentActivityMy.class);
                startActivity(intent6);
                break;
            case R.id.alarms:
                Intent intent7 = new Intent(MainActivity.this, MyAlarm.class);
                startActivity(intent7);
                break;
            case R.id.events:
                Intent intent8 = new Intent(MainActivity.this, Events.class);
                startActivity(intent8);
                break;
        }
    }

    private void getMyLocation() {
        // Start all of check location
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        mLocationCallback = new LocationCallback();

        createLocationRequest();

        new LocationSettingsRequest.Builder().addLocationRequest(mLocationRequest);

        // Start all of checkGoogleApi
        buildGoogleApiClient();

        if (mGoogleApiClient != null) {
            mGoogleApiClient.connect();
        } else {
            Toast.makeText(this, "האינטרנט לא מחובר...", Toast.LENGTH_SHORT).show();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.tutorial) {
            Intent intentTutorial = new Intent(MainActivity.this, WelcomeActivityTutorial.class);
            startActivity(intentTutorial);
        } else if (id == R.id.shareIntent) {
            Intent sendIntent = new Intent();
            sendIntent.setAction(Intent.ACTION_SEND);
            sendIntent.putExtra(Intent.EXTRA_TEXT,
                    "היי, אתה מוזמן לבקר באפליקצייה שלי: https://play.google.com/store/apps/details?id=com.eliorcohen1.synagogue");
            sendIntent.setType("text/plain");
            startActivity(sendIntent);
        } else if (id == R.id.credits) {
            Intent intentCredits = new Intent(MainActivity.this, MyCredits.class);
            startActivity(intentCredits);
        } else if (id == R.id.logout) {
            mAuth.signOut();
            startActivity(new Intent(MainActivity.this, SignIn_activity.class));
        } else if (id == R.id.exit) {
            ActivityCompat.finishAffinity(this);
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.END);
        return true;
    }

    protected void createLocationRequest() {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(5000);
        mLocationRequest.setFastestInterval(4000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    }

    private void startLocationUpdates() {
        if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mFusedLocationClient.requestLocationUpdates(mLocationRequest, mLocationCallback, null /* Looper */);
    }

    private void stopLocationUpdates() {
        mFusedLocationClient.removeLocationUpdates(mLocationCallback);
    }

    // Return whether permissions is needed as boolean value.
    private boolean checkPermissions() {
        int permissionState = ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);
        return permissionState == PackageManager.PERMISSION_GRANTED;
    }

    //Request permission from user
    private void requestPermissions() {
        Log.i(TAG, "Inside requestPermissions function");
        boolean shouldProvideRationale = ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION);
        if (shouldProvideRationale) {
            Log.i(TAG, "****Inside requestPermissions function when shouldProvideRationale = true");
            startLocationPermissionRequest();
        } else {
            Log.i(TAG, "****Inside requestPermissions function when shouldProvideRationale = false");
            startLocationPermissionRequest();
        }
    }

    // Start the permission request dialog
    private void startLocationPermissionRequest() {
        ActivityCompat.requestPermissions(MainActivity.this,
                new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                MY_PERMISSIONS_REQUEST_LOCATION);
    }

    // get LastLocation
    @SuppressWarnings("MissingPermission")
    private void getLastLocation() {
        mFusedLocationClient.getLastLocation()
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful() && task.getResult() != null) {
                        mLastLocation = task.getResult();
                    } else {
                        Log.i(TAG, "Inside getLocation function. Error while getting location");
                        System.out.println(TAG + task.getException());
                    }
                });
    }

    @Override
    public void onConnected(Bundle arg0) {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
    }

    @Override
    public void onConnectionFailed(ConnectionResult arg0) {
        Toast.makeText(this, "ההתחברות לאינטרנט נכשלה...", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onConnectionSuspended(int arg0) {
        Toast.makeText(this, "ההתחברות לאינטרנט מושעית...", Toast.LENGTH_SHORT).show();
    }

    // Build the GoogleApi
    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
    }

}
