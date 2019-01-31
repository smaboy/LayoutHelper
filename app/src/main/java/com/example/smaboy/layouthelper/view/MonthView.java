package com.example.smaboy.layouthelper.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.*;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;
import com.example.smaboy.layouthelper.util.ChinaDataUtils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * 类名: MonthView
 * 类作用描述: 月份组件
 * 1.提供输入指定月份后，绘制出该月份的天数和头部星期排列（可控制显示与否，可调整星期排序：星期一在一列，星期天在第一列）
 * 2.提供标题的显示与否，标题的排列方式（左对齐，居中对齐，右对齐）
 * 3.提供星期的显示与否及样式
 * 4.提供左右滑切换月份，上下滑切换年份，并为该功能提供了开关
 * 5.提供点击事件的回调监听
 * 6.提供设置选中日期的背景公共方法，可以设置选中日期的背景，不设置的话采用默认背景
 * 7.提供农历农历显示样式：共提供三种样式
 * 8.日历的上限和下限的响应事件控制（只允许用户选择在上限和下限之内的日期）
 * <p>
 * 作者: Smaboy
 * 创建时间: 2019/1/9 10:16
 */
public class MonthView extends View {

    //-------------常量部分----------------------
    private static final String[] WEEK_SUN = new String[]{"日", "一", "二", "三", "四", "五", "六"};
    private static final String[] WEEK_MON = new String[]{"一", "二", "三", "四", "五", "六", "日"};

    /**
     * 农历部分假日
     */
    private final static String[] lunarHolidays = new String[]{
            "0101 春节",
            "0115 元宵",
            "0505 端午",
            "0707 七夕",
            "0715 中元",
            "0815 中秋",
            "0909 重阳",
            "1208 腊八",
            "1224 小年",
            "0100 除夕"
    };

    /**
     * 公历部分节假日
     */
    private final static String[] solarHolidays = new String[]{
            "0101 元旦",
            "0214 情人",
            "0308 妇女",
            "0312 植树",
            "0401 愚人",
            "0501 劳动",
            "0504 青年",
            "0512 护士",
            "0601 儿童",
            "0701 建党",
            "0801 建军",
            "0910 教师",
            "1001 国庆",
            "1225 圣诞"
    };

    /**
     * 当前月的日期数组
     */
    private List<Integer> currentMonthDays;

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
    private int date;

    /**
     * 默认选中日期
     * <p>
     * 该参数用于处理在该月份中默认选中的日期，方便以后做日期选择范围的控制
     */
    private Calendar defCalendar;


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
     * 节假日专用画笔
     */
    private Paint holidayPaint;
    /**
     * 选中画圈专用画笔
     */
    private Paint selectedPaint;

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
    private int titleTextSize = 30;


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

    /**
     * 是否开启滑动切换日期
     * <p>
     * 默认开启
     */
    private boolean openScrollSwitch = true;

    /**
     * 每个日期所需要的宽度
     */
    private int dateViewWidth;

    /**
     * 每个日期所需要的高度
     */
    private int dateViewHeight;

    /**
     * 接口实例
     */
    private OnDateClickListener listener;

    /**
     * 选中的背景图
     */
    private Bitmap selectedBitmap;

    /**
     * 显示公历、农历的样式
     * 这里提供三个样式： NO_LUNAR_HOLIDAY , NO_LUNAR , LUNAR_HOLIDAY
     *
     */
    private Style showLunarStyle=Style.NO_LUNAR_HOLIDAY;

    /**
     * 向外界提供点击监听的接口
     */
    public interface OnDateClickListener {
        void onDateClick(int year, int month, int date);
    }


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
         * 礼拜日作为一周的第一天
         */
        SUNDAY_STYLE(5),
        /**
         * 礼拜一作为一周的第一天
         */
        MONDAY_STYLE(6),

        /**
         * 不显示农历,节假日
         */
        NO_LUNAR_HOLIDAY(7),
        /**
         * 不显示农历，如果当日为节假日则显示节假日
         */
        NO_LUNAR(8),
        /**
         * 显示农历，如果为节假日则显示节假日
         */
        LUNAR_HOLIDAY(9);

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
        //初始化相关参数
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        date = calendar.get(Calendar.DATE);

        //初始化画笔工具
        initPaint();

        //初始化日期数组
        currentMonthDays = new ArrayList<>();


    }

    /**
     * 初始化画笔工具
     */
    private void initPaint() {
        blackPaint = new Paint();
        blackPaint.setAntiAlias(true);
        blackPaint.setColor(Color.BLACK);
        blackPaint.setTextSize(titleTextSize+10);
        blackPaint.setStyle(Paint.Style.STROKE);

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
        redPaint.setTextSize(titleTextSize+10);
        redPaint.setStyle(Paint.Style.FILL);


        holidayPaint = new Paint();
        holidayPaint.setAntiAlias(true);
        holidayPaint.setColor(Color.BLUE);
        holidayPaint.setTextSize(titleTextSize);
        holidayPaint.setStyle(Paint.Style.FILL);


        selectedPaint = new Paint();
        selectedPaint.setAntiAlias(true);
        selectedPaint.setColor(Color.RED);
        selectedPaint.setStrokeWidth(2.5f);
        selectedPaint.setStyle(Paint.Style.STROKE);


    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        daysOfMonth = getDaysOfMonth(calendar);//获取当前月份总的天数
        for (int i = 1; i <= daysOfMonth; i++) {//保存当前日期数
            currentMonthDays.add(i);
        }
        dayOfWeekInMonthFirst = getDayOfWeekInMonthFirst(calendar);//获取指定日期所在月一号为于所在周的第几天
        switch (monthStyle) {
            case MONDAY_STYLE:
                if (dayOfWeekInMonthFirst == 1) {
                    weekCount = (daysOfMonth + 6) / 7 + ((daysOfMonth + 6) % 7 == 0 ? 0 : 1);

                } else {

                    weekCount = (daysOfMonth + dayOfWeekInMonthFirst - 2) / 7 + ((daysOfMonth + dayOfWeekInMonthFirst - 2) % 7 == 0 ? 0 : 1);
                }
                break;
            case SUNDAY_STYLE:
            default:
                weekCount = (daysOfMonth + dayOfWeekInMonthFirst - 1) / 7 + ((daysOfMonth + dayOfWeekInMonthFirst - 1) % 7 == 0 ? 0 : 1);
                break;
        }
        int canUsewidth = widthSize - getPaddingLeft() - getPaddingRight();//可以进行绘制的宽度


        //判断标题和week的显示处理高度设置
        int height = 0;
        if (titleStyle != Style.NO_TITLE) {
            height += titleHeight;
        }
        if (openWeek) {
            height += weekHeight;
        }

        //确定每个日期的宽高
        dateViewWidth = canUsewidth / 7;
        if (heightMode == MeasureSpec.EXACTLY) {//精确值
            dateViewHeight = (heightSize - height - getPaddingTop() - getPaddingBottom()) / weekCount;
        } else {
            if (heightSize > widthSize) {
                dateViewHeight = canUsewidth / 7;
            } else {
                dateViewHeight = (heightSize - height) / (weekCount + 1);
            }

        }

        //确定高度
        height += dateViewHeight * weekCount + getPaddingTop() + getPaddingBottom();

        //通过布局模式来确定宽高
        setMeasuredDimension(widthSize, heightMode == MeasureSpec.EXACTLY ? heightSize : height);
    }


    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {

    }

    @Override
    protected void onDraw(Canvas canvas) {

        //绘制标题
        drawTitle(canvas);

        //绘制星期
        drawWeek(canvas);


        //绘制日期内容
        drawMonthDay(canvas);


    }

    private void drawMonthDay(Canvas canvas) {
        //知道需要绘制的天数和绘制内容1号所在星期几
        float y;
        float offY = 0;//偏移量
        //一周有7天，这里我们将其平分成七分，高度我们可以设置为何宽度一致
        Paint.FontMetrics fontMetrics = blackPaint.getFontMetrics();
        y = getTop() + getPaddingTop() + dateViewHeight / 2 - fontMetrics.descent + (fontMetrics.descent - fontMetrics.ascent) / 2;//保证竖直居中
        //判断标题和星期的显示与否，确定y的初始位置
        if (titleStyle != Style.NO_TITLE) {
            offY += titleHeight;
        }
        if (openWeek) {
            offY += weekHeight;
        }
        //加上偏移
        y += offY;
        //开始绘制
        int day = 0;
        for (int i = 0; i < weekCount; i++) {//绘制步骤为，一周一周往下进行绘制
            for (int j = 0; j < 7; j++) {
                //开始绘制日期
                if (i == 0) {//处理此时绘制的内容
                    if (monthStyle == Style.SUNDAY_STYLE) {//如果一周以礼拜日为第一天
                        if (j < dayOfWeekInMonthFirst - 1) {
                            //不绘制
                            continue;
                        }
                        day++;
                    } else {//如果一周以礼拜一为第一天
                        if (j < dayOfWeekInMonthFirst - 2 && dayOfWeekInMonthFirst != 1) {
                            //不绘制
                            continue;
                        } else if (dayOfWeekInMonthFirst == 1 && j < 6) {
                            //不绘制
                            continue;

                        }
                        day++;

                    }
                } else {
                    day++;
                }

                //处理写入的文字
                if (day < 1 || day > daysOfMonth) {//日期不在当前月份的范围内，舍去
                    continue;
                }

                //绘制默认选中标识（这里我们绘制一个圆环标识选中）,这里我们需要提供一个将日期转化为坐标
                if (isDefCalendar(year, month, currentMonthDays.get(day - 1))) {//绘制默认选中的日期的背景
                    drawSelectTag(canvas, offY, j, i);
                }

                //绘制日期
                drawDate(canvas, y, day, j);


            }
            //确定绘制时，y的位置
            y += dateViewHeight;
        }


    }

    /**
     * 绘制日期
     * 真正开始绘制的方法
     *
     * @param canvas 画布
     * @param y y轴坐标
     * @param day 天
     * @param j 列
     */
    private void drawDate(Canvas canvas, float y, int day, int j) {
        String content;//公历日期内容
        float x;//绘制的x坐标
        String lunarContent;//判断此时的日期是否为节假日日期
        float dayWidth;//日期文字的宽度
        float lunarDayWidth;//农历日期文字的宽度
        switch (showLunarStyle) {
            case NO_LUNAR_HOLIDAY :
                //非节假日实际填写的字符串
                content = Integer.toString(currentMonthDays.get(day - 1));
                //获取日期内容的宽度
                dayWidth = blackPaint.measureText(content);

                //获取x坐标
                x = getLeft() + getPaddingLeft() + dateViewWidth * j + (dateViewWidth / 2 - dayWidth / 2);//保证文字水平居中

                //绘制
                canvas.drawText(content, x, y, blackPaint);
                break;
            case NO_LUNAR :
                if (!TextUtils.isEmpty(getFestivalContent(year, month, currentMonthDays.get(day - 1)))) {
                    //节假日字符串
                    content = getFestivalContent(year, month, currentMonthDays.get(day - 1));
                    //获取日期内容的宽度
                    dayWidth = holidayPaint.measureText(content);
                    //保证文字水平居中
                    x = getLeft() + getPaddingLeft() + dateViewWidth * j + (dateViewWidth / 2 - dayWidth / 2);
                    //绘制
                    canvas.drawText(content, x, y, holidayPaint);

                } else {
                    //非节假日实际填写的字符串
                    content = Integer.toString(currentMonthDays.get(day - 1));
                    //获取日期内容的宽度
                    dayWidth = blackPaint.measureText(content);
                    //保证文字水平居中
                    x = getLeft() + getPaddingLeft() + dateViewWidth * j + (dateViewWidth / 2 - dayWidth / 2);
                    //绘制
                    canvas.drawText(content, x, y, blackPaint);
                }


                break;
            case LUNAR_HOLIDAY :
                //非节假日实际填写的字符串
                content = Integer.toString(currentMonthDays.get(day - 1));
                //获取日期内容的宽度
                dayWidth = blackPaint.measureText(content);

                x = getLeft() + getPaddingLeft() + dateViewWidth * j + (dateViewWidth / 2 - dayWidth / 2);//保证文字水平居中

                //获取FontMetrics
                Paint.FontMetrics fm1 = blackPaint.getFontMetrics();
                Paint.FontMetrics fm2 = grayPaint.getFontMetrics();
                Paint.FontMetrics fm3 = holidayPaint.getFontMetrics();

                //绘制公历日期
                canvas.drawText(content, x, y-(fm1.bottom-fm1.top)/2,  blackPaint);

                //绘制农历日期
                if(!TextUtils.isEmpty(getFestivalContent(year, month, currentMonthDays.get(day - 1)))){//节假日
                    lunarContent=getFestivalContent(year, month, currentMonthDays.get(day - 1));
                    //获取需要绘制的农历字符串宽度
                    lunarDayWidth=holidayPaint.measureText(lunarContent);
                    //保证文字水平居中
                    float x2 = getLeft() + getPaddingLeft() + dateViewWidth * j + (dateViewWidth / 2 - lunarDayWidth / 2);
                    //绘制节假日
                    canvas.drawText(lunarContent, x2, y+(fm3.bottom-fm3.top)/2,holidayPaint);


                } else {
                    //获取对应的农历
                    long[] l = ChinaDataUtils.calElement(year, month+1, day);
                    lunarContent=ChinaDataUtils.getChinaDate((int) l[1],(int) l[2]);
                    //获取需要绘制的农历字符串宽度
                    lunarDayWidth=grayPaint.measureText(lunarContent);

                    //保证文字水平居中
                    float x2 = getLeft() + getPaddingLeft() + dateViewWidth * j + (dateViewWidth / 2 - (lunarDayWidth) / 2);
                    //绘制农历
                    canvas.drawText(lunarContent, x2, y+(fm2.bottom-fm2.top)/2,grayPaint);
                }



                break;
            default:
                //非节假日实际填写的字符串
                content = Integer.toString(currentMonthDays.get(day - 1));
                //获取日期内容的宽度
                dayWidth = blackPaint.measureText(content);

                //获取x坐标
                x = getLeft() + getPaddingLeft() + dateViewWidth * j + (dateViewWidth / 2 - dayWidth / 2);//保证文字水平居中

                //绘制
                canvas.drawText(content, x, y, blackPaint);
                break;
        }
    }

    /**
     * 判断当前传入的日期是否为节假日，如果是则返回节假日的名称
     * 判断规则：先查看当前日期与公历节假日数组匹配，如果匹配成功则返回当前节假日的名称
     * 如果匹配失败，则换算出当前日期的农历与农历节假日数组匹配进行筛选，匹配
     * 成功则返回农历节假日的名称，否则返回空的字符串
     * <p>
     * 注意：传参的话必须是公历日期（如果不是，会出现匹配异常）
     *
     * @param year  年
     * @param month 月
     * @param day   日
     */
    private String getFestivalContent(int year, int month, int day) {

        //节假日名称
        String holidayName = "";
        //获取传入的月日长度为4的字符串
        String m = String.valueOf(month + 1).length() == 2 ? String.valueOf(month + 1) : "0" + (month + 1);
        String d = String.valueOf(day).length() == 2 ? String.valueOf(day) : "0" + day;
        String md = m + d;

        try {
            //公历节假日匹配
            for (String solar : solarHolidays) {
                if (solar.contains(md)) {
                    holidayName = solar.split(" ")[1];
                    return holidayName;
                }

            }
            //获取对应的农历日期
            long[] lunarData = ChinaDataUtils.calElement(year, month + 1, day);
            String lm = String.valueOf(lunarData[1]).length() == 2 ? String.valueOf(lunarData[1]) : "0" + lunarData[1];
            String ld = String.valueOf(lunarData[2]).length() == 2 ? String.valueOf(lunarData[2]) : "0" + lunarData[2];
            String lmd = lm + ld;
            //公历节假日匹配
            for (String lunar : lunarHolidays) {
                if (lunar.contains(lmd)) {
                    holidayName = lunar.split(" ")[1];
                    return holidayName;
                }

            }
        } catch (Exception e) {
            Log.e("getFestivalContent", "节假日匹配异常");
        }


        return holidayName;
    }

    /**
     * 用于处理此时绘制的日期是否为默认选中日期
     *
     * @param year    年
     * @param month   月
     * @param integer 日
     * @return
     */
    private boolean isDefCalendar(int year, int month, int integer) {
        if (null != defCalendar && defCalendar.get(Calendar.YEAR) == year && defCalendar.get(Calendar.MONTH) == month
                && defCalendar.get(Calendar.DATE) == integer) {
            return true;

        }
        return false;
    }

    /**
     * 绘制选中标识
     *
     * @param canvas 画布
     * @param offY   Y轴偏移
     * @param i      横坐标
     * @param j      纵坐标
     */
    private void drawSelectTag(Canvas canvas, float offY, int i, int j) {
        if (i < 0 || j < 0) {
            return;
        }
        float x = getLeft() + getPaddingLeft() + dateViewWidth * i;
        float y = getTop() + getPaddingTop() + dateViewHeight * j + offY;

        if (null == selectedBitmap) {
            //绘制默认标识

            float cx = x + dateViewWidth / 2;
            float cy = y + dateViewHeight / 2;
            int min = Math.min(dateViewWidth, dateViewHeight);
            canvas.drawCircle(cx, cy, min / 2, selectedPaint);
        } else {
            //绘制背景图片
//            Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.jpyd_date_selected_bg);
            // 指定图片绘制区域(左上角的四分之一)
            Rect src = new Rect(0, 0, selectedBitmap.getWidth(), selectedBitmap.getHeight());
            // 指定图片在屏幕上显示的区域(原图大小)
            Rect dst = new Rect((int) x, (int) y, (int) x + dateViewWidth, (int) y + dateViewHeight);
            canvas.drawBitmap(selectedBitmap, src, dst, null);
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
        canvas.drawRect(getLeft() + getPaddingLeft(), getTop() + getPaddingTop(), getRight() - getPaddingRight(), getTop() + getPaddingTop() + titleHeight, grayPaint);
        //2.绘制标题
        String title = year + "年" + (month + 1) + "月" + date + "日";//标题文字
        float titleWidth = whitePaint.measureText(title);//测量文字宽度
        Paint.FontMetrics fontMetrics = whitePaint.getFontMetrics();
        float x, y;
        //竖值居中,绘制文字从文字左下角开始,因此"+"
        y = getTop() + getPaddingTop() + titleHeight / 2 - fontMetrics.descent + (fontMetrics.descent - fontMetrics.ascent) / 2;

        switch (titleStyle) {
            case TITLE_LEFT://左对齐
                x = getLeft() + getPaddingLeft() + 20;
                break;
            case TITLE_CENTER://居中

                x = getLeft() + getPaddingLeft() + (getWidth() - getPaddingLeft() - getPaddingRight()) / 2 - titleWidth / 2;
                break;
            case TITLE_RIGHT://右对齐
                x = getRight() - getPaddingRight() - 20 - titleWidth;
                break;
            default:
                x = getLeft() + getPaddingLeft() + (getWidth() - getPaddingLeft() - getPaddingRight()) / 2 - titleWidth / 2;
                break;
        }


        canvas.drawText(title, x, y, whitePaint);
    }

    /**
     * 触摸事件处理方法
     */
    float dx = 0, dy = 0;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        boolean b;//定义处理
        //添加滑动切换日历
        b = handleTouchEvent(event);

        return b;
    }

    private Boolean handleTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            //这里我们要实现一个功能，就是左滑进入下一个月，右滑滑进入上一个月,上滑进入上一年，下滑进入下一年

            case MotionEvent.ACTION_DOWN:
                dx = event.getX();
                dy = event.getY();

                return true;

            case MotionEvent.ACTION_UP:
                float ux = event.getX();
                float uy = event.getY();
                float tempX = Math.abs(ux - dx);//水平滑动的绝对值
                float tempY = Math.abs(uy - dy);//竖直滑动的绝对值

                Log.e("TAG", "tempX" + tempX);
                Log.e("TAG", "tempY" + tempY);
                if (openScrollSwitch) {//没开启滑动切换日历
                    if (tempX > tempY && tempX > dateViewWidth) {//月份变换
                        if (ux - dx > 0) {
                            calendar.add(Calendar.MONTH, -1);
                        } else {
                            calendar.add(Calendar.MONTH, 1);
                        }
                        setCalendar(calendar);
                        requestLayout();
                        invalidate();
                        return true;
                    }
                    if (tempX < tempY && tempY > dateViewHeight) {//年份变换
                        if (uy - dy > 0) {
                            calendar.add(Calendar.YEAR, -1);
                        } else {
                            calendar.add(Calendar.YEAR, 1);
                        }
                        setCalendar(calendar);
                        requestLayout();
                        invalidate();
                        return true;
                    }
                }


                //处理点击事件
                int h = 0;
                if (titleStyle != Style.NO_TITLE) {
                    h += titleHeight;
                }
                if (openWeek) {
                    h += weekHeight;
                }
                if (uy - h - getPaddingTop() > 0 && uy - getHeight() + getPaddingBottom() < 0 && ux - getPaddingLeft() > 0 && ux - getWidth() + getPaddingRight() < 0) {//点击区域只能在日期区域，不包含标题和星期区域

                    if (tempX < dateViewWidth / 2 && tempY < dateViewHeight / 2) {//判别是点击事件的条件
                        int a = (int) (uy - h - getPaddingTop()) / dateViewHeight;//获取点击的在哪一周区域,从0开始
                        int b = (int) (ux - getPaddingLeft()) / dateViewWidth;//获取点击的在哪一星期区域,从0开始
//                        Toast.makeText(getContext(), "第" + a + "周，第" + b + "列", Toast.LENGTH_SHORT).show();
                        //透过a、b我们可以获取点击区域的日期内容
                        if (null != listener) {
                            //获取日期
                            if (monthStyle == Style.SUNDAY_STYLE) {//当前样式为星期天为一周的第一天
                                int d = 7 * (a + 1) - (dayOfWeekInMonthFirst - 1) - (7 - (b + 1));
                                //解决点击区域无日期的事件
                                if (a == 0 && b < dayOfWeekInMonthFirst - 1) {//第一周无效日期处理
                                    Toast.makeText(getContext(), "您点击的区域没有日期，请重新选择吧！！", Toast.LENGTH_SHORT).show();
                                } else if (a == weekCount - 1 && d > daysOfMonth) {
                                    Toast.makeText(getContext(), "您点击的区域超过当前日期数，请重新选择吧！！", Toast.LENGTH_SHORT).show();
                                } else {
                                    listener.onDateClick(year, month + 1, d);
                                    Calendar calendar1 = Calendar.getInstance();
                                    calendar1.set(year, month, d);
                                    defCalendar = calendar1;//重写默认值
                                    invalidate();//刷新

                                }

                            } else {
                                int d2, f1;
                                if (dayOfWeekInMonthFirst == 1) {//礼拜日特别处理
                                    f1 = 7;
                                    d2 = 7 * (a + 1) - 6 - (7 - (b + 1));

                                } else {
                                    f1 = dayOfWeekInMonthFirst - 1;
                                    d2 = 7 * (a + 1) - (dayOfWeekInMonthFirst - 2) - (7 - (b + 1));
                                }
                                //解决点击区域无日期的事件
                                if (a == 0 && b < f1 - 1) {//第一周无效日期处理
                                    Toast.makeText(getContext(), "您点击的区域没有日期，请重新选择吧！！", Toast.LENGTH_SHORT).show();
                                } else if (a == weekCount - 1 && d2 > daysOfMonth) {
                                    Toast.makeText(getContext(), "您点击的区域超过当前日期数，请重新选择吧！！", Toast.LENGTH_SHORT).show();
                                } else {
                                    listener.onDateClick(year, month + 1, d2);
                                    Calendar calendar2 = Calendar.getInstance();
                                    calendar2.set(year, month, d2);
                                    defCalendar = calendar2;//置写默认值
                                    invalidate();//刷新

                                }
                            }
                        }

                    }

                }

                break;//跳出

        }
        return super.onTouchEvent(event);
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
        //重新获取calendar的实例是防止修改原calendar的值
        Calendar ca = Calendar.getInstance();
        ca.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DATE));
        return ca.get(Calendar.DAY_OF_WEEK);
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
        //重新获取calendar的实例是防止修改原calendar的值
        Calendar ca = Calendar.getInstance();
        ca.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), 1);
        return getDayOfWeek(ca);
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
        //初始化相关参数
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        date = calendar.get(Calendar.DATE);

        return this;
    }

    /**
     * 设置默认选中的日期
     *
     * @param defCalendar
     * @return
     */
    public MonthView setDefSelectedDay(Calendar defCalendar) {
        this.defCalendar = defCalendar;
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

    /**
     * 标题样式
     * 作用：提供了四种标题样式，分别对应 TITLE_LEFT(左对齐)  TITLE_CENTER(居中对齐) TITLE_RIGHT(右对齐) NO_TITLE(无标题)
     *
     * @param titleStyle 标题样式
     * @return 本view实例
     */
    public MonthView setTitleStyle(Style titleStyle) {
        this.titleStyle = titleStyle;
        return this;
    }

    /**
     * 设置本月份view的样式
     * <p>
     * 作用：Style.SUNDAY_STYLE 该样式，礼拜日作为第一天；Style.MONDAY_STYLE 该样式礼拜一作为第一天
     *
     * @param monthStyle 样式
     * @return 本view实例
     */
    public MonthView setMonthStyle(Style monthStyle) {
        this.monthStyle = monthStyle;
        return this;
    }

    /**
     * 设置标题高度
     *
     * @param titleHeight 高度
     * @return 本view实例
     */
    public MonthView setTitleHeight(float titleHeight) {
        this.titleHeight = titleHeight;
        return this;
    }

    /**
     * 设置标题文字的大小
     *
     * @param titleTextSize 大小
     * @return 本view实例
     */
    public MonthView setTitleTextSize(int titleTextSize) {
        this.titleTextSize = titleTextSize;
        return this;
    }

    /**
     * 设置周的标识title
     *
     * @param openWeek 控制开关
     * @return 本view实例
     */
    public MonthView setOpenWeek(boolean openWeek) {
        this.openWeek = openWeek;
        return this;
    }

    /**
     * 设置滑动切换开关
     * <p>
     * 作用：用于控制滑动切换日历，左滑切换到下个月，右滑切换到上个月
     * 上滑切换切换到下一年，下滑切换到下一年
     *
     * @param openScrollSwitch 滑动切换开关
     * @return 本view实例
     */
    public MonthView setOpenScrollSwitch(boolean openScrollSwitch) {
        this.openScrollSwitch = openScrollSwitch;
        return this;
    }

    /**
     * 向外界提供设置监听接口的方法
     *
     * @param listener 监听实例
     */
    public MonthView setOnDateClicktListener(OnDateClickListener listener) {
        this.listener = listener;
        return this;
    }

    /**
     * 向外界提供设置点击选中背景的方法，如果没有设置默认图片背景，则采用默认形式
     *
     * @param id 资源id
     */
    public MonthView setSelectedBackground(int id) {
        selectedBitmap = BitmapFactory.decodeResource(getResources(), id);
        return this;
    }

    /**
     * 向外界提供设置点击选中背景的方法，如果没有设置默认图片背景，则采用默认形式
     *
     * @param bitmap 位图
     */
    public MonthView setSelectedBackground(Bitmap bitmap) {
        selectedBitmap = bitmap;
        return this;
    }

    /**
     *
     * 设置农历显示的样式
     * 这里提供三个样式： NO_LUNAR_HOLIDAY(不显示农历，不显示节假日) , NO_LUNAR(不显示农历显示节假日) , LUNAR_HOLIDAY(显示农历和节假日)
     *
     * 注意：该样式的设置，当要显示节假日的时候我们必须要设置节假日定义资源，否则将无法生效
     *
     * @param showLunarStyle 样式
     * @return 本view实例
     */
    public MonthView setShowLunarStyle(Style showLunarStyle) {
        this.showLunarStyle = showLunarStyle;
        return this;
    }
}
