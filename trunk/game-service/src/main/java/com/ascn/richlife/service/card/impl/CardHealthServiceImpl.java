package com.ascn.richlife.service.card.impl;

import com.ascn.richlife.dao.card.CardHealthDao;
import com.ascn.richlife.model.card.CardHealth;
import com.ascn.richlife.service.card.CardHealthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Administrator on 2018/6/26 0026.
 */
@Service
public class CardHealthServiceImpl implements CardHealthService {

    @Autowired
    CardHealthDao cardHealthDao;

    @Override
    public List<CardHealth> findAll() {
        return cardHealthDao.findAll();
    }
}
