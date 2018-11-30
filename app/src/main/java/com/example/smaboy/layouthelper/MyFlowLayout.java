package com.example.smaboy.layouthelper;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * 类名: MyFlowLayout
 * 类作用描述: 流式布局
 * 作者: Smaboy
 * 创建时间: 2018/11/30 11:22
 */
public class MyFlowLayout extends ViewGroup {


    private List<View> lineChildViews=new ArrayList<>();
    private List<List<View>> allLineChildView=new ArrayList<>();
    private List<Integer> lineHeights=new ArrayList<>();


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
    protected void onLayout(boolean changed, int l, int t, int r, int b) {

        //遍历子类进行布局
        int childCount = getChildCount();

        int cl=0;
        int ct=0;
        int cr=0;
        int cb=0;

//        for(int i = 0; i < childCount; i++) {
//
//            View childView = getChildAt(i);//获取子view
//            int measuredWidth = childView.getMeasuredWidth();
//            int measuredHeight = childView.getMeasuredHeight();
//
//
//            //获取子view布局的参数
//            cl=0;
//            ct=cb+10;
//            cr=measuredWidth;
//            cb+=measuredHeight;
//
//
//            childView.layout(cl,ct,cr,cb);
//
//        }

        for(int i = 0; i < allLineChildView.size(); i++) {//行
            List<View> lineView = allLineChildView.get(i);
            for(int j = 0; j < lineView.size(); j++) {
                View view = lineView.get(j);

                MarginLayoutParams layoutParams = (MarginLayoutParams) view.getLayoutParams();


                cl=cr+(j==0 ? layoutParams.leftMargin : 0);
                ct=(i==0 ? 0 : lineHeights.get(i-1))+layoutParams.topMargin;
                cr=cl+view.getMeasuredWidth()+layoutParams.leftMargin+layoutParams.rightMargin;
                cb=ct+view.getMeasuredHeight()+layoutParams.topMargin+layoutParams.bottomMargin;

                //设置子view的布局
                view.layout(cl,ct,cr,cb);

            }


        }



    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int widthMeasureMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthMeasureSize = MeasureSpec.getSize(widthMeasureSpec)-getPaddingLeft()-getPaddingRight();//容器里面内容需要的宽度
        int heightMeasureMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightMeasuresize = MeasureSpec.getSize(heightMeasureSpec)-getPaddingTop()-getPaddingBottom();//容器里面内容需要的高度

        int childCount = getChildCount();
        int curWidth=0;//用于设置容器的宽度
        int curHeight=0;//由于设置容器的高度
        int lineWidth=0;
        int lineHeight=0;
        
        if(widthMeasureMode==MeasureSpec.EXACTLY&&heightMeasureMode==MeasureSpec.EXACTLY) {//宽高设置了固定值
            curWidth=widthMeasureSize;
            curHeight=heightMeasuresize;

        }else {
            for(int i = 0; i < childCount; i++) {

                View childAt = getChildAt(i);//获取子view

                measureChild(childAt,widthMeasureSpec,heightMeasureSpec);//测量子view（这一步必须走，不然拿不到子view的宽高）

                MarginLayoutParams layoutParams = (MarginLayoutParams) childAt.getLayoutParams();
                int childNeedWidth = childAt.getMeasuredWidth()+layoutParams.leftMargin+layoutParams.rightMargin;//子view需要的宽度
                int childNeedHeight = childAt.getMeasuredHeight()+layoutParams.bottomMargin+layoutParams.topMargin;//子view需要的高度




                if(lineWidth>widthMeasureSize) {//添加子view后，当前行长度大于容器的长度（换行条件）

                    curWidth=widthMeasureSize;//设置容器的宽度
                    curHeight+=lineHeight;//设置容器的高度

                    allLineChildView.add(lineChildViews);//添加当前的行到行集合view中
                    lineHeights.add(lineHeight);//添加当前行的行高到行高集合

                    lineChildViews=new ArrayList<>();//新建行容器
                    lineChildViews.add(childAt);//添加子view到集合

                    lineWidth=childNeedWidth;//设置行宽
                    lineHeight=childNeedHeight;//设置行高












                }else {//当前行可以添加当前的子view

                    lineChildViews.add(childAt);//将当前子view添加到行容器
                    lineWidth+=childNeedWidth;//不需要换行的话，行宽直接累加子view需要的宽度
                    lineHeight=Math.max(lineHeight,childNeedHeight);//不需要换行的话，通过取当前行高和子view行高的最打值作为新的行高

                    curWidth=Math.max(curWidth,lineWidth);//设置容器需要的宽度

                }

                if(i==childCount-1) {
                    allLineChildView.add(lineChildViews);//添加当前的行到行集合view中
                    lineHeights.add(lineHeight);//添加当前行的行高到行高集合
                    curHeight+=lineHeight;

                }





            }

        }




        //设置容器的测量尺寸
        setMeasuredDimension(curWidth,curHeight);


    }

    @Override
    protected LayoutParams generateDefaultLayoutParams() {
        return new MarginLayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
    }

    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new MarginLayoutParams(getContext(),attrs);
    }

    @Override
    protected LayoutParams generateLayoutParams(LayoutParams p) {
        return new MarginLayoutParams(p);
    }


}
