package pl.marchuck.myagh.utils;

import android.os.Handler;
import android.view.View;

/**
 * @author Lukasz Marczak
 * @since 07.05.16.
 */
public class Animations {
    public static void showView(View view) {
        view.setVisibility(View.VISIBLE);
        view.setAlpha(0);
        view.animate().alpha(1f).setDuration(Defaults.AnimationDuration).start();
    }

    public static void hideView(final View view) {
        view.animate().alpha(0f).setDuration(Defaults.AnimationDuration).start();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (view != null) view.setVisibility(View.GONE);
            }
        }, Defaults.AnimationDuration);
    }
}
