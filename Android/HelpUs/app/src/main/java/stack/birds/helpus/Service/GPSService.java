package stack.birds.helpus.Service;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;

/**
 * Created by sch on 2017-07-26.
 */

public class GPSService {
    private LocationManager manager;
    private LocationListener listener;

    private static double lat, lon;

    // GPS manager, listener 생성
    public GPSService(Context context) {
        manager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);

        listener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                setLat(location.getLatitude());
                setLon(location.getLongitude());

                Log.d("gps_info", lat + "");
                Log.d("gps_info", lon + "");
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {}

            @Override
            public void onProviderEnabled(String provider) {}

            @Override
            public void onProviderDisabled(String provider) {}
        };
    }

    // gps가 계속 업데이트 되게함
    public void listening_gps() {
        try {
            manager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
                    500,                 // 최소 시간간격 milliSecond
                    1,                    // 최소 변경 거리
                    listener);
        } catch (SecurityException e) {
            Log.d("Security", e.toString());
        }
    }

    // gps 업데이트를 그만하게 함
    public void stop_listening() {
        manager.removeUpdates(listener);
    }

    public static double getLat() {
        return lat;
    }

    public static void setLat(double lat) {
        GPSService.lat = lat;
    }

    public static double getLon() {
        return lon;
    }

    public static void setLon(double lon) {
        GPSService.lon = lon;
    }
}
