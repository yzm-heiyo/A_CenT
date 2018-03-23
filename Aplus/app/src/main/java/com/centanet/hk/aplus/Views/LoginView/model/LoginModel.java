package com.centanet.hk.aplus.Views.LoginView.model;

import android.os.Build;

import com.centanet.hk.aplus.MyApplication;
import com.centanet.hk.aplus.Utils.L;
import com.centanet.hk.aplus.Utils.net.GsonUtil;
import com.centanet.hk.aplus.Utils.net.HttpUtil;
import com.centanet.hk.aplus.common.APSystemParameterType;
import com.centanet.hk.aplus.common.CommandField;
import com.centanet.hk.aplus.common.DataManager;
import com.centanet.hk.aplus.entity.login.HomeConfig;
import com.centanet.hk.aplus.entity.login.Login;
import com.centanet.hk.aplus.entity.login.UserPermission;
import com.centanet.hk.aplus.entity.params.Parameter;
import com.centanet.hk.aplus.entity.params.SystemParam;
import com.centanet.hk.aplus.entity.params.SystemParamItems;
import com.centanet.hk.aplus.manager.ApplicationManager;

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
import java.util.UUID;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

import static com.centanet.hk.aplus.Utils.net.HttpUtil.URL_PARAMETER;
import static com.centanet.hk.aplus.common.APSystemParameterType.houseDirection;

/**
 * Created by yangzm4 on 2018/3/13.
 */

public class LoginModel implements ILoginModel {

    private static LoginModel loginModel = new LoginModel();
    private OnReceiveLisenter listener;
    private String path = MyApplication.getContext().getFilesDir().getAbsolutePath() + "sysParams.txt";
    private File file = new File(path);

    public static synchronized LoginModel getInstance() {
        return loginModel;
    }

    @Override
    public void doGet(String address, Object header, Object params) {
        HttpUtil.doGet(address, header, params, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String dataBack = response.body().string().toString();
                if (response.code() == 200) {
                    try {
                        HomeConfig config = GsonUtil.parseJson(dataBack, HomeConfig.class);
                        if (listener != null) listener.OnGetConfig(config);
                        L.d("Http_Config", config.toString());
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    } catch (InstantiationException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    //获得独一无二的Psuedo ID
    @Override
    public String getUniquePsuedoID() {
        String serial = null;

        String m_szDevIDShort = "35" +
                Build.BOARD.length() % 10 + Build.BRAND.length() % 10 +

                Build.CPU_ABI.length() % 10 + Build.DEVICE.length() % 10 +

                Build.DISPLAY.length() % 10 + Build.HOST.length() % 10 +

                Build.ID.length() % 10 + Build.MANUFACTURER.length() % 10 +

                Build.MODEL.length() % 10 + Build.PRODUCT.length() % 10 +

                Build.TAGS.length() % 10 + Build.TYPE.length() % 10 +

                Build.USER.length() % 10; //13 位

        try {
            serial = android.os.Build.class.getField("SERIAL").get(null).toString();
            //API>=9 使用serial号
            return new UUID(m_szDevIDShort.hashCode(), serial.hashCode()).toString();
        } catch (Exception exception) {
            //serial需要一个初始化
            serial = "serial"; // 随便一个初始化
        }
        //使用硬件信息拼凑出来的15位号码
        return new UUID(m_szDevIDShort.hashCode(), serial.hashCode()).toString();
    }

    @Override
    public void doPost(final String address, Object header, Object body) {

        final boolean isNeedVerify = isFileExist();

        HttpUtil.doPost(address, body, header, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String data = response.body().string().toString();

                if (response.code() == 200) {
                    switch (address) {
                        case HttpUtil.URL_SSO:
                            getLogin(data);
                            break;
                        case HttpUtil.URL_PERMISSION:
                            getPermission(data);
                            break;
                        case URL_PARAMETER:
                            getParams(data, isNeedVerify);
                            setParams(openFile());
                            if (listener != null) listener.Onfinish();
                            break;
                        default:
                            break;
                    }

                }
            }
        });
    }

    private void getLogin(String data) {
        try {
            Login login = GsonUtil.parseJson(data, Login.class);
            if (listener != null) listener.OnLogin(login);
            L.d("sso_login", login.toString());
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
    }

    private void getPermission(String data) {

        try {
            UserPermission permission = GsonUtil.parseJson(data, UserPermission.class);
            L.d("permission", permission.toString());
            if (listener != null) listener.OnPermissionFinish(permission);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
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

            for (SystemParam p : parameter.getSystemParam()) {
                L.d("params_system", p.toString());
                switch (p.getParameterType()) {
                    case APSystemParameterType.house:
                        ApplicationManager.setIntervalSystemParam(p);
                        break;
                    case APSystemParameterType.houseDirection:
                        ApplicationManager.setDirectionSystemParam(p);
                        break;

                    case APSystemParameterType.propertyTag:
                        ApplicationManager.setLabelSystemParam(p);
                }
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
        if (!parameter.isNeedUpdate() && isNeedVerify) return;
        saveFile(dataBack);
    }

    private boolean isFileExist() {
        return file.exists();
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
                    L.d("login", systemParam.getItemText() + ":" + systemParam.getItemValue());
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
    public void setLisenter(OnReceiveLisenter lisenter) {
        this.listener = lisenter;
    }

    public interface OnReceiveLisenter {

        void OnPermissionFinish(UserPermission permission);

        void OnGetConfig(HomeConfig config);

        void OnLogin(Login login);

        void Onfinish();
    }
}
