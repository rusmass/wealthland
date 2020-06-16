package com.ascn.richlife.dao.card;

import com.ascn.richlife.model.card.OuterSmallChance;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 外圈小机会卡牌
 */
@Repository
public interface OuterSmallChanceDao {

    /**
     * 查询所有外圈小机会卡牌信息
     * @return
     */
    List<OuterSmallChance> selectCard();

    /**
     * 查询单个卡牌信息
     * @param cardId
     * @return
     */
    OuterSmallChance selectCardById(int cardId);
}
