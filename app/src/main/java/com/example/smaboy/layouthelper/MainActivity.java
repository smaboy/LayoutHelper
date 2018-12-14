package com.example.smaboy.layouthelper;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/**
 * 类名: MainActivity
 * 类作用描述: java类作用描述
 * 作者: Smaboy
 * 创建时间: 2018/11/30 12:45
 */
public class MainActivity extends Activity implements View.OnClickListener {

    private MyFlowLayout myflowlayout;
    private Button left;
    private Button right;
    private Button center;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        myflowlayout = findViewById(R.id.myflowlayout);

        left = findViewById(R.id.left);
        right = findViewById(R.id.right);
        center = findViewById(R.id.center);

        left.setOnClickListener(this);
        right.setOnClickListener(this);
        center.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.left :

                myflowlayout.setGravity(MyFlowLayout.LEFT);
                break;

            case R.id.right :
                myflowlayout.setGravity(MyFlowLayout.RIGHT);
                break;
            case R.id.center :
                myflowlayout.setGravity(MyFlowLayout.CENTER);
                break;
        }

    }
}
