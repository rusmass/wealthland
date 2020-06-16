package com.ascn.richlife.service.card.impl;

import com.ascn.richlife.dao.card.CardStudyDao;
import com.ascn.richlife.model.card.CardStudy;
import com.ascn.richlife.service.card.CardStudyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Administrator on 2018/6/26 0026.
 */
@Service
public class CardStudyServiceImpl implements CardStudyService{

    @Autowired
    CardStudyDao cardStudyDao;

    @Override
    public List<CardStudy> findAll() {
        return cardStudyDao.findAll();
    }
}
