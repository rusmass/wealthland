package com.ascn.richlife.service.card.impl;


import com.ascn.richlife.dao.card.InnerRichRelaxDao;
import com.ascn.richlife.model.card.InnerRichRelax;
import com.ascn.richlife.service.card.InnerRichRelaxService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 内圈有钱有闲服务
 */
@Service("innerRichRelaxService")
public class InnerRichRelaxServiceImpl implements InnerRichRelaxService {
    @Autowired
    private InnerRichRelaxDao innerRichRelaxDao;

    @Override
    public List<InnerRichRelax> selectCard() {
        return innerRichRelaxDao.selectCard();
    }

    public InnerRichRelax selectCardById(int cardId) {
        return innerRichRelaxDao.selectCardById(cardId);
    }
}
