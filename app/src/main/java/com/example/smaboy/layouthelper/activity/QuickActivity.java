package com.example.smaboy.layouthelper.activity;

import android.os.Bundle;

import com.example.smaboy.layouthelper.R;
import com.example.smaboy.layouthelper.base.BaseActivity;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * 类名: SecondActivity
 * 类作用描述: java类作用描述
 * 作者: Smaboy
 * 创建时间: 2018/12/14 15:43
 */
public class QuickActivity extends BaseActivity {

    @Override
    public int getLayoutViewId() {
        return R.layout.activity_quick;
    }

    @Override
    public void init(@Nullable Bundle savedInstanceState) {

    }

    @Override
    public void setData() {
        getBar().invasionNavigationBar();

    }


    @NotNull
    @Override
    public Class initViewModel() {

        return getClass();
    }
}
