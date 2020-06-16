package com.ascn.richlife.dao.card;

import com.ascn.richlife.model.card.CardCharity;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Administrator on 2018/6/26 0026.
 */
@Repository
public interface CardCharityDao {

    List<CardCharity> findAll();
}
