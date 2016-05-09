package pl.marchuck.myagh.tabs.helpdesk;


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

public class HelpdeskFragment extends Fragment implements FabListener {

    public static final String TAG = HelpdeskFragment.class.getSimpleName();

HelpdeskPresenter helpdeskPresenter;
    public HelpdeskFragment() {
        // Required empty public constructor
    }

    public static HelpdeskFragment newInstance() {
        HelpdeskFragment fragment = new HelpdeskFragment();
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
        View view = inflater.inflate(R.layout.fragment_helpdesk, container, false);
        helpdeskPresenter = new HelpdeskPresenter(this,view);
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

    }

    @Override
    public void onStop() { asMain().showFab();
        super.onStop();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
         asMain().hideFab();

    }

    MainActivity asMain() {
        return (MainActivity) getActivity();
    }
}
