package com.centanet.hk.aplus.bean.build_tag;

import java.util.List;

/**
 * Created by yangzm4 on 2018/7/25.
 */

public class TagCategory {

    private String TagCategoryKeyId;
    private String ChineseName;
    private String EnglishName;
    private List<TagInfo> TagInfos;

    public TagCategory() {
    }

    public TagCategory(String tagCategoryKeyId, String chineseName, String englishName, List<TagInfo> tagInfos) {
        TagCategoryKeyId = tagCategoryKeyId;
        ChineseName = chineseName;
        EnglishName = englishName;
        TagInfos = tagInfos;
    }

    public void setTagCategoryKeyId(String tagCategoryKeyId) {
        TagCategoryKeyId = tagCategoryKeyId;
    }

    public void setChineseName(String chineseName) {
        ChineseName = chineseName;
    }

    public void setEnglishName(String englishName) {
        EnglishName = englishName;
    }

    public void setTagInfos(List<TagInfo> tagInfos) {
        TagInfos = tagInfos;
    }

    public String getTagCategoryKeyId() {
        return TagCategoryKeyId;
    }

    public String getChineseName() {
        return ChineseName;
    }

    public String getEnglishName() {
        return EnglishName;
    }

    public List<TagInfo> getTagInfos() {
        return TagInfos;
    }

    @Override
    public String toString() {
        return "TagCategory{" +
                "TagCategoryKeyId='" + TagCategoryKeyId + '\'' +
                ", ChineseName='" + ChineseName + '\'' +
                ", EnglishName='" + EnglishName + '\'' +
                ", TagInfos=" + TagInfos +
                '}';
    }
}
