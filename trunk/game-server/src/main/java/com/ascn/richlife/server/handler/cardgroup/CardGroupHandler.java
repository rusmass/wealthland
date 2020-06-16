package com.ascn.richlife.server.handler.cardgroup;

import com.ascn.richlife.model.CardGroup;
import com.ascn.richlife.server.data.CardInfo;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 卡牌组工具类
 *
 * Created by Administrator on 2017/7/7 0007.
 */
@Component("cardGroupHandler")
public class CardGroupHandler {

    private Logger logger = Logger.getLogger(CardGroupHandler.class);

    public CardGroup init() {
        CardGroup cardGroup = new CardGroup();

        logger.debug("分配小机会卡组");
        List<Integer> outerSmallChanceGroup = new ArrayList<Integer>();

        outerSmallChanceGroup.addAll(CardInfo.outerSmallChanceGroup);

        Collections.shuffle(outerSmallChanceGroup);

        logger.debug("分配股票卡组");
        List<Integer> outerStockGroup = new ArrayList<Integer>();

        outerStockGroup.addAll(CardInfo.outerStockGroup);

        Collections.shuffle(outerStockGroup);

        logger.debug("分配大机会卡组");
        List<Integer> outerBigChanceGroup = new ArrayList<Integer>();

        outerBigChanceGroup.addAll(CardInfo.outerBigChanceGroup);

        Collections.shuffle(outerBigChanceGroup);

        logger.debug("分配外圈命运卡组");
        List<Integer> outerFateGroup = new ArrayList<Integer>();

        outerFateGroup.addAll(CardInfo.outerFateGroup);

        Collections.shuffle(outerFateGroup);

        logger.debug("分配外圈风险卡组");
        List<Integer> outerRiskGroup = new ArrayList<Integer>();

        outerRiskGroup.addAll(CardInfo.outerRiskGroup);

        Collections.shuffle(outerRiskGroup);

        logger.debug("分配内圈命运卡组");
        List<Integer> innerFateGroup = new ArrayList<Integer>();

        innerFateGroup.addAll(CardInfo.innerFateGroup);

        Collections.shuffle(innerFateGroup);

        logger.debug("分配内圈投资卡组");
        List<Integer> innerInvestmentGroup = new ArrayList<Integer>();

        innerInvestmentGroup.addAll(CardInfo.innerInvestmentGroup);

        Collections.shuffle(innerInvestmentGroup);

        logger.debug("分配内圈品质生活卡组");
        List<Integer> innerQualityLifeGroup = new ArrayList<Integer>();

        innerQualityLifeGroup.addAll(CardInfo.innerQualityLifeGroup);

        Collections.shuffle(innerQualityLifeGroup);

        logger.debug("分配内圈有闲有钱卡组");
        List<Integer> innerRelaxGroup = new ArrayList<Integer>();

        innerRelaxGroup.addAll(CardInfo.innerRelaxGroup);

        Collections.shuffle(innerRelaxGroup);

        logger.debug("分配特殊卡组");
        List<Integer> specialGroup = new ArrayList<Integer>();

        specialGroup.addAll(CardInfo.specialGroup);

        Collections.shuffle(specialGroup);

        cardGroup.setOuterSmallChanceGroup(outerSmallChanceGroup);

        cardGroup.setOuterStockGroup(outerStockGroup);

        cardGroup.setOuterBigChanceGroup(outerBigChanceGroup);

        cardGroup.setOuterFateGroup(outerFateGroup);

        cardGroup.setOuterRiskGroup(outerRiskGroup);

        cardGroup.setInnerFateGroup(innerFateGroup);

        cardGroup.setInnerInvestmentGroup(innerInvestmentGroup);

        cardGroup.setInnerQualityLifeGroup(innerQualityLifeGroup);

        cardGroup.setInnerRelaxGroup(innerRelaxGroup);

        cardGroup.setSpecialGroup(specialGroup);

        return cardGroup;
    }
}
