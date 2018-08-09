package com.centanet.hk.aplus.bean.build_tag;

import com.centanet.hk.aplus.bean.BaseResponse;

import java.util.List;

/**
 * Created by yangzm4 on 2018/7/25.
 */

public class BuildTagResponse extends BaseResponse {

    private List<TagCategory> TagCategorys;

    public BuildTagResponse(List<TagCategory> tagCategorys) {
        TagCategorys = tagCategorys;
    }

    public BuildTagResponse(boolean flag, String errorMsg, String runTime, List<TagCategory> tagCategorys) {
        super(flag, errorMsg, runTime);
        TagCategorys = tagCategorys;
    }

    public void setTagCategorys(List<TagCategory> tagCategorys) {
        TagCategorys = tagCategorys;
    }

    public List<TagCategory> getTagCategorys() {
        return TagCategorys;
    }

    @Override
    public String toString() {
        return "BuildTagResponse{" +
                "TagCategorys=" + TagCategorys +
                '}';
    }
}
