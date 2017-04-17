package com.autowanglei.progressbar.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;

import com.autowanglei.progressbar.vo.CoordinateVO;

/**
 * 仿iphone带进度的进度条，线程安全的View，可直接在线程中更新进度
 */
public class CircleProgressBar extends RoundBaseProgressBar {

    public CircleProgressBar(Context context) {
        super(context, null);
    }

    public CircleProgressBar(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public synchronized void setProgress(int progress) {
        super.setProgress(progress);
        postInvalidate();
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
        /** * 画进度条的整体圆形背景（View的内切圆） */
        drawCircle(canvas, paint, new CoordinateVO((float) centre, (float) centre), centre,
                Color.WHITE, centre, Paint.Style.FILL);
        /** * 画进度百分比 text */
        if (textIsDisplayable && style == STROKE) {
            drawProgressText(canvas, paint, centre, radius, (int) (((float) progress / (float)
                    maxValue) * 100), textSize, textColor);
        }
        double sweepAngle = 360 * progress / maxValue;
        /** *画进度条 */
        if (hasSpecialEffects)
            drawArc(canvas, paint, style, roundProgressColor, progressWidth, startAngle,
                    (float) sweepAngle, progressOval, progress);
        if (hasSpecialEffects) {
            float spcERadius = progressWidth / 2;
            CoordinateVO mCoordinateStart = new CoordinateVO(centre, centre - progressRadius);
            CoordinateVO mCoordinateEnd = getCircleCenterVO(sweepAngle);
            if (progress > 20) {
                /** 进度将近100，需要先画起始缺口，再画结束圆弧，以便结束圆弧能盖住起始缺口 */
                /** *画进度条起始缺口 */
                drawCircle(canvas, paint, mCoordinateStart, spcERadius, Color.WHITE, 0,
                        Paint.Style.FILL);
                /** * 画进度条结束位置的半圆弧 */
                drawCircle(canvas, paint, mCoordinateEnd, spcERadius, roundProgressColor, 0,
                        Paint.Style.FILL);
            } else {
                /** 进度开始时，需要先画结束圆弧，再画起始缺口， 以便起始缺口能盖住结束圆弧 */
                /** * 画进度条结束位置的半圆弧 */
                int color = roundProgressColor;
                if (0 == progress) {
                    color = Color.WHITE;
                }
                drawCircle(canvas, paint, mCoordinateEnd, spcERadius, color, 0, Paint.Style.FILL);
                /** *画进度条起始缺口 */
                drawCircle(canvas, paint, mCoordinateStart, spcERadius, Color.WHITE, 0,
                        Paint.Style.FILL);
            }
        }
    }
}