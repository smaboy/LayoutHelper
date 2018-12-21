package com.example.smaboy.layouthelper.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * 类名: QuicklIndexBar
 * 类作用描述: 快速索引
 * 增加需求 ： 1.考虑到我们在使用过程中，索引可能比较少，这是我们需要对这个进行居中排列，
 *             这边我们可以设置默认为26，大于等于26的时候，我们设置充满高度，小于的时候进行居中排列
 *            2.准备做一个在该控件里面的字符被选中的时候，文字呈现爆炸般展开
 * 作者: Smaboy
 * 创建时间: 2018/12/14 16:21
 */
public class QuicklIndexBar extends View {



    public static final int DEFAULT_SELECTED_TYPE=0;//默认样式
    public static final int ENLARGE_SELECTED_TYPE=1;//放大样式
    public static final int BURST_SELECTED_TYPE=2;//爆炸样式
    private int selectedType=BURST_SELECTED_TYPE;//快速索引字符选中样式（为默认模式,即不做任何处理）
    private Context context;//上下文

    private Paint paint;

    private String[] data = new String[]{"A", "B", "C", "D"
            , "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T",
            "U", "V", "W", "X", "Y", "Z"};
    private int measuredWidth;
    private int measuredHeight;
    private List<Float> childNeedHeights = new ArrayList<>();//存储每个字符顶部的坐标值
    private int childMaxWidth;//字符能使用的最大宽度
    private int childMaxHeight;//字符能使用的最大高度
    private int textSize = 40;//字体大小默认40
    private int selectedPoint = -1;//选中的位置
    private int textColor = Color.BLACK;//字体大小默认40
    private int defaultCount = 26;

    private int textNeedMaxWidth;//字符数组中，需要最大的宽度

    //定义接口对象
    private OnFocusChangeStatusListener onFocusChangeStatusListener;

    //状态接口
    public interface OnFocusChangeStatusListener {
        /**
         * 点击监听
         *
         * @param index       位置
         * @param indexString 内容
         */
        void onItemClick(int index, String indexString);

        /**
         * 滑动监听
         *
         * @param index       当前滑动到的位置
         * @param indexString 当前滑动到的位置的内容
         */
        void onScroll(int index, String indexString);


        /**
         * 失去焦点的时候回调
         * eg: 手指离开视图区域
         */
        void onLoseFoucus();


    }

    //向外提供设置状态监听的方法
    public void setOnFocusChangeStatusListener(OnFocusChangeStatusListener onFocusChangeStatusListener) {
        this.onFocusChangeStatusListener = onFocusChangeStatusListener;
    }

    /**
     * 设置数据集
     *
     * @param data 数据集
     */
    public void setData(String[] data) {
        this.data = data;
        invalidate();
    }

    public QuicklIndexBar(Context context) {
        this(context, null);

    }

    public QuicklIndexBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public QuicklIndexBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;

        init();
    }

    private void init() {


        //初始化画笔(默认画笔)
        paint = new Paint();//画笔
        paint.setAntiAlias(true);//抗锯齿
        paint.setColor(textColor);//颜色
        paint.setTextSize(textSize);//文字大小
        paint.setStyle(Paint.Style.FILL);//填充

        //处理不同样式的背景
        switch (selectedType) {
            case DEFAULT_SELECTED_TYPE ://默认模式
                break;
            case ENLARGE_SELECTED_TYPE ://放大模式

                break;
            case BURST_SELECTED_TYPE ://爆炸模式
                //爆炸模式下我们最好将背景设置为透明色
                setBackgroundColor(Color.TRANSPARENT);

                break;
        }


    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthSize=MeasureSpec.getSize(widthMeasureSpec);
        int widthMode=MeasureSpec.getMode(widthMeasureSpec);
        int heightSize=MeasureSpec.getSize(heightMeasureSpec);
        int heightMode=MeasureSpec.getMode(heightMeasureSpec);

        if(data.length<=0) {
            return;
        }


        int width;
        //每个数组的字符能使用的最大高度

        int h =  data.length>=defaultCount ? (heightSize - getPaddingTop() - getPaddingBottom()) / data.length : (heightSize - getPaddingTop() - getPaddingBottom()) /defaultCount;//每个子view最大高度



        //考虑放大的字符
        switch (selectedType) {
            case DEFAULT_SELECTED_TYPE ://默认模式
                paint.setTextSize(textSize);
                break;
            case ENLARGE_SELECTED_TYPE ://放大模式

            case BURST_SELECTED_TYPE ://爆炸模式
                paint.setTextSize(textSize+20);
                break;
        }
        //这里我们算出数组中最大的文字宽度
        for(int i = 0; i < data.length; i++) {
            float value = paint.measureText(data[i]);//测量文字的宽度
            textNeedMaxWidth= (int) Math.max(textNeedMaxWidth,value);
        }

        width=h*2+textNeedMaxWidth;

        setMeasuredDimension(widthMode==MeasureSpec.EXACTLY ? widthSize : width,heightSize);

    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        measuredWidth = getMeasuredWidth();//获取容器的宽度
        measuredHeight = getMeasuredHeight();//获取容器的高度


    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        /**
         * 绘制文字之前，我们先了解一下文字绘制的方式
         * 参考博客 : https://blog.csdn.net/u014702653/article/details/51985821
         *
         */

        childMaxWidth = measuredWidth;//每个子view最大的宽度
        childMaxHeight =  data.length>=defaultCount ? (measuredHeight - getPaddingTop() - getPaddingBottom()) / data.length : (measuredHeight - getPaddingTop() - getPaddingBottom()) /defaultCount;//每个子view最大高度

        //开始绘制view的布局
        for (int i = 0; i < data.length; i++) {

            switch (selectedType) {
                case DEFAULT_SELECTED_TYPE ://默认模式
                    drawDefaultType(canvas, i);
                    break;
                case ENLARGE_SELECTED_TYPE ://放大模式

                    drawEnlargeType(canvas, i);
                    break;
                case BURST_SELECTED_TYPE ://爆炸模式
                    drawBurstType(canvas, i);


                    break;
            }



        }


    }

    private void drawEnlargeType(Canvas canvas, int i) {
        if(selectedPoint<0) {//当前没有选中的字符
            drawDefaultType(canvas,i);
        }else {
            //设置选中的文字的大小
            if(i==selectedPoint) {//在字符数组的范围内
                paint.setTextSize(textSize+20);
            }else {
                paint.setTextSize(textSize);
            }
//        //水平居中
//        float value1 = paint.measureText(data[i]);//测量文字的宽度
//        float startX = childMaxWidth / 2 - value1 / 2;
            //水平靠右对齐
            float value1 = paint.measureText(data[i]);//测量文字的宽度
            float startX = (float) (childMaxWidth-textNeedMaxWidth*0.5-value1*0.5-getPaddingEnd());
            //竖值居中,绘制文字从文字左下角开始,因此"+"
            Paint.FontMetrics fontMetrics = paint.getFontMetrics();
            float value2 = Math.abs((fontMetrics.bottom - fontMetrics.top));
            float startY = data.length>=defaultCount ? getPaddingTop()+childMaxHeight / 2 + value2 / 2 :(measuredHeight-childMaxHeight*data.length)/2+childMaxHeight / 2 + value2 / 2;

            //开始绘制
            canvas.drawText(data[i], startX, startY + childMaxHeight * i, paint);

            //将每个view的顶部位置存储起来
            childNeedHeights.add((float) (childMaxHeight * i));
        }


    }

    private void drawBurstType(Canvas canvas, int i) {
        //爆炸模式和默认模式的区别在于，当用户触发点击或者滑动事件后，当前所处位置的字符应该向外呈圆弧状显示，且所处位置的字符应该为圆弧的最外围
        if(selectedPoint<0) {//当前没有选中的字符
            drawDefaultType(canvas,i);
        }else {
            //此情况下，我们需要把对应的字符的上下两个字符，共五个字符，位置绘制成圆弧状（当下上两字符位置超过数组上下限做特殊处理）
            int a=selectedPoint-1;
            int b=selectedPoint;
            int c=selectedPoint+1;
            //圆弧半径为我们最大字符高度的两倍
            int r=childMaxHeight*2;


            //设置选中的文字的大小
            if(i==b) {//在字符数组的范围内
                paint.setTextSize(textSize+20);
            }else {
                paint.setTextSize(textSize);
            }

            //水平居中
//            float value1 = paint.measureText(data[i]);//测量文字的宽度
//            float startX = childMaxWidth / 2 - value1 / 2;
            //水平靠右对齐
            float value1 = paint.measureText(data[i]);//测量文字的宽度
//            float startX = childMaxWidth  - value1;
            float startX =  (float) (childMaxWidth-textNeedMaxWidth*0.5-value1*0.5-getPaddingEnd());
            //竖值居中,绘制文字从文字左下角开始,因此"+"
            Paint.FontMetrics fontMetrics = paint.getFontMetrics();
            float value2 = Math.abs((fontMetrics.bottom - fontMetrics.top));
            float startY = data.length>=defaultCount ? getPaddingTop()+childMaxHeight / 2 + value2 / 2 :(measuredHeight-childMaxHeight*data.length)/2+childMaxHeight / 2 + value2 / 2;
            if(a>=0&&i==a) {//在字符数组的范围内
                startX-= (float) Math.cos(Math.PI/4)*r;
                startY-=(r*Math.sin(Math.PI/4)-r*0.5);

            }
            if(i==b) {//在字符数组的范围内
                startX-= r;
            }
            if(c<=data.length-1&&i==c) {//在字符数组的范围内
                startX-= (float) Math.cos(Math.PI/4)*r;
                startY+=(r*Math.sin(Math.PI/4)-r*0.5);
            }

            //开始绘制
            canvas.drawText(data[i], startX, startY + childMaxHeight * i, paint);

            //将每个view的顶部位置存储起来
            childNeedHeights.add((float) (childMaxHeight * i));

        }

    }

    private void drawDefaultType(Canvas canvas, int i) {
        //将画笔大小回归默认大小
        paint.setTextSize(textSize);
//        //水平居中
//        float value1 = paint.measureText(data[i]);//测量文字的宽度
//        float startX = childMaxWidth / 2 - value1 / 2;
        //水平靠右对齐
        float value1 = paint.measureText(data[i]);//测量文字的宽度
        float startX = (float) (childMaxWidth-textNeedMaxWidth*0.5-value1*0.5-getPaddingEnd());
        //竖值居中,绘制文字从文字左下角开始,因此"+"
        Paint.FontMetrics fontMetrics = paint.getFontMetrics();
        float value2 = Math.abs((fontMetrics.bottom - fontMetrics.top));
        float startY = data.length>=defaultCount ? getPaddingTop()+childMaxHeight / 2 + value2 / 2 :(measuredHeight-childMaxHeight*data.length)/2+childMaxHeight / 2 + value2 / 2;

        //开始绘制
        canvas.drawText(data[i], startX, startY + childMaxHeight * i, paint);

        //将每个view的顶部位置存储起来
        childNeedHeights.add((float) (childMaxHeight * i));
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float mx = event.getX();
//        该判断用于处理当触点在默认字符排列的左侧时，特别实在爆炸模式下，对触摸事件的消费，该处理能有效控制触摸事件只能在字符所在的 区域消费，而不包含爆炸模式下等超过字符列的区域
        if(mx<measuredWidth-textNeedMaxWidth) {
            if(selectedPoint!=-1) {
                selectedPoint=-1;
                //设置监听回调（由于脱离设置区域UP事件不能回调，所以在这也得设置监听回调）
                if (onFocusChangeStatusListener != null) {
                    onFocusChangeStatusListener.onLoseFoucus();
                }
                invalidate();
            }
            return false;//不消费
        }

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (setActionDown(event)) return true;


                return true;

            case MotionEvent.ACTION_MOVE:
                if (setActionMove(event)) return true;


                return true;
            case MotionEvent.ACTION_UP:
                setActionUp(event);


                return true;

        }

        return super.onTouchEvent(event);
    }

    private boolean setActionDown(MotionEvent event) {
        //获取点击的坐标
        float x = event.getX();
        float y = event.getY();

        Log.e("TAG", "触发ACTION_DOWN事件了--x==" + x + "--y==" + y);
        int index;
        //判断点击的位置在那个字符区域
        if(data.length>=defaultCount ) {

            index = (int) (y / childMaxHeight);

        }else {
            if((y-(measuredHeight-childMaxHeight*data.length)/2)<0) {//防止index在-1和0之间取值为0
                return true;
            }
            index=(int) ((y-(measuredHeight-childMaxHeight*data.length)/2) / childMaxHeight);
        }

        //防止数组越界，这里我们需做一个处理
        if(index>=data.length||index<0) {
            return true;
        }

        //记录此时的位置
        selectedPoint=index;

        //设置监听回调
        if (onFocusChangeStatusListener != null) {
            onFocusChangeStatusListener.onItemClick(index, data[index]);
        }

        Toast.makeText(context, "index=" + index + "--" + data[index], Toast.LENGTH_SHORT).show();
        invalidate();//刷新
        return false;
    }

    private boolean setActionMove(MotionEvent event) {
        //获取移动时点击的坐标
        float x1 = event.getX();
        float y1 = event.getY();
        Log.e("TAG", "触发ACTION_MOVE事件了--x==" + x1 + "--y==" + y1);
        int index1;
        //判断点击的位置在那个字符区域
        if(data.length>=defaultCount ) {

            index1 = (int) (y1 / childMaxHeight);

        }else {
            if((y1-(measuredHeight-childMaxHeight*data.length)/2)<0) {//防止index在-1和0之间取值为0
                return true;
            }
            index1=(int) ((y1-(measuredHeight-childMaxHeight*data.length)/2) / childMaxHeight);
        }

        //防止数组越界，这里我们需做一个处理
        if(index1>=data.length||index1<0) {
            return true;
        }

        //记录此时的位置
        selectedPoint=index1;

        //设置监听回调
        if (onFocusChangeStatusListener != null) {
            onFocusChangeStatusListener.onScroll(index1, data[index1]);
        }

        Log.e("TAG", "滑动中的位置" + "index1=" + index1 + "--" + data[index1]);

        invalidate();//刷新
        return false;
    }

    private void setActionUp(MotionEvent event) {
        //获取移动时点击的坐标
        float x2 = event.getX();
        float y2 = event.getY();
        Log.e("TAG", "触发ACTION_UP事件了--x==" + x2 + "--y==" + y2);
        //设置监听回调
        if (onFocusChangeStatusListener != null) {
            onFocusChangeStatusListener.onLoseFoucus();
        }

        Log.e("TAG", "手指离开了");


        //记录此时的位置
        selectedPoint=-1;

        invalidate();//刷新
    }


}
