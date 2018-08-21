package com.alct.mdpsdksample.util;

import android.app.ActivityManager;
import android.content.Context;
import android.os.Build;

import com.alct.mdp.util.LogUtil;
import com.alct.mdpsdksample.constant.BaseConstant;

import java.util.List;

public class DeviceUtil {

    private DeviceUtil() {
        // Nothing here
    }

    public static String getCurrentProcessName(Context context) {
        String currentProcessName = "";
        int pid = android.os.Process.myPid();
        ActivityManager mActivityManager = (ActivityManager) context .getSystemService(Context.ACTIVITY_SERVICE);

        if (mActivityManager.getRunningAppProcesses() != null && mActivityManager.getRunningAppProcesses().size() > 0) {
            for (ActivityManager.RunningAppProcessInfo appProcess : mActivityManager.getRunningAppProcesses()) {
                if (appProcess.pid == pid) {
                    currentProcessName = appProcess.processName;
                }
            }
        }

        return currentProcessName;
    }

    public static boolean isLocationServiceRunning(Context context) {
        boolean serviceStatus = false;
        ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningServiceInfo> runningServices = null;

        try {
            runningServices = manager.getRunningServices(Integer.MAX_VALUE);
        } catch (Exception exception) {
            LogUtil.e(BaseConstant.TAG, "Get system running service failed, exception is " + exception.getMessage());
        }

        if (runningServices != null && runningServices.size() > 0) {
            for (ActivityManager.RunningServiceInfo serviceInfo : runningServices) {
                String name = serviceInfo.service.getClassName();

                if (name != null && name.contains("MDPLocationService")) {
                    serviceStatus = true;
                }
            }
        }
        return serviceStatus;
    }

    public static boolean isBelowLOLLIPOP() {
        // API < 21
        return Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP;
    }
}