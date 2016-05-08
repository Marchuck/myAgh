package pl.marchuck.myagh.utils;

import android.content.res.ColorStateList;
import android.support.annotation.DrawableRes;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentActivity;

import static android.util.Log.d;


import pl.marchuck.myagh.MyApp;
import pl.marchuck.myagh.R;

/**
 * @author Lukasz Marczak
 * @since 07.05.16.
 */
public class Defaults {
    public static final String TAG = Defaults.class.getSimpleName();
    public static final int AnimationDuration = 300;
    public static final int LazyAnimationDuration = 1000;

    public static void setupFAB(FragmentActivity activity, FloatingActionButton fab, @DrawableRes int imageResource) {
        d(TAG, "inside setupFAB ");
        int color = MyApp.instance.getResources().getColor(R.color.colorAccent);
        int whenPressedColor = MyApp.instance.getResources().getColor(R.color.colorAccentDarker);
        fab.setImageResource(imageResource);
        fab.setBackgroundTintList(ColorStateList.valueOf(color));
        fab.setRippleColor(whenPressedColor);

    }
}
