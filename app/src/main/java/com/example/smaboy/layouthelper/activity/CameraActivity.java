package com.example.smaboy.layouthelper.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.smaboy.layouthelper.R;
import com.example.smaboy.layouthelper.base.BaseActivity;
import com.example.smaboy.layouthelper.view.CameraPreview;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 类名: CameraActivity
 * 类作用描述: java类作用描述
 * 作者: Administrator
 * 创建时间: 2019/3/26 13:52
 */
public class CameraActivity extends BaseActivity {
    @BindView(R.id.sv_surface)
    CameraPreview svSurface;
    @BindView(R.id.b_open)
    Button bOpen;
    @BindView(R.id.b_close)
    Button bClose;

    @Override
    public int getLayoutViewId() {
        return R.layout.activity_camera;
    }

    @Override
    public void init(@Nullable Bundle savedInstanceState) {

    }

    @Override
    public void setData() {
        svSurface.setVisibility(View.GONE);

        getBar()
                .invasionStatusBar()
                .invasionNavigationBar();

    }

    @OnClick({R.id.b_open, R.id.b_close})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.b_open:
                Toast.makeText(CameraActivity.this, "您打开了我", Toast.LENGTH_SHORT).show();
                break;
            case R.id.b_close:
                Toast.makeText(CameraActivity.this, "您关闭了我", Toast.LENGTH_SHORT).show();
                break;
        }
    }



    @NotNull
    @Override
    public Class initViewModel() {

        return getClass();
    }
}
