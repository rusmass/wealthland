package com.ascn.richlife.server.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 卡牌信息工具类
 *
 * Created by zhangpengxiang on 17/4/10.
 */
public class CardInfo {

    //所有卡牌信息
    public static Map<Integer, Object> cardInfo = new HashMap<Integer, Object>();

    //小机会卡组
    public static List<Integer> outerSmallChanceGroup = new ArrayList<Integer>();

    //股票卡组
    public static List<Integer> outerStockGroup = new ArrayList<Integer>();

    //大机会卡组
    public static List<Integer> outerBigChanceGroup = new ArrayList<Integer>();

    //外圈命运卡组
    public static List<Integer> outerFateGroup = new ArrayList<Integer>();

    //风险卡组
    public static List<Integer> outerRiskGroup = new ArrayList<Integer>();

    //内圈命运卡组
    public static List<Integer> innerFateGroup = new ArrayList<Integer>();

    //内圈投资卡组
    public static List<Integer> innerInvestmentGroup = new ArrayList<Integer>();

    //内圈品质生活卡组
    public static List<Integer> innerQualityLifeGroup = new ArrayList<Integer>();

    //内圈有闲有钱卡组
    public static List<Integer> innerRelaxGroup = new ArrayList<Integer>();

    //特殊卡组
    public static List<Integer> specialGroup = new ArrayList<Integer>();

}
