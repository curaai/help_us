package stack.birds.helpus;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import stack.birds.helpus.Account.Account;
import stack.birds.helpus.AccountActivity.LandingActivity;

public class MainActivity extends AppCompatActivity {

    private final String LOGIN_URL = "";
    boolean login_flag = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Account account = new Account(getApplicationContext());
        int result = account.autoLogin();
        // 만약 자동로그인이 안되어있을 시에 LandingActivity 로 넘어감
        if(result != 1) {
            Intent intent = new Intent(this, LandingActivity.class);
            startActivity(intent);
            finish();
        }
    }
}