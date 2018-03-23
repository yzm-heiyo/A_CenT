package com.centanet.hk.aplus.Views.HousetListView.model;

import com.centanet.hk.aplus.MyApplication;
import com.centanet.hk.aplus.Utils.net.GsonUtil;
import com.centanet.hk.aplus.Utils.net.HttpUtil;
import com.centanet.hk.aplus.Utils.L;
import com.centanet.hk.aplus.entity.house.HouseData;
import com.centanet.hk.aplus.entity.http.AHeaderDescription;
import com.centanet.hk.aplus.entity.login.Permisstions;
import com.centanet.hk.aplus.entity.house.Properties;
import com.centanet.hk.aplus.eventbus.BaseClass;
import com.centanet.hk.aplus.manager.PermissionManager;

import java.io.File;
import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

import static com.centanet.hk.aplus.Utils.net.HttpUtil.*;
import static com.centanet.hk.aplus.eventbus.BUS_MESSAGE.HouseListDataCount.HOUSELIST_COUNT;
import static com.centanet.hk.aplus.eventbus.BUS_MESSAGE.NetWorkState.NETWORK_STATE_FAIL;
import static com.centanet.hk.aplus.eventbus.BUS_MESSAGE.NetWorkState.NETWORK_STATE_SUCCESS;
import static com.centanet.hk.aplus.eventbus.BUS_MESSAGE.Permission.HOUSELIST;
import static com.centanet.hk.aplus.eventbus.BUS_MESSAGE.Permission.HOUSELIST_NO;


/**
 * Created by yangzm4 on 2018/1/31.
 */

public class HouseListModel extends BaseClass implements IHouseListModel {

    private String thiz = getClass().getSimpleName();
    private boolean isEnd = false;
    private String path = MyApplication.getContext().getFilesDir().getAbsolutePath() + "sysParams.txt";
    private File file = new File(path);
    private static HouseListModel houseListModel = new HouseListModel();
    private OnReceiveListener receiveListener;

    public static synchronized HouseListModel getInstance() {
        return houseListModel;
    }

    @Override
    public void doPost(final String address, AHeaderDescription headers, Object bodys) {

        if (isEnd) {
            notifyEmptyBusMessage(NETWORK_STATE_SUCCESS);
            return;
        }

        final boolean isNeedVerify = isFileExist();

        HttpUtil.doPost(address, bodys, headers, new Callback() {

            @Override
            public void onFailure(Call call, IOException e) {
                notifyEmptyBusMessage(NETWORK_STATE_FAIL);
                L.d("NetWork-Exception", e.toString());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String dataBack = response.body().string().trim().toString();
                L.d(thiz + "-Response", dataBack);
                switch (address) {

                    case URL_PATH:
                        getHouseList(dataBack);
                        break;
                    case URL_FAVORITE:
                        L.d(thiz, "net_favo: " + dataBack);
                        break;
                    default:
                        break;
                }
                notifyEmptyBusMessage(NETWORK_STATE_SUCCESS);
            }
        });
    }

    private boolean isFileExist() {
        return file.exists();
    }

    private void getHouseList(String dataBack) {
        HouseData centaData = null;
        try {
            centaData = GsonUtil.parseJson(dataBack, HouseData.class);
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
        notifyBusMessage(HOUSELIST_COUNT, centaData.getRecordCount() + "");
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

        if (PermissionManager.verifyMeunPermission(PermissionManager.WAR_ZONE))
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
