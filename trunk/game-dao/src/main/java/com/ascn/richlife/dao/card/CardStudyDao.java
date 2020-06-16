package com.ascn.richlife.dao.card;

import com.ascn.richlife.model.card.CardStudy;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Administrator on 2018/6/25 0025.
 */
@Repository
public interface CardStudyDao {
    
    List<CardStudy> findAll();
}
