package stack.birds.helpus.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import stack.birds.helpus.Item.Report;
import stack.birds.helpus.R;

/**
 * Created by dsm2016 on 2017-08-02.
 */

public class ReportAdapter extends RecyclerView.Adapter<ReportAdapter.ReportViewHolder>{
    public ReportAdapter() {

    }

    public class ReportViewHolder extends RecyclerView.ViewHolder {
        public ReportViewHolder(View view) {
            super(view);
        }
    }

    @Override
    public ReportViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.report_list_row, parent, false);
        return new ReportViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ReportAdapter.ReportViewHolder holder, int position) {

    @Override
    public int getItemCount() {

    }
}
