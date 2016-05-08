package pl.marchuck.myagh.tabs;


import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import pl.marchuck.myagh.MainActivity;
import pl.marchuck.myagh.R;
import pl.marchuck.myagh.ifaces.FabListener;
import pl.marchuck.myagh.utils.Defaults;
import pl.marchuck.myagh.utils.EmailSender;

public class AboutFragment extends Fragment implements FabListener {

    public static final String TAG = AboutFragment.class.getSimpleName();


    public AboutFragment() {
        // Required empty public constructor
    }

    public static AboutFragment newInstance() {
        AboutFragment fragment = new AboutFragment();
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
        View view = inflater.inflate(R.layout.fragment_about, container, false);
        return view;
    }

    @Override
    public View.OnClickListener getFabListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EmailSender.fire(getActivity());
            }
        };
    }

    @Override
    public void setupFab(FloatingActionButton fab) {
        Defaults.setupFAB(getActivity(), fab, R.drawable.email);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setupFab(asMain().fab);

    }

    MainActivity asMain() {
        return (MainActivity) getActivity();
    }
}
