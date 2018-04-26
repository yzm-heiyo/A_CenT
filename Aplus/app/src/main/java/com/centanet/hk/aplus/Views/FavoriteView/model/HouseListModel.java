package com.centanet.hk.aplus.Views.FavoriteView.model;

import com.centanet.hk.aplus.MyApplication;
import com.centanet.hk.aplus.Utils.L;
import com.centanet.hk.aplus.Utils.net.GsonUtil;
import com.centanet.hk.aplus.Utils.net.HttpUtil;
import com.centanet.hk.aplus.common.CommandField;
import com.centanet.hk.aplus.common.DataManager;
import com.centanet.hk.aplus.entity.favo.FavoResponse;
import com.centanet.hk.aplus.entity.house.HouseData;
import com.centanet.hk.aplus.entity.house.Properties;
import com.centanet.hk.aplus.entity.http.AHeaderDescription;
import com.centanet.hk.aplus.entity.login.Permisstions;
import com.centanet.hk.aplus.entity.params.Parameter;
import com.centanet.hk.aplus.entity.params.SystemParam;
import com.centanet.hk.aplus.entity.params.SystemParamItems;
import com.centanet.hk.aplus.eventbus.BaseClass;
import com.centanet.hk.aplus.manager.PermissionManager;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

import static com.centanet.hk.aplus.Utils.net.HttpUtil.URL_CANCELFAVO;
import static com.centanet.hk.aplus.Utils.net.HttpUtil.URL_FAVORITE;
import static com.centanet.hk.aplus.Utils.net.HttpUtil.URL_PARAMETER;
import static com.centanet.hk.aplus.Utils.net.HttpUtil.URL_PATH;
import static com.centanet.hk.aplus.eventbus.BUS_MESSAGE.FavoState.FAVO_NO;
import static com.centanet.hk.aplus.eventbus.BUS_MESSAGE.HouseListDataCount.FAVOLIST_COUNT;
import static com.centanet.hk.aplus.eventbus.BUS_MESSAGE.HouseListDataCount.HOUSELIST_COUNT;
import static com.centanet.hk.aplus.eventbus.BUS_MESSAGE.NetWorkState.NETWORK_STATE_FAIL;
import static com.centanet.hk.aplus.eventbus.BUS_MESSAGE.NetWorkState.NETWORK_STATE_SUCCESS;
import static com.centanet.hk.aplus.eventbus.BUS_MESSAGE.Permission.HOUSELIST;
import static com.centanet.hk.aplus.eventbus.BUS_MESSAGE.Permission.HOUSELIST_NO;
import static com.centanet.hk.aplus.eventbus.BUS_MESSAGE.ReFreshDataState.DATA_END;
import static com.centanet.hk.aplus.eventbus.BUS_MESSAGE.ReFreshDataState.DATA_FAVO_END;


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
                        case URL_PARAMETER:
                            getParams(dataBack, isNeedVerify);
                            setParams(openFile());
                            break;
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
                        default:
                            break;
                    }
                    notifyEmptyBusMessage(NETWORK_STATE_SUCCESS);
                }else notifyEmptyBusMessage(NETWORK_STATE_FAIL);
            }
        });
    }

    private void parseFavo(String dataBack) {
        try {
            FavoResponse response1=GsonUtil.parseJson(dataBack, FavoResponse.class);
            if(response1.isFlag())notifyEmptyBusMessage(FAVO_NO);
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

    /**
     * 獲取參數，isNeedVerify是否需要驗證，本地沒有系統參數文件時不需要驗證
     *
     * @param dataBack
     * @param isNeedVerify
     */
    private void getParams(String dataBack, boolean isNeedVerify) {
        Parameter parameter = null;
        try {
            parameter = GsonUtil.parseJson(dataBack, Parameter.class);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
        if (!parameter.isNeedUpdate() && isNeedVerify) return;
        saveFile(dataBack);
    }

    //todo 驗證文件是否存在  不存在直接網絡請求  存在查詢是否需要更新

    private void saveFile(String data) {
        BufferedOutputStream bos = null;
        try {
            bos = new BufferedOutputStream(new FileOutputStream(file));
            bos.write(data.getBytes());
            bos.flush();
            bos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private List<Map<String, String>> openFile() {
        FileInputStream inputStream = null;
        BufferedReader reader = null;
        StringBuilder content = new StringBuilder();

        try {
            inputStream = new FileInputStream(path);
            reader = new BufferedReader(new InputStreamReader(inputStream));
            String line = "";
            while ((line = reader.readLine()) != null) {
                content.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            {
                if (reader != null) {
                    try {
                        inputStream.close();
                        reader.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        Parameter parameter = null;
        try {
            parameter = GsonUtil.parseJson(content.toString(), Parameter.class);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
        return getSystemParam(parameter);
    }

    public List<Map<String, String>> getSystemParam(Parameter parameter) {
        if (parameter == null) return null;
        int size = parameter.getSystemParam().size();
        List<Map<String, String>> paramsList = new ArrayList<>();
        List<SystemParam> params = parameter.getSystemParam();
        for (int i = 0; i < size; i++) {
            if (params.get(i).getParameterType() == CommandField.ParamsType.propertyStatusCategory) {
                List<SystemParamItems> paramsItem = params.get(i).getSystemParamItems();

                for (int j = 0; j < paramsItem.size(); j++) {
                    SystemParamItems systemParam = paramsItem.get(j);
                    Map<String, String> paramMap = new HashMap<>();
                    paramMap.put(systemParam.getItemText(), systemParam.getItemValue());
                    L.d(thiz, systemParam.getItemText() + ":" + systemParam.getItemValue());
                    paramsList.add(paramMap);
                }
            }
        }
        return paramsList;
    }

    public void setParams(List<Map<String, String>> params) {
        DataManager.parameter = params;
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
