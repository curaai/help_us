package stack.birds.helpus.Tab;

import android.Manifest;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import stack.birds.helpus.Adapter.RecordAdapter;
import stack.birds.helpus.Class.Record;
import stack.birds.helpus.R;

/**
 * Created by dsm2016 on 2017-07-31.
 */

public class ReportFragment extends Fragment implements View.OnClickListener{
    private View view;
    private Button button;
    private RecyclerView recyclerView;
    private RecordAdapter recAdpater;

    private List<Record> recList;
    private String path = "/mnt/shared/Other";
//    private String path = "/storage/emulated/0/Music";

    private MediaPlayer mPlayer;
    private SeekBar seekBar;
    public static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_report, container, false);

        button = (Button) view.findViewById(R.id.mp3_select);
        recyclerView = (RecyclerView) view.findViewById(R.id.record_list);

        initRecyclerView();

        button = (Button) view.findViewById(R.id.mp3_select);
        button.setOnClickListener(this);

        mPlayer = new MediaPlayer();
        seekBar = (SeekBar) view.findViewById(R.id.seekBar1);
        seekBar.setMax(mPlayer.getDuration());
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress,
                                          boolean fromUser) {
                if(fromUser)
                    mPlayer.seekTo(progress);
            }
        });

        return view;
    }

    // 버튼 클릭시 음악재생
    @Override
    public void onClick(View v) {
        // 만약 파일 읽는 권한이 없으면 권한을 얻고 재생
        int permission = ActivityCompat.checkSelfPermission(
                getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (permission != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                    getActivity(),
                    PERMISSIONS_STORAGE,
                    1
            );
        }

        if (mPlayer.isPlaying()) {
            // 재생중이면 실행될 작업 (정지)
            mPlayer.stop();
            try {
                mPlayer.prepare();
            } catch (IllegalStateException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            mPlayer.seekTo(0);

            button.setText("START");
            seekBar.setProgress(0);
        } else {
            // 재생중이 아니면 실행될 작업 (재생)
            mPlayer.start();
            button.setText("STOP");

            Thread();
        }
    }

    // 음악재생시 seekBar 가 채워짐
    public void Thread(){
        Runnable task = new Runnable(){
            public void run(){
                // while문을 돌려서 음악이 실행중일때 게속 돌아가게 합니다
                while(mPlayer.isPlaying()){
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    // music.getCurrentPosition()은 현재 음악 재생 위치를 가져오는 구문 입니다
                    seekBar.setProgress(mPlayer.getCurrentPosition());
                }
            }
        };
        Thread thread = new Thread(task);
        thread.start();
    }

    private void initRecyclerView() {
        recList = getRecFiles(path);

        recAdpater = new RecordAdapter(recList, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = recyclerView.getChildLayoutPosition(v);
                Toast.makeText(getContext(), recList.get(position).getFileName(), Toast.LENGTH_SHORT).show();

            }
        });
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(recAdpater);

    }
    private List<Record> getRecFiles(String path) {
        List<Record> recList = new ArrayList<Record>();

        File f = new File(path);
        File[] files = f.listFiles();
        for (File inFile : files) {

            Record rec = new Record(inFile.getName(), new Date(inFile.lastModified()));
            Log.d("current file name", inFile.getName());
            recList.add(rec);
        }
        return recList;
    }
}
