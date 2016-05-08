package pl.marchuck.myagh.utils;

import com.google.android.gms.maps.model.LatLng;

/**
 * @author Lukasz Marczak
 * @since 08.05.16.
 */
public class Locations {

    //AGH library
    public static final double libraryLatitude = 50.06562704041582;
    public static final double libraryLongitude = 19.92146473377943;

    //center of MS AGH
    public static final double msAghLatitude = 50.06834924320465;
    public static final double msAghLongitude = 19.9074562266469;


    public static LatLng asLatLng(double lat, double lon) {
        return new LatLng(lat, lon);
    }

}
