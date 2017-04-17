package com.autowanglei.progressbar;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.progressbar.R;
import com.autowanglei.progressbar.handler.CircleGradualProgressBarHandler;
import com.autowanglei.progressbar.handler.CircleProgressBarHandler;
import com.autowanglei.progressbar.handler.ProgressBarHandler;
import com.autowanglei.progressbar.handler.RollProgressBarHandler;
import com.autowanglei.progressbar.handler.StraightProgressBarHandler;
import com.autowanglei.progressbar.vo.ProgressBarViewVO;

public class ProgressBarsActivity extends Activity implements OnClickListener {

    private String TAG = "ProgressBarsActivity";
    private AlertDialog alertDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        AlertDialog.Builder builder = new AlertDialog.Builder(this);
//        builder.setCancelable(false);
//        alertDialog = builder.create();
        getButton(R.id.straight_btn);
        getButton(R.id.cirle_btn);
        getButton(R.id.roll_btn);
        getButton(R.id.gradual_circle_btn);
    }

    private Button getButton(int id) {
        Button btn = (Button) findViewById(id);
        btn.setOnClickListener(this);
        return btn;
    }

    @Override
    public void onClick(View view) {
        int clickViewId = view.getId();
        ProgressBarHandler handler = null;
        String barTitle = "";
        switch (clickViewId) {
            case R.id.straight_btn:
                handler = new StraightProgressBarHandler();
                barTitle = "I'm straight progressBar.";
                Log.i(TAG, "straight progressBar");
                break;
            case R.id.cirle_btn:
                handler = new CircleProgressBarHandler();
                barTitle = "I'm cirle progressBar.";
                Log.i(TAG, "cirle progressBar");
                break;
            case R.id.roll_btn:
                handler = new RollProgressBarHandler();
                barTitle = "I'm roll progressBar.";
                Log.i(TAG, "roll progressBar");
                break;
            case R.id.gradual_circle_btn:
                handler = new CircleGradualProgressBarHandler();
                barTitle = "I'm gradual cirle progressBar.";
                Log.i(TAG, "gradual cirle progressBar");
                break;
        }
        final ProgressBarHandler  mHandler = handler;
        if (handler != null) {
            final  ProgressBarViewVO progressBarview = getProgressBarview(handler);
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setCancelable(false);
            alertDialog = builder.create();
            alertDialog.show();
            alertDialog.getWindow().setContentView(progressBarview.getDialogView());
            progressBarview.getTvTitle().setText(barTitle);
            handler.show(progressBarview);
            new Thread() {
                public void run() {
                    int i = 1;
                    do {
                        mHandler.setProgress(progressBarview.getProgressBar(), i);
                        try {
                            Thread.sleep(200);
                        } catch (InterruptedException e) {
                        }
                        i++;
                    } while (i < 101);
                }
            }.start();
        }
    }


    private ProgressBarViewVO getProgressBarview(ProgressBarHandler handler) {
        View progressView = LayoutInflater.from(this).inflate(R.layout.peogress_bar_layout, null);
        RelativeLayout newAppRly = (RelativeLayout) progressView.findViewById(R.id
                .progress_bar_rly);
        ImageView ivIcon = (ImageView) progressView.findViewById(R.id.progress_bar_tile_icon);
        TextView tvTitle = (TextView) progressView.findViewById(R.id.progress_bar_tile_tv);
        View progressBar = handler.getProgressBar(this, progressView);
        Button getProgressBarContrlBtn = (Button) progressView.findViewById(R.id
                .progress_bar_contrl_btn);
        getProgressBarContrlBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
            }
        });
        return new ProgressBarViewVO(progressView, newAppRly,
                ivIcon, tvTitle, progressBar, getProgressBarContrlBtn);
    }
}
