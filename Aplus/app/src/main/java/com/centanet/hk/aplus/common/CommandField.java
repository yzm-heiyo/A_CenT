package com.centanet.hk.aplus.common;

/**
 * Created by yangzm4 on 2018/1/31.
 */

public class CommandField {

    public class DialogType {
        public static final int SORT = 1;
        public static final int STATUS = 2;
        public static final int PRICE = 3;
        public static final int HINT = 4;
        public static final int OPENDATE = 5;
        public static final int CONFIRM = 6;
        public static final int LOGOUT = 7;
        public static final int REMARK = 8;
    }

    public class ParamsType {

        public static final int propertyStatusCategory = 75;    // 房源狀態分類 1-有效，2-暫緩，3-預定，4-無效

    }

    public class PriceType {
        public static final int SALE = 9;
        public static final int RENT = 10;
    }

    public class DialogItemStatus {
        public static final int UNSELECT = 11;
    }

    public class FeedBackType {
        public static final int FOLLOW_GENERAL = 1;
    }

    public class TrustorDirectSellEnum {
        public static final int CAN = 0;
        public static final int CANNOT = 1;
        public static final int UNKNOWN = 2;
    }

    //房源日期类型
    public class APPropertyDateType {
        public static final int statusChangedDate = 1;  // 改盤日期
        public static final int lasetUpdate = 2;  // 最后修改日期
        public static final int lastFollowDate = 3; // 最后修改日期
        public static final int registDate = 4;  // 開盤日期
        public static final int estimatedDate = 5;  // 估計日期
        public static final int changePriceDate = 6;  // 最后修改日期
        public static final int onlyTrustStartDate = 7;  // 委託書開始日
        public static final int onlyTrustEndDate = 8;  // 委託書到期日
    }

    public class APPropertyTrustorDirectSell{
        public static final int ALLOW = 0;  // 改盤日期
        public static final int NOTALLOW = 1;  // 最后修改日期
        public static final int UNKNOW = 2; // 最后跟進日期
    }

    public class PriceUnitType{//均價類型(1:實均售,2:實均租,3:建均售,4:建均租,5:綠表價實均售,6:綠表價建均售)
        public static final int AVG_USE_SALE = 1;  // 實均售
        public static final int AVG_USE_RENT = 2;  // 實均租
        public static final int AVG_REALLY_SALE = 3; // 建均售
        public static final int AVG_REALLY_RENT = 4;  // 建均租
        public static final int AVG_GREEN_USE_SALE = 5;  // 綠表價實均售
        public static final int AVG_GREEN_REALLY_SALE = 6;  // 綠表價建均售
    }

    public class PropertySquareType{//1-建築面積，2-實用面積，3-花園面積
        public static final int AREA_REALLY = 1;  // 建築面積
        public static final int AREA_USE = 2;  // 實用面積
        public static final int AREA_GARDEN = 3;  // 花園面積
    }
}
