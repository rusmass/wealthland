package com.ascn.richlife.service.card.impl;

import com.ascn.richlife.dao.card.CardCharityDao;
import com.ascn.richlife.model.card.CardCharity;
import com.ascn.richlife.service.card.CardCharityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Administrator on 2018/6/26 0026.
 */
@Service
public class CardCharityServiceImpl implements CardCharityService{

    @Autowired
    CardCharityDao cardCharityDao;

    @Override
    public List<CardCharity> findAll() {
        return cardCharityDao.findAll();
    }
}
