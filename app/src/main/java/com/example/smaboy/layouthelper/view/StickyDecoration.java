package com.example.smaboy.layouthelper.view;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * 类名: StickyDecoration
 * 类作用描述: java类作用描述
 * 作者: Smaboy
 * 创建时间: 2018/12/14 15:36
 */
public class StickyDecoration extends RecyclerView.ItemDecoration {
    private  static Paint paint;//红色画笔
    private  static Paint wPaint;//白色画笔
    private  static Paint bPaint;//灰色画笔

//    ItemDecoration 是一个抽象类，字面意思是 Item 的装饰，我们可以通过内部的绘制方法绘制装饰，它有三个需要实现的抽象方法（过时的方法不管）：
//
//    onDraw() ：该方法在 Canvas 上绘制内容作为 RecyclerView 的 Item 的装饰，会在 Item 绘制之前绘制，也就是说，如果该 Decoration 没有设置偏移的话，Item 的内容会覆盖该 Decoration。
//
//    onDrawOver() ：在 Canvas 上绘制内容作为 RecyclerView 的 Item 的装饰，会在 Item 绘制之后绘制 ，也就是说，如果该 Decoration 没有设置偏移的话，该 Decoration 会覆盖 Item 的内容。
//
//    getItemOffsets() ：为 Decoration 设置偏移。


    static  {
        //在初始化时，先将一些设置构造出来
        paint=new Paint();
        paint.setAntiAlias(true);
        paint.setColor(Color.parseColor("#F6D4B8"));

        wPaint=new Paint();
        wPaint.setAntiAlias(true);
        wPaint.setColor(Color.WHITE);

        bPaint=new Paint();
        bPaint.setAntiAlias(true);
        bPaint.setColor(Color.GRAY);
    }

    @Override
    public void onDraw(@NonNull Canvas c, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.onDraw(c, parent, state);

        int childCount = parent.getChildCount();
        for(int i = 0; i < childCount; i++) {

            View child = parent.getChildAt(i);

            //画上部的红色背景(顶部高度30)
            c.drawRect(parent.getLeft(),child.getTop()-30,child.getRight(),child.getTop(),paint);
            //画左侧红色背景矩形
            c.drawRect(parent.getLeft(),child.getTop(),parent.getLeft()+80,child.getBottom(),paint);

            //画左侧的白色矩形(矩形的宽默认为10px，高度等同于item)
            c.drawRect(parent.getLeft()+35,child.getTop()-30,parent.getLeft()+45,child.getBottom(),wPaint);

            //画错左侧的圆（半径默认为20）
            c.drawCircle(parent.getLeft()+40,child.getTop()+child.getHeight()/2,20,wPaint);

            //画圆环
            bPaint.setStyle(Paint.Style.STROKE);
            bPaint.setStrokeWidth(2);
            c.drawCircle(parent.getLeft()+40,child.getTop()+child.getHeight()/2,20,bPaint);

        }
    }


    @Override
    public void onDrawOver(@NonNull Canvas c, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.onDrawOver(c, parent, state);

    }

    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);

        //outRect 相当于 Item 的整体绘制区域,设置 left、top、right、bottom 相当于设置左上右下的内间距
        if(parent.getChildAdapterPosition(view)!=0) {
            outRect.top = 30;
        }
        outRect.left = 80;


    }
}
