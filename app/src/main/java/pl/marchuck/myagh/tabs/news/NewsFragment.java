package pl.marchuck.myagh.tabs.news;


import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import pl.marchuck.myagh.MainActivity;
import pl.marchuck.myagh.R;
import pl.marchuck.myagh.ifaces.FabListener;

public class NewsFragment extends Fragment implements FabListener {

    public static final String TAG = NewsFragment.class.getSimpleName();
    NewsPresenter newsPresenter;

    public NewsFragment() {
   }

    public static NewsFragment newInstance() {
        NewsFragment fragment = new NewsFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_news, container, false);
        newsPresenter = new NewsPresenter(getActivity(), view);
        return view;
    }

    @Override
    public View.OnClickListener getFabListener() {
        return null;
    }


    @Override
    public void setupFab(FloatingActionButton fab) {
        asMain().hideFab();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setupFab(asMain().fab);

    }

    @Override
    public void onStop() {
        asMain().showFab();
        super.onStop();
    }

    MainActivity asMain() {
        return (MainActivity) getActivity();
    }
}
