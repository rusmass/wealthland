package com.ascn.richlife.dao.card;

import com.ascn.richlife.model.card.CardHealth;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Administrator on 2018/6/26 0026.
 */
@Repository
public interface CardHealthDao {

    List<CardHealth> findAll();
}
