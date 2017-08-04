package stack.birds.helpus.Service;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.http.AndroidHttpClient;
import android.os.StrictMode;
import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import stack.birds.helpus.Class.Report;

/**
 * Created by sch on 2017-07-28.
 */

public class AccountService {
    private StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

    // TODO 서버에서 신고하는 URL 받아오기
    private String REGIST_URL = "https://dmlwlsdk07.000webhostapp.com/joinin.php";
    private String LOGIN_URL = "https://dmlwlsdk07.000webhostapp.com/login_procs.php";
    private String REPORT_URL = "ASDF";
    private int LOGIN_FLAG = 0;
    private int REGIST_FLAG = 1;
    private int REPORT_FLAG = 2;

    private String TAG = "REQUEST";
    private String LOGIN_TAG = "AUTO_LOGIN";

    private Context context;
    private SharedPreferences auto_login;

    public AccountService(Context context) {
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
    public int autoLogin() {
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

    public void logout() {
        SharedPreferences.Editor loginEditor = auto_login.edit();
        loginEditor.clear();
        loginEditor.commit();
    }

    // TODO MP3파일 올리는거 하기 
    // MultipartEntityBuilder 로 서버에 file upload
    // HashMap 인자는 String 인자와 키 값들, 2번째 인자 Byte[] 는 서버로 보낼 mp3 데이터, 3번째는 GPS 데이터
    public void reportToServer(Report report, String gpsData) {

        String ID = auto_login.getString("id", null);

        File mp3 = new File(report.getFilePath());
        Date last = new Date(mp3.lastModified());
        Date currentDate = Calendar.getInstance().getTime();

        SimpleDateFormat format = new SimpleDateFormat("yyyy/mm/dd HH:mm");

        String lastModified = format.format(last);
        String reportDate = format.format(currentDate);

        MultipartEntityBuilder entity = MultipartEntityBuilder.create();
        entity.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);

        try {
            entity.addPart("id",            new StringBody(ID));
            entity.addPart("title",         new StringBody(report.getTitle()));
            entity.addPart("content",       new StringBody(report.getContent()));
            entity.addPart("receivers",     new StringBody(report.getReceivers()));
            entity.addPart("reportDate",    new StringBody(report.getReportDate()));
            entity.addPart("accidentDate",  new StringBody(report.getAccidentDate()));
            entity.addPart("anonymous",     new StringBody(Integer.toString(report.getANONYMOUS())));
            entity.addPart("gps",           new StringBody(gpsData));
            entity.addPart("file",          new FileBody(mp3));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        HttpClient client = AndroidHttpClient.newInstance("Android");
        HttpPost post = new HttpPost(REPORT_URL);

        try {

            HttpResponse response = client.execute(post);

            // 받은 respones로 부터 result 헤더의 값을 String 형태로 가져옴
            int result = response.getStatusLine().getStatusCode();
            Log.d(TAG, "received from server : " + result);

//            구글링 했던 코드들
//            post.setEntity(entity.build());
//            HttpResponse httpRes;
//            httpRes = client.execute(post);
//            HttpEntity httpEntity = httpRes.getEntity();
//            if (httpEntity != null) {
//                String response = EntityUtils.toString(httpEntity);
//                Log.d(TAG, "reportData : " + response);
//            }
        } catch (Exception e) {
            Log.d(TAG, "ERROR OCCUR : " + e.toString());
        }
    }
}

