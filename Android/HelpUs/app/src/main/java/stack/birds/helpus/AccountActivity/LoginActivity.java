package stack.birds.helpus.AccountActivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.HashMap;

import stack.birds.helpus.Account.Account;
import stack.birds.helpus.MainActivity;
import stack.birds.helpus.R;

public class LoginActivity extends AppCompatActivity {

    EditText id, pw;
    Button signIn;

    int LOGIN_FLAG = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        id = (EditText) findViewById(R.id.sign_in_id);
        pw = (EditText) findViewById(R.id.sign_in_pw);

        signIn = (Button) findViewById(R.id.sign_in_btn);
        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HashMap<String, String> login_param = new HashMap<String, String>();
                login_param.put("id", id.getText().toString());
                login_param.put("pw", pw.getText().toString());

                // 입력받은 값으로 로그인
                Account account = new Account(getApplicationContext());
                int result = account.requestToServer(LOGIN_FLAG, login_param);

                // 로그인 성공
                if(result == 1) {
                    // 자동 로그인
                    account.registAutoLogin(id.getText().toString(), pw.getText().toString());

                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                    finish();

                } else if (result == 0){
                    Snackbar.make(getWindow().getDecorView().getRootView()
                            , "아이디 또는 비밀번호가 잘못되었습니다.", Snackbar.LENGTH_LONG).show();
                } else if (result == -1) {
                    Snackbar.make(getWindow().getDecorView().getRootView()
                            , "로그인에 실패하였습니다. 네트워크를 확인해주세요.", Snackbar.LENGTH_LONG).show();
                }
            }
        });
    }
}
