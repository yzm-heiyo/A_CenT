package com.centanet.hk.aplus.Utils.net;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by yangzm4 on 2018/1/30.
 */

public class GsonUtil {

    public static <T> T parseJson(String jsonData, Class<T> data) throws IllegalAccessException, InstantiationException {

        Gson gson = new Gson();
        T object = gson.fromJson(jsonData, data);
        return object;
    }

    public static <T> T parseJson(InputStream in, Class<T> data) throws IllegalAccessException, InstantiationException {
        JsonReader reader = null;
        reader = new JsonReader(new InputStreamReader(in));
        Gson gson = new Gson();
        T object = gson.fromJson(reader, data);
        return object;
    }



    public static <T> List<T> getObjectList(String jsonString, Class<T> cls){
        List<T> list = new ArrayList<T>();
        try {
            Gson gson = new Gson();
            JsonArray arry = new JsonParser().parse(jsonString).getAsJsonArray();
            for (JsonElement jsonElement : arry) {
                list.add(gson.fromJson(jsonElement, cls));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

}
