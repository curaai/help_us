package stack.birds.helpus.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import stack.birds.helpus.Class.Record;
import stack.birds.helpus.R;

/**
 * Modified by dsm2016 on 2017-08-01.
 */

public class RecordAdapter extends RecyclerView.Adapter<RecordAdapter.RecViewHolder> {
    private List<Record> recList;
    Record rec;

    public interface OnItemClickListener {
        void onItemClick(Record item);
    }

    private final OnItemClickListener listener;

    public class RecViewHolder extends RecyclerView.ViewHolder {
        public TextView fileName, fileModified;

        public RecViewHolder(View view) {
            super(view);
            fileName = (TextView) view.findViewById(R.id.record_file_name);
            fileModified = (TextView) view.findViewById(R.id.record_modified_date);
        }
    }

    public RecordAdapter(List<Record> recList, OnItemClickListener listener) {
        this.listener = listener;
        this.recList = recList;
    }

    @Override
    public RecViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.record_list_row, parent, false);
        return new RecViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(RecViewHolder holder, int position) {
        rec = recList.get(position);
        holder.fileName.setText(rec.getFileName());
        holder.fileModified.setText(rec.getmodifyDate());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemClick(rec);
            }
        });
    }

    @Override
    public int getItemCount() {
        return recList.size();
    }
}
