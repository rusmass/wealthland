package com.ascn.richlife.service.card;



import com.ascn.richlife.model.card.OuterStock;

import java.util.List;

/**
 * 提供外圈股票服务
 */
public interface OuterStockService {

    /**
     * 外圈股票所有卡牌
     *
     * @return
     */
    List<OuterStock> selectCard();

    /**
     * 通过卡牌id查询卡牌
     *
     * @param cardId
     * @return
     */
    OuterStock selectCardById(int cardId);

}
