package com.ascn.richlife.service.card;


import com.ascn.richlife.model.card.InnerFate;

import java.util.List;

/**
 * 提供内圈命运卡牌服务
 */
public interface InnerFateService {

    /**
     * 查询所有内圈命运卡牌信息
     * @return
     */
    List<InnerFate> selectCard();

    /**
     * 查询单个卡牌信息
     * @param cardId
     * @return
     */
    InnerFate selectCardById(int cardId);
}
