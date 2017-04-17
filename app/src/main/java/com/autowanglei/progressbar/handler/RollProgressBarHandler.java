package com.autowanglei.progressbar.handler;

import android.content.Context;
import android.view.View;

import com.android.progressbar.R;
import com.autowanglei.progressbar.view.RollProgressBar;
import com.autowanglei.progressbar.vo.ProgressBarViewVO;

/**
 * Created by wanglei on 2017/1/18.
 */

public class RollProgressBarHandler extends RoundProgressBarBaseHandler {

    private RollProgressBar mRollProgressBar = null;

    @Override
    public View getProgressBar(Context context, View parentView) {
        mRollProgressBar = (RollProgressBar) parentView.findViewById
                (R.id.roll_progressbar);
        mRollProgressBar.setRollStep(5);
        mRollProgressBar.setRollSweepAngle(60);
        mRollProgressBar.setRollInterval(10);
        return mRollProgressBar;
    }

    @Override
    public void show(ProgressBarViewVO upgradeDialogViewVO) {
        mRollProgressBar.sartUpdateProgress();
        super.show(upgradeDialogViewVO);
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
        mRollProgressBar.stopUpdateProgress();
        super.dismiss(context, upgradeDialogViewVO, tvMsg, leftBtnListener,
                leftBtnText, rightBtnListener, rightBtnText);
    }
}
