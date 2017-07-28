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

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import stack.birds.helpus.R;

public class SignupActivity extends AppCompatActivity {

    EditText name, id, pw, re_pw;
    TextView regist_check;
    AQuery aq;
    String REGIST_URL = "http://dmlwlsdk07.000webhostapp.com/joinup.php";
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

    public void sendRegistInfo() {

        // ues AQUERY
//        HashMap<String, String> param = new HashMap<String, String>();
//        param.put("name", str_name);
//        param.put("id", str_id);
//        param.put("pw", str_pw);
//
//        aq = new AQuery(getApplicationContext());
//        aq.ajax(REGIST_URL, param, String.class, new AjaxCallback<String>() {
//            @Override
//            public void callback(String url, String object, AjaxStatus status) {
//                result = status.getHeader("result");
//            }
//        });
//        return true;

//        int result = response.getStatusLine().getStatusCode();
//
//            Header[] headers = response.getAllHeaders();
//            for (Header header : headers) {
//                Log.d("SIGNUP ", "name : " + header.getName()
//                        + " ,Value : " + header.getValue());
//
//            }
//            String responseString = EntityUtils.toString(response.getEntity(), HTTP.UTF_8);
//            Log.d("SIGNUP", responseString);
//            Log.d("SIGNUP", result + "");
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

        StrictMode.setThreadPolicy(policy);

        HttpClient httpclient = getHttpClient();
        HttpPost httppost = new HttpPost(REGIST_URL);

        try {
            // 아래처럼 적절히 응용해서 데이터형식을 넣으시고
            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
            nameValuePairs.add(new BasicNameValuePair("name", str_name));
            nameValuePairs.add(new BasicNameValuePair("id", str_id));
            nameValuePairs.add(new BasicNameValuePair("pw", str_pw));

            HttpParams params = httpclient.getParams();
            HttpConnectionParams.setConnectionTimeout(params, 5000);
            HttpConnectionParams.setSoTimeout(params, 5000);


            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs, "EUC-KR"));

            //HTTP Post 요청 실행
            HttpResponse response = httpclient.execute(httppost);
            HttpEntity resEntity = response.getEntity();

            Log.d("SIGNUP", EntityUtils.toString(resEntity));
//

        } catch (ClientProtocolException e) {
            Log.d("SIGNUP", "Client error");
        } catch (IOException e) {
            Log.d("SIGNUP", e.toString());

            // TODO Auto-generated catch block
        }
    }

    private HttpClient getHttpClient() {
        try {
            KeyStore trustStore = KeyStore.getInstance(KeyStore.getDefaultType());
            trustStore.load(null, null);

            SSLSocketFactory sf = new SFSSLSocketFactory(trustStore);
            sf.setHostnameVerifier(SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);

            HttpParams params = new BasicHttpParams();
            HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);
            HttpProtocolParams.setContentCharset(params, HTTP.UTF_8);

            SchemeRegistry registry = new SchemeRegistry();
            registry.register(new Scheme("http", PlainSocketFactory.getSocketFactory(), 80));
            registry.register(new Scheme("https", sf, 443));

            ClientConnectionManager ccm = new ThreadSafeClientConnManager(params, registry);

            return new DefaultHttpClient(ccm, params);
        } catch (Exception e) {
            return new DefaultHttpClient();
        }
    }
}


class SFSSLSocketFactory extends SSLSocketFactory {
    SSLContext sslContext = SSLContext.getInstance("TLS");

    public SFSSLSocketFactory(KeyStore truststore) throws NoSuchAlgorithmException, KeyManagementException, KeyStoreException, UnrecoverableKeyException {
        super(truststore);

        TrustManager tm = new X509TrustManager() {
            public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
            }

            public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
            }

            public X509Certificate[] getAcceptedIssuers() {
                return null;
            }


        };

        sslContext.init(null, new TrustManager[]{tm}, null);
//        sslContext.init(null, new TrustManager[] { tm }, new SecureRandom());
    }

    @Override
    public Socket createSocket(Socket socket, String host, int port, boolean autoClose) throws IOException, UnknownHostException {
        return sslContext.getSocketFactory().createSocket(socket, host, port, autoClose);
    }

    @Override
    public Socket createSocket() throws IOException {
        return sslContext.getSocketFactory().createSocket();
    }
}
