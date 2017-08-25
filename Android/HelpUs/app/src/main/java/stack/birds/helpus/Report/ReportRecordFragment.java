package stack.birds.helpus.Report;

import android.media.MediaMetadataRetriever;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import java.util.List;

import stack.birds.helpus.Class.Record;
import stack.birds.helpus.R;

/**
 * Created by dsm2016 on 2017-08-25.
 */

public class ReportRecordFragment extends Fragment {
    View view;
    String path;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_report, container, false);

        return view;
    }
}

class RecordAdapter extends RecyclerView.Adapter<RecordAdapter.RecViewHolder> {
    private List<Record> recList;
    Record rec;

    private final View.OnClickListener listener;

    class RecViewHolder extends RecyclerView.ViewHolder {
        public ImageView icon;
        public CheckBox checkBox;
        public SeekBar seekbar;
        public TextView fileName, fileDate, fileLength;

        RecViewHolder(View view) {
            super(view);
            icon = (ImageView) view.findViewById(R.id.record_play_icon);
            checkBox = (CheckBox) view.findViewById(R.id.record_check);
            seekbar = (SeekBar) view.findViewById(R.id.record_seek_bar);
            fileName = (TextView) view.findViewById(R.id.record_file_name);
            fileLength = (TextView) view.findViewById(R.id.record_file_length);
            fileDate = (TextView) view.findViewById(R.id.record_file_date);
        }
    }

    public RecordAdapter(List<Record> recList, View.OnClickListener listener) {
        this.listener = listener;
        this.recList = recList;
    }

    @Override
    public RecViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.record_list_row, parent, false);
        itemView.setOnClickListener(listener);
        return new RecViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(RecViewHolder holder, int position) {
        rec = recList.get(position);

        // get mp3 file's length
        MediaMetadataRetriever metaRetriever = new MediaMetadataRetriever();
        metaRetriever.setDataSource(rec.getFilePath());
        int duration = Integer.parseInt(metaRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION));
        int minute = duration / 60000;
        int second = (duration % 60000) / 1000;

        holder.fileName.setText(rec.getFileName());
        holder.fileDate.setText(rec.getmodifyDate());
        holder.fileLength.setText(minute + ":" + second);
        holder.seekbar.setMax(duration);
    }

    @Override
    public int getItemCount() {
        return recList.size();
    }
}