package com.centanet.hk.aplus.eventbus;


import retrofit2.http.PUT;

/**
 * EventBus 消息
 */
public class BUS_MESSAGE {

    public class NetWorkState {
        public static final int NETWORK_STATE_FAIL = 1;
        public static final int NETWORK_STATE_SUCCESS = 2;
    }

    public class RefreshView {
        public static final int VIEW_REFRESH_START = 3;
        public static final int VIEW_LOAD_START = 4;
    }

    public class DetailRefreshView {
        public static final int DETAIL_ADDRESS = 5;
        public static final int DETAIL_DETAILDATA = 6;
        public static final int DETAIL_FOLLOW = 7;
        public static final int DETAIL_CLIENT_INFO = 8;
        public static final int DETAIL_FOLLOW_SUCCESS = 9;
        public static final int DETAIL_ADDRESS_SEE = 10;
    }

    public class HouseListDataCount {
        public static final int HOUSELIST_COUNT = 11;
        public static final int FAVOLIST_COUNT = 12;
    }

    public class Permission {
        public static final int HOUSELIST = 13;
        public static final int HOUSELIST_NO = 14;
        public static final int SEARCH_ALL = 15;
        public static final int SEARCH_ALL_NO = 16;
        public static final int FOLLOW_ADD = 17;
        public static final int FOLLOW_ADD_NO = 18;
        public static final int CLIENTINFO = 19;
        public static final int CLIENTINFO_NO = 20;
    }

    public class ReFreshDataState {
        public static final int DATA_END = 21;  //數據已到尾
        public static final int DATA_FAVO_END = 22;
    }

    public class FavoState {
        public static final int FAVO_FAVO_CANCEL = 23;
        public static final int HOUSE_FAVO_CANCEL = 24;
        public static final int HOUSE_FAVO = 25;
    }

    public class DetailRefresh {
        public static final int DETAIL_REFRESH = 26;
    }

    public class HouseListRemove {
        public static final int HOUSE_CANCE_FAVO = 27;
    }

    public static final int VIRTUALPHONE = 28;
    public static final int CALLHIDDEN_YES = 29;

}
