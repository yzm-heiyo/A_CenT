package com.centanet.hk.aplus.Views.TransHomePagerView.TransSearchView.model;

import com.centanet.hk.aplus.Utils.L;
import com.centanet.hk.aplus.Utils.MD5Util;
import com.centanet.hk.aplus.Utils.TextUtil;
import com.centanet.hk.aplus.Utils.net.GsonUtil;
import com.centanet.hk.aplus.Utils.net.HttpUtil;
import com.centanet.hk.aplus.bean.auto_estate.AutoHouseResponse;
import com.centanet.hk.aplus.bean.auto_estate.PropertyParamHints;
import com.centanet.hk.aplus.bean.http.AHeaderDescription;

import org.litepal.LitePal;
import org.litepal.tablemanager.Connector;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * Created by yangzm4 on 2018/3/1.
 */

public class TransSearchModel implements ITransSearchModel {

    private final String thiz = getClass().getSimpleName();
    private static TransSearchModel resultModel = new TransSearchModel();
    private OnReceiveListener onReceiveListener;
    private int SEARCH_HISTORY_MAX = 20;

    public static synchronized TransSearchModel getInstance() {
        return resultModel;
    }

    @Override
    public void doPost(String address, AHeaderDescription headers, Object bodys) {

        String number = System.currentTimeMillis() / 1000 + "";
        L.d("time", number);
        headers.setNumber(number);
        headers.setSign(MD5Util.getMD5Str("CYDAP_com-group~Centa@" + number + headers.getStaffno()));

        HttpUtil.cancelRequest();//防止實時搜索數據覆蓋
        HttpUtil.doPost(address, bodys, headers, callback);
    }

    private Callback callback = new Callback() {
        @Override
        public void onFailure(Call call, IOException e) {
            L.d("NetWork-Exception", e.toString());
        }

        @Override
        public void onResponse(Call call, Response response) throws IOException {

            String dataBack = response.body().string().trim().toString();
            L.d(thiz + "-Response", dataBack);
            try {
                AutoHouseResponse autoHouseData = GsonUtil.parseJson(dataBack, AutoHouseResponse.class);
                if (onReceiveListener != null) {
                    onReceiveListener.onReceive(autoHouseData.getPropertyParamHints());
                    onReceiveListener.onReceivelFinish();
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            }
        }
    };

    @Override
    public void setRespontListener(OnReceiveListener receiveListener) {
        onReceiveListener = receiveListener;
    }

    @Override
    public void saveSearchHistory(List<PropertyParamHints> history) {
        Connector.getDatabase();

        List<PropertyParamHints> oldHistory = getSearchHistory();
        int historySize = history.size();
        if (oldHistory.size() == 0) {
            for (PropertyParamHints data : history) {
                data.save();
            }
        } else {
            for (PropertyParamHints his : history) {
                LitePal.deleteAll(PropertyParamHints.class, "KeyId = ?", his.getKeyId());
            }
            if (historySize + getSearchHistory().size() <= SEARCH_HISTORY_MAX) {
                for (PropertyParamHints data : history) {
                    data.clearSavedState();
                    data.save();
                }
            } else {
                int len = historySize + getSearchHistory().size() - SEARCH_HISTORY_MAX;
                for (int i = 0; i < len; i++) {
                    PropertyParamHints first = LitePal.findFirst(PropertyParamHints.class);
                    LitePal.deleteAll(PropertyParamHints.class, "KeyId = ?", first.getKeyId());
                    first.delete();
                }
                for (PropertyParamHints data : history) {
                    data.clearSavedState();
                    data.save();
                }
            }
        }
    }

    @Override
    public void getSearchHistory(CallBack callBack) {
        List<PropertyParamHints> dataList = getSearchHistory();
        List<PropertyParamHints> history = new ArrayList<>();
        int size = dataList.size();
        for (int i = 0; i < size; i++) {
            history.add(dataList.get(size - 1 - i));
        }
        callBack.getData(history);
    }

    @Override
    public void deleteSearchHistory() {
        LitePal.deleteAll(PropertyParamHints.class);
    }

    @Override
    public List<String> changeToLabelData(List<PropertyParamHints> datas) {
        List<String> labelData = new ArrayList<>();
        for (PropertyParamHints data : datas) {
            labelData.add(changeToLabelData(data));
        }
        return labelData;
    }

    @Override
    public String changeToLabelData(PropertyParamHints data) {
        String labelString = parseData(data);
        return labelString;
    }

    @Override
    public List<PropertyParamHints> searchLabelHistory(List<String> params) {
        Connector.getDatabase();
        List<PropertyParamHints> labelHistory = new ArrayList<>();
        for (String param : params) {
            labelHistory.addAll(LitePal.where("KeyId = ?", param).find(PropertyParamHints.class));
        }
        return labelHistory;
    }

    private String parseData(PropertyParamHints s) {
        String labelString = null;
//        if (data.getEnAddressName().length() > 0) {
//            labelString = data.getEnAddressName();
//        } else if (data.getDistrictName().length() > 0 && data.getAreaName().length() > 0) {
//            labelString = data.getDistrictName() + "\\\\\\" + data.getAreaName();
//        } else if (data.getDistrictName().length() > 0) {
//            labelString = data.getDistrictName();
//        } else if (data.getAreaName().length() > 0) {
//            labelString = data.getAreaName();
//        }

        String address = "";
        String street = "";
        if (!TextUtil.isEmply(s.getDistrictName()) && !TextUtil.isEmply(s.getAreaName())) {
            address = address + s.getDistrictName() + "/" + s.getAreaName();
        } else if (!TextUtil.isEmply(s.getDistrictName())) {
            address = address + s.getDistrictName();
        } else if (!TextUtil.isEmply(s.getAreaName())) {
            address = address + s.getAreaName();
        }

        if (!TextUtil.isEmply(s.getEnAddressName())) {
            street = s.getEnAddressName();
        }

        if (!TextUtil.isEmply(address) && !TextUtil.isEmply(street)) {
            labelString = address + "\n" + street;
        } else if (!TextUtil.isEmply(address)) {
            labelString = address;
        } else if (!TextUtil.isEmply(street)) {
            labelString = street;
        }

        return labelString;
    }


    public List<PropertyParamHints> getSearchHistory() {
        return LitePal.findAll(PropertyParamHints.class);
    }


    public interface CallBack {
        void getData(List<PropertyParamHints> dataBack);
    }

    public interface OnReceiveListener {
        void onReceive(List<PropertyParamHints> dataBack);

        void onReceivelFinish();
    }

}
