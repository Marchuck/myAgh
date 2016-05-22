package pl.marchuck.myagh.utils;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
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

    public static class DefaultErrorBuilder {
        View v;

        public DefaultErrorBuilder(View c) {
            v = c;
        }

        public Action1<Throwable> create(final String sourceTAG, final Activity ctx) {
            return DefaultError.create(v, sourceTAG, ctx);
        }

        public Action1<Throwable> create(final String sourceTAG, final Fragment ctx) {
            return DefaultError.create(v, sourceTAG, ctx.getActivity());
        }
    }

    public static DefaultErrorBuilder withHideView(@NonNull View view) {
        return new DefaultErrorBuilder(view);
    }

    /**
     * @param sourceTAG
     * @param ctx
     * @return
     */
    public static Action1<Throwable> create(final String sourceTAG, final Activity ctx) {
        return create(null, sourceTAG, ctx);
    }

    private static Action1<Throwable> create(final View view, final String sourceTAG, final Activity ctx) {
        return new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                final String message = printThrowable(sourceTAG, throwable);
                if (ctx != null) ctx.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Animations.hideView(view);
                        Toast.makeText(ctx, message, Toast.LENGTH_LONG).show();
                    }
                });
            }
        };
    }

    private static String printThrowable(String TAG, Throwable throwable) {
        final String message = throwable.getMessage();
        Log.e(TAG, "error: " + message);
        throwable.printStackTrace();
        return message;
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
