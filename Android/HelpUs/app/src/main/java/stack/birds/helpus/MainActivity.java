package stack.birds.helpus;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxCallback;
import com.google.firebase.iid.FirebaseInstanceId;

import java.util.HashMap;

import stack.birds.helpus.FCM.FirebaseInstanceIDService;
import stack.birds.helpus.GPS.GPSService;

public class MainActivity extends AppCompatActivity {

    private final String LOGIN_URL = "";

    // 만약 자동로그인이 안되어있을 시에 LandingActivity 로 넘어감
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences auto = getSharedPreferences("auto_login", Activity.MODE_PRIVATE);

        String loginID = auto.getString("ID", null);
        String loginPW = auto.getString("PW", null);


    }

    // 사용자의 아이디와 비밀번호를 받아서 만약 틀리면 false 반환
    public boolean login_check(String ID, String PW) {
        if(ID != null && PW != null) {
            String token = FirebaseInstanceId.getInstance().getToken();

            HashMap<String, String> login_param = new HashMap<String, String>();
            login_param.put("user_id", ID);
            login_param.put("user_pw", PW);
            login_param.put("user_token", token);

            AQuery aq = new AQuery(getApplicationContext());
            aq.ajax(LOGIN_URL, login_param, String.class, new AjaxCallback<String>() {
                // 서버와 통신하여 만약 아이디가 맞을 경우 로그인 완료
            })

        }
    }
}