package com.ascn.richlife.service.card;


import com.ascn.richlife.model.card.OuterFate;

import java.util.List;

/**
 * 提供外圈命运服务
 */
public interface OuterFateService {

    /**
     * 查询外圈命运卡牌
     *
     * @return
     */
    List<OuterFate> selectCard();

    /**
     * 通过id查询外圈命运卡牌
     *
     * @param cardId
     * @return
     */
    OuterFate selectCardById(int cardId);

}
