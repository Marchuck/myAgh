package pl.marchuck.myagh.tabs.skos;


import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.leakcanary.RefWatcher;

import pl.marchuck.myagh.MainActivity;
import pl.marchuck.myagh.MyApp;
import pl.marchuck.myagh.R;
import pl.marchuck.myagh.ifaces.FabListener;
import pl.marchuck.myagh.tabs.virtual_dean.VirtualDeanPresenter;

public class SkosFragment extends Fragment implements FabListener {

    public static final String TAG = SkosFragment.class.getSimpleName();

    private SkosPresenter presenter;

    public SkosFragment() {
        // Required empty public constructor
    }

    public static SkosFragment newInstance() {
        SkosFragment fragment = new SkosFragment();
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
        View view = inflater.inflate(R.layout.fragment_skos, container, false);
        presenter = new SkosPresenter(view,getActivity());
        return view;
    }

    @Override
    public View.OnClickListener getFabListener() {
        return null;
    }

    @Override
    public void onStop() {
        asMain().showFab();
        super.onStop();
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
    @Override public void onDestroy() {
        super.onDestroy();
        RefWatcher refWatcher = MyApp.getRefWatcher(getActivity());
        refWatcher.watch(this);
    }
    MainActivity asMain() {
        return (MainActivity) getActivity();
    }
}
