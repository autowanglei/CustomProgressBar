package com.autowanglei.progressbar.handler;

import android.content.Context;
import android.graphics.Color;
import android.view.View;

import com.autowanglei.progressbar.Utils;
import com.autowanglei.progressbar.view.RoundBaseProgressBar;
import com.autowanglei.progressbar.vo.ProgressBarViewVO;

/**
 * Created by wanglei on 2017/1/18.
 */

public class RoundProgressBarBaseHandler implements ProgressBarHandler {

    @Override
    public View getProgressBar(Context context, View parentView) {
        return null;
    }

    @Override
    public void show(ProgressBarViewVO upgradeDialogViewVO) {
        upgradeDialogViewVO.getNewAppRly().setBackgroundColor(Color.TRANSPARENT);
        upgradeDialogViewVO.getIvIcon().setVisibility(View.GONE);
//        upgradeDialogViewVO.getIvIcon().setVisibility(View.GONE);
//        upgradeDialogViewVO.getTvTitle().setVisibility(View.GONE);
        upgradeDialogViewVO.getProgressBar().setVisibility(View.VISIBLE);
    }

    @Override
    public void setProgress(View progressBar, int progress) {
        ((RoundBaseProgressBar) progressBar).setProgress(progress);
    }

    @Override
    public int getMaxProgress(View progressBar) {
        return ((RoundBaseProgressBar) progressBar).getMax();
    }

    @Override
    public void dismiss(Context context, ProgressBarViewVO upgradeDialogViewVO,
                        String tvMsg, View.OnClickListener leftBtnListener,
                        String leftBtnText, View.OnClickListener rightBtnListener,
                        String rightBtnText) {
        upgradeDialogViewVO.getNewAppRly().setBackgroundColor(Color.WHITE);
        upgradeDialogViewVO.getIvIcon().setVisibility(View.VISIBLE);
        upgradeDialogViewVO.getTvTitle().setVisibility(View.VISIBLE);
        upgradeDialogViewVO.getTvTitle().setText(Utils.getAppInfo(context).appName);
        upgradeDialogViewVO.getProgressBar().setVisibility(View.GONE);
    }

}
