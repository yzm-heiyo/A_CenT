package com.centanet.hk.aplus.Views.HouseDetailView.model;

import com.centanet.hk.aplus.Utils.L;
import com.centanet.hk.aplus.Utils.net.GsonUtil;
import com.centanet.hk.aplus.Utils.net.HttpUtil;
import com.centanet.hk.aplus.bean.detail.DetailAddress;
import com.centanet.hk.aplus.bean.detail.DetailFollow;
import com.centanet.hk.aplus.bean.detail.DetailHouse;
import com.centanet.hk.aplus.bean.detail.DetailTrustor;
import com.centanet.hk.aplus.bean.detail.VirtualPhone;
import com.centanet.hk.aplus.bean.http.AHeaderDescription;
import com.centanet.hk.aplus.eventbus.BUS_MESSAGE;
import com.centanet.hk.aplus.eventbus.BaseClass;
import com.centanet.hk.aplus.manager.PermissionManager;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

import static com.centanet.hk.aplus.eventbus.BUS_MESSAGE.CALLHIDDEN_YES;
import static com.centanet.hk.aplus.eventbus.BUS_MESSAGE.DetailRefreshView.DETAIL_ADDRESS;
import static com.centanet.hk.aplus.eventbus.BUS_MESSAGE.DetailRefreshView.DETAIL_CLIENT_INFO;
import static com.centanet.hk.aplus.eventbus.BUS_MESSAGE.DetailRefreshView.DETAIL_DETAILDATA;
import static com.centanet.hk.aplus.eventbus.BUS_MESSAGE.DetailRefreshView.DETAIL_FOLLOW;
import static com.centanet.hk.aplus.eventbus.BUS_MESSAGE.Permission.CLIENTINFO_NO;
import static com.centanet.hk.aplus.eventbus.BUS_MESSAGE.Permission.FOLLOW_ADD;
import static com.centanet.hk.aplus.eventbus.BUS_MESSAGE.Permission.SEARCH_ALL_NO;
import static com.centanet.hk.aplus.eventbus.BUS_MESSAGE.ReFreshDataState.DATA_END;
import static com.centanet.hk.aplus.eventbus.BUS_MESSAGE.VIRTUALPHONE;
import static com.centanet.hk.aplus.manager.PermissionManager.FOLLOW_ALL;

/**
 * Created by yangzm4 on 2018/3/8.
 */

public class DetailModel extends BaseClass implements IDetailModel {

    private String thiz = getClass().getSimpleName();
    boolean isEnd = false;

    private static DetailModel detailModel = new DetailModel();

    private OnReceiveListener onReceiveListener;

    private DetailHouse houseData;

    private String departmentPermission;

    public static synchronized DetailModel getInstance() {
        return detailModel;
    }

    @Override
    public void doPost(final String address, AHeaderDescription headers, final Object bodys) {


        HttpUtil.doPost(address, bodys, headers, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                notifyEmptyBusMessage(BUS_MESSAGE.NetWorkState.NETWORK_STATE_FAIL);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                if (response.isSuccessful()) {
                    String dataBack = response.body().string().trim().toString();
                    L.d(thiz + "-Response", dataBack);
                    switch (address) {
                        case HttpUtil.URL_ADDRESS_DETAIL:
                            getAddressDetail(dataBack);
                            break;
                        case HttpUtil.URL_DETAIL:
                            getDeatail(dataBack);
                            break;
                        case HttpUtil.URL_FOLLOWS:
                            if (isEnd) {
                                notifyEmptyBusMessage(DATA_END);
                                return;
                            }
                            getFollow(dataBack);
                            break;
                        case HttpUtil.URL_TRUSTOR:
                            getTrustor(dataBack);
                            break;
                        case HttpUtil.URL_CALL_VIRTUAL_PHONE:
                            getPhoneNumber(dataBack);
                        default:
                            break;
                    }
                } else notifyEmptyBusMessage(BUS_MESSAGE.NetWorkState.NETWORK_STATE_FAIL);
            }
        });
    }

    private void getPhoneNumber(String dataBack) {

        try {
            VirtualPhone virtualPhone = GsonUtil.parseJson(dataBack, VirtualPhone.class);
            notifyBusMessage(VIRTUALPHONE, virtualPhone);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }

    }

    private void getTrustor(String dataBack) {

        if (!verifyPermission(departmentPermission, PermissionManager.CLIENTINFO_ALL)) {
            notifyEmptyBusMessage(CLIENTINFO_NO);
            return;
        }

        if (verifyPermission(departmentPermission, PermissionManager.CALL_HIDDEN_PHONE)) {
            notifyEmptyBusMessage(CALLHIDDEN_YES);
        }

        DetailTrustor detailTrustor;
        try {
            detailTrustor = GsonUtil.parseJson(dataBack, DetailTrustor.class);
            L.d(thiz, "permission: " + detailTrustor.isDeptContactInformationSearch() + "");
            notifyBusMessage(DETAIL_CLIENT_INFO, detailTrustor);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
    }

    private boolean verifyPermission(String pers, String per) {
        boolean departPer = PermissionManager.verifyPermission(pers, per);
        boolean permission = PermissionManager.verifyPermission(per);
        L.d("", departPer + " : " + permission);
        if (!departPer || !permission) return false;
        return true;
    }

    private void getFollow(String dataBack) {

        if (!verifyPermission(departmentPermission, PermissionManager.SEARCH_ALL)) {
            notifyEmptyBusMessage(SEARCH_ALL_NO);
            notifyEmptyBusMessage(DETAIL_FOLLOW);
            return;
        }

        if (verifyPermission(departmentPermission, FOLLOW_ALL)) {
            notifyEmptyBusMessage(FOLLOW_ADD);
        }

        DetailFollow follows = null;
        try {
            follows = GsonUtil.parseJson(dataBack, DetailFollow.class);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
        if (follows.getPropertyFollows().size() < 5) isEnd = true;
        delayNoticeBusMessage(DETAIL_FOLLOW, follows.getPropertyFollows());
    }

    private void getAddressDetail(String dataBack) {
        DetailAddress address = null;
        try {
            address = GsonUtil.parseJson(dataBack, DetailAddress.class);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
        notifyBusMessage(DETAIL_ADDRESS, address);
    }

    private void getDeatail(String dataBack) {

        try {
            houseData = GsonUtil.parseJson(dataBack, DetailHouse.class);
            departmentPermission = houseData.getDepartmentPermissions();
            if (onReceiveListener != null) onReceiveListener.onReceive(houseData);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
        delayNoticeBusMessage(DETAIL_DETAILDATA, houseData);

    }

    public void setOnReceiveListener(OnReceiveListener onReceiveListener) {
        this.onReceiveListener = onReceiveListener;
    }

    @Override
    public void clearNetFlag() {
        isEnd = false;
    }

    public interface OnReceiveListener {
        void onReceive(DetailHouse dataBack);

        void onReceivelFinish();
    }
}
