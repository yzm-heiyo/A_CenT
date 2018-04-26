package com.centanet.hk.aplus.service;

import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

import com.centanet.hk.aplus.Utils.L;
import com.centanet.hk.aplus.manager.ApplicationManager;

import static android.content.ContentValues.TAG;

/**
 * Created by yangzm4 on 2018/4/6.
 */

public class PrefsessionService extends JobService {

    private String TAG = this.getClass().getSimpleName();
    private Context context;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        L.d(TAG, "onStartCommand");
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public boolean onStartJob(final JobParameters params) {
        L.d(TAG, "onStartJob ");
        ApplicationManager.getApplication().setOutTime(true);
        return true;       // 返回false表示任务执行完毕，下一个任务即将展开，true表示任务还未执行结束，需要手动调用jobFinished;
    }

    @Override
    public boolean onStopJob(JobParameters params) {  //在onStartJob（）返回true的前提下， 取消cancel或者强制停止Job任务的时候才会调用到此方法
        L.d(TAG, "onStopJob");
        return false;       // 任务是否应该在下次继续
    }

}
