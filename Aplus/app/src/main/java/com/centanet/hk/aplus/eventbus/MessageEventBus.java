package com.centanet.hk.aplus.eventbus;

/**
 * EventBus事件， msg是消息，object是数据
 */
public class MessageEventBus {

    private int msg;
    private Object object;

    public int getMsg() {
        return msg;
    }

    public Object getObject() {
        return object;
    }

    public void setMsg(int msg) {
        this.msg = msg;
    }

    public void setObject(Object object) {
        this.object = object;
    }
}

