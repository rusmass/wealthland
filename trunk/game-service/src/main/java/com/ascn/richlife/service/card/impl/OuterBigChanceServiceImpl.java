package com.ascn.richlife.service.card.impl;


import com.ascn.richlife.dao.card.OuterBigChanceDao;
import com.ascn.richlife.model.card.OuterBigChance;
import com.ascn.richlife.service.card.OuterBigChanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 外圈大机会服务
 */
@Service("outerBigChanceService")
public class OuterBigChanceServiceImpl implements OuterBigChanceService {

    @Autowired
    private OuterBigChanceDao outerBigChanceDao;

    @Override
    public List<OuterBigChance> selectCard() {
        return outerBigChanceDao.selectCard();
    }

    public OuterBigChance selectCardById(int cardId) {
        return outerBigChanceDao.selectCardById(cardId);
    }
}
