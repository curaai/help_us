package stack.birds.helpus.Service;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

import java.util.HashMap;

/**
 * Created by dsm2016 on 2017-07-24.
 */

public class MyFirebaseInstanceIDService extends FirebaseInstanceIdService {
    private static final String TAG = "FirebaseToken";
    private String HOST_URL = "https://dmlwlsdk07.000webhostapp.com/register.php";

    public MyFirebaseInstanceIDService(final Context context) {
        final String token = FirebaseInstanceId.getInstance().getToken();

        SharedPreferences auto_login = context.getSharedPreferences("auto_login", Activity.MODE_PRIVATE);
        String id = auto_login.getString("id", null);
        HashMap<String, String> param = new HashMap<String, String>();
        param.put("Token", token);
        param.put("id", id);

        AQuery aq = new AQuery(context);
        aq.ajax(HOST_URL, param, String.class, new AjaxCallback<String>() {
            @Override
            public void callback(String url, String object, AjaxStatus status) {
                if(status.getCode() == 200) {
                    Log.d(TAG, "SUCCESS");
                    Toast.makeText(context, "파일 전송이 완료되었습니다.", Toast.LENGTH_LONG).show();
                } else {
                    Log.d(TAG, "FAILED");
                }
                Log.d(TAG, "Token: " + token);
            }
        });
    }
}