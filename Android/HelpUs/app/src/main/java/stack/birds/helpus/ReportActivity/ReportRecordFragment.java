package stack.birds.helpus.ReportActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import io.realm.Realm;
import stack.birds.helpus.Item.Record;
import stack.birds.helpus.R;

/**
 * Created by dsm2016 on 2017-08-25.
 */

public class ReportRecordFragment extends Fragment {
    private View view;
    private Context context;
    private Realm mRealm;

    String path = Environment.getExternalStorageDirectory() + "/Music";
    List<Record> recordList;

    RecyclerView recyclerView;
    RecordAdapter adapter;

    @SuppressLint("ValidFragment")
    public ReportRecordFragment(Context context) {
        this.context = context;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.report_tab_record, container, false);
        mRealm = Realm.getInstance(context);

        recordList = getMp3Files();

        adapter = new RecordAdapter(new ArrayList<Record>(), context);
        recyclerView = (RecyclerView) view.findViewById(R.id.report_record_recycler);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(adapter);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        return view;
    }

    public List<Record> getMp3Files() {
        List<Record> recordList = new ArrayList<Record>();

        File f = new File(path);
        File[] files = f.listFiles();
        for(File inFile : files) {
            String fileName = inFile.getName();
            String filePath = inFile.getPath();
            Date fileDate = new Date(inFile.lastModified());

            Record record = mRealm.createObject(Record.class);
            record.setFileName(fileName);
            record.setFilePath(filePath);
            record.setFileDate(fileDate);
            recordList.add(record);
        }

        return recordList;
    }

    public List<Record> getReportData() {
        List<Record> selected = new ArrayList<Record>();
        for(int i = 0; i < adapter.getSelectedList().size(); i++) {
            selected.add(recordList.get(adapter.getSelectedList().get(i)));
        }
        return selected;
    }
}

class RecordAdapter extends RecyclerView.Adapter<RecordAdapter.RecViewHolder> implements View.OnClickListener {
    private List<Record> recList;
    private List<Integer> selectedList;
    Record rec;
    private Context context;

    class RecViewHolder extends RecyclerView.ViewHolder {
        public ImageView icon;
        public CheckBox checkBox;
        public SeekBar seekbar;
        public TextView fileName, fileDate, fileLength;
        public MediaPlayer mPlayer;

        RecViewHolder(View view) {
            super(view);
            icon = (ImageView) view.findViewById(R.id.record_play_icon);
            checkBox = (CheckBox) view.findViewById(R.id.record_check);
            seekbar = (SeekBar) view.findViewById(R.id.record_seek_bar);
            fileName = (TextView) view.findViewById(R.id.record_file_name);
            fileLength = (TextView) view.findViewById(R.id.record_file_length);
            fileDate = (TextView) view.findViewById(R.id.record_file_date);

            mPlayer = new MediaPlayer();
        }
    }

    public RecordAdapter(List<Record> recList, Context context) {
        this.recList = recList;
        this.context = context;
    }

    @Override
    public RecViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.report_tab_record_item, parent, false);
        return new RecViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final RecViewHolder holder, final int position) {
        rec = recList.get(position);

        // get mp3 file's length
        MediaMetadataRetriever metaRetriever = new MediaMetadataRetriever();
        metaRetriever.setDataSource(rec.getFilePath());
        int duration = Integer.parseInt(metaRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION));
        int minute = duration / 60000;
        int second = (duration % 60000) / 1000;

        holder.fileName.setText(rec.getFileName());
        holder.fileLength.setText(minute + ":" + second);
        holder.seekbar.setMax(duration);

        try {
            holder.mPlayer.setDataSource(rec.getFilePath());
            holder.mPlayer.prepare();
        } catch (IOException e) {}

        holder.icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (holder.mPlayer.isPlaying()) {
                    int currentDuration = holder.mPlayer.getCurrentPosition();
                    int start_minute = currentDuration / 60000;
                    int start_second = (currentDuration % 60000) / 1000;
                    holder.fileLength.setText(start_minute + ":" + start_second);

                    holder.mPlayer.stop();
                    holder.icon.setImageResource(R.drawable.pause);

                } else {
                    holder.mPlayer.start();
                    holder.icon.setImageResource(R.drawable.play);

                    new Timer().scheduleAtFixedRate(new TimerTask() {
                        @Override
                        public void run() {
                            holder.seekbar.setProgress(holder.mPlayer.getCurrentPosition());
                        }
                    }, 0, 1000);
                }
            }
        });

        // seekbar 바뀌 엇을 떄
        holder.seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                Log.i("Tranverse Change: ", Integer.toString(i));
                holder.mPlayer.seekTo(i);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        holder.checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedList.size() < 3){
                    selectedList.add(position);
                } else {
                    Toast.makeText(context, "이미지를 3개이상 선택할 수 없습니다.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return recList.size();
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.record_play_icon:

                break;
        }
    }

    public List<Integer> getSelectedList() {
        return selectedList;
    }
}

