package com.centanet.hk.aplus.bean.detail;

import java.io.Serializable;

/**
 * Created by yangzm4 on 2018/7/2.
 */

public class DetailDataInformation implements Serializable{

    String Estate;
    String Daybook;

    public void setEstate(String estate) {
        Estate = estate;
    }

    public void setDaybook(String daybook) {
        Daybook = daybook;
    }

    public String getEstate() {
        return Estate;
    }

    public String getDaybook() {
        return Daybook;
    }

    @Override
    public String toString() {
        return "CentaData{" +
                "Estate='" + Estate + '\'' +
                ", Daybook='" + Daybook + '\'' +
                '}';
    }
}
