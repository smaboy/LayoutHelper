package com.example.smaboy.layouthelper.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import com.example.smaboy.layouthelper.R;
import com.example.smaboy.layouthelper.view.MonthView;

import java.util.Calendar;

/**
 * 类名: DataActivity
 * 类作用描述: 日期选择类
 * 作者: Smaboy
 * 创建时间: 2019/1/9 10:12
 */
public class DataActivity extends Activity {

    private MonthView month;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_data);


        month = findViewById(R.id.month);

//        month.setTitleStyle(MonthView.Style.TITLE_RIGHT);
        Calendar instance = Calendar.getInstance();
        instance.add(Calendar.MONTH,-5);
        month.setCalendar(instance);



    }
}