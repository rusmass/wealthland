package com.ascn.richlife.server.data;

import com.ascn.richlife.model.card.CardCharity;
import com.ascn.richlife.model.card.CardHealth;
import com.ascn.richlife.model.card.CardStudy;
import com.ascn.richlife.model.card.Special;
import com.ascn.richlife.server.protocol.CardType;

import java.util.ArrayList;
import java.util.List;

/**
 * 卡牌提示信息
 *
 * Created by Administrator on 2018/6/26 0026.
 */
public class CardTipInfo {

    /**
     * 学习类型卡牌提示信息
     */
    public static List<CardStudy> cardStudies = new ArrayList<>();

    /**
     * 慈善类型卡牌提示信息
     */
    public static List<CardCharity> cardCharities = new ArrayList<>();

    /**
     * 健康类型卡牌提示信息
     */
    public static List<CardHealth> cardHealths = new ArrayList<>();

    /**
     * 获取特殊卡牌提示信息
     * @param special
     * @param cardType
     * @return
     */
    public static Special cardTipData(Special special, int cardType) {
        switch (cardType) {
            case CardType.charity:
                CardCharity cardCharity = cardCharities.get(0);
                special.setTipData(cardCharity);
                cardCharities.remove(0);
                cardCharities.add(cardCharity);
                break;
            case CardType.healthManage:
                CardHealth cardHealth = cardHealths.get(0);
                special.setTipData(cardHealth);
                cardHealths.remove(0);
                cardHealths.add(cardHealth);
                break;
            case CardType.studying:
                CardStudy cardStudy = cardStudies.get(0);
                special.setTipData(cardStudy);
                cardStudies.remove(0);
                cardStudies.add(cardStudy);
                break;
            default:
                throw new RuntimeException("获取小贴士出错");
        }
        return special;
    }
}
