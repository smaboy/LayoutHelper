package com.example.smaboy.layouthelper.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.example.smaboy.layouthelper.R;
import com.example.smaboy.layouthelper.base.BaseActivity;
import com.example.smaboy.layouthelper.util.DisplayUtils;
import com.example.smaboy.layouthelper.view.MyFlowLayout;

/**
 * 类名: FlowActivity
 * 类作用描述: java类作用描述
 * 作者: Smaboy
 * 创建时间: 2018/12/20 14:49
 */
public class FlowActivity extends BaseActivity implements View.OnClickListener {

    private MyFlowLayout myflowlayout;
    private Button left;
    private Button right;
    private Button center;
    private Button add;
    private Button delete;
    private Button delete_all;
    private EditText et_add;
    private TextView title;
    private TextView content;
    private TextView login;

    @Override
    public void onClick(View v) {
        int childCount = myflowlayout.getChildCount();
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
            case R.id.add :
                if(TextUtils.isEmpty(et_add.getText().toString())) {
                    Toast.makeText(FlowActivity.this, "请输入内容进行添加", Toast.LENGTH_SHORT).show();
                    return;
                }
                TextView textView = new TextView(this);
                textView.setText(et_add.getText().toString());
                textView.setBackgroundResource(R.drawable.shaped);
                ViewGroup.MarginLayoutParams marginLayoutParams = new ViewGroup.MarginLayoutParams(ViewGroup.MarginLayoutParams.WRAP_CONTENT, ViewGroup.MarginLayoutParams.WRAP_CONTENT);
                marginLayoutParams.setMargins(DisplayUtils.dp2px(this,10),DisplayUtils.dp2px(this,10),DisplayUtils.dp2px(this,10),DisplayUtils.dp2px(this,10));
                textView.setPadding(DisplayUtils.dp2px(this,5),DisplayUtils.dp2px(this,5),DisplayUtils.dp2px(this,5),DisplayUtils.dp2px(this,5));
                textView.setLayoutParams(marginLayoutParams);




                myflowlayout.addView(textView);
                break;
            case R.id.delete :
                if(childCount<=0) {
                    Toast.makeText(FlowActivity.this, "已经没有子view咯，请添加后再进行删除吧", Toast.LENGTH_SHORT).show();
                    return;
                }
                myflowlayout.removeViewAt(childCount-1);
                break;
            case R.id.delete_all :
                if(childCount<=0) {
                    Toast.makeText(FlowActivity.this, "已经没有子view咯，请添加后再进行删除吧", Toast.LENGTH_SHORT).show();
                    return;
                }
                myflowlayout.removeAllViews();
                break;
        }

    }


    @Override
    public int getLayoutViewId() {
        return R.layout.activity_flow;
    }

    @Override
    public void initViewId() {
        left = findViewById(R.id.left);
        right = findViewById(R.id.right);
        center = findViewById(R.id.center);
        add = findViewById(R.id.add);
        delete = findViewById(R.id.delete);
        delete_all = findViewById(R.id.delete_all);
        et_add = findViewById(R.id.et_add);
        myflowlayout = findViewById(R.id.myflowlayout);

        left.setOnClickListener(this);
        right.setOnClickListener(this);
        center.setOnClickListener(this);
        add.setOnClickListener(this);
        delete.setOnClickListener(this);
        delete_all.setOnClickListener(this);

    }

    @Override
    public void setData() {
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
            public boolean onLongClick(View v, final int position) {

                showDeleteDialog(position);

                return false;
            }
        });
    }

    /**
     * 显示长按删除弹窗
     * @param position 位置
     */
    @SuppressLint("SetTextI18n")
    private void showDeleteDialog(int position) {
        final Dialog dialog=new Dialog(FlowActivity.this, R.style.Dialog_Fullscreen);
//                final Dialog dialog=new Dialog(FlowActivity.this);
        dialog.setContentView(R.layout.sign_up_dialog);
        title = dialog.findViewById(R.id.title);
        content = dialog. findViewById(R.id.content);
        login = dialog.findViewById(R.id.login);

        //设置数据
        title.setText("删除提示");
        content.setText("您确定要删除该项吗？删除后不可恢复，那您可以通过添加按钮添加子view进来，不过添加进来的子view的样式是被固定的，如果您知晓请按确认键进行删除该view");
        login.setText("确定");
        //设置监听
        login.setOnClickListener(v -> {
            myflowlayout.removeViewAt(position);
            if(dialog.isShowing()) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }
}
