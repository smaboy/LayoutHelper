package com.example.smaboy.layouthelper.view;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextPaint;
import android.view.View;

/**
 * 类名: StickyDecoration
 * 类作用描述: java类作用描述
 * 作者: Smaboy
 * 创建时间: 2018/12/14 15:36
 */
public class StickyDecoration extends RecyclerView.ItemDecoration {

//    ItemDecoration 是一个抽象类，字面意思是 Item 的装饰，我们可以通过内部的绘制方法绘制装饰，它有三个需要实现的抽象方法（过时的方法不管）：
//
//    onDraw() ：该方法在 Canvas 上绘制内容作为 RecyclerView 的 Item 的装饰，会在 Item 绘制之前绘制，也就是说，如果该 Decoration 没有设置偏移的话，Item 的内容会覆盖该 Decoration。
//
//    onDrawOver() ：在 Canvas 上绘制内容作为 RecyclerView 的 Item 的装饰，会在 Item 绘制之后绘制 ，也就是说，如果该 Decoration 没有设置偏移的话，该 Decoration 会覆盖 Item 的内容。
//
//    getItemOffsets() ：为 Decoration 设置偏移。



    @Override
    public void onDraw(@NonNull Canvas c, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.onDraw(c, parent, state);
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
            outRect.bottom = 10;
            outRect.left = 10;
            outRect.right = 10;
        }else {
            outRect.top = 10;
            outRect.bottom = 10;
            outRect.left = 10;
            outRect.right = 10;
        }

    }
}
