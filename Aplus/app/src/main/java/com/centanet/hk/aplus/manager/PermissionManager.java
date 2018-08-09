package com.centanet.hk.aplus.manager;

import android.content.Context;

import com.centanet.hk.aplus.MyApplication;
import com.centanet.hk.aplus.Utils.L;
import com.centanet.hk.aplus.bean.login.Permisstions;

import java.io.Serializable;

/**
 * 管理当前用户拥有的权限
 * 用户可以通过该manager询问当前用户是否拥有某项权限
 * Created by yangzm4 on 2018/3/16.
 */

public class PermissionManager {

    public static final String WAR_ZONE = "Property.war-zone";
    public static final String FAVORITE = "Property.my-favorite";
    public static final String SEARCH_ALL = "Property.Follow.Search-All";
    public static final String FOLLOW_ALL = "Property.FollowInformation.Add";
    public static final String CLIENTINFO_ALL = "Property.ContactInformation.Search-All";  /** 查看业主资料权限 */
    public static final String CALL_HIDDEN_PHONE = "Property.HidePhone.Call";
    public static final String OBULIDING = "Property.OBuilding.Search-All";
    public static boolean seeFollowPermission = false;
//    public static boolean searchFollowPermission = false;
    public static boolean searchAllPermission = false;
    public static boolean seeDetailAddressPermission = false;
    public static boolean seeOBuildPermission = false;
    public static boolean callHiddenPhonePermission = false;
    public static boolean seeClientInfoPermission = false;
    public static boolean followAddPermission = false;

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
        L.d("menu", menu);
        if (menu.indexOf(per) != -1)
            return true;
        return false;
    }

    public static boolean verifyPermission(String pers, String per) {
        L.d("model_per", pers);
        L.d("per_params", per);
        if (pers.indexOf(per) != -1)
            return true;
        return false;
    }

    public static void setSeeFollowPermission(boolean seeFollowPermission) {
        PermissionManager.seeFollowPermission = seeFollowPermission;
    }

//    public static void setSearchFollowPermission(boolean searchFollowPermission) {
//        PermissionManager.searchFollowPermission = searchFollowPermission;
//    }

    public static void setSeeDetailAddressPermission(boolean seeDetailAddressPermission) {
        PermissionManager.seeDetailAddressPermission = seeDetailAddressPermission;
    }

    public static void setSeeOBuildPermission(boolean seeOBuildPermission) {
        PermissionManager.seeOBuildPermission = seeOBuildPermission;
    }

    public static void setCallHiddenPhonePermission(boolean callHiddenPhonePermission) {
        PermissionManager.callHiddenPhonePermission = callHiddenPhonePermission;
    }

    public static void setSeeClientInfoPermission(boolean seeClientInfoPermission) {
        PermissionManager.seeClientInfoPermission = seeClientInfoPermission;
    }

    public static void setFollowAddPermission(boolean followAddPermission) {
        PermissionManager.followAddPermission = followAddPermission;
    }

    public static void setSearchAllPermission(boolean setSearchAllPermission) {
        PermissionManager.searchAllPermission = setSearchAllPermission;
    }

    public static boolean isSearchAllPermission() {
        return searchAllPermission;
    }

    public static boolean isFollowAddPermission() {
        return followAddPermission;
    }

    public static boolean isSeeFollowPermission() {
        return seeFollowPermission;
    }

//    public static boolean isSearchFollowPermission() {
//        return searchFollowPermission;
//    }

    public static boolean isSeeDetailAddressPermission() {
        return seeDetailAddressPermission;
    }

    public static boolean isSeeOBuildPermission() {
        return seeOBuildPermission;
    }

    public static boolean isCallHiddenPhonePermission() {
        return callHiddenPhonePermission;
    }

    public static boolean isSeeClientInfoPermission() {
        return seeClientInfoPermission;
    }
}
