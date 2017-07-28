package stack.birds.helpus.Account;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.StrictMode;
import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by sch on 2017-07-28.
 */

public class Account {
    private StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

    private String REGIST_URL = "https://dmlwlsdk07.000webhostapp.com/joinin.php";
    private String LOGIN_URL = "https://dmlwlsdk07.000webhostapp.com/login_procs.php";
    private int REGIST_FLAG = 1;
    private int LOGIN_FLAG = 0;

    private String TAG = "REQUEST";
    private String LOGIN_TAG = "AUTO_LOGIN";

    private Context context;
    private SharedPreferences auto_login;

    public Account(Context context) {
        this.context = context;
         auto_login = context.getSharedPreferences("auto_login", Activity.MODE_PRIVATE);
    }

    // 리퀘스트 성공시 1 실패시 0 오류시 -1
    public int requestToServer(int flag, HashMap<String, String> params) {
        StrictMode.setThreadPolicy(policy);

        //  플래그가 1이면 회원가입, 0이면 로그인
        String URL = (flag == 1) ? REGIST_URL : LOGIN_URL;

        HttpClient httpclient = new DefaultHttpClient();
        HttpPost httppost = new HttpPost(URL);
        // 한글 인코딩
        httppost.setHeader(HTTP.CONTENT_TYPE,
                "application/x-www-form-urlencoded;charset=UTF-8");

        try {
            // POST body에 요청할 데이터를 넣음
            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
            for (HashMap.Entry<String, String> entry : params.entrySet()) {
                nameValuePairs.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
            }

            // url encoding 한글
            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs, "utf-8"));

            //HTTP Post 요청 실행
            HttpResponse response = httpclient.execute(httppost);

            // 받은 respones로 부터 result 헤더의 값을 String 형태로 가져옴
            int result = Integer.parseInt(response.getFirstHeader("result").getValue());
            Log.d(TAG, "received from server : " + result);
            Log.d(TAG, "flag : " + flag);

            return result;
        } catch (Exception e) {
            Log.d(TAG, "REQUEST ERROR : " + e.toString());
            return -1;
        }
    }

    // 저장했던 값들을 불러와 로그인 리퀘스트를 날림
    public int autoLogin(){
        String ID = auto_login.getString("id", null);
        String PW = auto_login.getString("pw", null);

        HashMap<String, String> login_param = new HashMap<String, String>();
        login_param.put("id", ID);
        login_param.put("pw", PW);
        login_param.put("token", FirebaseInstanceId.getInstance().getToken());

        return requestToServer(LOGIN_FLAG, login_param);
    }

    // 입력받은 id 와 pw 를 저장함
    public boolean registAutoLogin(String id, String pw) {
        SharedPreferences auto_login = context.getSharedPreferences("auto_login", Activity.MODE_PRIVATE);
        try {
            SharedPreferences.Editor loginEditor = auto_login.edit();
            loginEditor.putString("id", id);
            loginEditor.putString("pw", pw);
            loginEditor.commit();

            return true;
        } catch (Exception e) {
            Log.d(LOGIN_TAG, e.toString());
            return false;
        }
    }

    public void loginDataRemove() {
        SharedPreferences.Editor loginEditor = auto_login.edit();
        loginEditor.clear();
        loginEditor.commit();
    }
}
