package com.ascn.richlife.service.card;


import com.ascn.richlife.model.card.Special;

import java.util.List;

/**
 * 提供特别卡牌
 */
public interface SpecialService {

    /**
     * 查询所有特别卡牌
     *
     * @return
     */
    List<Special> selectCard();

    /**
     * 通过卡牌id查询卡牌
     *
     * @param cardId
     * @return
     */
    Special selectCardById(int cardId);
}
