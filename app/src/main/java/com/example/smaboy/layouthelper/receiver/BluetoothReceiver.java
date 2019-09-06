package com.example.smaboy.layouthelper.receiver;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;

import com.example.smaboy.layouthelper.Entity.MessageEvent;

import org.greenrobot.eventbus.EventBus;

/**
 * <ur>
 * <li> 类名: BluetoothReceiver</li>
 * <li> 类作用描述: 全局监听蓝牙的改变状态 </li>
 * <li> 页面名称: 页面描述 </li>
 * <li> 作者: <a href="mailto:liyongliang3@ceair.com">Li Yongliang</a></li>
 * <li> 创建时间: 2019/8/30 17:07</li>
 * </ur>
 */
public class BluetoothReceiver extends BroadcastReceiver {

    private StringBuilder stringBuilder=new StringBuilder();
    @Override
    public void onReceive(Context context, Intent intent) {
        if (null == intent || null == intent.getAction()) {
            return;
        }
        if (BluetoothAdapter.ACTION_STATE_CHANGED.equals(intent.getAction())) {
            int blueState = intent.getIntExtra(BluetoothAdapter.EXTRA_STATE, 0);
            switch (blueState) {
                case BluetoothAdapter.STATE_TURNING_ON:
                    EventBus.getDefault().post(new MessageEvent(102,0,"蓝牙正在开启"));
                    Log.e("TAG", "TURNING_ON");
                    break;
                case BluetoothAdapter.STATE_ON:

                    EventBus.getDefault().post(new MessageEvent(102,0,"蓝牙开启成功"));
                    Log.e("TAG", "STATE_ON");
                    break;
                case BluetoothAdapter.STATE_TURNING_OFF:
                    EventBus.getDefault().post(new MessageEvent(102,0,"蓝牙正在关闭"));
                    Log.e("TAG", "STATE_TURNING_OFF");
                    break;
                case BluetoothAdapter.STATE_OFF:
                    EventBus.getDefault().post(new MessageEvent(102,0,"蓝牙关闭成功"));
                    Log.e("TAG", "STATE_OFF");
                    break;
            }
        } else if (BluetoothDevice.ACTION_FOUND.equals(intent.getAction())) {//发现设备的广播
            // 从intent中获取设备
            BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
            // 判断是否配对过
            if (device.getBondState() != BluetoothDevice.BOND_BONDED) {//没有匹配过
                // 添加到列表
                if(!TextUtils.isEmpty(device.getName())) {//过滤掉没有名称的蓝牙设备
                    MessageEvent messageEvent = new MessageEvent(100);
                    messageEvent.setStr(device.getName()+"\n"+device.getAddress()+"\n\n");
                    EventBus.getDefault().post(messageEvent);
                }

//                Log.e("TAG", "设备为：\n"+ tvDevices);
            }
            // 搜索完成
        } else if (BluetoothAdapter.ACTION_DISCOVERY_FINISHED.equals(intent.getAction())) {
            MessageEvent messageEvent = new MessageEvent(101);
            messageEvent.setStr("扫描完成");
            EventBus.getDefault().post(messageEvent);
            Log.e("TAG", "搜索完成");
        }

    }
}
