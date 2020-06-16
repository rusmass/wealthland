package com.ascn.richlife.dao.card;

import com.ascn.richlife.model.card.OuterFate;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 外圈命运卡牌
 */
@Repository
public interface OuterFateDao {

    /**
     *  查询所有外圈命运卡牌信息
     * @return
     */
    List<OuterFate> selectCard();

    /**
     * 查询单个卡牌信息
     * @param cardId
     * @return
     */
    OuterFate selectCardById(int cardId);

}
