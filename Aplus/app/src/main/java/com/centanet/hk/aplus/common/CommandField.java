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

    public class ParamsType{
        public static final int propertyStatusCategory = 75;    // 房源狀態分類 1-有效，2-暫緩，3-預定，4-無效
    }

    public class PriceType{
        public static final int SALE = 9;
        public static final int RENT = 10;
    }

    public class DialogItemStatus{
        public static final int UNSELECT = 11;
    }

    public class FeedBackType{
        public static final int FOLLOW_GENERAL = 1;
    }

    public class TrustorDirectSellEnum{
        public static final int CAN = 0;
        public static final int CANNOT = 1;
        public static final int UNKNOWN =2;
    }



}
