package com.ascn.richlife.service.card.impl;


import com.ascn.richlife.dao.card.InnerFateDao;
import com.ascn.richlife.model.card.InnerFate;
import com.ascn.richlife.service.card.InnerFateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 内圈命运服务
 */
@Service("innerFateService")
public class InnerFateServiceImpl implements InnerFateService {

    @Autowired
    private InnerFateDao innerFateDao;

    @Override
    public List<InnerFate> selectCard() {
        return innerFateDao.selectCard();
    }

    @Override
    public InnerFate selectCardById(int cardId) {
        return innerFateDao.selectCardById(cardId);
    }
}
