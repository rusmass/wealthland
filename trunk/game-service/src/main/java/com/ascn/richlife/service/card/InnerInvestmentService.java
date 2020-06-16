package com.ascn.richlife.service.card;

import com.ascn.richlife.model.card.InnerInvestment;

import java.util.List;

/**
 * 提供内圈资产服务
 */
public interface InnerInvestmentService {

    /**
     * 查询内圈资产卡牌
     *
     * @return
     */
    List<InnerInvestment> selectCard();

    /**
     * 通过id查询卡牌
     *
     * @param cardId
     * @return
     */
    InnerInvestment selectCardById(int cardId);

}
