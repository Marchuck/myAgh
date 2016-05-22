package pl.marchuck.myagh.tabs.about_faculty;


import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.leakcanary.RefWatcher;

import butterknife.Bind;
import butterknife.ButterKnife;
import pl.marchuck.myagh.MainActivity;
import pl.marchuck.myagh.MyApp;
import pl.marchuck.myagh.R;
import pl.marchuck.myagh.ifaces.FabListener;
import pl.marchuck.myagh.utils.Animations;

public class FacultyFragment extends Fragment implements FabListener {

    public static final String TAG = FacultyFragment.class.getSimpleName();
    @Bind(R.id.recycler_view)
    RecyclerView recyclerView;

    public FacultyFragment() {
        // Required empty public constructor
    }

    public static FacultyFragment newInstance() {
        FacultyFragment fragment = new FacultyFragment();
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
        View view = inflater.inflate(R.layout.fragment_about_faculty, container, false);
        ButterKnife.bind(this, view);
        setupRecyclerView();
        return view;
    }

    private void setupRecyclerView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(new FacultyAdapter());
    }

    @Override
    public View.OnClickListener getFabListener() {
        return null;
    }

    @Override
    public void setupFab(FloatingActionButton fab) {
        Animations.hideView(fab);
        //  Defaults.setupFAB(getActivity(), fab, R.drawable.email);
    }

    @Override
    public void onStop() {
        Animations.showView(asMain().fab);
        super.onStop();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        RefWatcher refWatcher = MyApp.getRefWatcher(getActivity());
        refWatcher.watch(this);
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
