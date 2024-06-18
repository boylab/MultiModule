package com.boylab.multimodule.util;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class GridDecoration extends RecyclerView.ItemDecoration {

    private boolean isStandard = true;

    private int color = Color.GRAY;
    private boolean isDrawTop = false;
    private boolean isDrawSide = false; //左右两边

    private int offset = 2;
    private Paint mPaint;
    private Rect mRect = new Rect();

    /**
     * 粗略绘制实现右、下画线
     * @param color
     */
    public GridDecoration(int color) {
        this(color, 1, false, false);
        this.isStandard = false;
    }

    /**
     * 下列方法实现标准列表
     * @param color
     */
    public GridDecoration(int color, boolean isDrawTop, boolean isDrawSide) {
        this(color, 1, isDrawTop, isDrawSide);
    }

    public GridDecoration(int color, int offset, boolean isDrawTop, boolean isDrawSide) {
        this.color = color;
        this.offset = offset;
        this.isDrawTop = isDrawTop;
        this.isDrawSide = isDrawSide;
        initPaint();
    }

    private void initPaint(){
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(this.color);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setStrokeWidth(this.offset);
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDraw(c, parent, state);
        for (int i = 0; i < parent.getChildCount(); i++) {
            View view = parent.getChildAt(i);
            //得到Rect
            parent.getDecoratedBoundsWithMargins(view, mRect);
            c.drawRect(mRect, mPaint);
        }
    }

    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        if (!isStandard){
            outRect.set(0, 0, offset, offset);
            return;
        }
        GridLayoutManager manager = (GridLayoutManager) parent.getLayoutManager();
        int size = parent.getChildCount();
        int spanCount = manager.getSpanCount();
        int row = size/spanCount;

        int height = isDrawTop ? offset : 0;
        int width = isDrawSide ? offset : 0;

        //得到View的位置
        int position = parent.getChildAdapterPosition(view);

        if (position < spanCount) {
            if (position == 0) {
                outRect.set(width, height, offset, offset);
            }else if (position < spanCount-1){
                outRect.set(0, height, offset, offset);
            }else{
                outRect.set(0, height, width, offset);
            }
        }else if (position < row * spanCount){
            if (position % spanCount == 0) {
                outRect.set(width, 0, offset, offset);
            }else if (position % spanCount < spanCount-1){
                outRect.set(0, 0, offset, offset);
            }else{
                outRect.set(0, 0, width, offset);
            }
        }else {
            if (position % spanCount == 0) {
                outRect.set(width, 0, offset, offset);
            }else if (position % spanCount < spanCount-1){
                outRect.set(0, 0, offset, offset);
            }else{
                outRect.set(0, 0, width, offset);
            }
        }
    }
}
