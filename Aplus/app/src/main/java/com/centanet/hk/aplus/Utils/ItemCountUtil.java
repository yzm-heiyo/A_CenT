package com.centanet.hk.aplus.Utils;

import com.google.gson.Gson;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by yangzm4 on 2018/3/29.
 */

public class ItemCountUtil {

    public static int count(Object model) throws Exception {
        int count = 0;

        //遍歷屬性類中的所有參數
        for (Field field : model.getClass().getDeclaredFields()) {
            String type = field.getGenericType().toString();
            field.setAccessible(true);
            if (field.get(model) != null && !field.get(model).equals("") && !field.get(model).equals("null")) {
                L.d("itemValues", "name: " + field.getName() + " value: " + field.get(model));
                if (type.equals("java.util.List<java.lang.String>")) {
                    if (!((List<String>) field.get(model)).isEmpty()) {
                        count = count + ((List<String>) field.get(model)).size();
                        L.d("listSize", ((List<String>) field.get(model)).size() + "");
                    }
                    continue;
                }
                if (field.getName() == "serialVersionUID" || field.getName() == "PropertySquareType" || field.getName() == "PriceUnitType" || field.getName() == "PropertyDateType") {
                    continue;
                }
                count++;
                L.d("itemValue", "valueL: " + field.get(model));
            }
        }
        L.d("item", "count: " + count);
        return count - 6;
    }
}
