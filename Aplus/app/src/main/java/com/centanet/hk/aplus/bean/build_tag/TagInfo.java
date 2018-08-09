package com.centanet.hk.aplus.bean.build_tag;

import org.litepal.LitePal;
import org.litepal.crud.DataSupport;
import org.litepal.crud.LitePalSupport;

import java.io.Serializable;

/**
 * Created by yangzm4 on 2018/7/25.
 */

public class TagInfo extends LitePalSupport implements Serializable{

    private String TagKeyId;
    private String TagChineseName;
    private String TagEnglishName;

    public TagInfo(String tagKeyId, String tagChineseName, String tagEnglishName) {
        TagKeyId = tagKeyId;
        TagChineseName = tagChineseName;
        TagEnglishName = tagEnglishName;
    }

    public TagInfo() {
    }

    public void setTagKeyId(String tagKeyId) {
        TagKeyId = tagKeyId;
    }

    public void setTagChineseName(String tagChineseName) {
        TagChineseName = tagChineseName;
    }

    public void setTagEnglishName(String tagEnglishName) {
        TagEnglishName = tagEnglishName;
    }

    public String getTagKeyId() {
        return TagKeyId;
    }

    public String getTagChineseName() {
        return TagChineseName;
    }

    public String getTagEnglishName() {
        return TagEnglishName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TagInfo)) return false;

        TagInfo info = (TagInfo) o;

        if (!getTagKeyId().equals(info.getTagKeyId())) return false;
        if (!getTagChineseName().equals(info.getTagChineseName())) return false;
        return getTagEnglishName().equals(info.getTagEnglishName());
    }

    @Override
    public int hashCode() {
        int result = getTagKeyId().hashCode();
        result = 31 * result + getTagChineseName().hashCode();
        result = 31 * result + getTagEnglishName().hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "TagInfo{" +
                "TagKeyId='" + TagKeyId + '\'' +
                ", TagChineseName='" + TagChineseName + '\'' +
                ", TagEnglishName='" + TagEnglishName + '\'' +
                '}';
    }
}
