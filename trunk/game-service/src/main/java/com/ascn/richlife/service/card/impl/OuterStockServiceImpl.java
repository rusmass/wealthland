package com.ascn.richlife.service.card.impl;


import com.ascn.richlife.dao.card.OuterStockDao;
import com.ascn.richlife.model.card.OuterStock;
import com.ascn.richlife.service.card.OuterStockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 外圈股票服务层
 */
@Service("outerStockService")
public class OuterStockServiceImpl implements OuterStockService {

    @Autowired
    private OuterStockDao outerStockDao;

    @Override
    public List<OuterStock> selectCard() {
        return outerStockDao.selectCard();
    }

    public OuterStock selectCardById(int cardId) {
        return outerStockDao.selectCardById(cardId);
    }
}
