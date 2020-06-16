package com.ascn.richlife.service.card.impl;


import com.ascn.richlife.dao.card.InnerQualityLifeDao;
import com.ascn.richlife.model.card.InnerQualityLife;
import com.ascn.richlife.service.card.InnerQualityLifeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * 内圈品质生活服务
 */
@Service("innerQualityLifeService")
public class InnerQualityLifeServiceImpl implements InnerQualityLifeService {
    @Autowired
    private InnerQualityLifeDao innerQualityLifeDao;

    @Override
    public List<InnerQualityLife> selectCard() {
        return innerQualityLifeDao.selectCard();
    }

    public InnerQualityLife selectCardById(int cardId) {
        return innerQualityLifeDao.selectCardById(cardId);
    }
}
