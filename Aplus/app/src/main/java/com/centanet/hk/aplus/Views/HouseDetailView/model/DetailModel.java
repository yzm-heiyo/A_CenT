package com.centanet.hk.aplus.Views.HouseDetailView.model;

import com.centanet.hk.aplus.Utils.L;
import com.centanet.hk.aplus.Utils.MD5Util;
import com.centanet.hk.aplus.Utils.TextUtil;
import com.centanet.hk.aplus.Utils.net.GsonUtil;
import com.centanet.hk.aplus.Utils.net.HttpUtil;
import com.centanet.hk.aplus.bean.detail.DetailAddressResponse;
import com.centanet.hk.aplus.bean.detail.DetailBriefInfo;
import com.centanet.hk.aplus.bean.detail.DetailFollowResponse;
import com.centanet.hk.aplus.bean.detail.DetailHouse;
import com.centanet.hk.aplus.bean.detail.DetailTrustor;
import com.centanet.hk.aplus.bean.detail.DetaileNextKeyIdResponse;
import com.centanet.hk.aplus.bean.detail.VirtualPhoneResponse;
import com.centanet.hk.aplus.bean.http.AHeaderDescription;
import com.centanet.hk.aplus.bean.http.DetailListsDescription;
import com.centanet.hk.aplus.bean.http.DetaileNextKeyIdDescription;
import com.centanet.hk.aplus.bean.http.PropertyAddDescription;
import com.centanet.hk.aplus.eventbus.BUS_MESSAGE;
import com.centanet.hk.aplus.eventbus.BaseClass;
import com.centanet.hk.aplus.manager.ApplicationManager;
import com.centanet.hk.aplus.manager.PermissionManager;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

import static com.centanet.hk.aplus.eventbus.BUS_MESSAGE.CALLHIDDEN_YES;
import static com.centanet.hk.aplus.eventbus.BUS_MESSAGE.DetailRefreshView.DETAIL_ADDRESS;
import static com.centanet.hk.aplus.eventbus.BUS_MESSAGE.DetailRefreshView.DETAIL_CLIENT_INFO;
import static com.centanet.hk.aplus.eventbus.BUS_MESSAGE.DetailRefreshView.DETAIL_DETAILDATA;
import static com.centanet.hk.aplus.eventbus.BUS_MESSAGE.DetailRefreshView.DETAIL_FOLLOW;
import static com.centanet.hk.aplus.eventbus.BUS_MESSAGE.OBUILDING_YES;
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

    private OnReceiveListener onPropertyDetailReceiveListener, onPropertyOtherReceiveListener;

    private DetailHouse houseData;

    private String departmentPermission;

    private List<String> keyIds;

    private int pageIndex;

    private OnReceiveListener onPropertyNextReceiveListener;

    private CountDownLatch countDownLatch = new CountDownLatch(1);

    public static synchronized DetailModel getInstance() {
        return detailModel;
    }

    @Override
    public void doPost(final String address, AHeaderDescription headers, final Object bodys) {

        String number = System.currentTimeMillis() / 1000 + "";
        L.d("time", number);
        headers.setNumber(number);
        headers.setSign(MD5Util.getMD5Str("CYDAP_com-group~Centa@" + number + headers.getStaffno()));

        HttpUtil.doPost(address, bodys, headers, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                notifyEmptyBusMessage(BUS_MESSAGE.NetWorkState.NETWORK_STATE_FAIL);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String dataBack = response.body().string().trim().toString();

                L.d(thiz + "-Response", dataBack + "");
                if (response.isSuccessful()) {
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
                            break;
                        case HttpUtil.URL_USER_BEHAVIOR:
                            L.d("ScreenShot", dataBack);
                            break;
//                        case HttpUtil.URL_DETAILE_NEXT_KEYID:
//                            parseNextKeyIds(dataBack);
//                            break;
                        default:
                            break;
                    }
                } else notifyEmptyBusMessage(BUS_MESSAGE.NetWorkState.NETWORK_STATE_FAIL);
            }
        });
    }

    private void parseNextKeyIds(String dataBack) {

        L.d(thiz, "PropertyKeyIds: " + dataBack);
        try {
            DetaileNextKeyIdResponse nextKeyIdResponse = GsonUtil.parseJson(dataBack, DetaileNextKeyIdResponse.class);
            keyIds = nextKeyIdResponse.getKeyIds();
            if(onPropertyNextReceiveListener!=null)onPropertyNextReceiveListener.onReceive(null);
            L.d("parseNextKeyIds",keyIds.toString());
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void doGet(String address, AHeaderDescription headers, Object bodys) {
        String finalAddress = HttpUtil.URL + address;
        String number = System.currentTimeMillis() / 1000 + "";
        L.d("time", number);
        headers.setNumber(number);
        headers.setSign(MD5Util.getMD5Str("CYDAP_com-group~Centa@" + number + headers.getStaffno()));

        HttpUtil.doGet(finalAddress, headers, bodys, new Callback() {

            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
//                String dataBack = response.body().string().toString();
//                L.d("get_MoreList",dataBack);
                if (response.isSuccessful()) {
                    switch (address) {
                        case HttpUtil.URL_DETAILS_LIST:
                            getDetailLists(response.body().byteStream());
                            break;
                        case HttpUtil.URL_DETAILE_NEXT_KEYID:
                            String dataBack = response.body().string().toString();
                            L.d(thiz, dataBack);
                            pageIndex = ((DetaileNextKeyIdDescription) bodys).getPageIndex();
                            parseNextKeyIds(dataBack);
                            countDownLatch.countDown();
                            break;
                    }
                }
            }
        });
    }

    @Override
    public void setOnPropertDetailReceiveListener(OnReceiveListener onReceiveListener) {
        onPropertyDetailReceiveListener = onReceiveListener;
    }

    @Override
    public void setOnPropertOtherReceiveListener(OnReceiveListener onReceiveListener) {
        onPropertyOtherReceiveListener = onReceiveListener;
    }

    @Override
    public void setOnPropertNextReceiveListener(OnReceiveListener onReceiveListener) {
        this.onPropertyNextReceiveListener = onPropertyNextReceiveListener;
    }

    private void getDetailLists(InputStream dataBack) {

        try {
            DetailBriefInfo detailBriefInfo = GsonUtil.parseJson(dataBack, DetailBriefInfo.class);
            L.d(thiz, "detailLists: " + detailBriefInfo.toString());
            if (onPropertyOtherReceiveListener != null)
                onPropertyOtherReceiveListener.onReceive(detailBriefInfo);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
    }


    private void getPhoneNumber(String dataBack) {
        L.d(thiz, "PhoneNumber: " + dataBack);
        try {
            VirtualPhoneResponse virtualPhone = GsonUtil.parseJson(dataBack, VirtualPhoneResponse.class);
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

        PermissionManager.setSeeClientInfoPermission(true);

        if (verifyPermission(departmentPermission, PermissionManager.CALL_HIDDEN_PHONE)) {
            notifyEmptyBusMessage(CALLHIDDEN_YES);
            PermissionManager.setCallHiddenPhonePermission(true);
        }

        if (PermissionManager.verifyPermission(PermissionManager.OBULIDING)) {
            notifyEmptyBusMessage(OBUILDING_YES);
            PermissionManager.setSeeOBuildPermission(true);
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

        PermissionManager.setSearchAllPermission(true);

        if (verifyPermission(departmentPermission, FOLLOW_ALL)) {
            notifyEmptyBusMessage(FOLLOW_ADD);
            PermissionManager.setFollowAddPermission(true);
        }

        DetailFollowResponse follows = null;
        try {
            follows = GsonUtil.parseJson(dataBack, DetailFollowResponse.class);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
        if (follows.getPropertyFollows() != null && follows.getPropertyFollows().size() < 5)
            isEnd = true;
        notifyBusMessage(DETAIL_FOLLOW, follows.getPropertyFollows());
    }

    private void getAddressDetail(String dataBack) {
        DetailAddressResponse address = null;
        try {
            address = GsonUtil.parseJson(dataBack, DetailAddressResponse.class);
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

            /** 查看O盘 */
            if (PermissionManager.verifyPermission(PermissionManager.OBULIDING)) {
                PermissionManager.setSeeOBuildPermission(true);
            }

            /** 拨打业主电话 */
            if (verifyPermission(departmentPermission, PermissionManager.CALL_HIDDEN_PHONE)) {
                PermissionManager.setCallHiddenPhonePermission(true);
            }

            /** 查看业主资料 */
            if (verifyPermission(departmentPermission, PermissionManager.CLIENTINFO_ALL)) {
                PermissionManager.setSeeClientInfoPermission(true);
            }

            /** 添加跟进 */
            if (verifyPermission(departmentPermission, FOLLOW_ALL)) {
                PermissionManager.setFollowAddPermission(true);
            }

            /** 查看跟进 */
            if (verifyPermission(departmentPermission, PermissionManager.SEARCH_ALL)) {
                PermissionManager.setSearchAllPermission(true);
            }

            L.d("permission", departmentPermission);
            if (onPropertyDetailReceiveListener != null)
                onPropertyDetailReceiveListener.onReceive(houseData);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
        delayNoticeBusMessage(DETAIL_DETAILDATA, houseData);

    }


    @Override
    public void clearNetFlag() {
        isEnd = false;
    }


    @Override
    public void getPropertyDetail(int index) {

//        if(index)

        PropertyAddDescription detailsDescription = new PropertyAddDescription();
        if(TextUtil.isEmply(keyIds)){
            notifyEmptyBusMessage(BUS_MESSAGE.NetWorkState.NETWORK_STATE_FAIL);
            return;
        }
        detailsDescription.setKeyId(keyIds.get(index));
        doPost(HttpUtil.URL_DETAIL, ApplicationManager.getApplication().getHeaderDescription(), detailsDescription);

        DetailListsDescription description = new DetailListsDescription();
        description.setKeyId(keyIds.get(index));
//        doGet(HttpUtil.URL_DETAILS_LIST, ApplicationManager.getApplication().getHeaderDescription(), description);
    }

    @Override
    public void getPropertDetailOther(int index) {
//        new Thread(() -> {
//            try {
//                countDownLatch.await();
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
            DetailListsDescription description = new DetailListsDescription();
            description.setKeyId(keyIds.get(index));
            doGet(HttpUtil.URL_DETAILS_LIST, ApplicationManager.getApplication().getHeaderDescription(), description);
//        }).start();
    }

    @Override
    public String getPropertyKey(int index) {
        return keyIds.get(index);
    }

    public interface OnReceiveListener<T> {
        void onReceive(T dataBack);

        void onReceivelFinish();
    }
}
