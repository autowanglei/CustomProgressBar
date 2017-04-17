package com.autowanglei.progressbar.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;

import com.autowanglei.progressbar.vo.CoordinateVO;

/**
 * 仿iphone带进度的进度条，线程安全的View，可直接在线程中更新进度
 */
public class RollProgressBar extends RoundBaseProgressBar {

    /**
     * 滚动圆弧起始角度，12点钟方向为0度
     */
    private float rollStartAngle = 0;
    private Canvas mCanvas = null;

    private Runnable mLongPressRunnable = new Runnable() {
        public void run() {
            postInvalidate();
            postDelayed(mLongPressRunnable, rollInterval);
        }
    };

    @Override
    public synchronized void setProgress(int progress) {
        super.setProgress(progress);
    }

    public RollProgressBar(Context context) {
        super(context, null);
    }

    public RollProgressBar(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void sartUpdateProgress() {
        post(mLongPressRunnable);
    }

    public void stopUpdateProgress() {
        removeCallbacks(mLongPressRunnable);
    }


    /**
     * 画进度百分比
     *
     * @param canvas
     * @param paint
     * @param centre
     * @param radius
     * @param percent
     * @param textSize
     * @param textColor
     */
    @Override
    protected void drawProgressText(Canvas canvas, Paint paint, float centre, float radius,
                                    int percent, float textSize, int textColor) {
        super.drawProgressText(canvas, paint, centre, radius, percent, textSize, textColor);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (null == progressOval) {
            radius = (int) (centre - progressWidth / 2); // 圆环的半径
            progressRadius = (int) (centre * 0.85 - progressWidth / 2);
            progressOval = new RectF(centre - progressRadius,
                    centre - progressRadius, centre + progressRadius,
                    centre + progressRadius); // 用于定义的圆弧的形状和大小的界限
        }
        /** * 画进度条背景 */
        drawCircle(canvas, paint, new CoordinateVO(centre, centre),
                progressRadius, roundColor, progressWidth, Paint.Style.STROKE);
        /** * 画进度百分比 text */
        if (textIsDisplayable && style == STROKE) {
            drawProgressText(canvas, paint, centre, radius, (int) (((float) progress / (float)
                    maxValue) * 100), textSize, textColor);
        }
        if (null == mCanvas) {
            mCanvas = canvas;
        }
        float spcERadius = progressWidth / 2;
        drawRollCircle(canvas, paint, spcERadius, roundProgressColor, rollStartAngle);
        rollStartAngle += rollStep;
    }

    /**
     * @param canvas
     * @param paint
     * @param radius
     * @param color
     * @param rollStartAngle
     */
    private void drawRollCircle(Canvas canvas, Paint paint, float radius, int color,
                                double rollStartAngle) {
        CoordinateVO startCenter = getCircleCenterVO(rollStartAngle);
        drawCircle(canvas, paint, startCenter, radius, color, 0, Paint.Style.FILL);
        CoordinateVO endCenter = getCircleCenterVO(rollStartAngle + rollSweepAngle);
        drawCircle(canvas, paint, endCenter, radius, color, 0, Paint.Style.FILL);
        /** *画进度条 */
        drawArc(canvas, paint, style, color, progressWidth, (float) (rollStartAngle - 90),
                (float) rollSweepAngle, progressOval, progress);
    }
}