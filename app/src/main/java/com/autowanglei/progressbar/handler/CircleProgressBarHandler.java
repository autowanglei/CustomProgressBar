package com.autowanglei.progressbar.handler;

import android.content.Context;
import android.graphics.Color;
import android.view.View;

import com.android.progressbar.R;
import com.autowanglei.progressbar.view.CircleProgressBar;
import com.autowanglei.progressbar.view.RoundBaseProgressBar;
import com.autowanglei.progressbar.vo.ProgressBarViewVO;

/**
 * Created by wanglei on 2017/1/18.
 */

public class CircleProgressBarHandler extends RoundProgressBarBaseHandler {

    @Override
    public View getProgressBar(Context context, View parentView) {
        CircleProgressBar mCircleProgressBar = (CircleProgressBar) parentView.findViewById
                (R.id.circle_progressbar);
        return mCircleProgressBar;
    }

    @Override
    public void show(ProgressBarViewVO upgradeDialogViewVO) {
        super.show(upgradeDialogViewVO);
        upgradeDialogViewVO.getProgressBar().setBackgroundColor(Color.TRANSPARENT);
        ((RoundBaseProgressBar) upgradeDialogViewVO.getProgressBar()).setHasSpecialEffects(true);
    }

    @Override
    public void setProgress(View progressBar, int progress) {
        super.setProgress(progressBar, progress);
    }

    @Override
    public void dismiss(Context context, ProgressBarViewVO upgradeDialogViewVO,
                        String tvMsg, View.OnClickListener leftBtnListener,
                        String leftBtnText, View.OnClickListener rightBtnListener,
                        String rightBtnText) {
        super.dismiss(context, upgradeDialogViewVO, tvMsg, leftBtnListener,
                leftBtnText, rightBtnListener, rightBtnText);
    }

}
