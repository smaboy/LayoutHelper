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
 * 这边我们可以设置默认为26，大于等于26的时候，我们设置充满高度，小于的时候进行居中排列
 *            2.准备做一个在该控件里面的字符被选中的时候，文字呈现爆炸般展开
 * 作者: Smaboy
 * 创建时间: 2018/12/14 16:21
 */
public class QuicklIndexBar extends View {



    private int selectedType=0;//快速索引字符选中样式（为默认模式,即不做任何处理）
    private Context context;

    private Paint paint;

    private String[] data = new String[]{"A", "B", "C", "D"
            , "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T",
            "U", "V", "W", "X", "Y", "Z"};
    private int measuredWidth;
    private int measuredHeight;
    private List<Float> childNeedHeights = new ArrayList<>();//存储每个字符顶部的坐标值
    private int childMaxWidth;//字符最大宽度
    private int childMaxHeight;//字符最大高度
    private int textSize = 40;//字体大小默认40
    private int selectedPoint = -1;//选中的位置
    private int textColor = Color.BLACK;//字体大小默认40
    private int defaultCount = 26;

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
         *
         *
         */

        childMaxWidth = measuredWidth;//每个子view最大的宽度
        childMaxHeight =  data.length>=defaultCount ? (measuredHeight - getPaddingTop() - getPaddingBottom()) / data.length : (measuredHeight - getPaddingTop() - getPaddingBottom()) /defaultCount;//每个子view最大高度

        //开始绘制view的布局
        for (int i = 0; i < data.length; i++) {

            //水平居中
            float value1 = paint.measureText(data[i]);//测量文字的宽度
            float startX = childMaxWidth / 2 - value1 / 2;
            //竖值居中,绘制文字从文字左下角开始,因此"+"
            Paint.FontMetrics fontMetrics = paint.getFontMetrics();
            float value2 = Math.abs((fontMetrics.bottom - fontMetrics.top));
            float startY = data.length>=defaultCount ? childMaxHeight / 2 + value2 / 2 :(measuredHeight-childMaxHeight*data.length)/2+childMaxHeight / 2 + value2 / 2;

            //开始绘制
            canvas.drawText(data[i], startX, startY + childMaxHeight * i, paint);

            //将每个view的顶部位置存储起来
            childNeedHeights.add((float) (childMaxHeight * i));


        }


    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:

                //设置背景
//                setBackgroundColor(Color.RED);

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

                return true;

            case MotionEvent.ACTION_MOVE:
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

                return true;
            case MotionEvent.ACTION_UP:
                //获取移动时点击的坐标
                float x2 = event.getX();
                float y2 = event.getY();
                Log.e("TAG", "触发ACTION_POINTER_UP事件了--x==" + x2 + "--y==" + y2);
                //设置监听回调
                if (onFocusChangeStatusListener != null) {
                    onFocusChangeStatusListener.onLoseFoucus();
                }

                Log.e("TAG", "手指离开了");


                //记录此时的位置
                selectedPoint=-1;

                invalidate();
                return true;
        }

        return super.onTouchEvent(event);
    }


}
