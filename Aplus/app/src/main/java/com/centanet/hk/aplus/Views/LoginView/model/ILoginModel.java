package com.centanet.hk.aplus.Views.LoginView.model;

import com.centanet.hk.aplus.bean.http.AHeaderDescription;

/**
 * Created by yangzm4 on 2018/3/13.
 */

public interface ILoginModel {

    void doGet(String address, Object header, Object param);

    void doPost(String address, Object header, Object body);

    void setLisenter(LoginModel.OnReceiveLisenter lisenter);

    /**
     * 获取设施
     */
    void getTagCategory(AHeaderDescription header);

    /**
     * 獲得android設備唯一標識碼
     *
     * @return
     */
    String getUniquePsuedoID();

    /**
     * 檢測手機是否已經Root
     *
     * @return
     */
    boolean isRoot();
}
