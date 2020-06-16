package com.ascn.richlife.service.card.impl;


import com.ascn.richlife.dao.card.SpecialDao;
import com.ascn.richlife.model.card.Special;
import com.ascn.richlife.service.card.SpecialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 特别卡牌服务层
 */
@Service("specialService")
public class SpecialServiceImpl implements SpecialService {

    @Autowired
    private SpecialDao specialDao;

    @Override
    public List<Special> selectCard() {
        return specialDao.selectCard();
    }

    public Special selectCardById(int cardId) {
        return specialDao.selectCardById(cardId);
    }
}
