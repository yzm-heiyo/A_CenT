package com.centanet.hk.aplus.entity.login;

/**
 * Created by yangzm4 on 2018/3/13.
 */

public class DomainUser {

    private String CityCode;
    private String StaffNo;
    private String CnName;
    private String DeptName;
    private String DomainAccount;
    private String Mobile;
    private String Title;
    private String Email;
    private String AgentUrl;
    private String CompanyName;

    public void setCityCode(String cityCode) {
        CityCode = cityCode;
    }

    public void setStaffNo(String staffNo) {
        StaffNo = staffNo;
    }

    public void setCnName(String cnName) {
        CnName = cnName;
    }

    public void setDeptName(String deptName) {
        DeptName = deptName;
    }

    public void setDomainAccount(String domainAccount) {
        DomainAccount = domainAccount;
    }

    public void setMobile(String mobile) {
        Mobile = mobile;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public void setAgentUrl(String agentUrl) {
        AgentUrl = agentUrl;
    }

    public void setCompanyName(String companyName) {
        CompanyName = companyName;
    }

    public String getCityCode() {
        return CityCode;
    }

    public String getStaffNo() {
        return StaffNo;
    }

    public String getCnName() {
        return CnName;
    }

    public String getDeptName() {
        return DeptName;
    }

    public String getDomainAccount() {
        return DomainAccount;
    }

    public String getMobile() {
        return Mobile;
    }

    public String getTitle() {
        return Title;
    }

    public String getEmail() {
        return Email;
    }

    public String getAgentUrl() {
        return AgentUrl;
    }

    public String getCompanyName() {
        return CompanyName;
    }

    @Override
    public String toString() {
        return "DomainUser{" +
                "CityCode='" + CityCode + '\'' +
                ", StaffNo='" + StaffNo + '\'' +
                ", CnName='" + CnName + '\'' +
                ", DeptName='" + DeptName + '\'' +
                ", DomainAccount='" + DomainAccount + '\'' +
                ", Mobile='" + Mobile + '\'' +
                ", Title='" + Title + '\'' +
                ", Email='" + Email + '\'' +
                ", AgentUrl='" + AgentUrl + '\'' +
                ", CompanyName='" + CompanyName + '\'' +
                '}';
    }
}
