package pl.marchuck.myagh.tabs.helpdesk;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import pl.marchuck.myagh.R;
import pl.marchuck.myagh.utils.Animations;
import pl.marchuck.myagh.utils.DefaultError;
import pl.marchuck.myagh.utils.JsoupProxy;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * @author Lukasz Marczak
 * @since 09.05.16.
 */
public class HelpdeskPresenter {
    public static final String TAG = HelpdeskPresenter.class.getSimpleName();
    @Bind(R.id.recycler_view)
    RecyclerView recyclerView;

    @Bind(R.id.progress)
    ProgressBar progressBar;
    HelpdeskFragment ctx;

    public HelpdeskPresenter(HelpdeskFragment helpdeskFragment, View view) {
        ButterKnife.bind(this, view);
        this.ctx = helpdeskFragment;
        logic();
    }

    private void logic() {
        Log.d(TAG, "logic: ");
        Animations.showView(progressBar);
        JsoupProxy.getJsoupDocument("http://help.eaiib.agh.edu.pl").subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Document>() {
                    @Override
                    public void call(Document document) {
                        Animations.hideView(progressBar);
                        if (document == null) return;
                        Elements categories = document.getElementsByClass("category");
                        Log.d(TAG, "print all categories ");
                        JsoupProxy.printElements(TAG, categories);

                        new HelpdeskAdapterWrapper(ctx.getActivity()).build(document, recyclerView);
                    }
                }, DefaultError.create(TAG, ctx.getActivity()));

    }
}
