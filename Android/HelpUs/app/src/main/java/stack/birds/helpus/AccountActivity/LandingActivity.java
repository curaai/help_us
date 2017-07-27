package stack.birds.helpus.AccountActivity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import stack.birds.helpus.R;

public class LandingActivity extends AppCompatActivity {
    Button login, regist;
    TextView forget_pw;

    Intent intent;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing);

        login = (Button) findViewById(R.id.login_button);
        regist = (Button) findViewById(R.id.register_button_button);
        forget_pw = (TextView) findViewById(R.id.forget_pw_link);

        initListeners();
    }

    public void initListeners() {
        context = getApplicationContext();

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(context, LoginActivity.class);
                startActivity(intent);
            }
        });
        regist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(context, SignupActivity.class);
                startActivity(intent);
            }
        });
        forget_pw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(context, ForgetAccountActivity.class);
                startActivity(intent);
            }
        });
    }
}
