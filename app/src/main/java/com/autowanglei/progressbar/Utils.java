package com.autowanglei.progressbar;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;

import com.autowanglei.progressbar.vo.AppInfoVO;

/**
 * Created by wanglei on 2017/4/11.
 */

public class Utils {
    public static AppInfoVO getAppInfo(Context mContext) {
        AppInfoVO mAppInfo = new AppInfoVO();
        try {
            PackageManager pManager = mContext.getPackageManager();
            ApplicationInfo appInfo = pManager
                    .getApplicationInfo(mContext.getPackageName(), 0);
            mAppInfo.appName = (String) pManager.getApplicationLabel(appInfo);
            mAppInfo.appIcon = pManager.getApplicationIcon(appInfo);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        return mAppInfo;
    }
}
