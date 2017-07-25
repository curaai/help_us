package stack.birds.helpus.FCM;

import android.content.Context;
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

    public FirebaseInstanceIDService(Context context) {
        final String token = FirebaseInstanceId.getInstance().getToken();

        HashMap<String, String> param = new HashMap<String, String>();
        param.put("Token", token);

        AQuery aq = new AQuery(context);
        aq.ajax(HOST_URL, param, String.class, new AjaxCallback<String>() {
            @Override
            public void callback(String url, String object, AjaxStatus status) {
                if(status.getCode() == 200) {
                    Log.d(TAG, "SUCCESS");
                } else {
                    Log.d(TAG, "FAILED");
                }
                Log.d(TAG, "Token: " + token);
            }
        });
    }
}