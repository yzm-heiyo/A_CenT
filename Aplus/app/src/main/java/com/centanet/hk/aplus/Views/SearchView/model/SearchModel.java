package com.centanet.hk.aplus.Views.SearchView.model;

import com.centanet.hk.aplus.Utils.net.GsonUtil;
import com.centanet.hk.aplus.Utils.net.HttpUtil;
import com.centanet.hk.aplus.Utils.L;
import com.centanet.hk.aplus.entity.auto_estate.AutoHouseData;
import com.centanet.hk.aplus.entity.auto_estate.PropertyParamHints;
import com.centanet.hk.aplus.entity.http.AHeaderDescription;

import org.litepal.crud.DataSupport;
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

public class SearchModel implements ISearchModel {

    private final String thiz = getClass().getSimpleName();
    private static SearchModel resultModel = new SearchModel();
    private OnReceiveListener onReceiveListener;

    public static synchronized SearchModel getInstance() {
        return resultModel;
    }

    @Override
    public void doPost(String address, AHeaderDescription headers, Object bodys) {

        HttpUtil.doPost(address, bodys, headers, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                L.d("NetWork-Exception", e.toString());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String dataBack = response.body().string().trim().toString();
                L.d(thiz + "-Response", dataBack);
                try {
                    AutoHouseData autoHouseData = GsonUtil.parseJson(dataBack, AutoHouseData.class);
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
        });
    }

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
                DataSupport.deleteAll(PropertyParamHints.class, "KeyId = ?", his.getKeyId());
            }
            if (historySize + getSearchHistory().size() <= 10) {
                for (PropertyParamHints data : history) {
                    data.clearSavedState();
                    data.save();
                }
            } else {
                int len = historySize + getSearchHistory().size() - 10;
                for (int i = 0; i < len; i++) {
                    PropertyParamHints first = DataSupport.findFirst(PropertyParamHints.class);
                    DataSupport.deleteAll(PropertyParamHints.class, "KeyId = ?", first.getKeyId());
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
            labelHistory.addAll(DataSupport.where("KeyId = ?", param).find(PropertyParamHints.class));
        }
        return labelHistory;
    }

    private String parseData(PropertyParamHints data) {
        String labelString = null;
        if (data.getEnAddressName().length() > 0) {
            labelString = data.getEnAddressName();
        } else if (data.getDistrictName().length() > 0 && data.getAreaName().length() > 0) {
            labelString = data.getDistrictName() + "\\\\\\" + data.getAreaName();
        } else if (data.getDistrictName().length() > 0) {
            labelString = data.getDistrictName();
        } else if (data.getAreaName().length() > 0) {
            labelString = data.getAreaName();
        }
        return labelString;
    }


    public List<PropertyParamHints> getSearchHistory() {
        return DataSupport.findAll(PropertyParamHints.class);
    }


    public interface CallBack {
        void getData(List<PropertyParamHints> dataBack);
    }

    public interface OnReceiveListener {
        void onReceive(List<PropertyParamHints> dataBack);

        void onReceivelFinish();
    }

}
