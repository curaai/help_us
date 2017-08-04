package stack.birds.helpus.TabFragment;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.LayerDrawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import stack.birds.helpus.Adapter.RecordAdapter;
import stack.birds.helpus.Class.Record;
import stack.birds.helpus.Class.Report;
import stack.birds.helpus.R;
import stack.birds.helpus.Service.AccountService;

/**
 * Created by dsm2016 on 2017-07-31.
 */

public class ReportFragment extends Fragment implements View.OnClickListener{
    private View view;
    private ImageView mp3_select, play_img, reset_img;
    private LayerDrawable layer;
    private GradientDrawable shape;
    private TextView currentTime, musicDuration;
    private EditText title, body;
    private RecyclerView recyclerView;
    private RecordAdapter recAdpater;

    private List<Record> recList;
//    private String path = "/mnt/shared/Other";
    private String path = Environment.getExternalStorageDirectory() + "/Music";
    private String TAG = "Report";
    private String currentMediaPath = null;

    private MediaPlayer mPlayer;
    private SeekBar seekBar;
    public static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    BottomSheetBehavior bottomSheetBehavior;

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
            View bottomSheetRecycler = view.findViewById(R.id.bottom_sheet1);
            bottomSheetBehavior = BottomSheetBehavior.from(bottomSheetRecycler);

            bottomSheetBehavior.setPeekHeight(0);
            bottomSheetBehavior.setHideable(false);


            // 녹음 아이콘 클릭시 밑에서 bottom sheet로 recyclerview가 올라옴
            mp3_select = (ImageView) view.findViewById(R.id.mp3_select);


            mp3_select.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (bottomSheetBehavior.getState() != BottomSheetBehavior.STATE_EXPANDED) {
                        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                    } else {
                        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                    }
                }
            });

            play_img = (ImageView) view.findViewById(R.id.play_img);
            reset_img = (ImageView) view.findViewById(R.id.reset_img);
            play_img.setOnClickListener(this);
            reset_img.setOnClickListener(this);

            title = (EditText) view.findViewById(R.id.report_title);
            body = (EditText) view.findViewById(R.id.report_body);

            recyclerView = (RecyclerView) view.findViewById(R.id.record_list);

            initRecyclerView();

            currentTime = (TextView) view.findViewById(R.id.current_time);
            musicDuration = (TextView) view.findViewById(R.id.mp3_duration);

            mPlayer = new MediaPlayer();
            seekBar = (SeekBar) view.findViewById(R.id.seekBar1);
        }

        return view;
    }

    // 버튼 클릭시 음악재생
    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.play_img:
                // 만약 파일 읽는 권한이 없으면 권한을 얻고 재생
                if (mPlayer.isPlaying()) {
                    // 재생중이면 실행될 작업 (정지)
                    mPlayer.stop();

                    play_img.setImageResource(R.drawable.play);

                    try {
                        mPlayer.prepare();
                    } catch (IllegalStateException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    // 재생중이 아니면 실행될 작업 (재생)
                    mPlayer.start();
                    play_img.setImageResource(R.drawable.pause);

                    currentTime.post(UpdateTime);
                    Thread();
                }
                break;

            // 노래가 처음으로 되돌아가고 정지 상태가 됨
            case R.id.reset_img:
                if (mPlayer.isPlaying()) {
                    mPlayer.stop();
                }
                mPlayer.seekTo(0);
                play_img.setImageResource(R.drawable.play);

                break;

            case R.id.report_btn:
                File mp3 = new File(currentMediaPath);

                String reportTitle = title.getText().toString();
                String reportContent = body.getText().toString();
                String filePath = currentMediaPath;
                String firstPlace = "대전";
                String lastPlace = "아산";
                Date reportDate = Calendar.getInstance().getTime();
                Date accidentDate = new Date(mp3.lastModified());
                int anonymous = 0;
                String[] receivers = {"이재빈", "정필성", "서윤호", "정근철"};

                Report report = new Report(reportTitle, reportContent, filePath, receivers,
                        firstPlace, lastPlace, reportDate, accidentDate, anonymous);

                AccountService account = new AccountService(getContext());
                account.reportToServer(report, "1231254.123,43123123.123");
        }

    }

    private void initRecyclerView() {
        recList = getRecFiles(path);

        recAdpater = new RecordAdapter(recList, new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // 리스트에서 click 됬을 때 리스트가 닫히면서 노래 선택
                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                // mp3가 선택 됬으므로 색깔 수정
                mp3_select.setImageResource(R.drawable.color_record);

                // 만약 재생중에 다른 mp3가 클릭 되었을 때 재생 취소 및 아이콘 변경
                if(mPlayer.isPlaying()) {
                    mPlayer.stop();
                    play_img.setImageResource(R.drawable.play);
                }

                mPlayer = new MediaPlayer();
                // 클릭된 view(item)을 가져와 원래 경로 + '/' + 파일이름 으로 mp3데이터를 인식시킴
                int position = recyclerView.getChildLayoutPosition(v);
                try {
                    currentMediaPath = path + "/" + recList.get(position).getFileName();
                    mPlayer.setDataSource(currentMediaPath);
                    mPlayer.prepare();

                    // 해당 mp3 데이터에 대한 seekbar 설정
                    initSeekBar();


                } catch (IOException e) {
                    Log.d(TAG, e.toString());
                }

            }
        });
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(recAdpater);
    }

    //  노래가 재생시 initSeekBar가 생성되어 현재 노래 시간, 노래 길이가 textview에 설정됨
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

    // mp3파일이 재생중일 때 현재 시간이 계속 바뀜
    private Runnable UpdateTime = new Runnable() {
        public void run() {
            int currentDuration;
            if (mPlayer.isPlaying()) {
                currentDuration = mPlayer.getCurrentPosition();
                int start_minute = currentDuration / 60000;
                int start_second = (currentDuration % 60000) / 1000;
                currentTime.setText(start_minute + ":" + start_second);

                currentTime.postDelayed(this, 300);
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
}
