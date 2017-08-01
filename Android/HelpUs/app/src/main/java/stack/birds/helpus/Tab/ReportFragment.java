package stack.birds.helpus.Tab;

import android.Manifest;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
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
import android.widget.TextView;

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
    private TextView currentTime, musicDuration;
    private RecyclerView recyclerView;
    private RecordAdapter recAdpater;

    private List<Record> recList;
    private String path = "/mnt/shared/Other";
//    private String path = Environment.getExternalStorageDirectory() + "/Music";
    private String TAG = "Report";

    private Handler handler;

    private MediaPlayer mPlayer;
    private SeekBar seekBar;
    public static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_report, container, false);

        // ReportFragment 를 실행하기전에 mp3데이터에 대한 권한을 미리 획득한 후에 프래그먼트를 시작한다.
        int permission = ActivityCompat.checkSelfPermission(
                getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (permission != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                    getActivity(),
                    PERMISSIONS_STORAGE,
                    1
            );
        } else {
            button = (Button) view.findViewById(R.id.mp3_select);
            recyclerView = (RecyclerView) view.findViewById(R.id.record_list);

            initRecyclerView();

            currentTime = (TextView) view.findViewById(R.id.current_time);
            musicDuration = (TextView) view.findViewById(R.id.mp3_duration);

            button = (Button) view.findViewById(R.id.mp3_select);
            button.setOnClickListener(this);

            mPlayer = new MediaPlayer();
            seekBar = (SeekBar) view.findViewById(R.id.seekBar1);
        }

        return view;
    }

    // 버튼 클릭시 음악재생
    // TODO recycler View가 밑에서 위로 애니메이션으로 올라옴
    @Override
    public void onClick(View v) {
        // 만약 파일 읽는 권한이 없으면 권한을 얻고 재생
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

            currentTime.post(UpdateTime);
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
                // 만약 재생중에 다른 mp3가 클릭 되었으면 현재 재생을 멈추고 mPlayer를 다시 생성
                if(mPlayer.isPlaying()) {
                    mPlayer.stop();
                }

                button.setText("START");
                mPlayer = new MediaPlayer();
                // 클릭된 view(item)을 가져와 원래 경로 + '/' + 파일이름 으로 mp3데이터를 인식시킴
                int position = recyclerView.getChildLayoutPosition(v);
                try {
                    mPlayer.setDataSource(path + "/" + recList.get(position).getFileName());
                    mPlayer.prepare();

                    // 해당 mp3 데이터에 대한 seekbar 설정
                    initSeekBar();


                } catch (IOException e) {
                    Log.d(TAG, e.toString());
                }

                // TODO 올라왔던 recycler View가 다시 밑으로 애니메이션을 써서 내려감
            }
        });
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(recAdpater);

    }

    // mp3파일이 재생중일 때 현재 시간이 계속 바뀜
    private Runnable UpdateTime = new Runnable() {
        public void run() {
            int currentDuration;
            if (mPlayer.isPlaying()) {
                currentDuration = mPlayer.getCurrentPosition();
                int start_minute = currentDuration / 60000;
                int start_second = (currentDuration % 60000) / 1000;
                currentTime.setText(start_minute + ":" + start_second);

                currentTime.postDelayed(this, 1000);
            }else {
                currentTime.removeCallbacks(this);
            }
        }
    };

    // path 에 대해 listdir 을 실행하여 각 파일들의 이름과 날짜를 받아옴
    private List<Record> getRecFiles(String path) {
        List<Record> recList = new ArrayList<Record>();

        File f = new File(path);
        File[] files = f.listFiles();
        for (File inFile : files) {

            Record rec = new Record(inFile.getName(), new Date(inFile.lastModified()));
            recList.add(rec);
        }
        return recList;
    }

    private void initSeekBar() {
        seekBar.setMax(mPlayer.getDuration());
        int end_minute = mPlayer.getDuration() / 60000;
        int end_second = (mPlayer.getDuration() % 60000) / 1000;
        currentTime.setText("0:0");
        musicDuration.setText(end_minute + ":" + end_second);

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
    }
}
