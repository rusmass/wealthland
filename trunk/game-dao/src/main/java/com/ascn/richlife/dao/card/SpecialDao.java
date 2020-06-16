package com.ascn.richlife.dao.card;

import com.ascn.richlife.model.card.Special;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 特殊卡牌
 */
@Repository
public interface SpecialDao {

    /**
     *  查询所有特殊卡牌信息
     * @return
     */
    List<Special> selectCard();

    /**
     * 查询单个卡牌信息
     * @param cardId
     * @return
     */
    Special selectCardById(int cardId);
}
