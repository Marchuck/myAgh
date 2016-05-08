package pl.marchuck.myagh.tabs;


import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import butterknife.Bind;
import butterknife.ButterKnife;
import pl.marchuck.myagh.R;
import pl.marchuck.myagh.utils.DefaultError;
import pl.marchuck.myagh.utils.Defaults;

public class SplashScreenFragment extends Fragment {
    public static final String TAG = SplashScreenFragment.class.getSimpleName();
    Handler handler = new Handler();
    @Bind(R.id.agh_image)
    ImageView imageView;
    boolean canLooping = true;
    Runnable runnable;

    public SplashScreenFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static SplashScreenFragment newInstance() {
        SplashScreenFragment fragment = new SplashScreenFragment();

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
        View v = inflater.inflate(R.layout.fragment_error_occurred, container, false);
        ButterKnife.bind(this, v);
        return v;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setupLoop();
    }

    private final Runnable justLoop = new Runnable() {
        @Override
        public void run() {
            loopAction();
        }
    };

    private final Runnable loopUp = new Runnable() {
        @Override
        public void run() {
            imageView.animate().alpha(.3f) .setDuration(Defaults.LazyAnimationDuration).start();
            handler.postDelayed(justLoop, Defaults.LazyAnimationDuration);
        }
    };

    private void loopAction() {
        if (!canLooping) return;
        imageView.animate().alpha(1f) .setDuration(Defaults.LazyAnimationDuration).start();
        handler.postDelayed(loopUp, Defaults.LazyAnimationDuration);
    }

    private void setupLoop() {
        loopAction();
    }

    @Override
    public void onDestroyView() {
        stopLoop();
        super.onDestroyView();
    }

    private void stopLoop() {
        canLooping = false;
        handler.removeCallbacks(loopUp);
        handler.removeCallbacks(justLoop);
    }
}
