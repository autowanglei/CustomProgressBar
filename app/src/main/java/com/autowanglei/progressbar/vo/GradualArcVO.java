package com.autowanglei.progressbar.vo;

import android.graphics.RectF;

/**
 * 渐变圆弧属性 矩形区域 颜色 圆弧宽度
 * Created by wanglei on 2017/3/3.
 */

public class GradualArcVO {
    public RectF mRectF;
    public int color;
    public float arcWidth;

    public GradualArcVO(RectF mRectF, int color, float arcWidth) {
        this.mRectF = mRectF;
        this.color = color;
        this.arcWidth = arcWidth;
    }

}
