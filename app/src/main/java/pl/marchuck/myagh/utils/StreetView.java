package pl.marchuck.myagh.utils;

import android.app.Dialog;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.google.android.gms.maps.model.LatLng;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;


import pl.marchuck.myagh.R;

/**
 * @author Lukasz Marczak
 * @since 08.05.16.
 */
public class StreetView {
    public static final String TAG = StreetView.class.getSimpleName();

    static class LoadableImage implements Target {
        ImageView imageView;
        View progress;

        public LoadableImage(ImageView im, View v) {
            imageView = im;
            progress = v;
        }

        @Override
        public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
            Log.i(TAG, "onBitmapLoaded: ");
            imageView.setImageBitmap(bitmap);
            progress.setVisibility(View.GONE);
        }

        @Override
        public void onBitmapFailed(Drawable errorDrawable) {
            Log.i(TAG, "onBitmapFailed: ");
            progress.setVisibility(View.GONE);
        }

        @Override
        public void onPrepareLoad(Drawable placeHolderDrawable) {
            Log.i(TAG, "onPrepareLoad: ");
            progress.setVisibility(View.VISIBLE);
        }
    }

    private int currentFOV = 90;

    public void dialog(final LatLng latLng, final FragmentActivity fragmentActivity) {
        Log.d(TAG, "dialog: ");
        Dialog dialog = new Dialog(fragmentActivity);
        dialog.setContentView(R.layout.fragment_agh_map_street_view_dialog);
        final ImageView imageView = (ImageView) dialog.findViewById(R.id.image);
        final View progressBar = dialog.findViewById(R.id.progress);
        FloatingActionButton fabLeft = (FloatingActionButton) dialog.findViewById(R.id.left);
        FloatingActionButton fabRight = (FloatingActionButton) dialog.findViewById(R.id.right);
        fabLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentFOV == 0) currentFOV = 270;
                else currentFOV -= 90;
                loadImage(latLng, fragmentActivity, imageView, progressBar);
            }
        });
        fabRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentFOV == 270) currentFOV = 0;
                else currentFOV += 90;
                loadImage(latLng, fragmentActivity, imageView, progressBar);
            }
        });
        loadImage(latLng, fragmentActivity, imageView, progressBar);
        dialog.show();
    }

    private void loadImage(LatLng latLng, FragmentActivity fragmentActivity, ImageView imageView, View progressBar) {
        String key = fragmentActivity.getResources().getString(R.string.google_api_key);
        String preparedUrl = streetViewUrl(latLng, key, currentFOV);
        Picasso.with(fragmentActivity).load(preparedUrl).into(new LoadableImage(imageView, progressBar));
    }

    private static String streetViewUrl(LatLng latLng, String apiKey, int fov) {
//        return "https://maps.googleapis.com/maps/api/streetview?" +
//                "size=300x300&location=" + latLng.latitude + "," + latLng.longitude
//                + "&fov=" + fov + "&heading=150%pitch=-0.76&key=" + apiKey;

//        return "https://maps.googleapis.com/maps/api/streetview?size=300x300" +
//                "&location="+latLng.latitude+","+latLng.longitude
//                +"&fov=90&heading=235&pitch=10&key=AIzaSyCvvTK6bhvzvttsW9dhKHEN8tAeCLSJoCk";

        return "https://maps.googleapis.com/maps/api/streetview?size=400x400&location=40.720032,-73.988354&fov=90&heading=235&pitch=10&key=AIzaSyCvvTK6bhvzvttsW9dhKHEN8tAeCLSJoCk";
    }
}
