package com.example.smaboy.layouthelper.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.lifecycle.ViewModelProvider;

import com.example.smaboy.layouthelper.R;
import com.example.smaboy.layouthelper.base.BaseActivity;
import com.example.smaboy.layouthelper.util.DisplayUtils;
import com.example.smaboy.layouthelper.view.MyFlowLayout;
import com.example.smaboy.layouthelper.viewmodel.FlowActivityViewModule;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import butterknife.ButterKnife;

/**
 * 类名: FlowActivity
 * 类作用描述: java类作用描述
 * 作者: Smaboy
 * 创建时间: 2018/12/20 14:49
 */
public class FlowActivity extends BaseActivity<FlowActivityViewModule> implements View.OnClickListener {

    private MyFlowLayout myflowlayout;
    private ScrollView sv_scroll;
    private ImageView iv_woman;
    private Button left;
    private Button right;
    private Button center;
    private Button add;
    private Button delete;
    private Button delete_all;
    private EditText et_add;

    @Override
    public int getLayoutViewId() {
        return R.layout.activity_flow;
    }

    @Override
    public void init(@Nullable Bundle savedInstanceState) {
        sv_scroll = findViewById(R.id.sv_scroll);
        iv_woman = findViewById(R.id.iv_woman);
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
        //处理沉浸式状态栏，基类中状态栏导航栏默认是透明的
        getBar()
                .invasionStatusBar()
                .navigationBarBackground(getResources().getColor(android.R.color.holo_orange_dark));

        //设置监听
        myflowlayout.setOnChildViewClickListener(new MyFlowLayout.OnChildViewClickListener() {
            @Override
            public void onClick(View v, int position) {

                try {
                    TextView textView = (TextView) v;
                    textView.setText("我被改变了");
                } catch (Exception e) {
                    Toast.makeText(FlowActivity.this, "不是TextView类型不能做相关操作", Toast.LENGTH_SHORT).show();
                }

                Toast.makeText(FlowActivity.this, "您点击了我" + position, Toast.LENGTH_SHORT).show();
            }

            @Override
            public boolean onLongClick(View v, final int position) {

                getViewModel().showDeleteDialog(FlowActivity.this, myflowlayout, position);


                return false;
            }
        });
    }

    @Override
    public void onClick(View v) {
        int childCount = myflowlayout.getChildCount();
        switch (v.getId()) {
            case R.id.left:

                myflowlayout.setGravity(MyFlowLayout.LEFT);
                break;

            case R.id.right:
                myflowlayout.setGravity(MyFlowLayout.RIGHT);
                break;
            case R.id.center:
                myflowlayout.setGravity(MyFlowLayout.CENTER);
                break;
            case R.id.add:
                if (TextUtils.isEmpty(et_add.getText().toString())) {
                    Toast.makeText(FlowActivity.this, "请输入内容进行添加", Toast.LENGTH_SHORT).show();
                    return;
                }
                TextView textView = new TextView(this);
                textView.setText(et_add.getText().toString());
                textView.setBackgroundResource(R.drawable.shaped);
                ViewGroup.MarginLayoutParams marginLayoutParams = new ViewGroup.MarginLayoutParams(ViewGroup.MarginLayoutParams.WRAP_CONTENT, ViewGroup.MarginLayoutParams.WRAP_CONTENT);
                marginLayoutParams.setMargins(DisplayUtils.dp2px(this, 10), DisplayUtils.dp2px(this, 10), DisplayUtils.dp2px(this, 10), DisplayUtils.dp2px(this, 10));
                textView.setPadding(DisplayUtils.dp2px(this, 5), DisplayUtils.dp2px(this, 5), DisplayUtils.dp2px(this, 5), DisplayUtils.dp2px(this, 5));
                textView.setLayoutParams(marginLayoutParams);


                myflowlayout.addView(textView);
                break;
            case R.id.delete:
                if (childCount <= 0) {
                    Toast.makeText(FlowActivity.this, "已经没有子view咯，请添加后再进行删除吧", Toast.LENGTH_SHORT).show();
                    return;
                }
                myflowlayout.removeViewAt(childCount - 1);
                break;
            case R.id.delete_all:
                if (childCount <= 0) {
                    Toast.makeText(FlowActivity.this, "已经没有子view咯，请添加后再进行删除吧", Toast.LENGTH_SHORT).show();
                    return;
                }
                myflowlayout.removeAllViews();
                break;
        }

    }


    @NotNull
    @Override
    public Class<FlowActivityViewModule> initViewModel() {

        return FlowActivityViewModule.class;
    }


    /**
     * 设置观察者
     */
    @Override
    protected void setObserver() {
        super.setObserver();

    }
}
