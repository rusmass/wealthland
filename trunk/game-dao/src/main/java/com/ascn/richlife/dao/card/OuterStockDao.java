package com.ascn.richlife.dao.card;

import com.ascn.richlife.model.card.OuterStock;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 外圈股票卡牌
 */
@Repository
public interface OuterStockDao {

    /**
     *  查询所有外圈股票卡牌信息
     * @return
     */
    List<OuterStock> selectCard();

    /**
     * 查询单个卡牌信息
     * @param cardId
     * @return
     */
    OuterStock selectCardById(int cardId);

}
