package com.autowanglei.progressbar.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.FontMetrics;
import android.graphics.RectF;
import android.util.AttributeSet;

import com.autowanglei.progressbar.vo.GradualArcVO;

import java.util.LinkedList;

/**
 * 仿iphone带进度的进度条，线程安全的View，可直接在线程中更新进度
 */
public class CircleGradualProgressBar extends RoundBaseProgressBar {

    private float progressOuterRadius = 0;
    private LinkedList<GradualArcVO> mGradualArcVOs = null;
    private String[] colors = {"#FF38A7EA", "#FF49B4EE", "#FF55BBEE", "#FF55BDEE", "#FF54C3FF"};

    public CircleGradualProgressBar(Context context) {
        super(context, null);
    }

    public CircleGradualProgressBar(Context context, AttributeSet attrs) {
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
        paint.setStrokeWidth(0);
        paint.setStyle(Paint.Style.FILL); // 设置空心
        paint.setColor(textColor);
        paint.setShadowLayer(1, 1, 1, textColor);
        paint.setTextSize(textSize);
        float textWidth = paint.measureText(percent + "%"); // 测量字体宽度，我们需要根据字体的宽度设置在圆环中间
        float textHight = getTexthight(paint); // 测量字体高度，我们需要根据字体的宽度设置在圆环中间
        canvas.drawText(percent + "%", centre - textWidth / 2, centre + textHight / 2 - 15, paint); //
        // 画出进度百分比
        paint.setShadowLayer(0, 0, 0, Color.TRANSPARENT);
    }

    private float getTexthight(Paint paint) {
        FontMetrics fm = paint.getFontMetrics();
        return (float) Math.ceil(fm.descent - fm.ascent);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (null == mGradualArcVOs) {
            progressWidth = centre / 7;
            progressOuterRadius = centre * 0.62f;
            progressRadius = progressOuterRadius - progressWidth / 2;
            mGradualArcVOs = getGradualArcVOs(centre, progressOuterRadius, progressRadius);
        }
        /** * 画进度百分比 text */
        if (textIsDisplayable && style == STROKE) {
            drawProgressText(canvas, paint, centre, radius, (int) (((float) progress / (float)
                    maxValue) * 100), textSize, textColor);
        }

        /** *画进度条 */
        double sweepAngle = 360 * progress / maxValue;
        drawSymmetryGradualArc(canvas, paint, style, startAngle, (float) sweepAngle, progress);
    }

    private GradualArcVO getGradualArcVO(float centre, float radius, int color, float shadowWidth) {
        RectF mRectF = new RectF((centre - radius), (centre - radius), (centre + radius - 1.5f),
                (centre + radius - 1.5f));
        return new GradualArcVO(mRectF, color, shadowWidth);
    }

    /**
     * @param centre
     * @param progressOuterShadowRadius
     * @param progressRadius
     * @return
     */
    private LinkedList<GradualArcVO> getGradualArcVOs(float centre, final float
            progressOuterShadowRadius, final float progressRadius) {
        LinkedList<GradualArcVO> gradualArcVOs = new LinkedList<GradualArcVO>();
        float shadowWidth = 2f;
        float radius = progressOuterShadowRadius;
        /**outer gradual arc*/
        for (int i = 0; i < 4; i++) {
            radius -= shadowWidth;
            gradualArcVOs.add(getGradualArcVO(centre, radius, Color.parseColor(colors[i]),
                    shadowWidth));
        }
        float centWidth = (radius - progressRadius) * 2;
        /**center arc*/
        gradualArcVOs.add(getGradualArcVO(centre, progressRadius, Color.parseColor(colors[4]),
                centWidth + 2));
        radius -= centWidth;
        /**inner gradual arc*/
        for (int i = 3; i >= 0; i--) {
            radius -= shadowWidth;
            gradualArcVOs.add(getGradualArcVO(centre, radius, Color.parseColor(colors[i]),
                    shadowWidth));
        }
//        radius -= shadowWidth;
//        gradualArcVOs.add(getGradualArcVO(centre, radius, Color.parseColor(colors[5]),
// shadowWidth));
        return gradualArcVOs;
    }


    /**
     * 画颜色对称渐变的圆弧,分3部分外层渐变、中间纯色、内层渐变
     *
     * @param canvas
     * @param paint
     * @param style
     * @param startAngle
     * @param sweepAngle
     * @param progress
     */

    private void drawSymmetryGradualArc(Canvas canvas, Paint paint, int style, float startAngle,
                                        float sweepAngle, int progress) {
        for (GradualArcVO gradualArcVO : mGradualArcVOs) {
            drawArc(canvas, paint, style, gradualArcVO.color, gradualArcVO.arcWidth, startAngle,
                    sweepAngle, gradualArcVO.mRectF, progress);
        }

    }

}