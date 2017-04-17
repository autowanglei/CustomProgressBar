package com.autowanglei.progressbar.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.View;

import com.android.progressbar.R;
import com.autowanglei.progressbar.vo.CoordinateVO;

/**
 * 仿iphone带进度的进度条，线程安全的View，可直接在线程中更新进度
 */
public class RoundBaseProgressBar extends View {
    /**
     * 画笔对象的引用
     */
    protected Paint paint;

    /**
     * 圆环的颜色
     */
    protected int roundColor;

    /**
     * 圆环进度的颜色
     */
    protected int roundProgressColor;

    /**
     * 中间进度百分比的字符串的颜色
     */
    protected int textColor;

    /**
     * 中间进度百分比的字符串的字体
     */
    protected float textSize;

    /**
     * 圆环的宽度
     */
    protected float progressWidth;

    /**
     * 最大进度
     */
    protected int maxValue;

    /**
     * 当前进度
     */
    protected int progress = 0;
    /**
     * 是否显示中间的进度
     */
    protected boolean textIsDisplayable;

    /**
     * 进度条是否带有特效 起始的圆弧
     */
    protected boolean hasSpecialEffects = false;

    /**
     * 进度的风格，实心或者空心
     */
    protected int style;
    public static final int STROKE = 0;
    public static final int FILL = 1;
    protected Float startAngle = (float) 270;

    /**
     * default val
     */
    private static final int DEFAULT_STYLE = STROKE;
    private static final int DEFAULT_ROUND_COLOR = Color.RED;
    private static final int DEFAULT_ROUND_PROGRESS_COLOR = Color.GREEN;
    private static final int DEFAULT_TEXT_COLOR = Color.GREEN;
    private static final int DEFAULT_TEXT_SIZE = 15;
    private static final int DEFAULT_ROUND_WIDTH = 5;
    private static final int DEFAULT_MAX_VALUE = 100;
    private static final boolean DEFAULT_TEXT_DISPLAYABLE = true;

    /**
     * constant field
     */
    private static final String CUSTOM_CLASS = "plugin_emm_circle_progress_";
    private static final String ROUND_COLOR = "plugin_emm_round_bg_color";
    private static final String ROUND_PROGRESS_COLOR = "plugin_emm_round_progress_color";
    private static final String ROUND_WIDTH = "plugin_emm_round_width";
    private static final String TEXT_COLOR = "plugin_emm_text_color";
    private static final String TEXT_SIZE = "plugin_emm_text_size";
    private static final String MAX_VALUE = "plugin_emm_max_value";
    private static final String TEXT_DISPLAYABLE = "plugin_emm_text_is_displayable";
    private static final String STYLE = "plugin_emm_style";
    private static final String RES_TYPE = "styleable";
    /**
     * 进度环忽略宽度，外切正方向
     */
    protected RectF progressOval = null;// 进度条圆环的大小的界限
    protected float centre; // 背景圆（进度条圆环）圆心的x坐标
    protected float radius; // 背景圆的半径
    protected float progressRadius;// 进度条圆环的半径
    /**
     * 滚动圆弧扫过的角度
     */
    protected float rollSweepAngle = 60;
    /**
     * 滚动圆弧滚动的步长
     */
    protected int rollStep = 5;
    protected int rollInterval = 10;

    public RoundBaseProgressBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    private RoundBaseProgressBar(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        paint = new Paint();

        TypedArray mTypedArray = context.obtainStyledAttributes(attrs, R.styleable.circle_progress);
        // 获取自定义属性和默认值
        roundColor = mTypedArray.getColor(R.styleable.circle_progress_round_bg_color,
                DEFAULT_ROUND_COLOR);
        roundProgressColor = mTypedArray.getColor(
                R.styleable.circle_progress_round_progress_color, DEFAULT_ROUND_PROGRESS_COLOR);
        progressWidth = mTypedArray.getDimension(R.styleable.circle_progress_round_width,
                DEFAULT_ROUND_WIDTH);
        textColor = mTypedArray.getColor(R.styleable.circle_progress_text_color,
                DEFAULT_TEXT_COLOR);
        textSize = mTypedArray.getDimension(R.styleable.circle_progress_text_size,
                DEFAULT_TEXT_SIZE);
        maxValue = mTypedArray.getInteger(R.styleable.circle_progress_max_value, DEFAULT_MAX_VALUE);
        textIsDisplayable = mTypedArray.getBoolean(R.styleable.circle_progress_display_text, DEFAULT_TEXT_DISPLAYABLE);
        style = mTypedArray.getInt(R.styleable.circle_progress_style, DEFAULT_STYLE);
        mTypedArray.recycle();
    }

    @Override
    protected void onSizeChanged(int w, int h, int ow, int oh) {
        super.onSizeChanged(w, h, ow, oh);
    }

    /**
     * 画圆（实心、空心）
     *
     * @param canvas
     * @param paint
     * @param centerCoordinate 圆心坐标
     * @param radius           半径
     * @param color            圆环颜色
     * @param roundWidth       圆环宽度
     * @param style            Paint.Style.FILL：实心；Paint.Style.STROKE：空心
     */
    protected void drawCircle(Canvas canvas, Paint paint, CoordinateVO centerCoordinate,
                              float radius, int color, float roundWidth, Paint.Style style) {
        paint.setColor(color); // 设置圆环的颜色
        paint.setStyle(style); // 设置空心
        paint.setStrokeWidth(roundWidth); // 设置圆环的宽度
        paint.setAntiAlias(true); // 消除锯齿
        canvas.drawCircle(centerCoordinate.x, centerCoordinate.y, radius, paint); // 画出圆环
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
    protected void drawProgressText(Canvas canvas, Paint paint, float centre, float radius,
                                    int percent, float textSize, int textColor) {
        paint.setStrokeWidth(0);
        paint.setStyle(Paint.Style.FILL); // 设置空心
        paint.setColor(textColor);
        paint.setTextSize(textSize);
//        paint.setAntiAlias(true); // 消除锯齿
        paint.setTypeface(Typeface.DEFAULT_BOLD); // 设置字体
        // 中间的进度百分比，先转换成float在进行除法运算，不然都为0
        float textWidth = paint.measureText(percent + "%"); // 测量字体宽度，我们需要根据字体的宽度设置在圆环中间
        canvas.drawText(percent + "%", centre - textWidth / 2,
                centre + textSize / 2, paint); // 画出进度百分比
    }

    /**
     * 画圆弧
     *
     * @param canvas
     * @param paint
     * @param style
     * @param roundColor
     * @param roundWidth
     * @param startAngle
     * @param sweepAngle
     * @param oval
     * @param progress
     */
    protected void drawArc(Canvas canvas, Paint paint, int style, int roundColor,
                           float roundWidth, float startAngle, float sweepAngle, RectF oval,
                           int progress) {
        paint.setStrokeWidth(roundWidth); // 设置圆环的宽度
        paint.setColor(roundColor); // 设置进度的颜色
        paint.setAntiAlias(true); // 消除锯齿
        boolean uesCenter = false;
        switch (style) {
            case STROKE:
                paint.setStyle(Paint.Style.STROKE);
                break;
            case FILL:
                paint.setStyle(Paint.Style.FILL_AND_STROKE);
                break;
        }
        if (progress >= 0) {
            canvas.drawArc(oval, startAngle, sweepAngle, uesCenter, paint); // 根据进度画圆弧
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (null == progressOval) {
            centre = getWidth() / 2; // 获取圆心的x坐标
        }
    }

    public synchronized int getMax() {
        return maxValue;
    }

    /**
     * 设置进度的最大值
     *
     * @param max
     */
    public synchronized void setMax(int max) {
        if (max < 0) {
            throw new IllegalArgumentException("max not less than 0");
        }
        this.maxValue = max;
    }

    /**
     * 获取进度.需要同步
     *
     * @return
     */
    public synchronized int getProgress() {
        return progress;
    }

    /**
     * 设置进度，此为线程安全控件，由于考虑多线的问题，需要同步 刷新界面调用postInvalidate()能在非UI线程刷新
     *
     * @param progress
     */
    public synchronized void setProgress(int progress) {
        this.progress = progress;
        if (progress >= 0) {
            this.progress = (progress <= maxValue) ? progress : maxValue;
        }
    }

    public int getCricleProgressColor() {
        return roundProgressColor;
    }

    public void setCricleProgressColor(int cricleProgressColor) {
        this.roundProgressColor = cricleProgressColor;
    }

    public int getTextColor() {
        return textColor;
    }

    public void setTextColor(int textColor) {
        this.textColor = textColor;
    }

    public float getTextSize() {
        return textSize;
    }

    public void setTextSize(float textSize) {
        this.textSize = textSize;
    }

    public float getRoundWidth() {
        return progressWidth;
    }

    public void setRoundColor(int roundColor) {
        this.roundColor = roundColor;
    }

    public void setRoundWidth(float roundWidth) {
        this.progressWidth = roundWidth;
    }


    private double getAnglePI(double angle) {
        return (Math.PI * angle / 180);
    }

    /**
     * 根据角度获取圆周上点的坐标
     *
     * @param angle 相对于12点钟方向顺时针角度
     * @return 坐标点
     */
    protected CoordinateVO getCircleCenterVO(double angle) {
        double sweepAnglePI = getAnglePI(angle);
        CoordinateVO mCoordinateVO = new CoordinateVO(
                (float) (centre + progressRadius * Math.sin(sweepAnglePI)),
                (float) (centre - progressRadius * Math.cos(sweepAnglePI)));
        return mCoordinateVO;
    }

    public void setRollSweepAngle(int angle) {
        rollSweepAngle = angle;
    }

    public void setRollStep(int step) {
        rollStep = step;
    }

    public void setRollInterval(int rollInterval) {
        this.rollInterval = rollInterval;
    }

    public void setHasSpecialEffects(boolean hasSpecialEffects) {
        this.hasSpecialEffects = hasSpecialEffects;
    }

}