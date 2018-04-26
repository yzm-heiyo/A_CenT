package com.centanet.hk.aplus.Utils;

import android.content.Context;

import com.centanet.hk.aplus.Views.Dialog.SimpleTipsDialog;

/**
 * Created by yangzm4 on 2018/3/30.
 */

public class DialogUtil {


    //後面改爲工廠模式
    public static SimpleTipsDialog getSimpleDialog(String content){
        SimpleTipsDialog simpleTipsDialog = new SimpleTipsDialog();
        simpleTipsDialog.setContentString(content);
        simpleTipsDialog.setLeftBtnVisibility(false);
        return simpleTipsDialog;
    }

}