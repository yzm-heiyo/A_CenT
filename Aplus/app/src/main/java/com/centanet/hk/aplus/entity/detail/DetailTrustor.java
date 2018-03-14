package com.centanet.hk.aplus.entity.detail;

import java.util.List;

/**
 * Created by yangzm4 on 2018/3/13.
 */

public class DetailTrustor {

    private boolean CanBrowse;
    private boolean IsDeptContactInformationSearch;
    private String NoCallMessage;
    private int UsedBrowseCount;
    private int RemainingBrowseCount;
    private int TotalBrowseCount;
    private List<Trustor> Trustors;
    private boolean IsViewHiddenPhone;
    private boolean IsNocontact;
    private boolean IsSaleType;
    private boolean Flag;
    private String ErrorMsg;
    private String RunTime;

    public void setCanBrowse(boolean canBrowse) {
        CanBrowse = canBrowse;
    }

    public void setDeptContactInformationSearch(boolean deptContactInformationSearch) {
        IsDeptContactInformationSearch = deptContactInformationSearch;
    }

    public void setNoCallMessage(String noCallMessage) {
        NoCallMessage = noCallMessage;
    }

    public void setUsedBrowseCount(int usedBrowseCount) {
        UsedBrowseCount = usedBrowseCount;
    }

    public void setRemainingBrowseCount(int remainingBrowseCount) {
        RemainingBrowseCount = remainingBrowseCount;
    }

    public void setTotalBrowseCount(int totalBrowseCount) {
        TotalBrowseCount = totalBrowseCount;
    }

    public void setTrustors(List<Trustor> trustors) {
        Trustors = trustors;
    }

    public void setViewHiddenPhone(boolean viewHiddenPhone) {
        IsViewHiddenPhone = viewHiddenPhone;
    }

    public void setNocontact(boolean nocontact) {
        IsNocontact = nocontact;
    }

    public void setSaleType(boolean saleType) {
        IsSaleType = saleType;
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

    public boolean isCanBrowse() {
        return CanBrowse;
    }

    public boolean isDeptContactInformationSearch() {
        return IsDeptContactInformationSearch;
    }

    public String getNoCallMessage() {
        return NoCallMessage;
    }

    public int getUsedBrowseCount() {
        return UsedBrowseCount;
    }

    public int getRemainingBrowseCount() {
        return RemainingBrowseCount;
    }

    public int getTotalBrowseCount() {
        return TotalBrowseCount;
    }

    public List<Trustor> getTrustors() {
        return Trustors;
    }

    public boolean isViewHiddenPhone() {
        return IsViewHiddenPhone;
    }

    public boolean isNocontact() {
        return IsNocontact;
    }

    public boolean isSaleType() {
        return IsSaleType;
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

    @Override
    public String toString() {
        return "DetailTrustor{" +
                "CanBrowse=" + CanBrowse +
                ", IsDeptContactInformationSearch=" + IsDeptContactInformationSearch +
                ", NoCallMessage='" + NoCallMessage + '\'' +
                ", UsedBrowseCount=" + UsedBrowseCount +
                ", RemainingBrowseCount=" + RemainingBrowseCount +
                ", TotalBrowseCount=" + TotalBrowseCount +
                ", Trustors=" + Trustors +
                ", IsViewHiddenPhone=" + IsViewHiddenPhone +
                ", IsNocontact=" + IsNocontact +
                ", IsSaleType=" + IsSaleType +
                ", Flag=" + Flag +
                ", ErrorMsg='" + ErrorMsg + '\'' +
                ", RunTime='" + RunTime + '\'' +
                '}';
    }
}
