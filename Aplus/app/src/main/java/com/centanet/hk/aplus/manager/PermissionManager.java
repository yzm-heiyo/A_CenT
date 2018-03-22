package com.centanet.hk.aplus.manager;

import android.content.Context;

import com.centanet.hk.aplus.MyApplication;
import com.centanet.hk.aplus.Utils.L;
import com.centanet.hk.aplus.entity.login.Permisstions;

import static com.centanet.hk.aplus.eventbus.BUS_MESSAGE.Permission.HOUSELIST;

/**
 * Created by yangzm4 on 2018/3/16.
 */

public class PermissionManager {

    public static final String WAR_ZONE = "Property.war-zone";
    public static final String FAVORITE = "Property.my-favorite";
    public static final String SEARCH_ALL = "Property.Follow.Search-All";
    public static final String FOLLOW_ALL = "Property.FollowInformation.Add";
    public static final String CLIENTINFO_ALL = "Property.ContactInformation.Search-All";

    private Context context;

    public static void setPermission(Permisstions per) {
        MyApplication application = (MyApplication) MyApplication.getContext().getApplicationContext();
        application.setUserPermission(per);
    }

    public static Permisstions getPermisstion() {
        MyApplication application = (MyApplication) MyApplication.getContext().getApplicationContext();
        return application.getUserPermission();
    }


    public static boolean verifyPermission(String perName) {
        MyApplication application = (MyApplication) MyApplication.getContext().getApplicationContext();
        String right = application.getUserPermission().getRights();
        L.d("right", right);
        if (right.indexOf(perName) != -1)
            return true;
        return false;
    }

    public static boolean verifyMeunPermission(String per) {
        MyApplication application = (MyApplication) MyApplication.getContext().getApplicationContext();
        String menu = application.getUserPermission().getMenuPermisstion();
        L.d("menu",menu);
        if (menu.indexOf(per) != -1)
            return true;
        return false;
    }

    public static boolean verifyPermission(String pers,String per){
        L.d("model_per",pers);
        L.d("per_params",per);
        if(pers.indexOf(per)!=-1)
            return true;
        return false;
    }

}
