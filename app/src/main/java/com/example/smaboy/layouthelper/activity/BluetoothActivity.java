package com.example.smaboy.layouthelper.activity;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.smaboy.layouthelper.Entity.MessageEvent;
import com.example.smaboy.layouthelper.R;
import com.example.smaboy.layouthelper.base.BaseActivity;
import com.example.smaboy.layouthelper.receiver.BluetoothReceiver;
import com.example.smaboy.layouthelper.viewmodel.BluetoothViewModel;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.jetbrains.annotations.Nullable;

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
public class BluetoothActivity extends BaseActivity {
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

    private BluetoothViewModel model;


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
        model= ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication()).create(BluetoothViewModel.class);

        //设置监听
        registerBoradcastReceiver();

    }

    @Override
    public void setData() {

    }

    @OnClick({R.id.b_open, R.id.b_close, R.id.b_usable_list, R.id.b_paired_list})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.b_open://开启蓝牙
                model.openBluetooth(this).observe(this, s -> tvBluetoothOpenOrCloseStatus.setText(s));

                break;
            case R.id.b_close://关闭蓝牙
                model.closeBluetooth(this).observe(this, s -> tvBluetoothOpenOrCloseStatus.setText(s));

                break;
            case R.id.b_usable_list://获取可用列表
                model.getUsableList().observe(this, s -> tvUsableList.setText(s));

                break;
            case R.id.b_paired_list://获取配对列表
                model.getPairedList().observe(this,s -> tvPairedList.setText(s));

                break;
        }
    }

    /**
     * 接收事件总线发来的消息
     * @param event
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(MessageEvent event) {

        if(null!=event) {
           tvBluetoothOpenOrCloseStatus.setText(event.getArg1());
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

        BluetoothReceiver bluetoothReceiver = new BluetoothReceiver();
        registerReceiver(bluetoothReceiver, stateChangeFilter);
        registerReceiver(bluetoothReceiver, connectedFilter);
        registerReceiver(bluetoothReceiver, disConnectedFilter);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @androidx.annotation.Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==BluetoothViewModel.OPEN_BLUETOOTH_REQUEST_CODE) {//开启蓝牙的回调
            switch (resultCode) {
                case RESULT_CANCELED :
                    tvBluetoothOpenOrCloseStatus.setText("请求开启蓝牙失败");
//                    Log.e("TAG", "开启蓝牙-RESULT_CANCELED");
                    break;
                case RESULT_FIRST_USER :
//                    Log.e("TAG", "开启蓝牙-RESULT_FIRST_USER");
                    break;
                case RESULT_OK :
                    tvBluetoothOpenOrCloseStatus.setText("请求开启蓝牙成功");
//                    Log.e("TAG", "开启蓝牙-RESULT_OK");

                    break;
            }
        }

    }
}
