package com.centanet.hk.aplus.eventbus;


import org.greenrobot.eventbus.EventBus;

/**
 * 需要发送EventBus事件的类可以继承该类，方便方法调用
 */
public class BaseClass {

    /**
     * 发送EventBus事件 带消息
     * @param bus_msg
     */
    public void notifyEmptyBusMessage(int bus_msg){
        MessageEventBus msg = new MessageEventBus();
        msg.setMsg(bus_msg);
        EventBus.getDefault().post(msg);
    }

    /**
     *发送EventBus事件 带消息，带数据
     * @param bus_msg
     * @param object
     */
    public void notifyBusMessage(int bus_msg, Object object){
        MessageEventBus msg = new MessageEventBus();
        msg.setMsg(bus_msg);
        msg.setObject(object);
        EventBus.getDefault().post(msg);
    }

    /**
     *發送延時EventBus事件
     */
    public void delayNoticeBusMessage(int smg,Object object){

        MessageEventBus msg = new MessageEventBus();
        msg.setMsg(smg);
        msg.setObject(object);
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        EventBus.getDefault().post(msg);
    }

}
