package com.centanet.hk.aplus.eventbus;

import java.security.PublicKey;

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
    }

    public class HouseListDataCount {
        public static final int HOUSELIST_COUNT = 10;
        public static final int FAVOLIST_COUNT = 11;
    }

    public class Permission {
        public static final int HOUSELIST = 12;
        public static final int HOUSELIST_NO = 13;
        public static final int SEARCH_ALL = 14;
        public static final int SEARCH_ALL_NO = 15;
        public static final int FOLLOW_ADD = 16;
        public static final int FOLLOW_ADD_NO = 17;
        public static final int CLIENTINFO = 18;
        public static final int CLIENTINFO_NO = 19;
    }

}
