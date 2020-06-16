package com.ascn.richlife.dao.card;

import com.ascn.richlife.model.card.OuterBigChance;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 外圈大机会卡牌
 */
@Repository
public interface OuterBigChanceDao {

    /**
     *  查询所有外圈大机会卡牌信息
     * @return
     */
    List<OuterBigChance> selectCard();

    /**
     * 查询单个卡牌信息
     * @param cardId
     * @return
     */
    OuterBigChance selectCardById(int cardId);

}
