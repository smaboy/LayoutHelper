package com.example.smaboy.layouthelper.activity;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCallback;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.smaboy.layouthelper.R;
import com.example.smaboy.layouthelper.base.BaseActivity;
import com.example.smaboy.layouthelper.service.BeaconService;
import com.example.smaboy.layouthelper.util.IBeaconClass;
import com.example.smaboy.layouthelper.util.Utils;

import org.altbeacon.beacon.Beacon;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * <ur>
 * <li> 类名: BeaconActivity</li>
 * <li> 类作用描述: java类作用描述 </li>
 * <li> 页面名称: 页面描述 </li>
 * <li> 作者: <a href="mailto:liyongliang3@ceair.com">Li Yongliang</a></li>
 * <li> 创建时间: 2019/9/3 15:27</li>
 * </ur>
 */
public class BeaconActivity extends BaseActivity {
    @BindView(R.id.b_open_server)
    Button bOpenServer;


    private BeaconBroadcastReceiver beaconBroadcastReceiver;
    private static final String TAG = "Smaboy";
    public static final String BEACON_ACTION = "com.juju.beacontest.beacon.action";
    private static final int REQUEST_COARSE_LOCATION = 0;
    private static final String MY_UUID = "C91BBDBEDF544501A3AAD7BDF1FD2E1D";

    @Override
    public int getLayoutViewId() {
        return R.layout.activity_beacon;
    }

    @Override
    public void init(@Nullable Bundle savedInstanceState) {
        beaconBroadcastReceiver = new BeaconBroadcastReceiver();
    }

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(beaconBroadcastReceiver, getBeaconIntentFilter());
    }

    @Override
    public void setData() {

    }

    @Override
    protected void onPause() {
        super.onPause();
        if (beaconBroadcastReceiver != null)
            unregisterReceiver(beaconBroadcastReceiver);
    }


    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    @OnClick(R.id.b_open_server)
    public void onClick() {

//        Intent intent = new Intent(this, BeaconService.class);
//        startService(intent);
        mayRequestLocation();
//        startScan();
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    private void startScan() {
        BluetoothAdapter defaultAdapter = BluetoothAdapter.getDefaultAdapter();
        if (null != defaultAdapter) {
            defaultAdapter.startLeScan(new BluetoothAdapter.LeScanCallback() {
                @Override
                public void onLeScan(BluetoothDevice bluetoothDevice, int i, byte[] bytes) {
//                    IBeaconClass.iBeacon ibeacon = IBeaconClass.fromScanData(bluetoothDevice,i,bytes);
                    String s = Utils.bytesToHex(bytes);
                    if(s.contains(MY_UUID)) {
                        String name = bluetoothDevice.getName();
                        bluetoothDevice.getUuids();
                        Log.e(TAG, "名称: " + name);
                    }

                    Log.i(TAG, "设备信息: " + s);
                    Log.i(TAG, "rssi: " + i);

//                    bluetoothDevice.connectGatt(BeaconActivity.this, true, new BluetoothGattCallback() {
//                        @Override
//                        public void onPhyRead(BluetoothGatt gatt, int txPhy, int rxPhy, int status) {
//                            super.onPhyRead(gatt, txPhy, rxPhy, status);
//                        }
//                    });

                }
            });
        }
    }

    public class BeaconBroadcastReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (BEACON_ACTION.equals(action)) {
                List<Beacon> beacons = intent.getParcelableArrayListExtra("beacon");
                for (Beacon beacon : beacons) {
                    Log.i(TAG, "onReceive: " + beacon.getServiceUuid());
                }
            }
        }
    }

    IntentFilter getBeaconIntentFilter() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(BEACON_ACTION);
        return intentFilter;
    }

    //系统方法,从requestPermissions()方法回调结果
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        //确保是我们的请求
        if (requestCode == REQUEST_COARSE_LOCATION) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                startScan();
                Toast.makeText(this, "权限被授予", Toast.LENGTH_SHORT).show();
            } else if (grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "权限被拒绝", Toast.LENGTH_SHORT).show();
            }
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    private void mayRequestLocation() {
        if (Build.VERSION.SDK_INT >= 23) {
            int checkCallPhonePermission = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION);
            if (checkCallPhonePermission != PackageManager.PERMISSION_GRANTED) {
                //判断是否需要 向用户解释，为什么要申请该权限
                if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_COARSE_LOCATION))
                    Toast.makeText(this, "动态请求权限", Toast.LENGTH_LONG).show();
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, REQUEST_COARSE_LOCATION);
                return;
            } else {
                startScan();
            }
        } else {

        }
    }
}
