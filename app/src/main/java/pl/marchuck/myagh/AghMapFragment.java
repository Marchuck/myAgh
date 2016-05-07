package pl.marchuck.myagh;


import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;

import java.util.Random;

import pl.marchuck.myagh.utils.Asyncs;
import pl.marchuck.myagh.utils.DefaultError;
import rx.functions.Action1;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AghMapFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AghMapFragment extends Fragment {
    public static final String TAG = AghMapFragment.class.getSimpleName();
    SupportMapFragment supportMapFragment;
    GoogleMap googleMap;

    public AghMapFragment() {
        // Required empty public constructor
    }

    public static AghMapFragment newInstance() {
        AghMapFragment fragment = new AghMapFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_agh_map, container, false);
        initMap();
        return view;
    }

    public void initMap() {
        //lazy initialization
        if (supportMapFragment == null) supportMapFragment = SupportMapFragment.newInstance();

        getFragmentManager().beginTransaction()
                .replace(R.id.mapHolder, supportMapFragment)
                .commitAllowingStateLoss();

        Asyncs.getMap(supportMapFragment).subscribe(new Action1<GoogleMap>() {
            @Override
            public void call(GoogleMap _googleMap) {
                googleMap = _googleMap;
                setupMap();
            }
        }, DefaultError.create(TAG, getActivity(), getView()));
    }

    private boolean locationPermissionsGranted() {
        return ActivityCompat.checkSelfPermission(this.getActivity(),
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this.getActivity(),
                Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED;
    }

    public void setupMap() {
        Log.d(TAG, "setupMap: ");
//        if (!locationPermissionsGranted()) {
//            requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION,
//                    Manifest.permission.ACCESS_FINE_LOCATION}, MainActivity.LOCATION_PERMISSIONS);
//            return;
//        }
        googleMap.setMyLocationEnabled(true);

        googleMap.setOnMapLoadedCallback(new GoogleMap.OnMapLoadedCallback() {
            @Override
            public void onMapLoaded() {
                Location location = googleMap.getMyLocation();
                LatLng myLocation = new LatLng(50, 19);
                if (MyApp.instance.lastLocation != null) {
                    myLocation = new LatLng(MyApp.instance.lastLocation.getLatitude(),
                            MyApp.instance.lastLocation.getLongitude());
                }
                googleMap.animateCamera(CameraUpdateFactory
                        .newCameraPosition(new CameraPosition(myLocation, 16, 60, new Random().nextInt(360))));
            }
        });
    }

}
