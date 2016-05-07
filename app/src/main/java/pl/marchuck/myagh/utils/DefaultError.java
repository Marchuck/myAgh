package pl.marchuck.myagh.utils;

import android.app.Activity;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import rx.functions.Action1;

/**
 * @author Lukasz Marczak
 * @since 07.05.16.
 */
public class DefaultError {
    /**
     * @param sourceTAG
     * @return
     */
    public static Action1<Throwable> create(final String sourceTAG) {
        return new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                Log.e(sourceTAG, "error: " + throwable.getMessage());
                throwable.printStackTrace();
            }
        };
    }

    /**
     * @param sourceTAG
     * @param ctx
     * @return
     */
    public static Action1<Throwable> create(final String sourceTAG, final Activity ctx) {
        return new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                final String message = throwable.getMessage();
                Log.e(sourceTAG, "error: " + message);
                throwable.printStackTrace();
                if (ctx != null) ctx.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(ctx, message, Toast.LENGTH_LONG).show();
                    }
                });

            }
        };
    }

    /**
     * @param sourceTAG
     * @param ctx
     * @param view
     * @return
     */
    public static Action1<Throwable> create(final String sourceTAG, final Activity ctx, final View view) {
        return new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                final String message = throwable.getMessage();
                Log.e(sourceTAG, "error: " + message);
                throwable.printStackTrace();
                if (ctx != null && view != null) ctx.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Snackbar.make(view, message, Snackbar.LENGTH_LONG).show();
                    }
                });
            }
        };
    }

}
