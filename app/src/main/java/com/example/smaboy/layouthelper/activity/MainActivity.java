package com.example.smaboy.layouthelper.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.example.smaboy.layouthelper.R;
import com.example.smaboy.layouthelper.view.MyFlowLayout;

/**
 * 类名: MainActivity
 * 类作用描述: java类作用描述
 * 作者: Smaboy
 * 创建时间: 2018/11/30 12:45
 */
public class MainActivity extends Activity implements View.OnClickListener {


    private Button flow;
    private Button quick;
    private Button stick;
    private Button menu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);


        flow = findViewById(R.id.flow);
        quick = findViewById(R.id.quick);
        stick = findViewById(R.id.stick);
        menu = findViewById(R.id.menu);

        flow.setOnClickListener(this);
        quick.setOnClickListener(this);
        stick.setOnClickListener(this);
        menu.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.flow :

                startActivity(new Intent(MainActivity.this,FlowActivity.class));
                break;

            case R.id.quick :
                startActivity(new Intent(MainActivity.this,QuickActivity.class));
                break;
            case R.id.stick :
                break;
            case R.id.menu :
                startActivity(new Intent(MainActivity.this,DataActivity.class));

                break;
        }

    }
}