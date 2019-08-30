package com.example.smaboy.layouthelper.viewmodel;

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

    public BluetoothViewModel() {
        //初始化操作
        isOpen=new MutableLiveData<>();
        isClose=new MutableLiveData<>();
        isPaired=new MutableLiveData<>();
        isUsable=new MutableLiveData<>();

    }

    /**
     * 开启蓝牙
     * @return 返回蓝牙状态
     */
    public MutableLiveData<String> openBluetooth() {
        isOpen.postValue("已开启");

        return isOpen;

    }

    /**
     * 关闭蓝牙
     * @return 返回蓝牙状态
     */
    public MutableLiveData<String> closeBluetooth() {
        isClose.postValue("已关闭");

        return isClose;

    }
    /**
     * 获取可用设备
     * @return 返回蓝牙状态
     */
    public MutableLiveData<String> getUsableList() {
        isUsable.postValue("无");

        return isUsable;

    }
    /**
     * 获取匹配设备
     * @return 返回蓝牙状态
     */
    public MutableLiveData<String> getPairedList() {
        isPaired.postValue("无");
        return isPaired;

    }




}
