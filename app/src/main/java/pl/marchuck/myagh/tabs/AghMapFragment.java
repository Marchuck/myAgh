package pl.marchuck.myagh.tabs;


import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;

import java.util.Random;

import pl.marchuck.myagh.MyApp;
import pl.marchuck.myagh.R;
import pl.marchuck.myagh.ifaces.FabListener;
import pl.marchuck.myagh.utils.Asyncs;
import pl.marchuck.myagh.utils.DefaultError;
import pl.marchuck.myagh.utils.Defaults;
import pl.marchuck.myagh.utils.StreetView;
import rx.functions.Action1;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AghMapFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AghMapFragment extends Fragment implements FabListener {
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
                        .newCameraPosition(new CameraPosition(myLocation, 17, 60, new Random().nextInt(360))));
                googleMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
                    @Override
                    public void onMapLongClick(LatLng latLng) {
                        Log.d(TAG, "onMapLongClick: " + latLng);
                    }
                });
            }
        });
    }

    @Override
    public View.OnClickListener getFabListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (googleMap == null) return;
                LatLng l = googleMap.getCameraPosition().target;
                Log.i(TAG, "onClick: " + l);
                buildStreetViewDialog(l);
            }
        };
    }

    private void buildStreetViewDialog(LatLng l) {
        Log.d(TAG, "buildStreetViewDialog: ");
        new StreetView().dialog(l, this.getActivity());
    }

    @Override
    public void setupFab(FloatingActionButton fab) {
        Defaults.setupFAB(getActivity(), fab, R.drawable.agh_logo);
    }

}
