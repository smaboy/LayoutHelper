package com.example.smaboy.layouthelper.view;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.example.smaboy.layouthelper.R;

/**
 * 类名: QuickIndexView
 * 类作用描述: java类作用描述
 * 作者: Smaboy
 * 创建时间: 2018/12/14 16:33
 */
public class QuickIndexView extends LinearLayout {
    private Context context;

    public QuickIndexView(Context context) {
        this(context,null);
    }

    public QuickIndexView(Context context,  AttributeSet attrs) {
        this(context, attrs,0);
    }

    public QuickIndexView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        this.context=context;

        init();
    }

    private void init() {
        View inflate = LayoutInflater.from(context).inflate(R.layout.quick_index_view, this,false);

        initView(inflate);



        addView(inflate);
    }

    /**
     *初始化view
     *
     * @param inflate view
     */
    private void initView(View inflate) {

        RecyclerView recyclerView = inflate.findViewById(R.id.recycler);
        final TextView tv_notice = inflate.findViewById(R.id.tv_notice);
        QuicklIndexBar quickIndex = inflate.findViewById(R.id.quickIndex);

        //设置索引数据
        quickIndex.setData(new String[]{"☆","A", "B", "C", "D"
                , "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T",
                "U", "V", "W", "X", "Y", "Z","#"});

        //设置索引监听
        quickIndex.setOnFocusChangeStatusListener(new QuicklIndexBar.OnFocusChangeStatusListener() {
            @Override
            public void onItemClick(int index, String indexString) {
                tv_notice.setText(indexString);
                tv_notice.setVisibility(View.VISIBLE);
            }

            @Override
            public void onScroll(int index, String indexString) {

                if(tv_notice.getVisibility()!=View.VISIBLE) {
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
                },1000);

            }
        });


        //设置内容数据



    }

}
