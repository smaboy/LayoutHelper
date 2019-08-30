package com.example.smaboy.layouthelper.viewmodel;

import android.bluetooth.BluetoothAdapter;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.smaboy.layouthelper.receiver.BluetoothReceiver;

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
        BluetoothAdapter bTAdatper = BluetoothAdapter.getDefaultAdapter();
        
        if(null==bTAdatper) {
            isOpen.postValue("当前设备不支持蓝牙功能");
        }else {
            if(!bTAdatper.isEnabled()){//蓝牙处于关闭状态
           /* Intent i = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivity(i);*/
                bTAdatper.enable();
                isOpen.postValue("正在开启中");
                if(bTAdatper.isEnabled()){
                    isOpen.postValue("开启成功");
                } else {
                    isOpen.postValue("开启失败");
                }

            }else {//蓝牙处于开启状态
                isOpen.postValue("蓝牙处于开启状态");
            }


        }

        return isOpen;

    }

    /**
     * 关闭蓝牙
     * @return 返回蓝牙状态
     */
    public MutableLiveData<String> closeBluetooth() {
        BluetoothAdapter bTAdatper = BluetoothAdapter.getDefaultAdapter();

        if(null==bTAdatper) {
            isClose.postValue("当前设备不支持蓝牙功能");
        }else {
            if(!bTAdatper.isEnabled()){//蓝牙处于关闭状态
                isClose.postValue("蓝牙处于关闭状态");
            }else {//蓝牙处于开启状态
                bTAdatper.disable();
                isClose.postValue("正在关闭中");
                if(bTAdatper.isEnabled()){
                    isOpen.postValue("关闭失败");
                } else {
                    isOpen.postValue("关闭成功");
                }
            }


        }

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
