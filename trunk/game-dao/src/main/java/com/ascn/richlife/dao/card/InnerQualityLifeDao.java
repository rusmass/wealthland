package com.ascn.richlife.dao.card;

import com.ascn.richlife.model.card.InnerQualityLife;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 内圈品质生活卡牌
 */
@Repository
public interface InnerQualityLifeDao {

    /**
     * 查询所有内圈品质生活卡牌信息
     * @return
     */
    List<InnerQualityLife> selectCard();

    /**
     * 根据卡牌id查询卡牌信息
     * @param cardId
     * @return
     */
    InnerQualityLife selectCardById(int cardId);

}
