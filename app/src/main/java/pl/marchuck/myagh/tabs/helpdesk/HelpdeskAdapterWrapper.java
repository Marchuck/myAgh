package pl.marchuck.myagh.tabs.helpdesk;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.h6ah4i.android.widget.advrecyclerview.utils.AbstractExpandableItemViewHolder;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

import pl.lukaszmarczak.expandabledelegates.expandable_delegates.DelegableChildAdapter;
import pl.lukaszmarczak.expandabledelegates.expandable_delegates.DelegableParentAdapter;
import pl.lukaszmarczak.expandabledelegates.expandable_delegates.DelegatesAdapter;
import pl.lukaszmarczak.expandabledelegates.expandable_delegates.DelegatesManager;
import pl.lukaszmarczak.expandabledelegates.expandable_delegates.XChild;
import pl.lukaszmarczak.expandabledelegates.expandable_delegates.XParent;
import pl.lukaszmarczak.expandabledelegates.utils.ExpandableBuilder;
import pl.marchuck.myagh.R;
import pl.marchuck.myagh.utils.JsoupProxy;

/**
 * @author Lukasz Marczak
 * @since 09.05.16.
 */
public class HelpdeskAdapterWrapper {

    private ExpandableBuilder expandableBuilder;
    private Activity ctx;
    private RecyclerView.Adapter delegatesAdapter;


    public HelpdeskAdapterWrapper(Fragment ctx, Document doc) {
        this.ctx = ctx.getActivity();
        delegatesAdapter = createAdapter(doc);
    }

    public HelpdeskAdapterWrapper(Activity ctx, Document doc) {
        this.ctx = ctx;
        delegatesAdapter = createAdapter(doc);
    }

    public void build(RecyclerView recyclerView) {

        expandableBuilder = new ExpandableBuilder(ctx).withAdapter(delegatesAdapter).withRecyclerView(recyclerView);
        expandableBuilder.build();
    }

    private RecyclerView.Adapter createAdapter(Document document) {

        DelegatesManager<String, QA> manager = new DelegatesManager<>();
        manager.addDelegateChild(new ChildAdapter(ctx));
        manager.addDelegateParent(new ParentAdapter());

        List<XParent<String, QA>> dataSet = new ArrayList<>();
        Elements categories = document.getElementsByClass("category");

        for (Element element : categories) {
            Document docc = Jsoup.parse(element.html());
            Elements spans = docc.select("span");
            Elements que = docc.getElementsByClass("pytanie");
            Elements ans = docc.getElementsByClass("odpowiedz");
            Log.i("", "createAdapter: ______________________________");
            JsoupProxy.printElements("X", que);
            Log.i("", "createAdapter: ______________________________");
            JsoupProxy.printElements("X", ans);
            Log.i("", "createAdapter: ______________________________");
            List<XChild<QA>> children = new ArrayList<>();
            for (int j = 0; j < que.size(); j++) {
                String qq = que.get(j).text();
                String aa = ans.get(j).text();
                children.add(new XChild<>(new QA(qq, aa), 0));
            }
            dataSet.add(new XParent<>(spans.get(0).text(), children, 1));
        }
        DelegatesAdapter<String, QA> delegatesAdapter = new DelegatesAdapter<>(dataSet, manager);
        return delegatesAdapter;
    }


    public static class ParentAdapter extends DelegableParentAdapter<String, QA> {
        public static class ParentHolder extends AbstractExpandableItemViewHolder {
            TextView categoryName;

            public ParentHolder(View v) {
                super(v);
                categoryName = (TextView) v.findViewById(R.id.text);
            }
        }

        @Override
        public int getParentViewType() {
            return 1;
        }

        @Override
        public void onBindGroupViewHolder(AbstractExpandableItemViewHolder holder, int groupPosition, int childPosition) {
            final XParent<String, QA> parent = getDataSet().get(groupPosition);
            ParentHolder vh = (ParentHolder) holder;
            vh.categoryName.setText(parent.getParent());
        }

        @Override
        public AbstractExpandableItemViewHolder onCreateGroupViewHolder(ViewGroup viewGroup, int i) {
            View v = DelegatesManager.inflateMe(viewGroup, R.layout.about_faculty_item);
            return new ParentHolder(v);
        }
    }

    public static class ChildAdapter extends DelegableChildAdapter<String, QA> {

        Activity ctx;

        public ChildAdapter(Activity ctx) {
            this.ctx = ctx;
        }

        public static class ChildViewHolder extends RecyclerView.ViewHolder {
            TextView question;

            public ChildViewHolder(View v) {
                super(v);
                question = (TextView) v.findViewById(R.id.text);
            }
        }

        @Override
        public int getChildViewType() {
            return 0;
        }

        @Override
        public void onBindChildViewHolder(final RecyclerView.ViewHolder holder, int groupPosition, final int childPosition, int viewType) {
            final XChild<QA> childWrapper = getDataSet().get(groupPosition).getChildren().get(childPosition);
            final ChildViewHolder vh = (ChildViewHolder) holder;
            vh.question.setText(Html.fromHtml(childWrapper.getChild().question));
            vh.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog dialog = new AlertDialog.Builder(ctx)
                            .setMessage(Html.fromHtml(childWrapper.getChild().answer))
                            .setCancelable(true)
                            .setOnKeyListener(keyListener)
                            .setPositiveButton(android.R.string.ok,null)
                            .show();
                }
            });
        }

        public DialogInterface.OnKeyListener keyListener = new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                dialog.dismiss();
                return false;
            }
        };

        @Override
        public RecyclerView.ViewHolder onCreateChildViewHolder(ViewGroup viewGroup, int i) {
            View v = DelegatesManager.inflateMe(viewGroup, R.layout.helpdesk_item_child);
            return new ChildViewHolder(v);
        }
    }
}
