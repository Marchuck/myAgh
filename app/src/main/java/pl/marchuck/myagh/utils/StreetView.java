package pl.marchuck.myagh.utils;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.util.Size;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.Toast;

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

    public static abstract class LoadableImage implements Target {
        ImageView imageView;
        View progress;
        Activity a;
        Dialog dialog;
        boolean notLoaded = true;

        public LoadableImage(Activity ctx, Dialog di, ImageView im, View v) {
            dialog = di;
            a = ctx;
            imageView = im;
            progress = v;
        }

        @Override
        public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
            Log.i(TAG, "onBitmapLoaded: ");
            notLoaded = false;
            if (imageView != null && progress != null) {
                imageView.setImageBitmap(bitmap);
                progress.setVisibility(View.GONE);
            }
        }

        @Override
        public void onPrepareLoad(Drawable placeHolderDrawable) {
            Log.i(TAG, "onPrepareLoad: ");
            progress.setVisibility(View.VISIBLE);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (notLoaded && dialog != null && a != null) {
                        Toast.makeText(a, "Cannot fetch image", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }
                }
            }, 7000);
        }
    }

    public static void dialog(final LatLng latLng, final FragmentActivity fragmentActivity) {
        Log.d(TAG, "dialog: ");
        final Dialog dialog = new Dialog(fragmentActivity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.fragment_agh_map_street_view_dialog);
        final ImageView imageView = (ImageView) dialog.findViewById(R.id.image);
        final View progressBar = dialog.findViewById(R.id.progress);
        String key = fragmentActivity.getResources().getString(R.string.google_api_key);
        final String preparedUrl = streetViewUrl(latLng, key);
        Picasso.with(fragmentActivity).load(preparedUrl).into(new LoadableImage(fragmentActivity, dialog,
                imageView, progressBar) {
            @Override
            public void onBitmapFailed(Drawable errorDrawable) {
                fragmentActivity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (progressBar != null) Animations.hideView(progressBar);
                    }
                });
            }
        });
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }


    public static String streetViewUrl(LatLng latLng, String apiKey) {
        return "https://maps.googleapis.com/maps/api/streetview?" +
                "size=300x300&location=" + latLng.latitude + "," + latLng.longitude
                + "&heading=150&pitch=-0.76&key=" + apiKey;
    }

    public static String streetViewUrl(LatLng latLng, String apiKey, com.google.android.gms.common.images.Size size) {
        return "https://maps.googleapis.com/maps/api/streetview?" +
                "size=" + size.getWidth() + "x" + size.getHeight() + "&location="
                + latLng.latitude + "," + latLng.longitude
                + "&heading=150&pitch=-0.76&key=" + apiKey;
    }
}
