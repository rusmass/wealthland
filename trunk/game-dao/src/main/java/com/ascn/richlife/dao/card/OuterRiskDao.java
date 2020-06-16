package com.ascn.richlife.dao.card;

import com.ascn.richlife.model.card.OuterRisk;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 外圈风险卡牌
 */
@Repository
public interface OuterRiskDao {

    /**
     * 查询所外圈风险卡牌信息
     * @return
     */
    List<OuterRisk> selectCard();

    /**
     * 查询单个卡牌信息
     * @param cardId
     * @return
     */
    OuterRisk selectCardById(int cardId);

}
