package com.autowanglei.progressbar.handler;

import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;

import com.autowanglei.progressbar.vo.ProgressBarViewVO;

/**
 * Created by wanglei on 2017/1/10.
 */

public interface ProgressBarHandler {

    public View getProgressBar(Context context, View parentView);

    public void show(ProgressBarViewVO upgradeDialogViewVO);

    public void setProgress(View progressBar, int progress);
    public int getMaxProgress(View progressBar);

    public void dismiss(Context context, ProgressBarViewVO upgradeDialogViewVO,
                        String tvMsg, OnClickListener leftBtnListener,
                        String leftBtnText, OnClickListener rightBtnListener,
                        String rightBtnText);

}
