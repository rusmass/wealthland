package com.ascn.richlife.server.util;

/**
 * 星座获取工具类
 *
 * Created by Administrator on 2018/5/17 0017.
 */
public class ConstellationUtil {

    /**
     * 获取星座
     * @param birthday
     * @return
     */
    public static String getConstellation(String birthday) {
        String[] strings = birthday.split("\\.");
        int yueInt = Integer.parseInt(strings[1]);
        int riInt = Integer.parseInt(strings[2]);
        switch (yueInt) {
            case 1:
                return riInt >= 20 ? "水瓶座" : "摩羯座";
            case 2:
                return riInt >= 19 ? "双鱼座" : "水瓶座";
            case 3:
                return riInt >= 21 ? "白羊座" : "双鱼座";
            case 4:
                return riInt >= 20 ? "金牛座" : "白羊座";
            case 5:
                return riInt >= 21 ? "双子座" : "金牛座";
            case 6:
                return riInt >= 22 ? "巨蟹座" : "双子座";
            case 7:
                return riInt >= 23 ? "狮子座" : "巨蟹座";
            case 8:
                return riInt >= 23 ? "处女座" : "狮子座";
            case 9:
                return riInt >= 23 ? "天秤座" : "处女座";
            case 10:
                return riInt >= 24 ? "天蝎座" : "天秤座";
            case 11:
                return riInt >= 23 ? "射手座" : "天蝎座";
            case 12:
                return riInt >= 22 ? "摩羯座" : "射手座";
            default:
                return "";
        }
    }

}
