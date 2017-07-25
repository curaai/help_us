package stack.birds.helpus.FCM;

import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;

import stack.birds.helpus.MainActivity;

/**
 * Created by dsm2016 on 2017-07-24.
 */

public class FirebaseInstanceIDService extends FirebaseInstanceIdService {
    private static final String TAG = "MyFirebaseIIDService";
    String HOST_URL = "";
    AQuery aq;

    // 프로그램이 처음 생길시 토큰 생성
    @Override
    public void onTokenRefresh() {
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Log.d(TAG, "Refreshed token: " + refreshedToken);

        sendRegistrationToServer(refreshedToken);
    }

    // 토큰을 담아서 서버에 보냄
    private void sendRegistrationToServer(String token) {
        HashMap<String, String> param = new HashMap<>();
        param.put("FCM_Token", token);

        aq = new AQuery(getApplicationContext());
        aq.ajax(HOST_URL, param, JSONObject.class, new AjaxCallback<JSONObject>() {

            @Override
            public void callback(String url, JSONObject json, AjaxStatus status) {
                if (status.getCode() == 200 || status.getCode() == 201) {
                    Log.d("FCM_Token", "token send success");
                } else {
                    Log.d("FCM_Token", "token send failed");
                }
            }
        });
    }
}