package com.example.smaboy.layouthelper.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;
import com.example.smaboy.layouthelper.R;
import com.example.smaboy.layouthelper.view.MonthView;

import java.util.Calendar;

/**
 * 类名: DataActivity
 * 类作用描述: 日期选择类
 * 作者: Smaboy
 * 创建时间: 2019/1/9 10:12
 */
public class DataActivity extends Activity implements View.OnClickListener {

    private MonthView month;
    private Button b_show;
    private LinearLayout ll_tools;
    private Button b_today,btn01,btn02,btn03,btn04,btn05,btn06,btn07,btn08,btn09,btn10,btn11,btn12,btn13,btn14,btn15,btn16,btn17;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_data);


        month = findViewById(R.id.month);

//        Calendar instance = Calendar.getInstance();
//        instance.add(Calendar.MONTH, 6);
//        month
//                .setTitleStyle(MonthView.Style.TITLE_CENTER)
//                .setOpenWeek(true)
//                .setMonthStyle(MonthView.Style.MONDAY_STYLE)
//                .setOpenScrollSwitch(true)
//                .setOnDateClicktListener(new MonthView.OnDateClickListener() {
//                    @Override
//                    public void onDateClick(int year, int month, int date) {
//                        Toast.makeText(DataActivity.this, year + "-" + month + "-" + date, Toast.LENGTH_SHORT).show();
//                    }
//                })
//                .setDefSelectedDay(Calendar.getInstance())
//                .setSelectedBackground(R.mipmap.jpyd_date_selected_bg)
//                .setShowLunarStyle(MonthView.Style.LUNAR_HOLIDAY);

        month.setOnDateClicktListener(new MonthView.OnDateClickListener() {
            @Override
            public void onDateClick(int year, int month, int date) {
                Toast.makeText(DataActivity.this, year + "-" + month + "-" + date, Toast.LENGTH_SHORT).show();
            }
        });


        b_show = findViewById(R.id.b_show);
        ll_tools = findViewById(R.id.ll_tools);
        b_today = findViewById(R.id.b_today);
        btn01 = findViewById(R.id.btn01);
        btn02 = findViewById(R.id.btn02);
        btn03 = findViewById(R.id.btn03);
        btn04 = findViewById(R.id.btn04);
        btn05 = findViewById(R.id.btn05);
        btn06 = findViewById(R.id.btn06);
        btn07 = findViewById(R.id.btn07);
        btn08 = findViewById(R.id.btn08);
        btn09 = findViewById(R.id.btn09);
        btn10 = findViewById(R.id.btn10);
        btn11 = findViewById(R.id.btn11);
        btn12 = findViewById(R.id.btn12);
        btn13 = findViewById(R.id.btn13);
        btn14 = findViewById(R.id.btn14);
        btn15 = findViewById(R.id.btn15);
        btn16 = findViewById(R.id.btn16);
        btn17 = findViewById(R.id.btn17);

        b_show.setOnClickListener(this);
        b_today.setOnClickListener(this);
        btn01.setOnClickListener(this);
        btn02.setOnClickListener(this);
        btn03.setOnClickListener(this);
        btn04.setOnClickListener(this);
        btn05.setOnClickListener(this);
        btn06.setOnClickListener(this);
        btn07.setOnClickListener(this);
        btn08.setOnClickListener(this);
        btn09.setOnClickListener(this);
        btn10.setOnClickListener(this);
        btn11.setOnClickListener(this);
        btn12.setOnClickListener(this);
        btn13.setOnClickListener(this);
        btn14.setOnClickListener(this);
        btn15.setOnClickListener(this);
        btn16.setOnClickListener(this);
        btn17.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.b_show :
                if(ll_tools.getVisibility()==View.VISIBLE) {
                    ll_tools.setVisibility(View.GONE);
                }else {
                    ll_tools.setVisibility(View.VISIBLE);
                }

                break;
            case R.id.b_today :
                month.setCalendar(Calendar.getInstance());
                month.setDefSelectedDay(Calendar.getInstance());
                month.invalidate();

                break;
            case R.id.btn01 ://标题显示
            case R.id.btn03 ://标题左对齐
                month.setTitleStyle(MonthView.Style.TITLE_LEFT);
                ll_tools.setVisibility(View.GONE);
                month.invalidate();

                break;
            case R.id.btn02 ://标题隐藏
                month.setTitleStyle(MonthView.Style.NO_TITLE);
                ll_tools.setVisibility(View.GONE);
                month.invalidate();
                break;
            case R.id.btn04 ://标题居中
                month.setTitleStyle(MonthView.Style.TITLE_CENTER);
                ll_tools.setVisibility(View.GONE);
                month.invalidate();
                break;
            case R.id.btn05 ://标题右对齐
                month.setTitleStyle(MonthView.Style.TITLE_RIGHT);
                ll_tools.setVisibility(View.GONE);
                month.invalidate();
                break;
            case R.id.btn06 ://星期标题显示
                month.setOpenWeek(true);
                ll_tools.setVisibility(View.GONE);
                month.invalidate();

                break;
            case R.id.btn07 ://星期标题隐藏
                month.setOpenWeek(false);
                ll_tools.setVisibility(View.GONE);
                month.invalidate();

                break;
            case R.id.btn08 ://星期一为一周首日
                month.setMonthStyle(MonthView.Style.MONDAY_STYLE);
                ll_tools.setVisibility(View.GONE);
                month.invalidate();

                break;
            case R.id.btn09 ://星期日为一周首日
                month.setMonthStyle(MonthView.Style.SUNDAY_STYLE);
                ll_tools.setVisibility(View.GONE);
                month.invalidate();

                break;
            case R.id.btn10 ://默认选中背景
                month.setSelectedBackground(null);
                ll_tools.setVisibility(View.GONE);
                month.invalidate();

                break;
            case R.id.btn11 ://设置选中背景
                month.setSelectedBackground(R.mipmap.jpyd_date_selected_bg);
                ll_tools.setVisibility(View.GONE);
                month.invalidate();

                break;
            case R.id.btn12 ://默认显示
                month.setShowLunarStyle(MonthView.Style.NO_LUNAR_HOLIDAY);
                ll_tools.setVisibility(View.GONE);
                month.invalidate();

                break;
            case R.id.btn13 ://节假日
                month.setShowLunarStyle(MonthView.Style.NO_LUNAR);
                ll_tools.setVisibility(View.GONE);
                month.invalidate();

                break;
            case R.id.btn14 ://节假日和农历
                month.setShowLunarStyle(MonthView.Style.LUNAR_HOLIDAY);
                ll_tools.setVisibility(View.GONE);
                month.invalidate();

                break;
            case R.id.btn15 ://只设置上限日期
                month.setUpperLimitDay(Calendar.getInstance());
                month.setLowerLimitDay(null);
                ll_tools.setVisibility(View.GONE);
                month.invalidate();

                break;
            case R.id.btn16 ://只设置下限日期
                month.setUpperLimitDay(null);
                month.setLowerLimitDay(Calendar.getInstance());
                ll_tools.setVisibility(View.GONE);
                month.invalidate();

                break;
            case R.id.btn17 ://设置上下限日期
                Calendar calendar1 = Calendar.getInstance();
                Calendar calendar2 = Calendar.getInstance();
                calendar1.add(Calendar.DATE,-10);
                calendar2.add(Calendar.DATE,10);
                month.setUpperLimitDay(calendar1);
                month.setLowerLimitDay(calendar2);
                ll_tools.setVisibility(View.GONE);
                month.invalidate();

                break;
        }

    }
}
