package com.centanet.hk.aplus.Utils.net;

import com.centanet.hk.aplus.Utils.L;
import com.google.gson.Gson;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * Created by yangzm4 on 2018/1/30.
 * 該類可以適配所有網頁請求并將返回數據通過Gson轉換成對應類型
 */

public class HttpUtil {

    public static String URL = "";
    public static String SSO_DEBUG_ADDRESS = "https://hkqasso.centanet.com/api/api/";//测试地址
    public static String SS0_RELEASE_ADDRESS = "https://hkagencysso.centanet.com/api/api/";
    public static String URL_USERLAUSE;
    public static final String URL_PATH = "property/war-zone";
    public static final String URL_PARAMETER = "permission/update-parameter";
    public static final String URL_AUTOSEARCH = "property/auto-estate";
    public static final String URL_FAVORITE = "property/favorite";
    public static final String URL_CANCELFAVO = "property/cancelfavorite";
    public static final String URL_DETAIL = "property/details";
    public static final String URL_ADDRESS_DETAIL = "property/property-address-detail";
    public static final String URL_FOLLOWS = "property/follows";
    public static final String URL_FOLLOW_ADD = "property/follow-add";
    public static final String URL_TRUSTOR = "property/trustor";

    public static final String URL_SSO = "https://hkqasso.centanet.com/api/api/Login";//测试
    public static final String URL_HomeConfig = "https://hkqasso.centanet.com/api/api/HomeConfig";//测试
    public static final String URL_UPDATE = "https://hkqasso.centanet.com/api/api/AppVersion";//测试
    public static final String URL_SSO_FEEDBACK = "https://hkqasso.centanet.com/api/api/Feedback";//测试

//    public static final String URL_SSO = "https://hkagencysso.centanet.com/api/api/Login";
//    public static final String URL_HomeConfig = "https://hkagencysso.centanet.com/api/api/HomeConfig";
//    public static final String URL_UPDATE = "https://hkagencysso.centanet.com/api/api/AppVersion";
//    public static final String URL_SSO_FEEDBACK = "https://hkagencysso.centanet.com/api/api/Feedback";

    public static final String URL_PERMISSION = "permission/user-permisstion";
    public static final String URL_USERINFO = "permission/user-info";
    public static final String URL_CALL_VIRTUAL_PHONE = "center/call-virtual-phone";
    public static final String URL_USER_BEHAVIOR = "common/com-behavior";
    public static final String URL_DETAILS_LIST = "property/get-details-list";
    public static final String URL_DETAILE_NEXT_KEYID = "property/get-details-next-keyids";

    public static final String URL_TRAN_LIST = "property/property-tran-list";
    public static final String URL_DISTRICT = "property/get-user-district";

    public static final String URL_FAST_SEARCH = "property/get-fast-searcher-tag"; // 獲取快速篩選項標籤
    public static final String URL_TAG_BUILD = "property/get-building-tag"; // 獲取棟座設施標籤
    public static final String URL_TAG_TRANLIST = "property/property-transaction-record"; // 獲取棟座設施標籤

    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    public static int READ_TIME_OUT = 10;
    public static int CONNECT_TIME_OUT = 10;
    private static Call call;

    public static void doPost(String address, Object bodys, Object headers, okhttp3.Callback callback) {

        String url = address;
        if (address != HttpUtil.URL_SSO && address != HttpUtil.URL_HomeConfig && address != URL_USERLAUSE && address != URL_SSO_FEEDBACK && address != URL_UPDATE)
            url = HttpUtil.URL + address;
        L.d("Http_Util", url);

        OkHttpClient client = new OkHttpClient.Builder().connectTimeout(CONNECT_TIME_OUT, TimeUnit.SECONDS)
                .readTimeout(READ_TIME_OUT, TimeUnit.SECONDS).build();

        Request.Builder builder = null;
        String requestJson = null;

        try {
            requestJson = getBoys(bodys);
            builder = addHedaer(headers);
        } catch (Exception e) {
            e.printStackTrace();
        }

        RequestBody requestBody = RequestBody.create(JSON, requestJson);
        L.d("HttpUtil-Json", requestJson);

        L.d("URL", url);
        Request request = builder.url(url).post(requestBody).build();
        call = client.newCall(request);
        call.enqueue(callback);
    }

    public static void cancelRequest() {
        if (call != null) call.cancel();
    }

    public static void doGet(String address, Object headers, Object param, okhttp3.Callback callback) {
        OkHttpClient client = new OkHttpClient.Builder().connectTimeout(CONNECT_TIME_OUT, TimeUnit.SECONDS)
                .readTimeout(READ_TIME_OUT, TimeUnit.SECONDS).build();
        Request.Builder builder = null;
        try {
            builder = addHedaer(headers);
            if (param != null)
                address = address + "?" + addGetParams(param);
            L.d("Get", address);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Request request = builder.url(address).build();
        client.newCall(request).enqueue(callback);

    }

    private static String addGetParams(Object params) throws Exception {
        String paramString = "";
        for (Field field : params.getClass().getDeclaredFields()) {

            field.setAccessible(true);
            if (field.get(params) != null) {

                if (field.getName() == "serialVersionUID") {
                    continue;
                }
                paramString = paramString + field.getName() + "=" + field.get(params) + "&";
            }
        }
        return paramString.substring(0, paramString.length() - 1);
    }


    /**
     * 添加所有的Header
     */
    private static Request.Builder addHedaer(Object model) throws Exception {
        Request.Builder builder = new Request.Builder();
        builder.addHeader("content-type", "application/json");
        for (Field field : model.getClass().getDeclaredFields()) {

            field.setAccessible(true);
            if (field.get(model) != null) {

                if (field.getName() == "serialVersionUID") {
                    continue;
                }

                if (field.getName().equals("UserAgent")) {
                    builder.addHeader("User-Agent", "" + field.get(model));
                    continue;
                }
                builder.addHeader(field.getName(), "" + field.get(model));
            }
        }
        return builder;
    }

    /**
     * 遍歷添加Body請求類中的所有屬性
     *
     * @param model
     * @return
     * @throws Exception
     */
    private static String getBoys(Object model) throws Exception {
        String request;

        Map<String, String> body = new HashMap<>();
        List<String> paramMoreItemList = new ArrayList<>();
        //遍歷屬性類中的所有參數
        for (Field field : model.getClass().getDeclaredFields()) {
            String type = field.getGenericType().toString();
            field.setAccessible(true);
            if (field.get(model) != null) {

                if (type.equals("java.util.List<java.lang.String>")) {
                    String item = field.getName() + ":" + new Gson().toJson(field.get(model));
                    paramMoreItemList.add(item);
                    continue;
                }
                if (field.getName() == "serialVersionUID") {
                    continue;
                }

                body.put(field.getName(), field.get(model) + "");
            }
        }
        request = new Gson().toJson(body);

        if (!paramMoreItemList.isEmpty()) {
            request = request.substring(0, request.length() - 1) + ",";
            for (String item : paramMoreItemList) {
                request = request + item + ",";
            }
            request = request.substring(0, request.length() - 1) + "}";
        }
        L.d("HttpUtil-Json", "request: " + request);
        return request;
    }
}