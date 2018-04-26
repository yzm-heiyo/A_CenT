package com.centanet.hk.aplus.Utils;

import android.text.TextUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by yangzm4 on 2018/4/3.
 */

public class StringUtil {



    /**
     * 空字符串,为避免不必要的创建空字符串对象.
     */
    public static final String Empty = "";
    public static final String Space =" "; // 空格

    public static String nullToEmpty(String value) {
        if (value == null) {
            value = Empty;
        }
        return value;
    }

    public static boolean isNullOrEmpty(String value) {
        if (value == null) {
            return true;
        }
        if (TextUtils.isEmpty(value)) {
            return true;
        }
        return false;
    }

    public static boolean isAllBlank(String value) {
        if (value == null) {
            return true;
        }
        if (value.length() == 0) {
            return true;
        }
        if (value.length() != 0 && value.trim().equals(StringUtil.Empty)) {
            return true;
        }
        return false;
    }

    public static String convertDate(long longTime) {
        String oldTime = DateUtil.replymodDate(longTime / 1000);
        String result = StringUtil.Empty;
        if (oldTime.length() == 16) {
            result = oldTime.substring(0, oldTime.lastIndexOf(" "));
        } else {
            result = oldTime;
        }
        return result;
    }


    public static void addForStringBufferAndTile(StringBuffer sbf, String appendTxt, String prefix) {
        if (!TextUtils.isEmpty(appendTxt)) {
            if (!TextUtils.isEmpty(prefix))
                sbf.append(prefix);
            sbf.append(appendTxt);
            addForStringBufferAndLF(sbf);
        }
    }

    public static void appendFilterLabel(StringBuffer sbf, ArrayList<String> laebls, String prefix) {
        int count = laebls.size();
        if (count > 0) {
            if (!TextUtils.isEmpty(prefix))
                sbf.append(prefix);

            int limit = count - 1;
            for (int i = 0; i < count; i++) {
                String str = laebls.get(i);
                if (!TextUtils.isEmpty(str))
                    if (i != limit) {
                        addForStringBufferAndDot(sbf, str);
                    } else {
                        sbf.append(str);
                        addForStringBufferAndLF(sbf);
                    }
            }
        }
    }

    private static void addForStringBufferAndDot(StringBuffer sbf, String appendTxt) {
        if (!TextUtils.isEmpty(appendTxt)) {
            sbf.append(appendTxt);
            sbf.append("，");
        }
    }

    public static long dateStingToLong(String str) {
        if (str != null) {
            Date date = null;
            String time = str;
            if (str.length() > 19) {
                time = time.substring(0, time.lastIndexOf("."));
            }
            time = time.substring(0, time.lastIndexOf("T")) + " " + time.substring(time.lastIndexOf("T") + 1);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            try {
                date = sdf.parse(time);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            return date.getTime();
        }
        return 0;
    }

    private static void addForStringBufferAndLF(StringBuffer sbf) {
        sbf.append("\n");
    }

}
