package com.alct.mdpsdksample;

import android.app.Application;
import android.content.Context;
import android.content.Intent;

import com.alct.mdp.MDPLocationCollectionManager;
import com.alct.mdpsdksample.processprotection.PlayerMusicService;
import com.alct.mdpsdksample.util.DeviceUtil;

public class LocationApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Context context = getApplicationContext();
        // 确保只有主进程进行SDK初始化
        if (context.getPackageName().equals(DeviceUtil.getCurrentProcessName(context))) {
            MDPLocationCollectionManager.initialize(context, BuildConfig.OPEN_API_URL);
            MDPLocationCollectionManager.initServiceProcessProguard(context);
            context.startService(new Intent(context, PlayerMusicService.class));
        }
    }
}