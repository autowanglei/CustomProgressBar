package com.autowanglei.progressbar.handler;

import android.content.Context;
import android.view.View;

import com.android.progressbar.R;
import com.autowanglei.progressbar.Utils;
import com.autowanglei.progressbar.view.StraightProgressBar;
import com.autowanglei.progressbar.vo.ProgressBarViewVO;

/**
 * Created by wanglei on 2017/1/18.
 */

public class StraightProgressBarHandler implements ProgressBarHandler {
    @Override
    public View getProgressBar(Context context, View parentView) {
        return (StraightProgressBar) parentView.findViewById(R.id.straight_progressbar);
    }

    @Override
    public void show(ProgressBarViewVO upgradeDialogViewVO) {
        upgradeDialogViewVO.getIvIcon().setVisibility(View.GONE);
        upgradeDialogViewVO.getProgressBar().setVisibility(View.VISIBLE);

    }

    @Override
    public void setProgress(View progressBar, int progress) {
        ((StraightProgressBar) progressBar).setProgress(progress);
    }

    @Override
    public int getMaxProgress(View progressBar) {
        return ((StraightProgressBar) progressBar).getMax();
    }

    @Override
    public void dismiss(Context context, ProgressBarViewVO upgradeDialogViewVO,
                        String tvMsg, View.OnClickListener leftBtnListener,
                        String leftBtnText, View.OnClickListener rightBtnListener,
                        String rightBtnText) {
        upgradeDialogViewVO.getIvIcon().setVisibility(View.VISIBLE);
        upgradeDialogViewVO.getTvTitle().setText(Utils.getAppInfo(context).appName);
        upgradeDialogViewVO.getProgressBar().setVisibility(View.GONE);
    }
}
