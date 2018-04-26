package com.centanet.hk.aplus;

import android.app.Activity;
import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;
import android.os.Build;
import android.support.multidex.MultiDex;

import com.centanet.hk.aplus.Utils.L;
import com.centanet.hk.aplus.Utils.LogPrintToLocalUtil;
import com.centanet.hk.aplus.bean.complexSearch.Operation;
import com.centanet.hk.aplus.bean.http.AHeaderDescription;
import com.centanet.hk.aplus.bean.http.SSOHeaderDescription;
import com.centanet.hk.aplus.bean.login.Permisstions;
import com.centanet.hk.aplus.bean.params.Parameter;
import com.centanet.hk.aplus.bean.params.SystemParam;
import com.centanet.hk.aplus.helper.AppFrontBackHelper;
import com.centanet.hk.aplus.service.PrefsessionService;

import org.litepal.LitePalApplication;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static android.content.ContentValues.TAG;

/**
 * Created by mzh1608258 on 2018/1/2.
 */

public class MyApplication extends LitePalApplication {

    private static List<Activity> activities;

    private static boolean isRelase;

    private String useClauseUrl;

    private int openDataType;

    private AHeaderDescription headerDescription;

    private SSOHeaderDescription ssoHeaderDescription;

    public static Context context;

    private Permisstions userPermission;

    private Parameter parameter;

    private SystemParam intervalSystemParam;

    private SystemParam directionSystemParam;

    private SystemParam labelSystenParam;

    private Map<String, String> statusParams;

    private Map<String, String> statusCodes;

    private Operation houseOperation = new Operation();

    private Operation favoOperation = new Operation();

    private boolean isOutTime = false;

    private List<String> contactType;

    private long onBackTime = System.currentTimeMillis();

    private int updateType = 0;

    private String updateUrl;

    private int clientVer;

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(base);
        LogPrintToLocalUtil.init(base);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        isRelase = false;
        contactType = new ArrayList<>();
        context = getApplicationContext();
        AppFrontBackHelper helper = new AppFrontBackHelper();
        helper.register(MyApplication.this, new AppFrontBackHelper.OnAppStatusListener() {
            @Override
            public void onFront() {
                //应用切到前台处理
                L.d(TAG, "onFront: ");

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    //取消上次任务时执行
                    JobScheduler jobScheduler = (JobScheduler) getSystemService(Context.JOB_SCHEDULER_SERVICE);
                    List<JobInfo> allPendingJobs = jobScheduler.getAllPendingJobs();
                    if (allPendingJobs.size() > 0) {
                        // Finish the last one
                        int jobId = allPendingJobs.get(0).getId();
                        if (jobId == 1)
                            jobScheduler.cancel(jobId);
                        L.d(TAG, "onFront: " + "cancel");
                    }
                } else {
                    //todo
                    if (System.currentTimeMillis() - onBackTime >= 30 * 1000 * 60) {
                        isOutTime = true;
                        L.d("application", "isOutTime");
                    }
                }
            }

            @Override
            public void onBack() {
                //应用切到后台处理
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    ComponentName jobService = new ComponentName(getBaseContext(), PrefsessionService.class);
                    JobScheduler scheduler = (JobScheduler) getSystemService(Context.JOB_SCHEDULER_SERVICE);

                    JobInfo.Builder builder = new JobInfo.Builder(1, jobService);
                    JobInfo jobInfo = builder
                            .setMinimumLatency(30 * 60 * 1000)
                            .setOverrideDeadline(30 * 60 * 1000)
                            .build();
                    int state = scheduler.schedule(jobInfo);
                } else {
                    //todo
                    onBackTime = System.currentTimeMillis();
                }
            }
        });
    }

    public static Context getContext() {
        return context;
    }

    public void setUseClauseUrl(String useClauseUrl) {
        this.useClauseUrl = useClauseUrl;
    }

    public String getUseClauseUrl() {
        return useClauseUrl;
    }

    public void setSsoHeaderDescription(SSOHeaderDescription ssoHeaderDescription) {
        this.ssoHeaderDescription = ssoHeaderDescription;
    }


    public int getUpdateType() {
        return updateType;
    }

    public void setUpdateType(int updateType) {
        this.updateType = updateType;
    }

    public String getUpdateUrl() {
        return updateUrl;
    }

    public void setUpdateUrl(String updateUrl) {
        this.updateUrl = updateUrl;
    }

    public void setOnBackTime(long onBackTime) {
        this.onBackTime = onBackTime;
    }

    public long getOnBackTime() {
        return onBackTime;
    }

    public void setHouseOperation(Operation houseOperation) {
        this.houseOperation = houseOperation;
    }

    public void setStatusCodes(Map<String, String> statusCodes) {
        this.statusCodes = statusCodes;
    }

    public Map<String, String> getStatusCodes() {
        return statusCodes;
    }

    public void setFavoOperation(Operation favoOperation) {
        this.favoOperation = favoOperation;
    }

    public Operation getHouseOperation() {
        return houseOperation;
    }

    public Operation getFavoOperation() {
        return favoOperation;
    }

    public SystemParam getIntervalSystemParam() {
        return intervalSystemParam;
    }

    public SystemParam getDirectionSystemParam() {
        return directionSystemParam;
    }

    public void setDirectionSystemParam(SystemParam directionSystemParam) {
        this.directionSystemParam = directionSystemParam;
    }

    public List<String> getContactType() {
        return contactType;
    }

    public void setContactType(List<String> contactType) {
        this.contactType = contactType;
    }

    public SystemParam getLabelSystenParam() {
        return labelSystenParam;
    }

    public void setLabelSystenParam(SystemParam labelSystenParam) {
        this.labelSystenParam = labelSystenParam;
    }

    public void setClientVer(int clientVer) {
        this.clientVer = clientVer;
    }

    public int getClientVer() {
        return clientVer;
    }

    public void setIntervalSystemParam(SystemParam intervalSystemParam) {
        this.intervalSystemParam = intervalSystemParam;
    }

    public void setStatusParams(Map<String, String> statusParams) {
        this.statusParams = statusParams;
    }

    public boolean isOutTime() {
        return isOutTime;
    }

    public void setOutTime(boolean outTime) {
        isOutTime = outTime;
    }

    public Map<String, String> getStatusParams() {
        return statusParams;
    }

    public SSOHeaderDescription getSsoHeaderDescription() {
        return ssoHeaderDescription;
    }

    public void setUserPermission(Permisstions userPermission) {
        this.userPermission = userPermission;
    }

    public Permisstions getUserPermission() {
        return userPermission;
    }

    public void setHeaderDescription(AHeaderDescription headerDescription) {
        this.headerDescription = headerDescription;
    }

    public void setParameter(Parameter parameter) {
        this.parameter = parameter;
    }

    public Parameter getParameter() {
        return parameter;
    }

    public void setOpenDataType(int openDataType) {
        this.openDataType = openDataType;
    }

    public int getOpenDataType() {
        return openDataType;
    }

    public AHeaderDescription getHeaderDescription() {
        return headerDescription;
    }

    public static boolean getRelase() {
        return isRelase;
    }


    public static boolean addActivity(Activity activity) {
        if (activities == null) {
            activities = new ArrayList<>();
        }
        return activities.add(activity);
    }

    public static boolean removeActivity(Activity activity) {
        if (activities != null) {
            return activities.remove(activity);
        }
        return false;
    }

    public static boolean removeAllActiies() {

        if (activities == null) {
            return true;
        }

        if (activities.size() > 0) {
            for (int i = 0; i < activities.size(); i++) {
                activities.get(i).finish();
            }

            activities.clear();
        }

        return activities.size() == 0;
    }


    public static int getActiviesSize() {

        return activities.size();
    }

}
