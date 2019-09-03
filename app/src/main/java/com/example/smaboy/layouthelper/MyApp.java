package com.example.smaboy.layouthelper;

import android.app.Application;

import org.altbeacon.beacon.BeaconManager;
import org.altbeacon.beacon.BeaconParser;
import org.altbeacon.beacon.Region;
import org.altbeacon.beacon.powersave.BackgroundPowerSaver;
import org.altbeacon.beacon.startup.BootstrapNotifier;
import org.altbeacon.beacon.startup.RegionBootstrap;

/**
 * <ur>
 * <li> 类名: MyApp</li>
 * <li> 类作用描述: java类作用描述 </li>
 * <li> 页面名称: 页面描述 </li>
 * <li> 作者: <a href="mailto:liyongliang3@ceair.com">Li Yongliang</a></li>
 * <li> 创建时间: 2019/9/3 15:19</li>
 * </ur>
 */
public class MyApp extends Application implements BootstrapNotifier {
    private static final long DEFAULT_FOREGROUND_BETWEEN_SCAN_PERIOD = 1000;
    private static final long DEFAULT_FOREGROUND_SCAN_PERIOD = 1000;
    private RegionBootstrap regionBootstrap;
    private BackgroundPowerSaver backgroundPowerSaver;

    @Override
    public void onCreate() {
        super.onCreate();

//        initBeacon();
    }


    private void initBeacon() {
        // 获取beaconManager实例对象
        BeaconManager beaconManager = BeaconManager.getInstanceForApplication(this);
        //设置搜索的时间间隔和周期
        beaconManager.setForegroundBetweenScanPeriod(DEFAULT_FOREGROUND_BETWEEN_SCAN_PERIOD);
        beaconManager.setForegroundScanPeriod(DEFAULT_FOREGROUND_SCAN_PERIOD);
        //清除beacon数据包格式
        beaconManager.getBeaconParsers().clear();
        //设置beacon数据包格式
        beaconManager.getBeaconParsers().add(new BeaconParser()
                .setBeaconLayout("m:2-3=0215,i:4-19,i:20-21,i:22-23,p:24-24,d:25-25"));
        //将activity与库中的BeaconService绑定到一起,服务准备完成后就会自动回调下面的onBeaconServiceConnect方法
//        beaconManager.bind(this);

        Region region = new Region("all-region-beacon",null,null,null);
        regionBootstrap = new RegionBootstrap(this,region);
        backgroundPowerSaver = new BackgroundPowerSaver(this);
    }

    @Override
    public void didEnterRegion(Region region) {

    }

    @Override
    public void didExitRegion(Region region) {

    }

    @Override
    public void didDetermineStateForRegion(int i, Region region) {

    }
}
