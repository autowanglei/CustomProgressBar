package com.autowanglei.progressbar.vo;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * Created by wanglei on 2017/1/11.
 */

public class ProgressBarViewVO {

    private View dialogView;
    private RelativeLayout newAppRly;
    private ImageView ivIcon;
    private TextView tvTitle;
    private View progressBar;
    private Button progressBarContrlBtn;

    public ProgressBarViewVO(View dialogView, RelativeLayout newAppRly, ImageView ivIcon,
                             TextView tvTitle, View progressBar, Button progressBarContrlBtn) {
        this.dialogView = dialogView;
        this.newAppRly = newAppRly;
        this.ivIcon = ivIcon;
        this.tvTitle = tvTitle;
        this.progressBar = progressBar;
        this.progressBarContrlBtn = progressBarContrlBtn;
    }

    public View getDialogView() {
        return dialogView;
    }

    public ImageView getIvIcon() {
        return ivIcon;
    }


    public RelativeLayout getNewAppRly() {
        return newAppRly;
    }

    public View getProgressBar() {
        return progressBar;
    }

    public TextView getTvTitle() {
        return tvTitle;
    }
    public Button getProgressBarContrlBtn() {
        return progressBarContrlBtn;
    }
}
