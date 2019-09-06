package com.example.smaboy.layouthelper.activity;

import android.Manifest;
import android.app.AlertDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.RemoteException;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.lifecycle.ViewModelProvider;

import com.example.smaboy.layouthelper.Entity.MessageEvent;
import com.example.smaboy.layouthelper.R;
import com.example.smaboy.layouthelper.base.BaseActivity;
import com.example.smaboy.layouthelper.receiver.BluetoothReceiver;
import com.example.smaboy.layouthelper.viewmodel.BluetoothViewModel;

import org.altbeacon.beacon.Beacon;
import org.altbeacon.beacon.BeaconConsumer;
import org.altbeacon.beacon.BeaconManager;
import org.altbeacon.beacon.BeaconParser;
import org.altbeacon.beacon.RangeNotifier;
import org.altbeacon.beacon.Region;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * <ur>
 * <li> 类名: BluetoothActivity</li>
 * <li> 类作用描述: java类作用描述 </li>
 * <li> 页面名称: 页面描述 </li>
 * <li> 作者: <a href="mailto:liyongliang3@ceair.com">Li Yongliang</a></li>
 * <li> 创建时间: 2019/8/30 15:43</li>
 * </ur>
 */
public class BluetoothActivity extends BaseActivity implements BeaconConsumer {
    private static final long DEFAULT_FOREGROUND_BETWEEN_SCAN_PERIOD = 1000L;//扫描间隔时间 30s
    private static final long DEFAULT_FOREGROUND_SCAN_PERIOD = 1000L;//扫描周期 10min
    private static final String TAG = "Smaboy2";
    public static final String FILTER_UUID = "C91BBDBE-DF54-4501-A3AA-D7BDF1FD2E1D";//开发uuid
    //    public static final String FILTER_UUID = "FDA50693A4E24FB1AFCFC6EB07647825";//生产uuid
    private static final int PERMISSION_REQUEST_COARSE_LOCATION = 0x100;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.ll_title)
    LinearLayout llTitle;
    @BindView(R.id.tv_bluetooth_open_or_close_status)
    TextView tvBluetoothOpenOrCloseStatus;
    @BindView(R.id.tv_usable_list)
    TextView tvUsableList;
    @BindView(R.id.tv_paired_list)
    TextView tvPairedList;
    @BindView(R.id.b_open)
    Button bOpen;
    @BindView(R.id.b_close)
    Button bClose;
    @BindView(R.id.b_usable_list)
    Button bUsableList;
    @BindView(R.id.b_paired_list)
    Button bPairedList;
    @BindView(R.id.tv_beacon_info)
    TextView tvBeaconInfo;
    @BindView(R.id.ll_beacon_info)
    LinearLayout llBeaconInfo;
    @BindView(R.id.b_start_rang)
    Button bStartRang;
    @BindView(R.id.b_stop_rang)
    Button bStopRang;
    @BindView(R.id.ll_bluetooth_info)
    LinearLayout llBluetoothInfo;

    private BluetoothViewModel model;
    private BeaconManager beaconManager;
    private Region region;
    private StringBuilder usableString = new StringBuilder();
    private StringBuilder beaconString = new StringBuilder();


    @Override
    protected void onStart() {
        super.onStart();
        //初始化事件总线
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onStop() {
        super.onStop();

        //解注册事件总线
        EventBus.getDefault().unregister(this);

    }

    @Override
    public int getLayoutViewId() {
        return R.layout.activity_bluetooth;
    }

    @Override
    public void init(@Nullable Bundle savedInstanceState) {

        //获取model
        model = ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication()).create(BluetoothViewModel.class);

        //设置监听
        registerBoradcastReceiver();


    }

    @Override
    public void setData() {

    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    @OnClick({R.id.b_open, R.id.b_close, R.id.b_usable_list, R.id.b_paired_list, R.id.b_start_rang, R.id.b_stop_rang})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.b_open://开启蓝牙
                model.openBluetooth(this).observe(this, s -> tvBluetoothOpenOrCloseStatus.setText(s));
                isBeanconStatus(false);

                break;
            case R.id.b_close://关闭蓝牙
                model.closeBluetooth(this).observe(this, s -> tvBluetoothOpenOrCloseStatus.setText(s));
                isBeanconStatus(false);

                break;
            case R.id.b_usable_list://获取可用列表
                model.getUsableList().observe(this, s -> tvUsableList.setText(s));
                isBeanconStatus(false);

                break;
            case R.id.b_paired_list://获取配对列表
                model.getPairedList().observe(this, s -> tvPairedList.setText(s));
                isBeanconStatus(false);

                break;
            case R.id.b_start_rang://开启beancon
                requestLocationPermissions();
                isBeanconStatus(true);

                break;
            case R.id.b_stop_rang://关闭beacon
                try {
                    if(null!=beaconManager) beaconManager.stopRangingBeaconsInRegion(region);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
                isBeanconStatus(true);

                break;
        }
    }

    /**
     * 接收事件总线发来的消息
     *
     * @param event
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(MessageEvent event) {

        if (null != event) {
            switch (event.getWhat()) {
                case 100:
                case 101:
                    usableString.append(event.getStr());
                    tvUsableList.setText(usableString);
                    break;
                case 102:
                    tvBluetoothOpenOrCloseStatus.setText(event.getStr());
                    break;
                default:
                    break;
            }
        }

    }

    private void registerBoradcastReceiver() {
        //注册监听
        IntentFilter stateChangeFilter = new IntentFilter(
                BluetoothAdapter.ACTION_STATE_CHANGED);
        IntentFilter connectedFilter = new IntentFilter(
                BluetoothDevice.ACTION_ACL_CONNECTED);
        IntentFilter disConnectedFilter = new IntentFilter(
                BluetoothDevice.ACTION_ACL_DISCONNECTED);

        // 找到设备的广播
        IntentFilter foundFilter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
        // 搜索完成的广播
        IntentFilter discoveryFinishedFilter = new IntentFilter(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);

        BluetoothReceiver bluetoothReceiver = new BluetoothReceiver();
        registerReceiver(bluetoothReceiver, stateChangeFilter);
        registerReceiver(bluetoothReceiver, connectedFilter);
        registerReceiver(bluetoothReceiver, disConnectedFilter);
        registerReceiver(bluetoothReceiver, foundFilter);
        registerReceiver(bluetoothReceiver, discoveryFinishedFilter);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @androidx.annotation.Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == BluetoothViewModel.OPEN_BLUETOOTH_REQUEST_CODE) {//开启蓝牙的回调
            switch (resultCode) {
                case RESULT_CANCELED:
                    tvBluetoothOpenOrCloseStatus.setText("请求开启蓝牙失败");
//                    Log.e("TAG", "开启蓝牙-RESULT_CANCELED");
                    break;
                case RESULT_FIRST_USER:
//                    Log.e("TAG", "开启蓝牙-RESULT_FIRST_USER");
                    break;
                case RESULT_OK:
                    tvBluetoothOpenOrCloseStatus.setText("请求开启蓝牙成功");
//                    Log.e("TAG", "开启蓝牙-RESULT_OK");

                    break;
            }
        }

    }

    private void initBeacon() {
        BluetoothAdapter defaultAdapter = BluetoothAdapter.getDefaultAdapter();
        if(null!=defaultAdapter&&!defaultAdapter.isEnabled()) {
            Toast.makeText(BluetoothActivity.this, "请先开启蓝牙", Toast.LENGTH_SHORT).show();
            return;
        }

        //是否首次创建
        if (null != beaconManager) {
            try {
                beaconManager.startRangingBeaconsInRegion(region);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
            return;
        }

        //置空beaconString
        beaconString.delete(0,beaconString.length());
        tvBeaconInfo.setText("开始扫描周边beacon设备...");


        // 获取beaconManager实例对象
        beaconManager = BeaconManager.getInstanceForApplication(this);
        //设置搜索的时间间隔和周期
        beaconManager.setForegroundBetweenScanPeriod(DEFAULT_FOREGROUND_BETWEEN_SCAN_PERIOD);
        beaconManager.setForegroundScanPeriod(DEFAULT_FOREGROUND_SCAN_PERIOD);
        //清除beacon数据包格式
        beaconManager.getBeaconParsers().clear();
        //设置beacon数据包格式
        beaconManager.getBeaconParsers().add(new BeaconParser()
                .setBeaconLayout("m:2-3=0215,i:4-19,i:20-21,i:22-23,p:24-24,d:25-25"));
        //将activity与库中的BeaconService绑定到一起,服务准备完成后就会自动回调下面的onBeaconServiceConnect方法
        beaconManager.bind(this);



    }

    @Override
    public void onBeaconServiceConnect() {
        //获取附近的Beacon设备信息
        beaconManager.addRangeNotifier(new RangeNotifier() {
            @Override
            public void didRangeBeaconsInRegion(Collection<Beacon> collection, Region region) {
                Log.i(TAG, "didRangeBeaconsInRegion: 调用了这个方法:" + collection.size());
                if (collection.size() > 0) {
                    //符合要求的beacon集合
                    List<Beacon> beacons = new ArrayList<>();
                    for (Beacon beacon : collection) {
//                        判断该beacon的UUID是否为我们感兴趣的
                        if (beacon.getId1().toString().equalsIgnoreCase(FILTER_UUID)) {
//                            是则添加到集合
                            beacons.add(beacon);
                        }
                    }

                    if (beacons.size() > 0) {
                        //                    给收集到的beacons按rssi的信号强弱排序
                        Collections.sort(beacons, new Comparator<Beacon>() {
                            public int compare(Beacon arg0, Beacon arg1) {
                                return arg1.getRssi() - arg0.getRssi();
                            }
                        });
//                        获取最近的beacon
                        Beacon nearBeacon = beacons.get(0);
//                        获取beacon的uuid信息
                        String uuid = nearBeacon.getId1().toString();
//                        获取beacon的major信息
                        String major = nearBeacon.getId2().toString();
//                        获取beacon的minor信息
                        String minor = nearBeacon.getId3().toString();
                        //获取rssi
                        int rssi = nearBeacon.getRssi();
                        //获取txPower
                        int txPower = nearBeacon.getTxPower();


//                        从数据类中获取位置信息
                        Log.i(TAG, "didRangeBeaconsInRegion: " + beacons.toString());
                        Log.i(TAG, "nearBeacon: " + uuid + "===" + major + "===" + minor + "===" + rssi + "===" + txPower);

                        //处理数据的展示
                        for (Beacon beacon : beacons) {
                            beaconString
                                    .append("uuid :  ").append(beacon.getId1()).append("\n")
                                    .append("major: ").append(beacon.getId2()).append("\n")
                                    .append("minor: ").append(beacon.getId3()).append("\n")
                                    .append("rssi: ").append(beacon.getRssi()).append("\n")
                                    .append("txPower: ").append(beacon.getTxPower()).append("\n\n\n");
                            tvBeaconInfo.setText(beaconString);

                        }



                    }
                }
            }

        });

        try {
//            别忘了启动搜索,不然不会调用didRangeBeaconsInRegion方法
            region = new Region(FILTER_UUID, null, null, null);
            beaconManager.startRangingBeaconsInRegion(region);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }


    /**
     * 6.0以上需要动态权限请求，才能使用beacon
     */
    private void requestLocationPermissions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            // Android M Permission check

            if (this.checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

                final AlertDialog.Builder builder = new AlertDialog.Builder(this);

                builder.setTitle("This app needs location access");
                builder.setMessage("Please grant location access so this app can detect beacons.");
                builder.setPositiveButton(android.R.string.ok, null);

                builder.setOnDismissListener(new DialogInterface.OnDismissListener() {


                    @Override

                    public void onDismiss(DialogInterface dialog) {
                        requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, PERMISSION_REQUEST_COARSE_LOCATION);

                    }


                });

                builder.show();

            } else {
                initBeacon();
            }

        } else {
            initBeacon();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQUEST_COARSE_LOCATION: {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    initBeacon();
                } else {
                    final AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setTitle("Functionality limited");
                    builder.setMessage("Since location access has not been granted, this app will not be able to discover beacons when in the background.");
                    builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            goIntentSetting();

                        }
                    });
                    builder.setNegativeButton(android.R.string.cancel, null);
                    builder.setOnDismissListener(new DialogInterface.OnDismissListener() {

                        @Override
                        public void onDismiss(DialogInterface dialog) {
                        }

                    });
                    builder.show();
                }
                return;
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        beaconManager.unbind(this);
    }


    /**
     * 是否是beacon状态
     *
     * @param status
     */
    private void isBeanconStatus(Boolean status) {
        if (status) {
            llBluetoothInfo.setVisibility(View.GONE);
            llBeaconInfo.setVisibility(View.VISIBLE);
        } else {
            llBluetoothInfo.setVisibility(View.VISIBLE);
            llBeaconInfo.setVisibility(View.GONE);
        }

    }

    private void goIntentSetting() {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", getPackageName(), null);
        intent.setData(uri);
        try {
            startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
