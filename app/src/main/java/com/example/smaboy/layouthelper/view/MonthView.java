package com.example.smaboy.layouthelper.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import java.util.Calendar;
import java.util.Date;

/**
 * 类名: MonthView
 * 类作用描述: 月份组件
 * 1.提供输入指定月份后，绘制出该月份的天数和头部星期排列（可控制显示与否，可调整星期排序：星期一在一列，星期天在第一列）
 * <p>
 * 作者: Smaboy
 * 创建时间: 2019/1/9 10:16
 */
public class MonthView extends View {

    private static final String[] WEEK_SUN = new String[]{"日", "一", "二", "三", "四", "五", "六"};
    private static final String[] WEEK_MON = new String[]{"一", "二", "三", "四", "五", "六", "日"};

    /**
     * 日历
     * <p>
     * 该参数是该类的必须参数，用于生成月份视图view
     */
    private Calendar calendar;

    /**
     * 当前年份
     */
    private int year;
    /**
     * 当前月份
     * <p>
     * 注意：这里的月份和实际月份差一位（月份计数是从0开始
     * 如：实际中1月对应这里的0月）
     */
    private int month;
    /**
     * 当前日
     */
    private int day;

    /**
     * 默认选中日期
     * <p>
     * 该参数用于处理在该月份中默认选中的日期，方便以后做日期选择范围的控制
     */
    private int defSelectedDay;


    /**
     * 上限日期
     * <p>
     * 该参数用于控制本月中最小能选择的日期（包含该日期）
     */
    private int upperLimitDay;

    /**
     * 下限日期
     * <p>
     * 该参数用于控制本月中最大能选择的日期（包含该日期）
     */
    private int lowerLimitDay;

    /**
     * 黑色画笔
     */
    private Paint blackPaint;

    /**
     * 灰色画笔
     */
    private Paint grayPaint;
    /**
     * 白色画笔
     */
    private Paint whitePaint;
    /**
     * 红色画笔
     */
    private Paint redPaint;

    /**
     * 标题样式
     * <p>
     * 默认为居中对齐
     */
    private Style titleStyle = Style.TITLE_CENTER;

    /**
     * 月份view样式
     * <p>
     * 默认为礼拜日作为一周的第一天
     */
    private Style monthStyle = Style.SUNDAY_STYLE;

    /**
     * 标题高度
     */
    private float titleHeight = 100;
    /**
     * 星期高度
     */
    private float weekHeight = 120;

    /**
     * 标题文字大小
     */
    private int titleTextSize = 40;

    /**
     * 该view能使用的宽度
     */
    private float canUsewidth;

    /**
     * 当前月份总的天数
     */
    private int daysOfMonth;

    /**
     * 当前月一号为于所在周的第几天
     */
    private int dayOfWeekInMonthFirst;

    /**
     * 当前月有几周
     */
    private int weekCount;

    /**
     * 是否开启星期标识
     * <p>
     * 默认开启
     */
    private boolean openWeek = true;


    public MonthView(Context context) {
        this(context, null);
    }

    public MonthView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MonthView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        init();
    }

    /**
     * 自定义view的样式类
     * 1.包含标题样式( TITLE_LEFT,TITLE_CENTER,TITLE_RIGHT,NO_TITLE)
     * 2.包含日期排布样式(SUNDAY_STYLE,MONDAY_STYLE)
     */
    public enum Style {
        /**
         * 标题左对齐
         */
        TITLE_LEFT(0),

        /**
         * 标题居中
         */
        TITLE_CENTER(1),

        /**
         * 标题右对齐
         */
        TITLE_RIGHT(2),

        /**
         * 无标题
         */
        NO_TITLE(3),
        /**
         * 无标题
         */
        NO_WEEK(4),
        /**
         * 礼拜日作为一周的第一天
         */
        SUNDAY_STYLE(5),
        /**
         * 礼拜一作为一周的第一天
         */
        MONDAY_STYLE(6);

        Style(int nativeInt) {
            this.nativeInt = nativeInt;
        }

        final int nativeInt;

    }

    /**
     * 初始化操作
     */
    private void init() {
        //默认去的日期为当前的日期
        calendar = Calendar.getInstance();


        //初始化画笔工具
        initPaint();


    }

    /**
     * 初始化画笔工具
     */
    private void initPaint() {
        blackPaint = new Paint();
        blackPaint.setAntiAlias(true);
        blackPaint.setColor(Color.BLACK);
        blackPaint.setTextSize(titleTextSize);
        blackPaint.setStyle(Paint.Style.FILL);

        grayPaint = new Paint();
        grayPaint.setAntiAlias(true);
        grayPaint.setColor(Color.GRAY);
        grayPaint.setTextSize(titleTextSize);
        grayPaint.setStyle(Paint.Style.FILL);

        whitePaint = new Paint();
        whitePaint.setAntiAlias(true);
        whitePaint.setColor(Color.WHITE);
        whitePaint.setTextSize(titleTextSize);
        whitePaint.setStyle(Paint.Style.FILL);

        redPaint = new Paint();
        redPaint.setAntiAlias(true);
        redPaint.setColor(Color.RED);
        redPaint.setTextSize(titleTextSize);
        redPaint.setStyle(Paint.Style.FILL);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        daysOfMonth = getDaysOfMonth(calendar);//获取当前月份总的天数
        dayOfWeekInMonthFirst = getDayOfWeekInMonthFirst(calendar);//获取指定日期所在月一号为于所在周的第几天
        switch (monthStyle) {
            case MONDAY_STYLE:
                if (dayOfWeekInMonthFirst == 1) {
                    weekCount = (daysOfMonth + 6) / 7 + (daysOfMonth + dayOfWeekInMonthFirst - 1) % 7 == 0 ? 0 : 1;

                } else {

                    weekCount = (daysOfMonth + dayOfWeekInMonthFirst - 2) / 7 + ((daysOfMonth + dayOfWeekInMonthFirst - 1) % 7 == 0 ? 0 : 1);
                }
                break;
            case SUNDAY_STYLE:
            default:
                weekCount = (daysOfMonth + dayOfWeekInMonthFirst - 1) / 7 + ((daysOfMonth + dayOfWeekInMonthFirst - 1) % 7 == 0 ? 0 : 1);
                break;
        }
        canUsewidth = widthSize - getPaddingLeft() - getPaddingRight();//可以进行绘制的宽度

        //判断标题和week的显示处理高度设置
        int height = (int) (canUsewidth / 7 * weekCount);
        if (titleStyle != Style.NO_TITLE) {
            height += titleHeight;
        }
        if (openWeek) {
            height += weekHeight;
        }

        setMeasuredDimension(widthSize, height);
    }


    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {

    }

    @Override
    protected void onDraw(Canvas canvas) {

        //初始化相关参数
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DATE);

        Log.e("MonthView", "当前月份的天数为：" + daysOfMonth);
        Log.e("MonthView", "当前日期所在月的一号为此周的第几天：" + dayOfWeekInMonthFirst);
        Log.e("MonthView", "当前日期为" + calendar.get(Calendar.MONTH));

        //绘制标题
        drawTitle(canvas);

        //绘制星期
        drawWeek(canvas);


        //绘制日期内容
        drawMonthDay(canvas);


    }

    private void drawMonthDay(Canvas canvas) {
        //知道需要绘制的天数和绘制内容1号所在星期几
        switch (monthStyle) {
            case SUNDAY_STYLE://周日作为一周的第一天
                Log.e("TAG", "当前月为" + month + 1 + "月，共有" + weekCount + "周");
                float x;
                float y;
                String content;//填写的日期（如：1，2，3...）
                //一周有7天，这里我们将其平分成七分，高度我们可以设置为何宽度一致
                Paint.FontMetrics fontMetrics = blackPaint.getFontMetrics();
                y = getTop() + getPaddingTop() + canUsewidth / 14 - fontMetrics.descent + (fontMetrics.descent - fontMetrics.ascent) / 2;//保证竖值居中
                //判断标题和星期的显示与否，确定y的初始位置
                if (titleStyle != Style.NO_TITLE) {
                    y += titleHeight;
                }
                if (openWeek) {
                    y += weekHeight;
                }
                //开始绘制
                for (int i = 0; i < weekCount; i++) {//绘制步骤为，一周一周往下进行绘制
                    for (int j = 0; j < 7; j++) {
                        //开始绘制日期
                        float dayWidth = blackPaint.measureText(Integer.toString(day));
                        x = getLeft() + getPaddingLeft() + canUsewidth / 7 * j + (canUsewidth / 14 - dayWidth / 2);//保证文字水平居中
                        int day = 7 * i + j - dayOfWeekInMonthFirst + 2;
                        //处理写入的文字
                        if (day < 1 || day > daysOfMonth) {//日期不在当前月份的范围内，舍去
                            continue;
                        }

                        //实际填写的字符串

                        content = Integer.toString(day);
                        canvas.drawText(content, x, y, blackPaint);

                    }
                    //确定绘制时，y的位置
                    y += canUsewidth / 7;
                }


                break;
            case MONDAY_STYLE://周一作为一周的第一天

                break;
            default:

                break;
        }
    }

    /**
     * 绘制星期
     *
     * @param canvas 画布
     */
    private void drawWeek(Canvas canvas) {
        //判别是否需要绘制标题
        if (!openWeek) {//不需要星期
            return;
        }

        //1.绘制背景矩形
//        canvas.drawRect(getLeft(), getTop(), getRight(), weekHeight, grayPaint);


        switch (monthStyle) {
            case MONDAY_STYLE://周一作为一周的第一天
                float temp = (getWidth() - getPaddingLeft() - getPaddingRight()) / WEEK_MON.length;//每个星期的宽度
                float x, y;

                for (int i = 0; i < WEEK_MON.length; i++) {

                    //2.绘制星期
                    String week = WEEK_MON[i];//星期文字
                    float titleWidth = whitePaint.measureText(week);//测量文字宽度
                    Paint.FontMetrics fontMetrics = blackPaint.getFontMetrics();
                    x = getLeft() + getPaddingLeft() + temp / 2 - titleWidth / 2 + temp * i;
                    //竖值居中,绘制文字从文字左下角开始,因此"+"
                    y = titleStyle != Style.NO_TITLE ? getTop() + getPaddingTop() + titleHeight + weekHeight / 2 - fontMetrics.descent + (fontMetrics.descent - fontMetrics.ascent) / 2
                            : getTop() + getPaddingTop() + weekHeight / 2 - fontMetrics.descent + (fontMetrics.descent - fontMetrics.ascent) / 2;

                    if (i == WEEK_MON.length - 1 || i == WEEK_MON.length - 2) {//礼拜六合礼拜日，文字设置为红色

                        canvas.drawText(week, x, y, redPaint);
                    } else {
                        canvas.drawText(week, x, y, blackPaint);

                    }

                }
                break;
            case SUNDAY_STYLE://周日作为一周的第一天
               float temp2 = (getWidth() - getPaddingLeft() - getPaddingRight()) / WEEK_MON.length;//每个星期的宽度
                float x2, y2;

                for (int i = 0; i < WEEK_MON.length; i++) {

                    //2.绘制星期
                    String week = WEEK_SUN[i];//星期文字
                    float titleWidth = whitePaint.measureText(week);//测量文字宽度
                    Paint.FontMetrics fontMetrics = blackPaint.getFontMetrics();
                    x2 = getLeft() + getPaddingLeft() + temp2 / 2 - titleWidth / 2 + temp2 * i;
                    //竖值居中,绘制文字从文字左下角开始,因此"+"
                    y2 = titleStyle != Style.NO_TITLE ? getTop() + getPaddingTop() + titleHeight + weekHeight / 2 - fontMetrics.descent + (fontMetrics.descent - fontMetrics.ascent) / 2
                            : getTop() + getPaddingTop() + weekHeight / 2 - fontMetrics.descent + (fontMetrics.descent - fontMetrics.ascent) / 2;

                    if (i == WEEK_MON.length - 1 || i == 0) {//礼拜六合礼拜日，文字设置为红色

                        canvas.drawText(week, x2, y2, redPaint);
                    } else {
                        canvas.drawText(week, x2, y2, blackPaint);

                    }

                }
                break;
            default:
                break;
        }


    }


    /**
     * 绘制标题
     *
     * @param canvas 画布
     */
    private void drawTitle(Canvas canvas) {
        //判别是否需要绘制标题
        if (titleStyle == Style.NO_TITLE) {//不需要标题
            return;
        }

        //1.绘制背景矩形
        canvas.drawRect(getLeft(), getTop(), getRight(), titleHeight, grayPaint);
        //2.绘制标题
        String title = year + "年" + (month + 1) + "月" + day + "日";//标题文字
        float titleWidth = whitePaint.measureText(title);//测量文字宽度
        Paint.FontMetrics fontMetrics = whitePaint.getFontMetrics();
        float x, y;
        //竖值居中,绘制文字从文字左下角开始,因此"+"
        y = titleHeight / 2 - fontMetrics.descent + (fontMetrics.descent - fontMetrics.ascent) / 2;

        switch (titleStyle) {
            case TITLE_LEFT://左对齐
                x = getLeft() + 20;
                break;
            case TITLE_CENTER://居中

                x = (getRight() - getLeft() - titleWidth) / 2 + getLeft();
                break;
            case TITLE_RIGHT://右对齐
                x = getRight() - 20 - titleWidth;
                break;
            default:
                x = (getRight() - getLeft() - titleWidth) / 2 + getLeft();
                break;
        }


        canvas.drawText(title, x, y, whitePaint);
    }


    /**
     * 获取指定日期所在月的天数
     *
     * @param calendar 日期
     * @return 当前日期所在月份的天数
     */
    private int getDaysOfMonth(Calendar calendar) {
        return calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
    }

    /**
     * 获取指定日期在当前周的第几天
     * <p>
     * 注意：默认礼拜日为一个星期的第一天
     *
     * @param calendar 日期
     * @return 当前日期所在月份的天数
     */
    private int getDayOfWeek(Calendar calendar) {
        return calendar.get(Calendar.DAY_OF_WEEK);
    }

    /**
     * 获取指定日期所在月一号为于所在周的第几天
     * <p>
     * 注意：默认礼拜日为一个星期的第一天
     *
     * @param calendar 日期
     * @return 当前日期所在月份的天数
     */
    private int getDayOfWeekInMonthFirst(Calendar calendar) {
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        return getDayOfWeek(calendar);
    }

    /**
     * 向外界提供的刷新方法
     */
    public void updata() {
        invalidate();
    }

    //    -------------------------向外界提供一些属性设置的公共方法------------------------------------------
    public MonthView setCalendar(Calendar calendar) {
        this.calendar = calendar;
        Log.e("TAG", "当前日期为:" + calendar.getTime().toString());
        return this;
    }

    public MonthView setDefSelectedDay(int defSelectedDay) {
        this.defSelectedDay = defSelectedDay;
        return this;
    }

    public MonthView setUpperLimitDay(int upperLimitDay) {
        this.upperLimitDay = upperLimitDay;
        return this;
    }

    public MonthView setLowerLimitDay(int lowerLimitDay) {
        this.lowerLimitDay = lowerLimitDay;
        return this;
    }

    public MonthView setTitleStyle(Style titleStyle) {
        this.titleStyle = titleStyle;
        return this;
    }

    public MonthView setMonthStyle(Style monthStyle) {
        this.monthStyle = monthStyle;
        return this;
    }

    public MonthView setTitleHeight(float titleHeight) {
        this.titleHeight = titleHeight;
        return this;
    }

    public MonthView setTitleTextSize(int titleTextSize) {
        this.titleTextSize = titleTextSize;
        return this;
    }
}
