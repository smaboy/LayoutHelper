package com.example.smaboy.layouthelper.receiver;

import android.bluetooth.BluetoothAdapter;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * <ur>
 * <li> 类名: BluetoothReceiver</li>
 * <li> 类作用描述: java类作用描述 </li>
 * <li> 页面名称: 页面描述 </li>
 * <li> 作者: <a href="mailto:liyongliang3@ceair.com">Li Yongliang</a></li>
 * <li> 创建时间: 2019/8/30 17:07</li>
 * </ur>
 */
public class BluetoothReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if (null == intent || null == intent.getAction()) {
            return;
        }
        if (BluetoothAdapter.ACTION_STATE_CHANGED.equals(intent.getAction())) {
            int blueState = intent.getIntExtra(BluetoothAdapter.EXTRA_STATE, 0);
            switch (blueState) {
                case BluetoothAdapter.STATE_TURNING_ON:
                    Log.e("TAG", "TURNING_ON");
                    break;
                case BluetoothAdapter.STATE_ON:
                    Log.e("TAG", "STATE_ON");
                    break;
                case BluetoothAdapter.STATE_TURNING_OFF:
                    Log.e("TAG", "STATE_TURNING_OFF");
                    break;
                case BluetoothAdapter.STATE_OFF:
                    Log.e("TAG", "STATE_OFF");
                    break;
            }
        }
    }
}
