package pl.marchuck.myagh.tabs.virtual_dean;


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
import pl.marchuck.myagh.utils.Defaults;
import pl.marchuck.myagh.utils.EmailSender;

public class VirtualDeanFragment extends Fragment implements FabListener {

    public static final String TAG = VirtualDeanFragment.class.getSimpleName();

    private VirtualDeanPresenter presenter;

    public VirtualDeanFragment() {
        // Required empty public constructor
    }

    public static VirtualDeanFragment newInstance() {
        VirtualDeanFragment fragment = new VirtualDeanFragment();
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
        View view = inflater.inflate(R.layout.fragment_virtual_dean, container, false);
        presenter = new VirtualDeanPresenter(view,getActivity());
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
