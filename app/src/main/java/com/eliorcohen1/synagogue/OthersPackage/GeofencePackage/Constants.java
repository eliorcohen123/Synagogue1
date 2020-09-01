package com.eliorcohen1.synagogue.OthersPackage.GeofencePackage;

public final class Constants {

    private Constants() {

    }

    private static final String PACKAGE_NAME = "com.eliorcohen12345.locationproject";
    public static final String GEOFENCES_ADDED_KEY = PACKAGE_NAME + ".GEOFENCES_ADDED_KEY";
    private static final long GEOFENCE_EXPIRATION_IN_HOURS = -1;
    public static final long GEOFENCE_EXPIRATION_IN_MILLISECONDS = GEOFENCE_EXPIRATION_IN_HOURS * 60 * 60 * 1000;

}
