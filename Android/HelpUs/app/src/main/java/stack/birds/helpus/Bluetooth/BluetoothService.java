package stack.birds.helpus.Bluetooth;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.os.Handler;
import android.util.Log;

/**
 * Created by sch on 2017-07-25.
 */

public class BluetoothService {
    private static final String TAG = "BluetoothService";

    private BluetoothAdapter btAdapter;

    private Activity mActivity;
    private Handler mHandler;

    private static final int REQUEST_ENABLE_BT = 2;
    private static final int REQUEST_CONNECT_DEVICE = 1;

    public BluetoothService(Activity activity, Handler handler) {
        mActivity = activity;
        mHandler = handler;

        btAdapter = BluetoothAdapter.getDefaultAdapter();
    }

    // 기기가 블루투스를 지원하는지 확인함
    public boolean getDeviceState() {
        Log.d(TAG, "Check the Bluetooth support");

        if(btAdapter == null) {
            Log.d(TAG, "Bluetooth is not available");
            return false;
        } else {
            Log.d(TAG, "Bluetooth is available");
            return true;
        }
    }

    // 블루투스 상태를 확인하고 만약 꺼져잇다면 블루투스를 킴
    public void enableBluetooth() {
        Log.i(TAG, "Check the enabled Bluetooth");

        if(btAdapter.isEnabled()) {
            Log.d(TAG, "Bluetooth Enabled now");
        } else {
            Log.d(TAG, "Bluetooth Enabled Request");

            Intent i = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            mActivity.startActivityForResult(i, REQUEST_ENABLE_BT);
        }
    }
}
