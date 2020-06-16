package com.ascn.richlife.dao.card;

import com.ascn.richlife.model.card.InnerRichRelax;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 内圈有钱有闲卡牌
 */
@Repository
public interface InnerRichRelaxDao {

    /**
     *  查询所有内圈有闲有钱卡牌信息
     * @return
     */
    List<InnerRichRelax> selectCard();

    /**
     * 查询单个卡牌信息
     * @param cardId
     * @return
     */
    InnerRichRelax selectCardById(int cardId);

}
