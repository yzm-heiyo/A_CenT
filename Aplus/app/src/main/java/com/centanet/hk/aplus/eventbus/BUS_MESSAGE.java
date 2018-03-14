package com.centanet.hk.aplus.eventbus;

/**
 * EventBus 消息
 */
public class BUS_MESSAGE {

    public class NetWorkState {
        public static final int NETWORK_STATE_FAIL = 1;
        public static final int NETWORK_STATE_SUCCESS = 2;
    }

    public class RefreshView{
        public static final int VIEW_REFRESH_START = 3;
        public static final int VIEW_LOAD_START = 4;
    }

    public class DetailRefreshView{
        public static final int DETAIL_ADDRESS = 5;
        public static final int DETAIL_DETAILDATA = 6;
        public static final int DETAIL_FOLLOW = 7;
    }

}
