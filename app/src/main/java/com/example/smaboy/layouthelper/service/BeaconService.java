package com.example.smaboy.layouthelper.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.Parcelable;
import android.os.RemoteException;

import androidx.annotation.Nullable;

import com.example.smaboy.layouthelper.activity.BeaconActivity;

import org.altbeacon.beacon.Beacon;
import org.altbeacon.beacon.BeaconConsumer;
import org.altbeacon.beacon.BeaconManager;
import org.altbeacon.beacon.RangeNotifier;
import org.altbeacon.beacon.Region;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * <ur>
 * <li> 类名: BeaconService</li>
 * <li> 类作用描述: java类作用描述 </li>
 * <li> 页面名称: 页面描述 </li>
 * <li> 作者: <a href="mailto:liyongliang3@ceair.com">Li Yongliang</a></li>
 * <li> 创建时间: 2019/9/3 15:24</li>
 * </ur>
 */
public class BeaconService extends Service implements BeaconConsumer, RangeNotifier {

    private static final long DEFAULT_BACKGROUND_SCAN_PERIOD = 1000L;
    private static final long DEFAULT_BACKGROUND_BETWEEN_SCAN_PERIOD = 1000L;

    private BeaconManager beaconManager = BeaconManager.getInstanceForApplication(this);

    public BeaconService() {

    }

    @Override
    public void onCreate() {
        super.onCreate();
        initBeacon();
        beaconManager.bind(this);
    }

    private void initBeacon() {
        beaconManager.setBackgroundScanPeriod(DEFAULT_BACKGROUND_SCAN_PERIOD);
        beaconManager.setBackgroundBetweenScanPeriod(DEFAULT_BACKGROUND_BETWEEN_SCAN_PERIOD);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (beaconManager != null)
            beaconManager.removeRangeNotifier(this);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onBeaconServiceConnect() {
        Region region = new Region("myRangingUniqueId", null, null, null);
        beaconManager.addRangeNotifier(this);
        try {
            beaconManager.startRangingBeaconsInRegion(region);
        } catch (RemoteException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void didRangeBeaconsInRegion(Collection<Beacon> collections, Region region) {
        List<Beacon> beacons = new ArrayList<>();
        for (Beacon beacon : collections) {
            beacons.add(beacon);
        }
        Intent intent = new Intent(BeaconActivity.BEACON_ACTION);
        intent.putParcelableArrayListExtra("beacon", (ArrayList<? extends Parcelable>) beacons);//因为Beacon继承了Parcelable,
        sendBroadcast(intent);

    }
}
