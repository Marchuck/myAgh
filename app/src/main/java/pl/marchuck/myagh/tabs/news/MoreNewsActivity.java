package pl.marchuck.myagh.tabs.news;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.squareup.leakcanary.RefWatcher;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import butterknife.Bind;
import butterknife.ButterKnife;
import pl.marchuck.myagh.MyApp;
import pl.marchuck.myagh.R;
import pl.marchuck.myagh.utils.Animations;
import pl.marchuck.myagh.utils.DefaultError;
import pl.marchuck.myagh.utils.JsoupProxy;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

public class MoreNewsActivity extends AppCompatActivity {
    public static final String TAG = MoreNewsActivity.class.getSimpleName();
    @Bind(R.id.textViewMoreNews)
    TextView textView;

    @Bind(R.id.progress)
    ProgressBar progess;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_more_news);
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        String url = getIntent().getStringExtra("URL");
        Animations.showView(progess);

        JsoupProxy.getJsoupDocument(url).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Document>() {
                    @Override
                    public void call(@Nullable Document document) {
                        if (document != null) {
                            showNews(document);
                        } else {
                            showErrorMessage(new Throwable("Document is null"));
                        }
                        Animations.hideView(progess);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        showErrorMessage(throwable);
                        Animations.hideView(progess);
                    }
                });

    }

    private void showNews(Document document) {
        Log.d(TAG, "showNews: ");
        Elements date = document.getElementsByClass("news-date");
        Elements title = document.getElementsByClass("news-title");
        Elements body = document.getElementsByClass("news-content");
        Log.d(TAG, "showNews: date");
        JsoupProxy.printElements(TAG, date);
        Log.d(TAG, "\n\nshowNews: title");
        JsoupProxy.printElements(TAG, title);
        Log.d(TAG, "\n\nshowNews: body");
        JsoupProxy.printElements(TAG, body);
        setTitle(title.get(0).text());
        textView.setText(Html.fromHtml(body.get(0).html()));
    }
    @Override public void onDestroy() {
        super.onDestroy();
        RefWatcher refWatcher = MyApp.getRefWatcher(this);
        refWatcher.watch(this);
    }
    private void showErrorMessage(Throwable throwable) {
        DefaultError.create(TAG, this).call(throwable);
    }
}
