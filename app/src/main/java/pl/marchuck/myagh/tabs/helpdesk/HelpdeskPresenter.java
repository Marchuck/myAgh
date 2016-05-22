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
import pl.lukaszmarczak.expandabledelegates.utils.ExpandableBuilder;
import pl.marchuck.myagh.R;
import pl.marchuck.myagh.utils.Animations;
import pl.marchuck.myagh.utils.DefaultError;
import pl.marchuck.myagh.utils.JsoupProxy;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
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
        JsoupProxy.getJsoupDocument("http://help.eaiib.agh.edu.pl").map(new Func1<Document, HelpdeskAdapterWrapper>() {
            @Override
            public HelpdeskAdapterWrapper call(Document document) {
                return new HelpdeskAdapterWrapper(ctx, document);
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<HelpdeskAdapterWrapper>() {
                    @Override
                    public void call(HelpdeskAdapterWrapper helpdeskAdapterWrapper) {
                        Animations.hideView(progressBar);
                        helpdeskAdapterWrapper.build(recyclerView);
                    }
                }, DefaultError.withHideView(progressBar).create(TAG, ctx));
    }
}
