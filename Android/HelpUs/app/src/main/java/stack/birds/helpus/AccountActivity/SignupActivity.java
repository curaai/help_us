package stack.birds.helpus.AccountActivity;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;

import java.util.HashMap;

import stack.birds.helpus.R;

public class SignupActivity extends AppCompatActivity {

    EditText name, id, pw, re_pw;
    TextView regist_check;
    AQuery aq;
    String REGIST_URL = "https://dmlwlsdk07.000webhostapp.com/joinup.php";
    String str_name, str_id, str_pw, str_re_pw;
    int result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        name = (EditText) findViewById(R.id.name_enter);
        id= (EditText) findViewById(R.id.ID_enter);
        pw = (EditText) findViewById(R.id.PW_enter);
        re_pw = (EditText) findViewById(R.id.rPW_enter);

        str_name = name.getText().toString();
        str_id = id.getText().toString();
        str_pw = pw.getText().toString();
        str_re_pw = re_pw.getText().toString();

        regist_check = (TextView) findViewById(R.id.regist_check);
        regist_check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!str_pw.equals(str_re_pw)) {
                    Snackbar.make(getWindow().getDecorView().getRootView()
                            , "비밀번호를 다시 확인해 주세요", Snackbar.LENGTH_LONG).show();
                } else {
                    sendRegistInfo();
                }
            }
        });


    }

    public boolean sendRegistInfo() {

        HashMap<String, String> param = new HashMap<String, String>();
        param.put("name", str_name);
        param.put("id", str_id);
        param.put("pw", str_pw);

        aq = new AQuery(getApplicationContext());
        aq.ajax(REGIST_URL, param, String.class, new AjaxCallback<String>() {
            @Override
            public void callback(String url, String object, AjaxStatus status) {
                result = status.getHeader("result");
            }
        });
        return true;
    }
}
