package com.ascn.richlife.service.card.impl;


import com.ascn.richlife.dao.card.InnerInvestmentDao;
import com.ascn.richlife.model.card.InnerInvestment;
import com.ascn.richlife.service.card.InnerInvestmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * 内圈资产服务
 */
@Service("innerInvestmentService")
public class InnerInvestmentServiceImpl implements InnerInvestmentService {

    @Autowired
    private InnerInvestmentDao innerInvestmentDao;

    @Override
    public List<InnerInvestment> selectCard() {
        return innerInvestmentDao.selectCard();
    }

    public InnerInvestment selectCardById(int cardId) {
        return innerInvestmentDao.selectCardById(cardId);
    }
}
