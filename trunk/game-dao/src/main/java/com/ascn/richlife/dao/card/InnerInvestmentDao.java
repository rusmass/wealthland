package com.ascn.richlife.dao.card;

import com.ascn.richlife.model.card.InnerInvestment;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 内圈投资卡牌
 */
@Repository
public interface InnerInvestmentDao {

    /**
     * 查询所有内投资卡牌信息
     * @return
     */
    List<InnerInvestment> selectCard();

    /**
     * 根据卡牌id查询内圈投资卡牌信息
     * @param cardId
     * @return
     */
    InnerInvestment selectCardById(int cardId);

}
