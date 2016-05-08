package pl.marchuck.myagh.utils;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import pl.marchuck.myagh.R;
import pl.marchuck.myagh.tabs.Article;

/**
 * @author Lukasz Marczak
 * @since 08.05.16.
 */
public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.VH> {

    List<Article> dataSet;
    Activity ctx;

    public NewsAdapter(Activity ctx, List<Article> dataSet) {
        this.dataSet = dataSet;
        this.ctx = ctx;
    }

    @Override
    public VH onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_news, null, false);
        return new VH(v);
    }

    @Override
    public void onBindViewHolder(VH holder, int position) {
        final Article item = dataSet.get(position);
        holder.textView.setText(item.title);
        holder.date.setText(item.date);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(item.url));
                ctx.startActivity(browserIntent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }

    public static class VH extends RecyclerView.ViewHolder {
        TextView textView;
        TextView date;

        public VH(View v) {
            super(v);
            textView = (TextView) v.findViewById(R.id.text);
            date = (TextView) v.findViewById(R.id.date);
        }
    }
}
