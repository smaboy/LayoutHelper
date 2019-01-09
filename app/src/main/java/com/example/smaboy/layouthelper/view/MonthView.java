package com.example.smaboy.layouthelper.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import java.util.Calendar;
import java.util.Date;

/**
 * 类名: MonthView
 * 类作用描述: 月份组件
 *            1.提供输入指定月份后，绘制出该月份的天数和头部星期排列（可控制显示与否，可调整星期排序：星期一在一列，星期天在第一列）
 *
 * 作者: Smaboy
 * 创建时间: 2019/1/9 10:16
 */
public class MonthView extends View {

    /**
     * 日历
     *
     * 该参数是该类的必须参数，用于生成月份视图view
     *
     */
    private Calendar calendar;

    /**
     * 当前年份
     *
     */
    private int year;
    /**
     * 当前月份
     *
     *注意：这里的月份和实际月份差一位（月份计数是从0开始
     * 如：实际中1月对应这里的0月）
     */
    private int month;
    /**
     * 当前日
     *
     */
    private int day;

    /**
     * 默认选中日期
     *
     * 该参数用于处理在该月份中默认选中的日期，方便以后做日期选择范围的控制
     */
    private int defSelectedDay;


    /**
     * 上限日期
     *
     * 该参数用于控制本月中最小能选择的日期（包含该日期）
     *
     */
    private int upperLimitDay;

    /**
     * 下限日期
     *
     * 该参数用于控制本月中最大能选择的日期（包含该日期）
     *
     */
    private int lowerLimitDay;

    /**
     *
     * 黑色画笔
     */
    private Paint blackPaint;

    /**
     *
     * 灰色画笔
     */
    private Paint grayPaint;
    /**
     *
     * 白色画笔
     */
    private Paint whitePaint;


    public MonthView(Context context) {
        this(context,null);
    }

    public MonthView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public MonthView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        init();
    }

    /**
     * 初始化操作
     *
     */
    private void init() {
        //默认去的日期为当前的日期
        calendar=Calendar.getInstance();

        //初始化相关参数
        year=calendar.get(Calendar.YEAR);
        month=calendar.get(Calendar.MONTH);
        day=calendar.get(Calendar.DAY_OF_MONTH);

        //初始化画笔工具
        initPaint();



        Log.e("MonthView","当前月份的天数为："+getDaysOfMonth(calendar));
        Log.e("MonthView","当前日期为一周的第几天："+getDayOfWeek(calendar));
        Log.e("MonthView","当前日期所在月为一周的第几天："+getDayOfWeekInMonth(calendar));
        Log.e("MonthView","当前日期"+calendar.toString());

    }

    /**
     *
     * 初始化画笔工具
     *
     */
    private void initPaint() {
        blackPaint=new Paint();
        blackPaint.setAntiAlias(true);
        blackPaint.setColor(Color.BLACK);
        blackPaint.setTextSize(40);
        blackPaint.setStyle(Paint.Style.FILL);

        grayPaint=new Paint();
        grayPaint.setAntiAlias(true);
        grayPaint.setColor(Color.GRAY);
        grayPaint.setTextSize(40);
        grayPaint.setStyle(Paint.Style.FILL);

        whitePaint=new Paint();
        whitePaint.setAntiAlias(true);
        whitePaint.setColor(Color.WHITE);
        whitePaint.setTextSize(40);
        whitePaint.setStyle(Paint.Style.FILL);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
    }

    @Override
    protected void onDraw(Canvas canvas) {
//        super.onDraw(canvas);


        //绘制标题
        //1.绘制背景矩形
        canvas.drawRect(getLeft(),getTop(),getRight(),200,grayPaint);
        //2.绘制标题
        String title=year+"年"+(month+1)+"月"+day+"日";
        canvas.drawText(title,100,100,whitePaint);

        //绘制日期内容







    }


    /**
     * 获取指定日期所在月的天数
     *
     * @param calendar 日期
     * @return 当前日期所在月份的天数
     */
    private   int getDaysOfMonth(Calendar calendar) {
        return calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
    }
    /**
     * 获取指定日期在当前周的第几天
     *
     * 注意：默认礼拜日为一个星期的第一天
     *
     * @param calendar 日期
     * @return 当前日期所在月份的天数
     */
    private   int getDayOfWeek(Calendar calendar) {
        return calendar.get(Calendar.DAY_OF_WEEK);
    }

    /**
     * 获取指定日期所在月一号为于所在周的第几天
     *
     * 注意：默认礼拜日为一个星期的第一天
     *
     * @param calendar 日期
     * @return 当前日期所在月份的天数
     */
    private   int getDayOfWeekInMonth(Calendar calendar) {
        calendar.set(Calendar.DAY_OF_MONTH,1);
        return getDayOfWeek(calendar);
    }


}
