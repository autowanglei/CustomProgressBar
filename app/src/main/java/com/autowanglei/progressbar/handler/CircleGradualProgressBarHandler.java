package com.autowanglei.progressbar.handler;

import android.content.Context;
import android.view.View;

import com.android.progressbar.R;
import com.autowanglei.progressbar.view.CircleGradualProgressBar;
import com.autowanglei.progressbar.view.RoundBaseProgressBar;
import com.autowanglei.progressbar.vo.ProgressBarViewVO;

/**
 * Created by wanglei on 2017/1/18.
 */

public class CircleGradualProgressBarHandler extends RoundProgressBarBaseHandler {

    @Override
    public View getProgressBar(Context context, View parentView) {
        CircleGradualProgressBar mCircleShadowProgressBar = (CircleGradualProgressBar) parentView
                .findViewById(R.id.circle_shadow_progressbar);
        return mCircleShadowProgressBar;
    }

    @Override
    public void show(ProgressBarViewVO upgradeDialogViewVO) {
        super.show(upgradeDialogViewVO);
        ((RoundBaseProgressBar) upgradeDialogViewVO.getProgressBar()).setHasSpecialEffects(false);
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
        super.dismiss(context, upgradeDialogViewVO, tvMsg, leftBtnListener, leftBtnText,
                rightBtnListener, rightBtnText);
    }

}
