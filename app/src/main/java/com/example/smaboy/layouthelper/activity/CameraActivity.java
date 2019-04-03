package com.example.smaboy.layouthelper.activity;

import android.app.Activity;
import android.hardware.Camera;
import android.os.Bundle;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.example.smaboy.layouthelper.R;
import com.example.smaboy.layouthelper.view.CameraPreview;

/**
 * 类名: CameraActivity
 * 类作用描述: java类作用描述
 * 作者: Administrator
 * 创建时间: 2019/3/26 13:52
 */
public class CameraActivity extends Activity {

    @BindView(R.id.sv_surface)
    CameraPreview svSurface;
    @BindView(R.id.b_open)
    Button bOpen;
    @BindView(R.id.b_close)
    Button bClose;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_camera);
        ButterKnife.bind(this);

    }

    @Override
    protected void onResume() {
        super.onResume();


    }

    @OnClick({R.id.b_open, R.id.b_close})
    public void onViewClicked(View view) {

        switch (view.getId()) {
            case R.id.b_open:
                svSurface.init(Camera.open(0));
                svSurface.open();
                break;
            case R.id.b_close:
                svSurface.close();
                break;
        }
    }


}
