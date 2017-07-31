package stack.birds.helpus.Tab;

import android.Manifest;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SeekBar;

import java.io.IOException;

import stack.birds.helpus.R;

/**
 * Created by dsm2016 on 2017-07-31.
 */

public class ReportFragment extends Fragment implements View.OnClickListener {
    Button button;
    SeekBar seekBar;
    MediaPlayer mPlayer;
    View view;
    String path = "/storage/emulated/0/music.mp3"; // 내가 설정한 경로
    public static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_report, container, false);

        if(mPlayer == null) {
            Log.d("music", "this is null");
        }
        mPlayer = new MediaPlayer();
        try {
            mPlayer.setDataSource(path);
            mPlayer.prepare();
        } catch (Exception e){
            Log.d("ERROR", e.toString());
        }

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

        button = (Button) view.findViewById(R.id.mp3_select);
        button.setOnClickListener(this);

        return view;
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


    // 버튼 클릭시 음악재생
    @Override
    public void onClick(View v) {
        // 만약 파일 읽는 권한이 없으면 권한을 얻고 재생
        int permission = ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (permission != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                    getActivity(),
                    PERMISSIONS_STORAGE,
                    1
            );
        }

        if(mPlayer.isPlaying()){
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
        }else{
            // 재생중이 아니면 실행될 작업 (재생)
            mPlayer.start();
            button.setText("STOP");

            Thread();
        }
    }
}
