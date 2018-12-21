package com.example.smaboy.layouthelper.view;

import android.content.Context;
import android.graphics.Color;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.MainThread;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.support.spring.annotation.FastJsonView;
import com.example.smaboy.layouthelper.Entity.UserInfoGroup;
import com.example.smaboy.layouthelper.R;
import com.example.smaboy.layouthelper.activity.QuickActivity;
import com.example.smaboy.layouthelper.adapter.MyRecyclerViewAdapter;
import com.example.smaboy.layouthelper.util.DataUtils;

/**
 * 类名: QuickIndexView
 * 类作用描述: 联系人页面类型
 *            1.提供黏性头部（可控，支持开关）
 * 作者: Smaboy
 * 创建时间: 2018/12/14 16:33
 */
public class QuickIndexView extends LinearLayout {
    private Context context;
    private RecyclerView recyclerView;
    private TextView tv_notice;
    private QuicklIndexBar quickIndex;
    private UserInfoGroup userInfoGroup;

    public QuickIndexView(Context context) {
        this(context, null);
    }

    public QuickIndexView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }
    private Handler handler=new Handler(Looper.getMainLooper());

    public QuickIndexView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        this.context = context;

        init();
    }

    private void init() {
        View inflate = LayoutInflater.from(context).inflate(R.layout.quick_index_view, this, false);

        initData();

        initView(inflate);


        addView(inflate);
    }

    private void initData() {
        //制作假数据
        new Thread(new Runnable() {
            @Override
            public void run() {
                String json = DataUtils.getJsonFromAsset(getContext(), "userinfo.json");
                Log.e("TA", json);
                userInfoGroup = JSONObject.parseObject(json, UserInfoGroup.class);

                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        setContentData();

                    }
                });
            }
        }).start();

     

    }

    private void setContentData() {
        if(userInfoGroup==null) {
            return;
        }
        //设置数据
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.addItemDecoration(new StickyDecoration());
        recyclerView.setAdapter(new MyRecyclerViewAdapter(getContext(),userInfoGroup));
    }

    /**
     * 初始化view
     *
     * @param inflate view
     */
    private void initView(View inflate) {

        recyclerView = inflate.findViewById(R.id.recycler);
        tv_notice = inflate.findViewById(R.id.tv_notice);
        quickIndex = inflate.findViewById(R.id.quickIndex);

        //设置索引数据
        quickIndex.setData(new String[]{"☆", "A", "B", "C", "D"
                , "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T",
                "U", "V", "W", "X", "Y", "Z", "#"});
//        quickIndex.setData(new String[]{ "L", "M", "N", "O", "P", "Q", "R", "S", "T",
//                "U", "V", "W", "X", "Y", "Z", "#"});

        //设置索引监听
        quickIndex.setOnFocusChangeStatusListener(new QuicklIndexBar.OnFocusChangeStatusListener() {
            @Override
            public void onItemClick(int index, String indexString) {
                tv_notice.setText(indexString);
                tv_notice.setVisibility(View.VISIBLE);

//                quickIndex.setBackgroundColor(Color.RED);
            }

            @Override
            public void onScroll(int index, String indexString) {

                if (tv_notice.getVisibility() != View.VISIBLE) {
                    tv_notice.setVisibility(View.VISIBLE);
                }
                tv_notice.setText(indexString);


            }

            @Override
            public void onLoseFoucus() {
                postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        tv_notice.setVisibility(View.GONE);
                    }
                }, 1000);
//                quickIndex.setBackgroundColor(Color.TRANSPARENT);

            }
        });


        //设置内容数据



    }

}
