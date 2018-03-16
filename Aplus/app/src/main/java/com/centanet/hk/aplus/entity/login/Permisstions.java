package com.centanet.hk.aplus.entity.login;

import java.util.Map;

/**
 * Created by yangzm4 on 2018/3/14.
 */

public class Permisstions {

    String MenuPermisstion;
    String Rights;
    String DepartmentKeyIds;
    String RightUpdateTime;
    Map<String,String> OperatorValPermisstion;

    public String getMenuPermisstion() {
        return MenuPermisstion;
    }

    public String getRights() {
        return Rights;
    }

    public String getDepartmentKeyIds() {
        return DepartmentKeyIds;
    }

    public String getRightUpdateTime() {
        return RightUpdateTime;
    }

    public Map<String, String> getOperatorValPermisstion() {
        return OperatorValPermisstion;
    }

    public void setMenuPermisstion(String menuPermisstion) {
        MenuPermisstion = menuPermisstion;
    }

    public void setRights(String rights) {
        Rights = rights;
    }

    public void setDepartmentKeyIds(String departmentKeyIds) {
        DepartmentKeyIds = departmentKeyIds;
    }

    public void setRightUpdateTime(String rightUpdateTime) {
        RightUpdateTime = rightUpdateTime;
    }

    public void setOperatorValPermisstion(Map<String, String> operatorValPermisstion) {
        OperatorValPermisstion = operatorValPermisstion;
    }

    @Override
    public String toString() {
        return "Permisstions{" +
                "MenuPermisstion='" + MenuPermisstion + '\'' +
                ", Rights='" + Rights + '\'' +
                ", DepartmentKeyIds='" + DepartmentKeyIds + '\'' +
                ", RightUpdateTime='" + RightUpdateTime + '\'' +
                ", OperatorValPermisstion=" + OperatorValPermisstion +
                '}';
    }
}
