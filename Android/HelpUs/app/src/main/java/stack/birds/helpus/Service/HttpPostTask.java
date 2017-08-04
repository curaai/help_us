package stack.birds.helpus.Service;

import android.net.http.AndroidHttpClient;
import android.os.AsyncTask;
import android.util.Log;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

import stack.birds.helpus.Class.Report;

/**
 * Created by sch on 2017-08-04.
 */

public class HttpPostTask extends AsyncTask<Object, String, Integer> {
    private String REPORT_URL;
    private String TAG;

    @Override
    protected void onPreExecute() {
        REPORT_URL = "https://dmlwlsdk07.000webhostapp.com/file.php";
        TAG = "POST_TASK";
    }

    @Override
    protected Integer doInBackground(Object... params) {
        Report report = (Report) params[0];
        String gps = (String) params[1];
        String ID = (String) params[2];

        HttpClient httpClient = AndroidHttpClient.newInstance("Android");
        HttpPost post = new HttpPost(REPORT_URL);

        File mp3 = new File(report.getFilePath());

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
            entity.addPart("gps",           new StringBody(gps));
            entity.addPart("file",          new FileBody(mp3));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        HttpResponse response = null;
        try {
            post.setEntity(entity.build());
            response = httpClient.execute(post);
        } catch (IOException e) {
            Log.d(TAG, e.toString());
        }

        // post 성공시 200 반환
        int result = response.getStatusLine().getStatusCode();
        Log.d(TAG, "received from server : " + result);

        post.abort();
        return result;
    }

    @Override
    protected void onPostExecute(Integer integer) {
        super.onPostExecute(integer);
    }

    @Override
    protected void onProgressUpdate(String... values) {
        super.onProgressUpdate(values);
    }
}
