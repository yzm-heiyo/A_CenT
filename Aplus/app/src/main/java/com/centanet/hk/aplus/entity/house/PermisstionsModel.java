package com.centanet.hk.aplus.entity.house;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by yangzm4 on 2018/1/30.
 */

public class PermisstionsModel implements Serializable {

    private String MenuPermisstion;
    private String Rights;
    private String DepartmentKeyIds;
    private Map<String, String> OperatorValPermisstion = new HashMap<>();
    private String RightUpdateTime;

    public PermisstionsModel() {
    }

    public PermisstionsModel(String menuPermisstion, String rights, String departmentKeyIds, Map<String, String> operatorValPermisstion, String rightUpdateTime) {
        MenuPermisstion = menuPermisstion;
        Rights = rights;
        DepartmentKeyIds = departmentKeyIds;
        OperatorValPermisstion = operatorValPermisstion;
        RightUpdateTime = rightUpdateTime;
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

    public void setOperatorValPermisstion(Map<String, String> operatorValPermisstion) {
        OperatorValPermisstion = operatorValPermisstion;
    }

    public Map<String, String> getOperatorValPermisstion() {

        return OperatorValPermisstion;
    }

    @Override
    public String toString() {
        return "PermisstionsModel{" +
                "MenuPermisstion='" + MenuPermisstion + '\'' +
                ", Rights='" + Rights + '\'' +
                ", DepartmentKeyIds='" + DepartmentKeyIds + '\'' +
                ", RightUpdateTime='" + RightUpdateTime + '\'' +
                '}';
    }
}
