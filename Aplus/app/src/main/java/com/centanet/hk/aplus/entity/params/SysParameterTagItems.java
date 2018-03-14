package com.centanet.hk.aplus.entity.params;

import java.io.Serializable;

/**
 * Created by yangzm4 on 2018/2/2.
 */

public class SysParameterTagItems implements Serializable{

    String KeyId;
    String SysParameterItemsKeyId;
    String TagItemName;
    String TagItemType;
    String TagItemVaule;

    public SysParameterTagItems() {
    }

    public SysParameterTagItems(String keyId, String sysParameterItemsKeyId, String tagItemName, String tagItemType, String tagItemVaule) {
        KeyId = keyId;
        SysParameterItemsKeyId = sysParameterItemsKeyId;
        TagItemName = tagItemName;
        TagItemType = tagItemType;
        TagItemVaule = tagItemVaule;
    }

    public void setKeyId(String keyId) {
        KeyId = keyId;
    }

    public void setSysParameterItemsKeyId(String sysParameterItemsKeyId) {
        SysParameterItemsKeyId = sysParameterItemsKeyId;
    }

    public void setTagItemName(String tagItemName) {
        TagItemName = tagItemName;
    }

    public void setTagItemType(String tagItemType) {
        TagItemType = tagItemType;
    }

    public void setTagItemVaule(String tagItemVaule) {
        TagItemVaule = tagItemVaule;
    }

    public String getKeyId() {
        return KeyId;
    }

    public String getSysParameterItemsKeyId() {
        return SysParameterItemsKeyId;
    }

    public String getTagItemName() {
        return TagItemName;
    }

    public String getTagItemType() {
        return TagItemType;
    }

    public String getTagItemVaule() {
        return TagItemVaule;
    }

    @Override
    public String toString() {
        return "SysParameterTagItems{" +
                "KeyId='" + KeyId + '\'' +
                ", SysParameterItemsKeyId='" + SysParameterItemsKeyId + '\'' +
                ", TagItemName='" + TagItemName + '\'' +
                ", TagItemType='" + TagItemType + '\'' +
                ", TagItemVaule='" + TagItemVaule + '\'' +
                '}';
    }
}
