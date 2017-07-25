package stack.birds.helpus.FCM;

import android.util.Log;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

import java.util.HashMap;

/**
 * Created by dsm2016 on 2017-07-24.
 */

public class FirebaseInstanceIDService extends FirebaseInstanceIdService {
    private static final String TAG = "MyFirebaseIIDService";
    private String HOST_URL = "https://dmlwlsdk07.000webhostapp.com/register.php";
    private AQuery aq;


    // 프로그램이 처음 생길시 토큰 생성
    @Override
    public void onTokenRefresh() {
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Log.d(TAG, "Refreshed token: " + refreshedToken);

        sendRegistrationToServer(refreshedToken);
    }

    private void sendRegistrationToServer(String token){
        // 서버로 토큰 보내기
        HashMap<String, String> param = new HashMap<String, String>();
        param.put("registration_ids", token);

        aq = new AQuery(getApplicationContext());
        aq.ajax(HOST_URL, param, String.class, new AjaxCallback<String>() {
            @Override
            public void callback(String url, String object, AjaxStatus status) {
                if(status.getCode() == 200) {
                    Log.d(TAG, "SUCCESS");
                } else {
                    Log.d(TAG, "FAILED");

                }
            }
        });
    }
}