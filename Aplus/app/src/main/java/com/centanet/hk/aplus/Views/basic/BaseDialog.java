package com.centanet.hk.aplus.Views.basic;

import android.app.Dialog;
import android.support.v4.app.DialogFragment;

/**
 * Created by yangzm4 on 2018/2/13.
 */

public class BaseDialog extends DialogFragment{

    protected onDialogOnclikeLisenter onDialogClikeLisenter;

    public void setOnDialogClikeLisenter(BaseDialog.onDialogOnclikeLisenter onDialogClikeLisenter) {
        this.onDialogClikeLisenter = onDialogClikeLisenter;
    }

    public interface onDialogOnclikeLisenter<T>{
        void onClike(Dialog dialog,int viewId,T result);
    }
}
