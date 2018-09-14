package com.centanet.hk.aplus.Utils;

import android.text.Html;
import android.text.Spanned;

import java.text.DecimalFormat;
import java.util.List;

/**
 * Created by yangzm4 on 2018/3/6.
 * 字體顔色轉換工具
 */

public class TextUtil {

    /**
     * 關鍵字標識指定顔色
     *
     * @param point
     * @param keyword
     * @param color
     * @return
     */
    public static Spanned changeKeyWordColor(String point, String keyword, String color) {
        int index = point.indexOf(keyword.toUpperCase());
        int len = keyword.length();
        Spanned temp = null;
        if (index >= 0) {
            temp = Html.fromHtml(
                    point.substring(0, index) + "<font color=" + color + ">" + point.substring(index, index + len)
                            + "</font>" + point.substring(index + len, point.length()));
            return temp;
        }
        temp = Html.fromHtml(point);
        return temp;
    }

    /**
     * 轉換整段文字
     *
     * @param point
     * @param color
     * @return
     */
    public static Spanned changeTextColor(String point, String color) {

        Spanned temp = Html.fromHtml(
                "<font color=" + color + ">" + point
                        + "</font>");
        return temp;
    }

    /**
     * 将属性名称的首字母变成大写
     */
    public static String getMethodName(String fieldName) {
        byte[] bytes = fieldName.getBytes();
        bytes[0] = (byte) (bytes[0] - 'a' + 'A');
        return new String(bytes);
    }

    public static String getInteger(String s) {
        L.d("getInteger", s);
        if (s.indexOf(".") != -1) {
            return s.substring(0, s.indexOf("."));
//            L.d("getInteger", s.indexOf(".") +"");
        }
        return s;
    }

    //此方法返回的数字类型如：45.8600
    public static String NumberFormat3(double num) {
        DecimalFormat decimal_format = new DecimalFormat("#####.0000");
        return decimal_format.format(num);

    }

    public static boolean isEmply(String s) {
        if (s != null && !s.equals("")) return false;
        return true;
    }

    public static boolean isEmply(List s) {
        if (s != null && !s.isEmpty()) return false;
        return true;
    }

}
