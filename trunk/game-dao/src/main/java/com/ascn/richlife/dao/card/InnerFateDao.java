package com.ascn.richlife.dao.card;

import com.ascn.richlife.model.card.InnerFate;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 内圈命运卡牌
 */
@Repository
public interface InnerFateDao {

    /**
     *  查询所有内圈卡牌信息
     * @return
     */
    List<InnerFate> selectCard();

    /**
     * 根据卡牌ID查询内圈命运卡牌信息
     * @param cardId
     * @return
     */
    InnerFate selectCardById(int cardId);
}
