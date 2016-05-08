package pl.marchuck.myagh.tabs;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import pl.marchuck.myagh.MainActivity;
import pl.marchuck.myagh.R;
import pl.marchuck.myagh.ifaces.FabListener;
import pl.marchuck.myagh.utils.DefaultError;
import pl.marchuck.myagh.utils.Defaults;
import pl.marchuck.myagh.utils.EmailSender;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

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
