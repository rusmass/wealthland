package com.ascn.richlife.service.card;


import com.ascn.richlife.model.card.OuterSmallChance;

import java.util.List;

/**
 * 提供外圈小机会服务
 */
public interface OuterSmallChanceService {

    /**
     * 查询外圈小机会卡牌
     *
     * @return
     */
    List<OuterSmallChance> selectCard();

    /**
     * 通过id查询外圈小机会
     *
     * @param cardId
     * @return
     */
    OuterSmallChance selectCardById(int cardId);
}
