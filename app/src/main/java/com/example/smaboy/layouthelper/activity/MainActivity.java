package com.example.smaboy.layouthelper.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.Toast;

import com.example.smaboy.layouthelper.Entity.responses.HomeArticleListResp;
import com.example.smaboy.layouthelper.R;
import com.example.smaboy.layouthelper.api.HomeService;
import com.example.smaboy.layouthelper.databinding.ActivityMainBinding;
import com.smaboy.lib_http.HttpClient;
import com.smaboy.lib_http.utils.LogUtil;
import com.trello.rxlifecycle3.components.support.RxFragmentActivity;
import com.yanzhenjie.sofia.Sofia;

import java.util.Timer;
import java.util.TimerTask;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

/**
 * 类名: MainActivity
 * 类作用描述: java类作用描述
 * 作者: Smaboy
 * 创建时间: 2018/11/30 12:45
 */
public class MainActivity extends RxFragmentActivity implements View.OnClickListener {


    private boolean mBackKeyPressed;
    private ActivityMainBinding binding;


    @Override
    protected void onCreate(@androidx.annotation.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.flow.setOnClickListener(this);
        binding.quick.setOnClickListener(this);
        binding.stick.setOnClickListener(this);
        binding.menu.setOnClickListener(this);
        binding.customPhoto.setOnClickListener(this);
        binding.h5Page.setOnClickListener(this);
        binding.materialPage.setOnClickListener(this);
        binding.skeleton.setOnClickListener(this);
        binding.bluetoothPage.setOnClickListener(this);


        //处理沉浸式状态栏，基类中状态栏导航栏默认是透明的
        Sofia.with(this)
                .statusBarDarkFont()//状态栏深色字体
                .statusBarBackgroundAlpha(0)//状态栏透明度为0
                .navigationBarBackgroundAlpha(0)//导航栏透明度为0
                .invasionStatusBar()
                .statusBarBackground(getResources().getColor(android.R.color.holo_orange_dark))
                .navigationBarBackground(getResources().getColor(android.R.color.holo_orange_dark));
//            .invasionStatusBar()//内容入侵状态栏
//            .invasionNavigationBar()//内容入侵导航栏

        //添加布局动画
        startAnimation();
    }


    @SuppressLint("NonConstantResourceId")
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
//                Toast.makeText(MainActivity.this, "我是骨架屏", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(Intent.ACTION_VIEW,Uri.parse("smaboy://com.example.test:8888/myTest")));
                break;
            case R.id.custom_photo://自定义拍照
                startActivity(new Intent(MainActivity.this, CameraActivity.class));
                break;
            case R.id.h5_page://h5页面

                Toast.makeText(MainActivity.this, "我是h5页面", Toast.LENGTH_SHORT).show();
                break;
            case R.id.material_page://material页面
                Toast.makeText(MainActivity.this, "我是material页面", Toast.LENGTH_SHORT).show();


                break;

            case R.id.bluetooth_page://material页面
                startActivity(new Intent(MainActivity.this, BluetoothActivity.class));
//                HomeService homeService = HttpClient.Companion.getInstance().getClient().create(HomeService.class);
//
//                homeService.getHomeArticleList(0)
//                        .observeOn(Schedulers.io())
//                        .observeOn(AndroidSchedulers.mainThread())
//                        .subscribe(new Observer<HomeArticleListResp>() {
//                            @Override
//                            public void onSubscribe(@NonNull Disposable d) {
//
//                            }
//
//                            @Override
//                            public void onNext(@NonNull HomeArticleListResp homeArticleListResp) {
//                                LogUtil.d(this,homeArticleListResp.toString());
//
//                            }
//
//                            @Override
//                            public void onError(@NonNull Throwable e) {
//
//                            }
//
//                            @Override
//                            public void onComplete() {
//
//                            }
//                        });
//
//                break;
        }

    }

    public void startAnimation(){

        /*方法一*/
        LayoutAnimationController layoutAnimationController = AnimationUtils.loadLayoutAnimation(this, R.anim.layout_animation_fade_in);
        binding.llButtonGroup.setLayoutAnimation(layoutAnimationController);
        binding.llButtonGroup.startLayoutAnimation();

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
