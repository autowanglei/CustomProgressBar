package com.autowanglei.progressbar.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.ProgressBar;

public class StraightProgressBar extends ProgressBar {

    /**
     * 百分比字符的大小（单位sp）
     */
    private final static int PERCENTAGE_TEXT_SIZE = 12;
    private String text = "0%";
    private Paint mPaint;

    public StraightProgressBar(Context context) {
        super(context);
        initText();
    }

    public StraightProgressBar(Context context, AttributeSet attrs,
                               int defStyle) {
        super(context, attrs, defStyle);
        initText();
    }

    public StraightProgressBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        initText();
    }

    @Override
    public Drawable getProgressDrawable() {
        return super.getProgressDrawable();
    }

    @Override
    public void setProgressDrawable(Drawable d) {
        super.setProgressDrawable(d);
    }

    @Override
    public synchronized void setProgress(int progress) {
        text = progress + "%";
        super.setProgress(progress);
    }

    @Override
    public synchronized void setSecondaryProgress(int secondaryProgress) {
        super.setSecondaryProgress(secondaryProgress);
    }

    @Override
    public synchronized int getProgress() {
        return super.getProgress();
    }

    @Override
    public synchronized int getSecondaryProgress() {
        return super.getSecondaryProgress();
    }

    @Override
    public synchronized int getMax() {
        return super.getMax();
    }

    @Override
    public synchronized void setMax(int max) {
        super.setMax(max);
    }

    private int sp2px(Context context, float spValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (spValue * scale + 0.5f);
    }

    // 初始化，画笔
    private void initText() {
        this.mPaint = new Paint();
        this.mPaint.setColor(Color.WHITE);
        this.mPaint.setTextSize(sp2px(getContext(), PERCENTAGE_TEXT_SIZE));
        this.mPaint.setAntiAlias(true);
    }

    @Override
    protected synchronized void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        // this.setText();
        Rect rect = new Rect();
        this.mPaint.getTextBounds(this.text, 0, this.text.length(), rect);
        int x = (getWidth() / 2) - rect.centerX();
        int y = (getHeight() / 2) - rect.centerY();
        canvas.drawText(this.text, x, y, this.mPaint);
    }
}
