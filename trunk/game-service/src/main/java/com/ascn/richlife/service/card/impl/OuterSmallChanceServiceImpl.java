package com.ascn.richlife.service.card.impl;


import com.ascn.richlife.dao.card.OuterSmallChanceDao;
import com.ascn.richlife.model.card.OuterSmallChance;
import com.ascn.richlife.service.card.OuterSmallChanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 外圈小机会服务
 */
@Service("outerSmallChanceService")
public class OuterSmallChanceServiceImpl implements OuterSmallChanceService {
    @Autowired
    private OuterSmallChanceDao outerSmallChanceDao;

    @Override
    public List<OuterSmallChance> selectCard() {
        return outerSmallChanceDao.selectCard();
    }

    public OuterSmallChance selectCardById(int cardId) {
        return outerSmallChanceDao.selectCardById(cardId);
    }
}
