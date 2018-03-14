package com.centanet.hk.aplus.Utils.net;

import com.google.gson.Gson;

/**
 * Created by yangzm4 on 2018/1/30.
 */

public class GsonUtil {

    public static <T>T parseJson(String jsonData, Class<T> data) throws IllegalAccessException, InstantiationException {

        Gson gson = new Gson();
        T object= gson.fromJson(jsonData, data);
        return object;

    }


}
