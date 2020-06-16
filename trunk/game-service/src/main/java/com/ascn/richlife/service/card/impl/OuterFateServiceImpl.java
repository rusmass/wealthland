package com.ascn.richlife.service.card.impl;


import com.ascn.richlife.dao.card.OuterFateDao;
import com.ascn.richlife.model.card.OuterFate;
import com.ascn.richlife.service.card.OuterFateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 外圈命运卡牌
 */
@Service("outerFateService")
public class OuterFateServiceImpl implements OuterFateService {
    @Autowired
    private OuterFateDao outerFateDao;

    @Override
    public List<OuterFate> selectCard() {
        return outerFateDao.selectCard();
    }

    public OuterFate selectCardById(int cardId) {
        return outerFateDao.selectCardById(cardId);
    }
}
