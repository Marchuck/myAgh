package pl.marchuck.myagh.tabs.news;

import android.app.Activity;
import android.support.annotation.Nullable;
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
import pl.marchuck.myagh.utils.DefaultError;
import pl.marchuck.myagh.utils.JsoupProxy;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * @author Lukasz Marczak
 * @since 08.05.16.
 */
public class NewsPresenter {
    public static final String TAG = NewsPresenter.class.getSimpleName();
    @Bind(R.id.recycler_view)
    RecyclerView recyclerView;
    @Bind(R.id.progress)
    ProgressBar progressBar;

    Activity activity;

    Activity getActivity() {
        return activity;
    }

    public NewsPresenter(Activity a, View v) {
        ButterKnife.bind(this, v);
        this.activity = a;
        logic();
    }

    private void logic() {
        progressBar.setVisibility(View.VISIBLE);
        JsoupProxy.getJsoupDocument("https://www.eaiib.agh.edu.pl/aktualnosci.html")
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Document>() {
                    @Override
                    public void call(@Nullable Document document) {
                        if (document == null) {
                            DefaultError.create(TAG, activity).call(new Throwable("Nullable document"));
                            return;
                        }
                        Log.i(TAG, "received document " + document.title());
                        Elements articles = document.getElementsByClass("article-next-link");
                        setupList(articles);
                        progressBar.setVisibility(View.GONE);

                    }
                }, DefaultError.create(TAG, getActivity()));

    }


    void setupList(Elements articles) {
        List<Article> dataset = new ArrayList<>();
        for (Element el : articles) {
            JsoupProxy.printElement(TAG, el);
            dataset.add(new Article(el));
        }
        recyclerView.setLayoutManager(new LinearLayoutManager(activity));

        recyclerView.setAdapter(new NewsAdapter(activity, dataset));
    }

}
