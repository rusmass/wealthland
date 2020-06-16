package com.ascn.richlife.server.turntable;

import com.ascn.richlife.server.protocol.CardType;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 游戏中轮盘数据
 *
 * Created by zhangpengxiang on 17/4/6.
 */
public class Turntable {

    private static Logger logger = Logger.getLogger(Turntable.class);

    /**
     * 外圈轮盘的格子
     */
    private static Map<Integer, Integer> outTurntable = new HashMap<Integer, Integer>();

    /**
     * 内圈轮盘的格子
     */
    private static Map<Integer, Integer> innerTurntable = new HashMap<Integer, Integer>();

    /**
     * 用于计算玩家外圈踩过的结账日
     */
    private static List<Integer> outList = new ArrayList<Integer>();

    /**
     * 用于计算玩家内圈踩过的结账日
     */
    private static List<Integer> innerList = new ArrayList<Integer>();

    /**
     * 初始化所有数据
     */
    public static void init() {

        logger.debug("开始初始化轮盘");
        initOutTurntable();
        initInnerTurntable();
    }

    /**
     * 弹卡牌
     *
     * @param outOrIn
     * @param placeByTurntable
     * @param num
     * @return
     */
    public static Map<String, Integer> popupCard(int outOrIn, int placeByTurntable, int num) {

        Map<String, Integer> data = null;

        switch (outOrIn) {

            //外圈
            case 0:
                data = outerPopupCard(placeByTurntable, num);
                break;

            //内圈
            case 1:
                data = innerPopupCard(placeByTurntable, num);
                break;

            default:
                logger.error("错误的内外圈标识代码");
                break;
        }

        return data;
    }

    /**
     * 外圈弹卡牌
     *
     * @param placeByTurntable
     * @param point
     * @return
     */
    private static Map<String, Integer> outerPopupCard(int placeByTurntable, int point) {


        int num = 0;

        int sum = placeByTurntable + point;

        int cardType = placeByTurntable;

        if (sum > 24) {

            for (int i = placeByTurntable + 1; i < outList.size(); i++) {

                if (i == 7) {
                    num = num + 1;
                }
                if (i == 16) {
                    num = num + 1;
                }
                if (i == 24) {
                    num = num + 1;
                }
            }

            for (int i = 0; i < sum - 24; i++) {

                if (i == 7) {
                    num = num + 1;
                }
                if (i == 16) {
                    num = num + 1;
                }
                if (i == 24) {
                    num = num + 1;
                }
            }
            cardType = sum - 25;

        } else {
            for (int i = placeByTurntable + 1; i < sum; i++) {

                if (i == 7) {
                    num = num + 1;
                }
                if (i == 16) {
                    num = num + 1;
                }
                if (i == 24) {
                    num = num + 1;
                }
            }
            cardType = sum;
        }

        Map<String, Integer> data = new HashMap<String, Integer>();
        //卡牌类型
        data.put("cardType", outTurntable.get(cardType));
        //结账日次数
        data.put("closingDateNumber", num);

        return data;
    }

    /**
     * 内圈弹卡牌
     *
     * @param placeByTurntable
     * @param point
     * @return
     */
    private static Map<String, Integer> innerPopupCard(int placeByTurntable, int point) {

        int num = 0;

        int sum = placeByTurntable + point;

        int cardType = placeByTurntable;

        if (sum > 18) {

            for (int i = placeByTurntable + 1; i < innerList.size(); i++) {

                if (i == 8) {
                    num = num + 1;
                }
                if (i == 18) {
                    num = num + 1;
                }
            }

            for (int i = 0; i < sum - 18; i++) {

                if (i == 8) {
                    num = num + 1;
                }
                if (i == 18) {
                    num = num + 1;
                }
            }
            cardType = sum - 19;

        } else {
            for (int i = placeByTurntable + 1; i < sum; i++) {

                if (i == 8) {
                    num = num + 1;
                }
                if (i == 18) {
                    num = num + 1;
                }
            }
            cardType = sum;
        }


        Map<String, Integer> data = new HashMap<String, Integer>();
        //卡牌类型
        data.put("cardType", innerTurntable.get(cardType));
        //结账日次数
        data.put("closingDateNumber", num);

        return data;

    }

    /**
     * 初始化外圈轮盘数据
     */
    private static void initOutTurntable() {
        //机会
        outTurntable.put(0, CardType.chance);
        //风险
        outTurntable.put(1, CardType.risk);
        //机会
        outTurntable.put(2, CardType.chance);
        //命运
        outTurntable.put(3, CardType.outFate);
        //机会
        outTurntable.put(4, CardType.chance);
        //慈善
        outTurntable.put(5, CardType.charity);
        //机会
        outTurntable.put(6, CardType.chance);
        //结账日
        outTurntable.put(7, CardType.closingDate);
        //生孩子
        outTurntable.put(8, CardType.giveChild);
        //命运
        outTurntable.put(9, CardType.outFate);
        //机会
        outTurntable.put(10, CardType.chance);
        //风险
        outTurntable.put(11, CardType.risk);
        //机会
        outTurntable.put(12, CardType.chance);
        //学习
        outTurntable.put(13, CardType.studying);
        //机会
        outTurntable.put(14, CardType.chance);
        //命运
        outTurntable.put(15, CardType.outFate);
        //结账日
        outTurntable.put(16, CardType.closingDate);
        //机会
        outTurntable.put(17, CardType.chance);
        //风险
        outTurntable.put(18, CardType.risk);
        //机会
        outTurntable.put(19, CardType.chance);
        //健康管理
        outTurntable.put(20, CardType.healthManage);
        //机会
        outTurntable.put(21, CardType.chance);
        //命运
        outTurntable.put(22, CardType.outFate);
        //机会
        outTurntable.put(23, CardType.chance);
        //结账日
        outTurntable.put(24, CardType.closingDate);

        for (int i = 0; i < 25; i++) {
            outList.add(i);
        }

    }

    /**
     * 初始化内圈轮盘数据
     */
    private static void initInnerTurntable() {
        //有闲有钱
        innerTurntable.put(0, CardType.richRelax);
        //品质生活
        innerTurntable.put(1, CardType.qualityLife);
        //内圈命运
        innerTurntable.put(2, CardType.inFate);
        //自由选择
        innerTurntable.put(3, CardType.freeChoice);
        //有闲有钱
        innerTurntable.put(4, CardType.richRelax);
        //投资
        innerTurntable.put(5, CardType.investment);
        //有闲有钱
        innerTurntable.put(6, CardType.richRelax);
        //学习
        innerTurntable.put(7, CardType.innerStudying);
        //结账日
        innerTurntable.put(8, CardType.closingDate);
        //投资
        innerTurntable.put(9, CardType.investment);
        //品质生活
        innerTurntable.put(10, CardType.qualityLife);
        //内圈命运
        innerTurntable.put(11, CardType.inFate);
        //有闲有钱
        innerTurntable.put(12, CardType.richRelax);
        //投资
        innerTurntable.put(13, CardType.investment);
        //品质生活
        innerTurntable.put(14, CardType.qualityLife);
        //健康
        innerTurntable.put(15, CardType.innerHealthManage);
        //有闲有钱
        innerTurntable.put(16, CardType.richRelax);
        //投资
        innerTurntable.put(17, CardType.investment);
        //结账日
        innerTurntable.put(18, CardType.closingDate);

        for (int i = 0; i < 19; i++) {
            innerList.add(i);
        }

    }
}
