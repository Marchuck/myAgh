package pl.marchuck.myagh.tabs.about_faculty;

/**
 * @author Lukasz Marczak
 * @since 08.05.16.
 */
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import pl.marchuck.myagh.R;


/**
 * @author Lukasz Marczak
 * @since 08.05.16.
 */
public class FacultyAdapter extends RecyclerView.Adapter<FacultyAdapter.FacultyAdapterViewHolder> {

    List<String> dataSet;

    public FacultyAdapter() {
        dataSet=new ArrayList<>();
        dataSet.add("O wydziale");
        dataSet.add("Władze");
        dataSet.add("Rada Wydziału");
        dataSet.add("Katedry");
        dataSet.add("Biuro Dziekana");
        dataSet.add("Misja i Strategia Wydziału");
        dataSet.add("Dokumenty");
    }

    @Override
    public FacultyAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.about_faculty_item, null, false);
        return new FacultyAdapterViewHolder(v);
    }

    @Override
    public void onBindViewHolder(FacultyAdapterViewHolder holder, int position) {
        final String item = dataSet.get(position);
        holder.textView.setText(item);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //todo:
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }

    public static class FacultyAdapterViewHolder extends RecyclerView.ViewHolder {

        TextView textView;

        public FacultyAdapterViewHolder(View v) {
            super(v);
            textView = (TextView) v.findViewById(R.id.text);
        }
    }
}

