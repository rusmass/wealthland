package com.ascn.richlife.service.card;


import com.ascn.richlife.model.card.InnerRichRelax;

import java.util.List;

/**
 * 提供查询内圈有钱有闲卡牌服务
 */
public interface InnerRichRelaxService {

    /**
     * 查询内圈有钱有闲卡牌信息
     *
     * @return
     */
    List<InnerRichRelax> selectCard();

    /**
     * 通过id查询内圈有钱有闲卡牌
     *
     * @param cardId
     * @return
     */
    InnerRichRelax selectCardById(int cardId);

}
