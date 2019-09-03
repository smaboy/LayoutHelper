package com.example.smaboy.layouthelper.viewmodel;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.provider.Settings;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;


/**
 * <ur>
 * <li> 类名: BluetoothViewModel</li>
 * <li> 类作用描述: java类作用描述 </li>
 * <li> 页面名称: 页面描述 </li>
 * <li> 作者: <a href="mailto:liyongliang3@ceair.com">Li Yongliang</a></li>
 * <li> 创建时间: 2019/8/30 16:21</li>
 * </ur>
 */
public class BluetoothViewModel extends ViewModel {

    public MutableLiveData<String> isOpen;//蓝牙开启状态
    public MutableLiveData<String> isClose;//蓝牙关闭状态
    public MutableLiveData<String> isUsable;//可用列表
    public MutableLiveData<String> isPaired;//匹配列表
    public static final int OPEN_BLUETOOTH_REQUEST_CODE = 0x001;
    public static final int CLOSE_BLUETOOTH_REQUEST_CODE = 0x002;

    public BluetoothViewModel() {
        //初始化操作
        isOpen = new MutableLiveData<>();
        isClose = new MutableLiveData<>();
        isPaired = new MutableLiveData<>();
        isUsable = new MutableLiveData<>();

    }

    /**
     * 开启蓝牙
     *
     * @return 返回蓝牙状态
     */
    public MutableLiveData<String> openBluetooth(Activity activity) {
        BluetoothAdapter bTAdapter = BluetoothAdapter.getDefaultAdapter();

        if (null == bTAdapter) {
            isOpen.postValue("当前设备不支持蓝牙功能");
        } else {
            if (!bTAdapter.isEnabled()) {//蓝牙处于关闭状态
                Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                activity.startActivityForResult(intent,OPEN_BLUETOOTH_REQUEST_CODE);
//                bTAdapter.enable(); 此方法开启 没有提示
            } else {//蓝牙处于开启状态
                isOpen.postValue("蓝牙处于开启状态");
            }


        }

        return isOpen;

    }

    /**
     * 关闭蓝牙
     *
     * @return 返回蓝牙状态
     */
    public MutableLiveData<String> closeBluetooth(Activity activity) {
        BluetoothAdapter bTAdatper = BluetoothAdapter.getDefaultAdapter();

        if (null == bTAdatper) {
            isClose.postValue("当前设备不支持蓝牙功能");
        } else {
            if (!bTAdatper.isEnabled()) {//蓝牙处于关闭状态
                isClose.postValue("蓝牙处于关闭状态");
            } else {//蓝牙处于开启状态
                isClose.postValue("蓝牙处于开启状态");
                bTAdatper.disable();
                // 跳转到蓝牙设置页面，关闭蓝牙，没有发现弹出对话框关闭蓝牙的
//                Intent intent = new Intent(Settings.ACTION_BLUETOOTH_SETTINGS);
//                activity.startActivityForResult(intent,CLOSE_BLUETOOTH_REQUEST_CODE);
            }


        }

        return isClose;

    }

    /**
     * 获取可用设备
     *
     * @return 返回蓝牙状态
     */
    public MutableLiveData<String> getUsableList() {
        isUsable.postValue("无");

        return isUsable;

    }

    /**
     * 获取匹配设备
     *
     * @return 返回蓝牙状态
     */
    public MutableLiveData<String> getPairedList() {
        isPaired.postValue("无");
        return isPaired;

    }


}
