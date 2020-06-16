package com.ascn.richlife.service.card.impl;


import com.ascn.richlife.dao.card.OuterRiskDao;
import com.ascn.richlife.model.card.OuterRisk;
import com.ascn.richlife.service.card.OuterRiskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 外圈风险卡牌
 */
@Service("outerRiskService")
public class OuterRiskServiceImpl implements OuterRiskService {

    @Autowired
    private OuterRiskDao outerRiskDao;

    @Override
    public List<OuterRisk> selectCard() {
        return outerRiskDao.selectCard();
    }

    public OuterRisk selectCardById(int cardId) {
        return outerRiskDao.selectCardById(cardId);
    }
}
