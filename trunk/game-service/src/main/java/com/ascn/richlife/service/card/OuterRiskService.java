package com.ascn.richlife.service.card;


import com.ascn.richlife.model.card.OuterRisk;

import java.util.List;

/**
 * 提供外圈风险卡牌服务
 */
public interface OuterRiskService {

    /**
     * 查询外圈风险卡牌
     *
     * @return
     */
    List<OuterRisk> selectCard();

    /**
     * 通过id查询外圈风险
     *
     * @param cardId
     * @return
     */
    OuterRisk selectCardById(int cardId);

}
