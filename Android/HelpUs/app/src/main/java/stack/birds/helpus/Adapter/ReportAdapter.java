package stack.birds.helpus.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import stack.birds.helpus.Class.Report;
import stack.birds.helpus.R;

/**
 * Created by dsm2016 on 2017-08-02.
 */

public class ReportAdapter extends RecyclerView.Adapter<ReportAdapter.ReportViewHolder>{
    private List<Report> reportList;
    Report report;

    public class ReportViewHolder extends RecyclerView.ViewHolder {
        public TextView title, date, receivers;

        public ReportViewHolder(View view) {
            super(view);

            title = (TextView) view.findViewById(R.id.report_row_title);
            date = (TextView) view.findViewById(R.id.report_row_date);
            receivers = (TextView) view.findViewById(R.id.report_row_receivers);
        }
    }

    public ReportAdapter(List<Report> reportList) {
        this.reportList = reportList;
    }

    @Override
    public ReportViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.report_list_row, parent, false);
        return new ReportViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ReportAdapter.ReportViewHolder holder, int position) {
        report = reportList.get(position);
        holder.title.setText(report.getTitle());
        holder.date.setText(report.getReportDate());
        holder.receivers.setText(report.getReceivers());
    }

    @Override
    public int getItemCount() {
        return reportList.size();
    }
}
