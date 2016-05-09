package pl.marchuck.myagh.utils;

import android.os.Handler;
import android.view.View;

/**
 * @author Lukasz Marczak
 * @since 07.05.16.
 */
public class Animations {

    static final int HALF_A_SECOND = 500;

    public static void showView(View view) {
        view.setVisibility(View.VISIBLE);
        view.setAlpha(0);
        view.animate().alpha(1f).setDuration(HALF_A_SECOND).start();
    }

    public static void hideView(final View view) {
        view.animate().alpha(0f).setDuration(HALF_A_SECOND).start();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (view != null) view.setVisibility(View.GONE);
            }
        }, HALF_A_SECOND);
    }
}
