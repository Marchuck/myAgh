package pl.marchuck.myagh.utils;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func0;

/**
 * @author Lukasz Marczak
 * @since 07.05.16.
 */
public class Asyncs {
    public static Observable<GoogleMap> getMap(final SupportMapFragment mapFragment) {
        return Observable.create(new Observable.OnSubscribe<GoogleMap>() {
            @Override
            public void call(final Subscriber<? super GoogleMap> subscriber) {
                if (mapFragment == null) {
                    subscriber.onError(new Throwable("Nullable mapFragment!"));
                    return;
                }
                mapFragment.getMapAsync(new OnMapReadyCallback() {
                    @Override
                    public void onMapReady(GoogleMap googleMap) {
                        if (!subscriber.isUnsubscribed()) {
                            subscriber.onNext(googleMap);
                            subscriber.onCompleted();
                        }
                    }
                });
            }
        }).subscribeOn(AndroidSchedulers.mainThread()).observeOn(AndroidSchedulers.mainThread());

    }
}
