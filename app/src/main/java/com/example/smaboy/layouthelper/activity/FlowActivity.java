package com.example.smaboy.layouthelper.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.example.smaboy.layouthelper.R;
import com.example.smaboy.layouthelper.view.MyFlowLayout;

/**
 * 类名: FlowActivity
 * 类作用描述: java类作用描述
 * 作者: Smaboy
 * 创建时间: 2018/12/20 14:49
 */
public class FlowActivity extends Activity implements View.OnClickListener {

    private MyFlowLayout myflowlayout;
    private Button left;
    private Button right;
    private Button center;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_flow);

        left = findViewById(R.id.left);
        right = findViewById(R.id.right);
        center = findViewById(R.id.center);
        myflowlayout = findViewById(R.id.myflowlayout);

        left.setOnClickListener(this);
        right.setOnClickListener(this);
        center.setOnClickListener(this);

        myflowlayout.setOnChildViewClickListener(new MyFlowLayout.OnChildViewClickListener() {
            @Override
            public void onClick(View v, int position) {

                try {
                    TextView textView= (TextView) v;
                    textView.setText("我被改变了");
                } catch (Exception e) {
                    Toast.makeText(FlowActivity.this, "不是TextView类型不能做相关操作", Toast.LENGTH_SHORT).show();
                }

                Toast.makeText(FlowActivity.this, "您点击了我"+position, Toast.LENGTH_SHORT).show();
            }

            @Override
            public boolean onLongClick(View v, int position) {

                Dialog dialog=new Dialog(FlowActivity.this);
                dialog.setContentView(R.layout.sign_up_dialog);

                return false;
            }
        });
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

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}
