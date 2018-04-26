package com.centanet.hk.aplus.Utils;

import android.text.TextUtils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by yangzm4 on 2018/4/3.
 */

class DateUtil {

    public static String getTimestamp() {
        DateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        return dateFormat.format(new Date());
    }

    public static String Format4ModDate(String str) {
        try {
            DateFormat format2 = new SimpleDateFormat("yyyy-MM-dd");
            Date date = new Date();
            date.setTime(Long.parseLong(str));
            return format2.format(date);
        } catch (Exception e) {
            return str;
        }
    }

    //格式化时间(年月日)
    public static String formatDate(Date date) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            return sdf.format(date);
        } catch (Exception e) {
            return StringUtil.Empty;
        }
    }

    //格式化时间(年月日)
    public static String formatDateOutDat(Date date) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
            return sdf.format(date);
        } catch (Exception e) {
            return StringUtil.Empty;
        }
    }

    //格式化时间(年月日 周)
    public static String format2Week(Date date) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd E", Locale.CHINA);
            return sdf.format(date);
        } catch (Exception e) {
            return StringUtil.Empty;
        }
    }

    //格式化时间(年月日)
    public static String formatDate1(Date date) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
            return sdf.format(date);
        } catch (Exception e) {
            return StringUtil.Empty;
        }
    }

    /**
     * 获取当前时间
     */
    public static String CurrentModDate() {
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date(System.currentTimeMillis());
        return format.format(date);
    }

    public static String CurrentModDate(Date date) {
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(date);
    }

    /**
     * string类型的时间(yyyy-MM-dd)解析成date 类型
     *
     * @param date
     * @return
     * @throws ParseException
     */
    public static Date strDataParseDate(String date) throws ParseException {
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.parse(date);
    }

    /**
     * string类型的时间(yyyy-MM-dd HH:mm)解析成date 类型
     *
     * @param date
     * @return
     * @throws ParseException
     */
    public static Date strDataParseDateHHmm(String date) throws ParseException {
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        return format.parse(date);
    }

    /**
     * 获取当前时间
     */
    public static String CurrentModDate1() {
        SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm EEEE", Locale.CHINA);
        Date date = new Date(System.currentTimeMillis());
        return format1.format(date);
    }

    /**
     * 修改时间格式
     * 格式为 yyyy-MM-dd HH:mm
     */
    public static String getTimeYearHourMinute(Date date) {
        SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.CHINA);
        return format1.format(date);
    }

    /**
     * 获取当前时间
     */
    public static String CurrentModDate3() {
        SimpleDateFormat format1 = new SimpleDateFormat("HH:mm", Locale.CHINA);
        Date date = new Date(System.currentTimeMillis());
        return format1.format(date);
    }

    /**
     * 获取30天前时间
     */
    public static String Pre30ModDate() {
        DateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, -30);
        return format1.format(calendar.getTime());
    }


    public static String getActValid(String arg0, String arg1) {
        if (TextUtils.isEmpty(arg0) || TextUtils.isEmpty(arg0))
            return "";
        StringBuilder sb = new StringBuilder();
        if (isCurrentYear(arg0)
                && isCurrentYear(arg1)) {
            sb.append(getActValidTime(arg0));
            sb.append("-");
            sb.append(getActValidTime(arg1));
        } else {
            if (isCurrentYear(arg0)) {
                sb.append(getActValidTime(arg0));
            } else {
                sb.append(getFullTime(arg0));
            }
            sb.append("-");
            if (isCurrentYear(arg0)) {
                sb.append(getActValidTime(arg1));
            } else {
                sb.append(getFullTime(arg1));
            }
        }
        return sb.toString();
    }

    /**
     * 活动时间计算
     */
    public static String getActOnline(String arg0, String arg1) {
        if (TextUtils.isEmpty(arg0) || TextUtils.isEmpty(arg0))
            return "";
        long l1 = Format4Date(arg0);// 开始时间
        long l2 = Format4Date(arg1) + 1000 * 3600 * 24;// 结束时间需要加上偏移1天的时间
        long now = System.currentTimeMillis();
        StringBuilder sb = new StringBuilder();
        if (l1 <= now && now <= l2) {
            // 活动中
            final long days = (l2 - now) > 0 ? (l2 - now) / (1000 * 3600 * 24)
                    + 1 : 1;
            if (days > 30) {
                if (isCurrentYear(arg0)
                        && isCurrentYear(arg1)) {
                    sb.append(getActValidTime(arg0));
                    sb.append("-");
                    sb.append(getActValidTime(arg1));
                } else {
                    if (isCurrentYear(arg0)) {
                        sb.append(getActValidTime(arg0));
                    } else {
                        sb.append(getFullTime(arg0));
                    }
                    sb.append("-");
                    if (isCurrentYear(arg0)) {
                        sb.append(getActValidTime(arg1));
                    } else {
                        sb.append(getFullTime(arg1));
                    }
                }
            } else {
                sb.append("活动剩余");
                sb.append(days);
                sb.append("天");
            }
        } else if (now < l1) {
            // 未开始
            sb.append("活动即将开始");
        } else if (now > l2) {
            // 结束
            sb.append("活动已结束");
        }
        return sb.toString();
    }

    public static long Format4Date(final String str) {
        try {
            DateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.CHINA);
            Date date = format.parse(str);
            return date.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
            return 0l;
        }
    }

    /**
     * 是否当前年份
     */
    public static boolean isCurrentYear(final String str) {
        boolean result;
        try {
            DateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.CHINA);
            DateFormat format2 = new SimpleDateFormat("yyyy");
            Date date = format.parse(str);
            result = format2.format(date).equals(format2.format(new Date()));
        } catch (ParseException e) {
            e.printStackTrace();
            return false;
        }
        return result;
    }

    /**
     * 活动时间
     */
    public static String getActValidTime(final String str) {
        try {
            DateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
            Date date = format.parse(str);
            DateFormat format2 = new SimpleDateFormat("yyyy");
            DateFormat format3 = new SimpleDateFormat("MM.dd");
            DateFormat format4 = new SimpleDateFormat("yyyy.MM.dd");
            return format2.format(date).equals(format2.format(new Date())) ? format3
                    .format(date) : format4.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
            return str;
        }
    }

    /**
     * 显示完整年月日
     */
    public static String getFullTime(final String str) {
        try {
            DateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
            Date date = format.parse(str);
            DateFormat format4 = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
            return format4.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
            return str;
        }
    }

    /**
     * 计算消息的时间差
     */
    public static String getGapTime(Long modDate) {
        Calendar calendar = Calendar.getInstance();
        long timeInMillis = calendar.getTimeInMillis() / 1000L;
        long gap = timeInMillis - modDate;
        if (gap < (3600)) {
            long minutes = (gap / 60);
            if (minutes < 2) {
                return "刚刚";
            } else {
                return minutes + "分钟前";
            }
        } else {
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int day = calendar.get(Calendar.DAY_OF_MONTH);
            calendar.set(year, month, day, 0, 0, 0);
            // 发布时间-今天00:00，如果大于0就不是昨天
            long gapTime = modDate - calendar.getTimeInMillis() / 1000;
            if (gapTime > 0) {
                return (gap / 60 / 60) + "小时前";
            } else if (gapTime > -60 * 60 * 24) {
                return "昨天";
            } else {
                SimpleDateFormat dateFormat = new SimpleDateFormat("MM-dd");
                return dateFormat.format(modDate * 1000L);
            }
        }
    }

    public static String Format4Date(String... str) {
        try {
            DateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
            Date date = format.parse(str[0]);
            DateFormat format2;
            if (str.length > 1 && !TextUtils.isEmpty(str[1])) {
                format2 = new SimpleDateFormat(str[1]);
            } else {
                format2 = new SimpleDateFormat("yyyy-MM-dd");
            }

            return format2.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
            return str[0];
        }
    }

    public static String FormatDate(String str, String formatStr) {
        try {
            DateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
            DateFormat format2 = new SimpleDateFormat(formatStr);
            return format2.format(format.parse(str));
        } catch (Exception e) {
            e.printStackTrace();
            return StringUtil.Empty;
        }
    }

    public static final String FORMAT_YEAR_PREVIOUS = "yyyy-MM-dd HH:mm";
    public static final String FORMAT_YEAR_PREVIOUS_SECOND = "yyyy-MM-dd HH:mm:ss";
    public static final String FORMAT_YEAR_DATE = "yyyy-MM-dd";
    public static final String FORMAT_YEAR_CURRENT = "MM-dd HH:mm";
    public static final String FORMAT_DAY_DEFAULT = "HH:mm";

    public static String replymodDate(long modDate) {
        modDate = modDate * 1000;
        SimpleDateFormat f;
        Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        c.clear();
        c.set(Calendar.YEAR, year);
        if (c.getTimeInMillis() > modDate) {
            // 去年
            f = new SimpleDateFormat(FORMAT_YEAR_PREVIOUS);
            return f.format(new Date(modDate));
        } else {
            c = Calendar.getInstance();
            c.set(Calendar.HOUR_OF_DAY, 0);
            c.set(Calendar.MINUTE, 0);
            if (c.getTimeInMillis() <= modDate) {
                // 今天
                f = new SimpleDateFormat(FORMAT_DAY_DEFAULT);
                return f.format(new Date(modDate));
            } else {
                c = Calendar.getInstance();
                c.add(Calendar.DATE, -1);
                c.set(Calendar.HOUR_OF_DAY, 0);
                c.set(Calendar.MINUTE, 0);
                if (c.getTimeInMillis() <= modDate) {
                    // 昨天
                    f = new SimpleDateFormat(FORMAT_DAY_DEFAULT);
                    return "昨天  " + f.format(new Date(modDate));
                } else {
                    c = Calendar.getInstance();
                    c.add(Calendar.DATE, -2);
                    c.set(Calendar.HOUR_OF_DAY, 0);
                    c.set(Calendar.MINUTE, 0);
                    if (c.getTimeInMillis() <= modDate) {
                        // 前天
                        f = new SimpleDateFormat(FORMAT_DAY_DEFAULT);
                        return "前天  " + f.format(new Date(modDate));
                    } else {
                        // 其他
                        f = new SimpleDateFormat(FORMAT_YEAR_CURRENT);
                        return f.format(new Date(modDate));
                    }
                }
            }
        }
    }

    public static Date formatStr(String str) {
        try {
            DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            Date date = format.parse(str);
            return date;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getPostTime() {
        try {
            long time = System.currentTimeMillis();
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSSSZ");
            Date d2 = new Date(time);
            return format.format(d2);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


}
