package stack.birds.helpus.ReceiveActivity;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import stack.birds.helpus.Item.Receive;
import stack.birds.helpus.R;

/**
 * Created by dsm2016 on 2017-09-27.
 */

public class ReceiveAdapter extends RecyclerView.Adapter<ReceiveAdapter.ReceiveViewHolder>{
    private List<Receive> receiveList;

    public class ReceiveViewHolder extends RecyclerView.ViewHolder {
        public TextView title;
        public TextView date;
        public TextView reporter;

        public ReceiveViewHolder(View view) {
            super(view);

            title = (TextView) view.findViewById(R.id.receive_title);
            date = (TextView) view.findViewById(R.id.receive_date);
            reporter = (TextView) view.findViewById(R.id.receive_reporter);

        }
    }

    public ReceiveAdapter(List<Receive> receiveList) {
        this.receiveList = receiveList;
    }

    @Override
    public ReceiveViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.receive_list_row, parent, false);
        return new ReceiveViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ReceiveViewHolder holder, int position) {
        Receive receive = receiveList.get(position);
        Date date= receive.getAccidentDate();
        SimpleDateFormat trans = new SimpleDateFormat("yyyy MM dd");
        String accidentDate = trans.format(date);

        holder.title.setText(receive.getTitle());
        holder.date.setText(accidentDate);
        holder.reporter.setText(receive.getReporter());
    }

    @Override
    public int getItemCount () {
        return receiveList.size();
    }
}
