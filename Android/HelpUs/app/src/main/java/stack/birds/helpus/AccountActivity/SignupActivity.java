package stack.birds.helpus.AccountActivity;

import android.os.Bundle;
import android.os.StrictMode;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.androidquery.AQuery;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import stack.birds.helpus.R;

public class SignupActivity extends AppCompatActivity {

    EditText name, id, pw, re_pw;
    TextView regist_check;
    AQuery aq;
    String REGIST_URL = "https://dmlwlsdk07.000webhostapp.com/joinin.php";
    String str_name, str_id, str_pw, str_re_pw;
    int result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        name = (EditText) findViewById(R.id.name_enter);
        id = (EditText) findViewById(R.id.ID_enter);
        pw = (EditText) findViewById(R.id.PW_enter);
        re_pw = (EditText) findViewById(R.id.rPW_enter);

        regist_check = (TextView) findViewById(R.id.regist_check);
        regist_check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                str_name = name.getText().toString();
                str_id = id.getText().toString();
                str_pw = pw.getText().toString();
                str_re_pw = re_pw.getText().toString();

                // 비밀번호 check가 다르면 다시 입력
                if (!str_pw.equals(str_re_pw)) {
                    Snackbar.make(getWindow().getDecorView().getRootView()
                            , "비밀번호를 다시 확인해 주세요", Snackbar.LENGTH_LONG).show();
                } else {
                    sendRegistInfo();
                }
            }
        });
    }

    public void sendRegistInfo() {
        // thread exception 처리
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        HttpClient httpclient = new DefaultHttpClient();
        HttpPost httppost = new HttpPost(REGIST_URL);

        try {
            // POST body에 요청할 데이터를 넣음
            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
            nameValuePairs.add(new BasicNameValuePair("name", str_name));
            nameValuePairs.add(new BasicNameValuePair("id", str_id));
            nameValuePairs.add(new BasicNameValuePair("password", str_pw));

            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs, "EUC-KR"));

            //HTTP Post 요청 실행
            HttpResponse response = httpclient.execute(httppost);

            // 받은 respones로 부터 result 헤더의 값을 String 형태로 가져옴
            String res = response.getFirstHeader("result").getValue();
            Log.d("SIGNUP", "received from server : " + res);

        } catch (ClientProtocolException e) {
            Log.d("SIGNUP", "Client error");
        } catch (IOException e) {
            Log.d("SIGNUP", e.toString());
        }
    }
}
