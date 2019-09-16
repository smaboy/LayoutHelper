package com.example.smaboy.layouthelper.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.smaboy.layouthelper.R;
import com.example.smaboy.layouthelper.base.BaseActivity;

import org.jetbrains.annotations.Nullable;

import java.util.Timer;
import java.util.TimerTask;

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
    private LinearLayout ll_button_group;
    private boolean mBackKeyPressed;


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
        ll_button_group = findViewById(R.id.ll_button_group);

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

        //添加布局动画
        startAnimation();


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

    public void startAnimation(){

        /*方法一*/
        LayoutAnimationController layoutAnimationController = AnimationUtils.loadLayoutAnimation(this, R.anim.layout_animation_fade_in);
        ll_button_group.setLayoutAnimation(layoutAnimationController);
        ll_button_group.startLayoutAnimation();

        /*方法二*/
//        Animation animation = AnimationUtils.loadAnimation(this, R.anim.fade_in);
//        LayoutAnimationController layoutAnimationController1 = new LayoutAnimationController(animation);
//        layoutAnimationController1.setDelay(0.5f);
//        layoutAnimationController1.setOrder(LayoutAnimationController.ORDER_NORMAL);
//        layoutAnimationController1.setInterpolator(new AccelerateDecelerateInterpolator());
//        ll_button_group.setLayoutAnimation(layoutAnimationController1);
//        ll_button_group.startLayoutAnimation();


    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            if (!mBackKeyPressed) {
                Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
                mBackKeyPressed = true;
                new Timer().schedule(new TimerTask() {
                    @Override
                    public void run() {
                        mBackKeyPressed = false;
                    }
                }, 2000);
                return true;
            } else {
                finish();
            }
        }

        return super.onKeyDown(keyCode, event);

    }
}
