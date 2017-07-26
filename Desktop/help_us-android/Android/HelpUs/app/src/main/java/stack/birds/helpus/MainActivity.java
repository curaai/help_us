package stack.birds.helpus;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import stack.birds.helpus.FCM.FirebaseInstanceIDService;
import stack.birds.helpus.GPS.GPSService;

public class MainActivity extends AppCompatActivity {

    TextView langitude, longitude;
    Button refresh, gps_btn;

    GPSService gps;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        langitude = (TextView) findViewById(R.id.latitude);
        longitude = (TextView) findViewById(R.id.longitude);

        refresh = (Button) findViewById(R.id.btn_refresh);
        gps_btn = (Button) findViewById(R.id.gps_on);

        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseInstanceIDService fb = new FirebaseInstanceIDService(getApplicationContext());
            }
        });

        gps_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gps = new GPSService(getApplicationContext());

                gps.listening_gps();
                Log.d("gps in activity", gps.getLat() + "");
                Log.d("gps in activity", gps.getLon() + "");

                langitude.setText(Double.toString(gps.getLat()));
                longitude.setText(Double.toString(gps.getLon()));
            }
        });

    }
}