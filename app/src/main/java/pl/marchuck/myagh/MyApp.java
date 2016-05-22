package pl.marchuck.myagh;

import android.app.Application;
import android.content.Context;
import android.location.Location;
import android.util.Log;

import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;

import pl.charmas.android.reactivelocation.ReactiveLocationProvider;
import rx.functions.Action1;

/**
 * @author Lukasz Marczak
 * @since 07.05.16.
 */
public class MyApp extends Application {
    public static final String TAG = MyApp.class.getSimpleName();
    public static MyApp instance;
    public Location lastLocation;
    private RefWatcher refWatcher;
    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        refWatcher = LeakCanary.install(this);
        ReactiveLocationProvider locationProvider = new ReactiveLocationProvider(this);
        locationProvider.getLastKnownLocation()
                .subscribe(new Action1<Location>() {
                    @Override
                    public void call(Location location) {
                        lastLocation = location;
                        Log.d(TAG, "received last location: "+lastLocation);
                    }
                });
    }


    public static RefWatcher getRefWatcher(Context context) {
        MyApp application = (MyApp) context.getApplicationContext();
        return application.refWatcher;
    }
}
