package com.centanet.hk.aplus.common_model.model;

import com.centanet.hk.aplus.Utils.L;
import com.centanet.hk.aplus.Utils.net.GsonUtil;
import com.centanet.hk.aplus.Utils.net.HttpUtil;
import com.centanet.hk.aplus.bean.district.DistrictResponse;
import com.centanet.hk.aplus.manager.ApplicationManager;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * Created by yangzm4 on 2018/7/20.
 */

public class DistrictModel implements IDistrictModel {


    private static DistrictModel model = new DistrictModel();

    public static synchronized DistrictModel getInstance() {
        return model;
    }


    @Override
    public void getDistrict() {
        HttpUtil.doGet(HttpUtil.URL + HttpUtil.URL_DISTRICT, ApplicationManager.getApplication().getHeaderDescription(), null, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    try {
                        DistrictResponse districtResponse = GsonUtil.parseJson(response.body().byteStream(), DistrictResponse.class);
                        ApplicationManager.setDistrictItems(districtResponse.getDistrictItems());
                        L.d("District",""+districtResponse.toString());
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    } catch (InstantiationException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

}
