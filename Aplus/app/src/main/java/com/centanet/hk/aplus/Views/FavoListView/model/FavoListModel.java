package com.centanet.hk.aplus.Views.FavoListView.model;

import com.centanet.hk.aplus.MyApplication;
import com.centanet.hk.aplus.Utils.L;
import com.centanet.hk.aplus.Utils.MD5Util;
import com.centanet.hk.aplus.Utils.net.GsonUtil;
import com.centanet.hk.aplus.Utils.net.HttpUtil;
import com.centanet.hk.aplus.bean.favo.FavoResponse;
import com.centanet.hk.aplus.bean.house.HouseData;
import com.centanet.hk.aplus.bean.house.Properties;
import com.centanet.hk.aplus.bean.http.AHeaderDescription;
import com.centanet.hk.aplus.bean.login.Permisstions;
import com.centanet.hk.aplus.eventbus.BaseClass;
import com.centanet.hk.aplus.manager.PermissionManager;

import java.io.File;
import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

import static com.centanet.hk.aplus.Utils.net.HttpUtil.URL_CANCELFAVO;
import static com.centanet.hk.aplus.Utils.net.HttpUtil.URL_PATH;
import static com.centanet.hk.aplus.eventbus.BUS_MESSAGE.FavoState.FAVO_FAVO_CANCEL;
import static com.centanet.hk.aplus.eventbus.BUS_MESSAGE.HouseListDataCount.FAVOLIST_COUNT;
import static com.centanet.hk.aplus.eventbus.BUS_MESSAGE.NetWorkState.NETWORK_STATE_FAIL;
import static com.centanet.hk.aplus.eventbus.BUS_MESSAGE.NetWorkState.NETWORK_STATE_SUCCESS;
import static com.centanet.hk.aplus.eventbus.BUS_MESSAGE.Permission.HOUSELIST;
import static com.centanet.hk.aplus.eventbus.BUS_MESSAGE.Permission.HOUSELIST_NO;
import static com.centanet.hk.aplus.eventbus.BUS_MESSAGE.ReFreshDataState.DATA_FAVO_END;


/**
 * Created by yangzm4 on 2018/1/31.
 */

public class FavoListModel extends BaseClass implements IHouseListModel {

    private String thiz = getClass().getSimpleName();
    private boolean isEnd = false;
    private String path = MyApplication.getContext().getFilesDir().getAbsolutePath() + "sysParams.txt";
    private File file = new File(path);
    private static FavoListModel houseListModel = new FavoListModel();
    private OnReceiveListener receiveListener;

    public static synchronized FavoListModel getInstance() {
        return houseListModel;
    }

    @Override
    public void doPost(final String address, AHeaderDescription headers, Object bodys) {

        String number = System.currentTimeMillis() / 1000 + "";
        L.d("time", number);
        headers.setNumber(number);
        headers.setSign(MD5Util.getMD5Str("CYDAP_com-group~Centa@" + number + headers.getStaffno()));
        final boolean isNeedVerify = isFileExist();

        HttpUtil.doPost(address, bodys, headers, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                notifyEmptyBusMessage(NETWORK_STATE_FAIL);
                L.d("NetWork-Exception", e.toString());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.code() == 200) {
                    String dataBack = response.body().string().trim().toString();
                    L.d(thiz + "-Response", dataBack);
                    switch (address) {
                        case URL_PATH:
                            if (isEnd) {
                                notifyEmptyBusMessage(DATA_FAVO_END);
                                return;
                            }
                            getHouseList(dataBack);
                            break;

                        case URL_CANCELFAVO:
                            parseFavo(dataBack);
                            break;

                        case HttpUtil.URL_USER_BEHAVIOR:
                            L.d("ScreenShot", dataBack);
                            break;
                        default:
                            break;
                    }
                    notifyEmptyBusMessage(NETWORK_STATE_SUCCESS);
                } else notifyEmptyBusMessage(NETWORK_STATE_FAIL);
            }
        });
    }

    private void parseFavo(String dataBack) {
        try {
            FavoResponse response1 = GsonUtil.parseJson(dataBack, FavoResponse.class);
            if (response1.isFlag()) notifyEmptyBusMessage(FAVO_FAVO_CANCEL);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
    }

    private boolean isFileExist() {
        return file.exists();
    }


    private void getHouseList(String dataBack) {
        HouseData centaData = null;
        try {
            centaData = GsonUtil.parseJson(dataBack, HouseData.class);
            notifyBusMessage(FAVOLIST_COUNT, centaData.getRecordCount() + "");
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }

        Permisstions permisstions = centaData.getPermisstionsModel();

        List<Properties> data = centaData.getPropertie();
        if (receiveListener != null && data != null) {
            if (permisstions != null) verifyAndSavePermission(permisstions);
            receiveListener.onReceiveHouseData(data);
            isEnd = data.size() < 15 ? true : false;
        }
    }

    public void verifyAndSavePermission(Permisstions per) {

        Permisstions permission = PermissionManager.getPermisstion();
        if (per != null) {
            if (per.getMenuPermisstion() == null || per.getMenuPermisstion().equals("")) {
                receiveListener.onReceivePermission(per);
            }
            PermissionManager.setPermission(per);
        } else {
            if (permission.getMenuPermisstion() == null || permission.equals("")) {
                receiveListener.onReceivePermission(permission);
            }
        }

        if (PermissionManager.verifyMeunPermission(PermissionManager.FAVORITE))
            notifyEmptyBusMessage(HOUSELIST);
        else notifyEmptyBusMessage(HOUSELIST_NO);
    }


    @Override
    public void setRespontListener(OnReceiveListener receiveListener) {
        this.receiveListener = receiveListener;
    }

    @Override
    public void clearFlag() {
        isEnd = false;
    }

    public interface OnReceiveListener {
        void onReceiveHouseData(List<Properties> dataBack);

        void onReceivePermission(Permisstions permisstions);

        void onReceivelFinish();
    }
}
