package com.ascn.richlife.service.card;


import com.ascn.richlife.model.card.InnerQualityLife;

import java.util.List;

/**
 * 提供内圈品质生活服务
 */
public interface InnerQualityLifeService {

    /**
     * 查询内圈品质生活卡牌
     *
     * @return
     */
    List<InnerQualityLife> selectCard();

    /**
     * 通过id查询卡牌
     *
     * @param cardId
     * @return
     */
    InnerQualityLife selectCardById(int cardId);

}
