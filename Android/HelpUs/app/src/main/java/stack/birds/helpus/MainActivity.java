package stack.birds.helpus;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;
import com.google.firebase.iid.FirebaseInstanceId;

import java.util.HashMap;

import stack.birds.helpus.AccountActivity.LandingActivity;

public class MainActivity extends AppCompatActivity {

    private final String LOGIN_URL = "";
    boolean login_flag = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences auto = getSharedPreferences("auto_login", Activity.MODE_PRIVATE);

        String loginID = auto.getString("ID", null);
        String loginPW = auto.getString("PW", null);

        login_check(loginID, loginPW);
         // 만약 자동로그인이 안되어있을 시에 LandingActivity 로 넘어감
        if(!login_flag) {
            Intent intent = new Intent(this, LandingActivity.class);
            startActivity(intent);
            finish();
        }
    }

    // 사용자의 아이디와 비밀번호를 받아서 만약 틀리면 false 반환
    public void login_check(String ID, String PW) {
        if(ID != null && PW != null) {

            String token = FirebaseInstanceId.getInstance().getToken();

            HashMap<String, String> login_param = new HashMap<String, String>();
            login_param.put("user_id", ID);
            login_param.put("user_pw", PW);
            login_param.put("user_token", token);

            AQuery aq = new AQuery(getApplicationContext());
            aq.ajax(LOGIN_URL, login_param, String.class, new AjaxCallback<String>() {
                // 서버와 통신하여 만약 아이디가 맞을 경우 로그인 완료
                @Override
                public void callback(String url, String object, AjaxStatus status) {
                    if(status.getCode() == 200) {
                        login_flag = false;
                    } else {
                        login_flag = true;
                    }
                }
            });
        }
    }
}