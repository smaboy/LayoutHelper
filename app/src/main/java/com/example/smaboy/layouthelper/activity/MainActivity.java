package com.example.smaboy.layouthelper.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.smaboy.layouthelper.R;
import com.example.smaboy.layouthelper.base.BaseActivity;

import org.jetbrains.annotations.Nullable;

/**
 * 类名: MainActivity
 * 类作用描述: java类作用描述
 * 作者: Smaboy
 * 创建时间: 2018/11/30 12:45
 */
public class MainActivity extends BaseActivity implements View.OnClickListener {


    private LinearLayout ll_title;
    private TextView tv_title;
    private Button flow;
    private Button quick;
    private Button stick;
    private Button menu;
    private Button custom_photo;
    private Button h5_page;
    private Button material_page;
    private Button skeleton;
    private Button bluetooth_page;



    @Override
    public int getLayoutViewId() {
        return R.layout.activity_main;
    }

    @Override
    public void init(@Nullable Bundle savedInstanceState) {
        ll_title = findViewById(R.id.ll_title);
        tv_title = findViewById(R.id.tv_title);
        flow = findViewById(R.id.flow);
        quick = findViewById(R.id.quick);
        stick = findViewById(R.id.stick);
        menu = findViewById(R.id.menu);
        custom_photo = findViewById(R.id.custom_photo);
        h5_page = findViewById(R.id.h5_page);
        material_page = findViewById(R.id.material_page);
        skeleton = findViewById(R.id.skeleton);
        bluetooth_page = findViewById(R.id.bluetooth_page);

        flow.setOnClickListener(this);
        quick.setOnClickListener(this);
        stick.setOnClickListener(this);
        menu.setOnClickListener(this);
        custom_photo.setOnClickListener(this);
        h5_page.setOnClickListener(this);
        material_page.setOnClickListener(this);
        skeleton.setOnClickListener(this);
        bluetooth_page.setOnClickListener(this);
    }

    @Override
    public void setData() {

        //处理沉浸式状态栏，基类中状态栏导航栏默认是透明的
        bar
                .statusBarBackground(getResources().getDrawable(android.R.color.holo_orange_dark))
                .navigationBarBackground(getResources().getColor(android.R.color.holo_orange_dark));

    }


    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.flow:

                startActivity(new Intent(MainActivity.this, FlowActivity.class));
                break;

            case R.id.quick:
                startActivity(new Intent(MainActivity.this, QuickActivity.class));
                break;
            case R.id.stick:
                startActivity(new Intent(MainActivity.this, StickyActivity.class));
                break;
            case R.id.menu:
                startActivity(new Intent(MainActivity.this, DataActivity.class));

                break;
            case R.id.skeleton://骨架屏

                Toast.makeText(MainActivity.this, "我是骨架屏", Toast.LENGTH_SHORT).show();
                break;
            case R.id.custom_photo://自定义拍照
                startActivity(new Intent(MainActivity.this, CameraActivity.class));
                break;
            case R.id.h5_page://h5页面

                Toast.makeText(MainActivity.this, "我是h5页面", Toast.LENGTH_SHORT).show();
                break;
            case R.id.material_page://material页面
                Toast.makeText(MainActivity.this, "我是material页面", Toast.LENGTH_SHORT).show();

            case R.id.bluetooth_page://material页面
                startActivity(new Intent(MainActivity.this, BluetoothActivity.class));

                break;
        }

    }
}
