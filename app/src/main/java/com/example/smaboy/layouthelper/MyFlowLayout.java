package com.example.smaboy.layouthelper;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 类名: MyFlowLayout
 * 类作用描述: 流式布局
 * 作者: Smaboy
 * 创建时间: 2018/11/30 11:22
 */
public class MyFlowLayout extends ViewGroup {


    private List<List<View>> allLineViews = new ArrayList<>();//行集合
    private List<View> lineView = new ArrayList<>();//行集合
    private List<Integer> lineHeights = new ArrayList<>();//行高的集合
    private List<Integer> lineWidths = new ArrayList<>();//行宽的集合

    public static final int LEFT = 0;
    public static final int CENTER = 1;
    public static final int RIGHT = 2;
    private   int curGravityType = LEFT;


    public MyFlowLayout(Context context) {
        super(context);
    }

    public MyFlowLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyFlowLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

//        注：在该方法中我们需要确定我们的流式布局容器的测量尺寸，即他的宽高，通过方法setMeasuredDimension()进行设置
//        MeasureSpec.EXACTLY -表示父控件已经确切的指定了子View的大小。
//        MeasureSpec.AT_MOST - 表示子View具体大小没有尺寸限制，但是存在上限，上限一般为父View大小。
//        MeasureSpec.UNSPECIFIED -父控件没有给子view任何限制，子View可以设置为任意大小。

        int widthSize = MeasureSpec.getSize(widthMeasureSpec);//容器的测量宽度
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);//容器的测量宽度模式
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);

        int width = 0;
        int height = 0;

        int lineWidth = 0;//行宽
        int lineHeight = 0;//行高

        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {

            //获取子view
            View childView = getChildAt(i);

            //判断子view是否显示
            if (childView.getVisibility() == GONE) {
                if (i == childCount - 1) {
                    width = Math.max(width, lineWidth);//判别当前行宽和已经设置的宽度谁大，取值大的重新设置为宽度
                    height += lineHeight;
                }

                continue;
            }


            //测量子view，通过该方法我们才能获得子view的测量宽高
            measureChild(childView, widthMeasureSpec, heightMeasureSpec);

            //考虑到，我们需要获取子view的外边距，但LayoutParams布局属性不提供外边距的获取方法,所以我们需要获取MarginLayoutParams布局属性;
            //view的布局属性是指view所在的父容器的布局属性，因此在此自定义容器中，我们需要重写MarginLayoutParams获取方法
            MarginLayoutParams layoutParams = (MarginLayoutParams) childView.getLayoutParams();

            int childNeedWidth = childView.getMeasuredWidth() + layoutParams.leftMargin + layoutParams.rightMargin;
            int childNeedHeight = childView.getMeasuredHeight() + layoutParams.topMargin + layoutParams.bottomMargin;

            //换行逻辑(当前子view所需要的宽度加上已使用的行宽大于容器内容最大宽度)
            if (lineWidth + childNeedWidth > widthSize - getPaddingLeft() - getPaddingRight()) {
                //换行
                width = Math.max(width, lineWidth);//判别当前行宽和已经设置的宽度谁大，取值大的重新设置为宽度
                height += lineHeight;
                lineWidth = childNeedWidth;
                lineHeight = childNeedHeight;

            } else {
                lineWidth += childNeedWidth;
                lineHeight = Math.max(lineHeight, childNeedHeight);

            }

            //考虑到（1.只有一行的情况 2.多行的情况，最后一个子view所在的行，没进行宽高的设置）
            //这里还有一个隐性问题，就是当最后一个子view为GONE的时候，我们实际是走不到这里的，
            // 这就导致，我们少计算了一行的高度，也可能导致容器需要的宽度过小，因此我们还得在判读显隐的时候再做一次宽高处理
            if (i == childCount - 1) {
                width = Math.max(width, lineWidth);//判别当前行宽和已经设置的宽度谁大，取值大的重新设置为宽度
                height += lineHeight;
            }


        }

        //设置测量尺寸
        setMeasuredDimension(widthMode == MeasureSpec.EXACTLY ? widthSize : width + getPaddingLeft() + getPaddingRight(),
                heightMode == MeasureSpec.EXACTLY ? heightSize : height + getPaddingTop() + getPaddingBottom());


    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {

        //在该方法中我们需要对子view进行布局，在布局之前我们需要做一件事，就是确定子view排列的行数和其中每行的数量

        //清空集合
        allLineViews.clear();
        lineView.clear();
        lineHeights.clear();
        lineWidths.clear();

        //获取容器宽度
        int width = getWidth();

        int lineWidth = 0;//行宽
        int lineHeight = 0;//行高

        //获取子view数量
        int childCount = getChildCount();

        for (int i = 0; i < childCount; i++) {

            //获取子view
            View childView = getChildAt(i);

            if (childView.getVisibility() == View.GONE) {
                continue;
            }

            //因为在onmeasure中我们已经测量过子view了，所以在这里我们就不重复测量了
            //获取布局属性
            MarginLayoutParams layoutParams = (MarginLayoutParams) childView.getLayoutParams();

            int childNeedWidth = childView.getMeasuredWidth() + layoutParams.leftMargin + layoutParams.rightMargin;
            int childNeedHeight = childView.getMeasuredHeight() + layoutParams.topMargin + layoutParams.bottomMargin;

            //换行条件判别
            if (lineWidth + childNeedWidth > width - getPaddingLeft() - getPaddingRight()) {
                //换行，将行加入行集合，将行宽、高分别加入对应集合
                allLineViews.add(lineView);
                lineHeights.add(lineHeight);
                lineWidths.add(lineWidth);

                //新建行
                lineView = new ArrayList<>();
                lineHeight = childNeedHeight;
                lineWidth = 0;


            }

            lineHeight = Math.max(lineHeight, childNeedHeight);
            lineWidth += childNeedWidth;
            lineView.add(childView);


        }

        //跳出上面循环的时候，最后一行没有添加，一次还需要加上这几行代码
        allLineViews.add(lineView);
        lineHeights.add(lineHeight);
        lineWidths.add(lineWidth);

        //-------------------------开始设置子view的布局------------------------------------//


        int left = getPaddingLeft();//设置子view时候需要的坐标
        int top = getPaddingTop();//设置子view时候需要的坐标
        int lineNum = allLineViews.size();//行数
        for (int i = 0; i < lineNum; i++) {

            List<View> mLine = allLineViews.get(i);
            int mLineHeight = lineHeights.get(i);
            int mLineWidth = lineWidths.get(i);

            //这里我们提供三个对齐方式(左对齐，居中对齐，右对齐，默认左对齐)
            switch (curGravityType) {
                case LEFT:
                    left = getPaddingLeft();
                    break;
                case CENTER:
                    left = (width - mLineWidth) / 2 + getPaddingLeft();
                    break;
                case RIGHT:
                    //  适配了rtl，需要补偿一个padding值
                    left = width - (mLineWidth + getPaddingLeft()) - getPaddingRight();
                    //  适配了rtl，需要把lineViews里面的数组倒序排
                    Collections.reverse(lineView);
                    break;

                default:
                    left = getPaddingLeft();
                    break;

            }


            for (int j = 0; j < mLine.size(); j++) {
                //获取子view
                View mChild = mLine.get(j);


                if (mChild.getVisibility() == View.GONE) {
                    continue;
                }

                //获取布局属性
                MarginLayoutParams layoutParams = (MarginLayoutParams) mChild.getLayoutParams();

                //子view需要的宽
                int mChildNeedWidth = mChild.getMeasuredWidth() + layoutParams.leftMargin + layoutParams.rightMargin;

                int cl = left+layoutParams.leftMargin;
                int ct = top+layoutParams.topMargin;
                int cr = cl + mChild.getMeasuredWidth();
                int cb = ct + mChild.getMeasuredHeight();

                Log.e("MyFlowLayout",cl+"--"+ct+"--"+cr+"--"+cb);
                //设置子view的布局
                mChild.layout(cl, ct, cr, cb);

                //设置完一个后left须增加
                left += mChildNeedWidth;


            }
            //换行之后，top须增加
            top += mLineHeight;
        }


    }


    @Override
    protected LayoutParams generateDefaultLayoutParams() {
        return new MarginLayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
    }

    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new MarginLayoutParams(getContext(), attrs);
    }

    @Override
    protected LayoutParams generateLayoutParams(LayoutParams p) {
        return new MarginLayoutParams(p);
    }

    public void setGravity(int curGravityType) {
        this.curGravityType = curGravityType;
        requestLayout();

    }
}
