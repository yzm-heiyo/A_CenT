package com.centanet.hk.aplus.entity.house;

import java.util.List;

/**
 * Created by yangzm4 on 2018/3/13.
 */

public class HouseData {

    private int RecordCount;
    private List<Properties> Properties;
    private boolean Flag;
    private String ErrorMsg;
    private PermisstionsModel PermisstionsModel;
    private String RunTime;

    public int getRecordCount() {
        return RecordCount;
    }

    public List<Properties> getPropertie() {
        return Properties;
    }

    public boolean isFlag() {
        return Flag;
    }

    public String getErrorMsg() {
        return ErrorMsg;
    }

    public String getRunTime() {
        return RunTime;
    }

    public void setRecordCount(int recordCount) {
        RecordCount = recordCount;
    }

    public void setPropertie(List<Properties> propertie) {
        Properties = propertie;
    }

    public void setFlag(boolean flag) {
        Flag = flag;
    }

    public void setErrorMsg(String errorMsg) {
        ErrorMsg = errorMsg;
    }

    public void setRunTime(String runTime) {
        RunTime = runTime;
    }

    public void setPermisstionsModel(PermisstionsModel permisstionsModel) {
        this.PermisstionsModel = permisstionsModel;
    }

    public PermisstionsModel getPermisstionsModel() {

        return PermisstionsModel;
    }

    @Override
    public String toString() {
        return "CentaData{" +
                "RecordCount=" + RecordCount +
                ", Properties=" + Properties +
                ", Flag=" + Flag +
                ", ErrorMsg='" + ErrorMsg + '\'' +
                ", PermisstionsModel=" + PermisstionsModel +
                ", RunTime='" + RunTime + '\'' +
                '}';
    }

}
