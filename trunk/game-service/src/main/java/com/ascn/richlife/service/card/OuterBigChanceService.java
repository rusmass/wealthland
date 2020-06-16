package com.ascn.richlife.service.card;


import com.ascn.richlife.model.card.OuterBigChance;

import java.util.List;

/**
 * 外圈大机会卡牌服务
 */
public interface OuterBigChanceService {

    List<OuterBigChance> selectCard();

    OuterBigChance selectCardById(int cardId);

}
