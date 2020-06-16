package com.ascn.richlife.server.handler;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ascn.richlife.model.CardGroup;
import com.ascn.richlife.model.Location;
import com.ascn.richlife.model.card.*;
import com.ascn.richlife.model.integral.Integral;
import com.ascn.richlife.model.loan.RoleLoanInfo;
import com.ascn.richlife.model.role.*;
import com.ascn.richlife.server.data.CardId;
import com.ascn.richlife.server.data.CardInfo;
import com.ascn.richlife.server.data.CardTipInfo;
import com.ascn.richlife.server.group.*;
import com.ascn.richlife.server.handler.role.*;
import com.ascn.richlife.server.message.MessageStatus;
import com.ascn.richlife.server.message.NettyMessage;
import com.ascn.richlife.server.protocol.CardType;
import com.ascn.richlife.server.protocol.ConnectionStatus;
import com.ascn.richlife.server.protocol.ProtoIds;
import com.ascn.richlife.server.turntable.Turntable;
import com.ascn.richlife.server.util.BuildResponseMessage;
import com.ascn.richlife.service.login.PlayerService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.smartcardio.Card;
import java.math.BigDecimal;
import java.util.*;

/**
 * 游戏具体逻辑处理
 * <p>
 * Created by zhangpengxiang on 2017/6/20.
 */
@Component("gameHandler")
public class GameHandler {

    private Logger logger = Logger.getLogger(GameHandler.class);

    @Autowired
    private RoleDataManageInfoHandler roleDataManageInfoHandler;

    @Autowired
    private RoleLoanInfoHandler roleLoanInfoHandler;

    @Autowired
    private RoleHaveAssetInfoHandler roleHaveAssetInfoHandler;

    @Autowired
    private RoleIntegralRecordHandler roleIntegralRecordHandler;

    @Autowired
    private RoleDebtsInfoHandler roleDebtsInfoHandler;

    @Autowired
    private ReconnectionHandler reconnectionHandler;

    @Autowired
    private PlayerService playerService;

    @Autowired
    private GameStatisticsHandler gameStatisticsHandler;

    /**
     * 流动总资金
     */
    private static final int FLOW_CASH_TOTAL_INTEGRAL = 200000;

    /**
     * 时间总积分
     */
    private static final int TIME_TOTAL_INTEGRAL = 1000;

    /**
     * 品质总积分
     */
    private static final int QUALITY_TOTAL_INTEGRAL = 100;

    /**
     * 角色现金
     */
    private static final int ROLE_CASH = 10000;

    /**
     * 掷骰子
     *
     * @param roomId
     * @param playerId
     */
    public void rollTheDice(String roomId, String playerId) {

        //保存触发卡牌的玩家
        GameManager.getGame(roomId).setTriggerCardPlayerId(playerId);

        RoleInfo roleInfo = GameManager.getRoleInfo(roomId, playerId);

        //角色掷骰子数据
        Map<String, Integer> roleRollDiceData = GameManager.getRoleRollDiceData(roomId);

        //角色位置信息
        Map<String, Location> roleLocationData = GameManager.getRoleLocationData(roomId);

        //获取玩家是在内圈还是外圈
        int outOrIn = roleLocationData.get(playerId).getOutOrIn();

        //获取玩家该回合掷几个骰子
        int rollDiceNumber = roleRollDiceData.get(playerId);

        //获取玩家在轮盘上所在的位置
        int placeByTurntable = GameManager.getRoleLocationData(roomId).get(playerId).getPlaceByTurntable();

        logger.debug(playerId + "玩家信息:");

        logger.debug("玩家在内圈还是外圈:" + outOrIn);

        logger.debug("玩家该回合掷几个骰子:" + rollDiceNumber);

        logger.debug("玩家目前所在的位置:" + placeByTurntable);

        logger.debug(roleInfo.getRoleBasicInfo().getName() + "开始掷骰子...");

        //摇骰子的信息
        Map<String, Object> diceInfo = generatorPoint(rollDiceNumber);

        //获取摇到的骰子
        int[] point = (int[]) diceInfo.get("point");

        //获取摇到骰子的总点数,说明要走几个格子
        int sum = Integer.parseInt(diceInfo.get("sum").toString());

        //重新定位玩家的位置和累计玩家点数
        if (outOrIn == 0) {

            //累计玩家外圈点数
            roleLocationData.get(playerId).setOuterTotalPoint(roleLocationData.get(playerId).getOuterTotalPoint() + sum);

            if (roleLocationData.get(playerId).getPlaceByTurntable() + sum > 24) {

                roleLocationData.get(playerId).setPlaceByTurntable(roleLocationData.get(playerId).getPlaceByTurntable() + sum - 25);

            } else {
                roleLocationData.get(playerId).setPlaceByTurntable(roleLocationData.get(playerId).getPlaceByTurntable() + sum);
            }
        } else if (outOrIn == 1) {

            //累计玩家内圈点数
            roleLocationData.get(playerId).setInnerTotalPoint(roleLocationData.get(playerId).getInnerTotalPoint() + sum);

            if (roleLocationData.get(playerId).getPlaceByTurntable() + sum > 18) {

                roleLocationData.get(playerId).setPlaceByTurntable(roleLocationData.get(playerId).getPlaceByTurntable() + sum - 19);

            } else {
                roleLocationData.get(playerId).setPlaceByTurntable(roleLocationData.get(playerId).getPlaceByTurntable() + sum);
            }
        }

        //弹出卡牌信息
        Map<String, Integer> popupCardResult = Turntable.popupCard(outOrIn, placeByTurntable, sum);

        //弹出卡牌的类型
        int cardType = popupCardResult.get("cardType");

        //结账日的次数
        int closingDateNumber = popupCardResult.get("closingDateNumber");

        //获取房间的卡组
        CardGroup cardGroup = GameManager.getCardGroup(roomId);

        //数据
        Map<String, Object> data = new HashMap<String, Object>();

        //掷的点数
        data.put("point", point);

        //结账日的次数
        data.put("closingDateNumber", closingDateNumber);

        //结账日金额
        data.put("closingDateMoney", roleInfo.getRoleDataManageInfo().getClosingDateMoney());

        //更新角色现金
        roleDataManageInfoHandler.updateRoleCash(roleInfo.getRoleDataManageInfo(), closingDateNumber * roleInfo.getRoleDataManageInfo().getClosingDateMoney());

        //重置骰子的点数
        roleRollDiceData.put(playerId, 1);

        //查询卡牌的信息
        switch (cardType) {

            //机会
            case CardType.chance:

                //判断当前玩家的现金是否大于10000
                if (roleInfo.getRoleDataManageInfo().getCash() > ROLE_CASH) {

                    //如果大于10000,则玩家可以自由选择大机会和小机会,否则只能选择小机会
                    data.put("cardType", CardType.choiceChance);

                } else {

                    //增加遇到小机会的次数
                    //createWealthService.updateAllSmallChanceNumber(role.getGameSettleInfo().getCreateWealth());

                    //从小机会卡组抽取卡牌
                    int smallChanceCardId = getCardFromCardGroup(cardGroup.getOuterSmallChanceGroup(), CardType.smallChance);

                    //查询卡牌信息
                    if (smallChanceCardId > CardId.SMALL_CHANCE_CARD_ID_MIN && smallChanceCardId < CardId.SMALL_CHANCE_CARD_ID_MAX) {

                        //资产
                        OuterSmallChance outerSmallChance = (OuterSmallChance) CardInfo.cardInfo.get(smallChanceCardId);

                        //小机会卡牌积分
                        GameStatisticsManager.getRoleOperateNum(playerId).setSmallChanceIntegral(GameStatisticsManager.getRoleOperateNum(playerId).getSmallChanceIntegral() + outerSmallChance.getCardIntegral());
                        data.put("cardType", CardType.smallChance_asset);
                        data.put("cardInfo", outerSmallChance);
                    } else {

                        //股票
                        OuterStock outerStock = (OuterStock) CardInfo.cardInfo.get(smallChanceCardId);

                        //小机会卡牌积分
                        try {
                            GameStatisticsManager.getRoleOperateNum(playerId).setSmallChanceIntegral(GameStatisticsManager.getRoleOperateNum(playerId).getSmallChanceIntegral() + outerStock.getCardIntegral());
                        } catch (Exception e) {
                            logger.debug(e.getMessage());
                        }

                        data.put("cardType", CardType.smallChance_stock);
                        data.put("cardInfo", outerStock);

                    }
                }
                break;

            //风险
            case CardType.risk:

                //从卡组获取风险卡牌
                int riskCardId = getCardFromCardGroup(cardGroup.getOuterRiskGroup(), CardType.risk);

                if (riskCardId == CardId.RISK_ID) {

                    //记录失业次数
                    GameStatisticsManager.getRoleOperateNum(playerId).setUnemploymentNum(GameStatisticsManager.getRoleOperateNum(playerId).getUnemploymentNum() + 1);

                    riskCardId = getCardFromCardGroup(cardGroup.getOuterRiskGroup(), CardType.risk);
                }

                //查询卡牌信息
                OuterRisk outerRisk = (OuterRisk) CardInfo.cardInfo.get(riskCardId);

                data.put("cardType", CardType.risk);
                data.put("cardInfo", outerRisk);

                break;

            //外圈命运
            case CardType.outFate:

                //从外圈命运获取卡牌
                int outFateCardId = getCardFromCardGroup(cardGroup.getOuterFateGroup(), CardType.outFate);

                if (outFateCardId == CardId.OUT_FATE_CARD_ID) {
                    outFateCardId = getCardFromCardGroup(cardGroup.getOuterFateGroup(), CardType.outFate);
                }

                //查询卡牌信息
                OuterFate outerFate = (OuterFate) CardInfo.cardInfo.get(outFateCardId);

                data.put("cardType", CardType.outFate);
                data.put("cardInfo", outerFate);

                break;

            //慈善
            case CardType.charity:

                int charityCardId = CardType.charity;

                //记录遇到慈善的次数
                GameStatisticsManager.getRoleOperateNum(playerId).setMeetCharitableNum(GameStatisticsManager.getRoleOperateNum(playerId).getMeetCharitableNum() + 1);

                Special charity = (Special) CardInfo.cardInfo.get(charityCardId);

                CardTipInfo.cardTipData(charity, CardType.charity);

                data.put("cardType", CardType.charity);

                data.put("cardInfo", charity);

                //增加遇到的慈善事业次数
                //useWealthService.updateAllCharity(role.getGameSettleInfo().getUseWealth());

                break;

            //学习
            case CardType.studying:

                //记录遇到学习的次数
                GameStatisticsManager.getRoleOperateNum(playerId).setMeetStudyNum(GameStatisticsManager.getRoleOperateNum(playerId).getMeetStudyNum() + 1);

                int studyCardId = CardType.studying;

                Special studying = (Special) CardInfo.cardInfo.get(studyCardId);

                CardTipInfo.cardTipData(studying, CardType.studying);
                data.put("cardType", CardType.studying);

                data.put("cardInfo", studying);

                //增加遇到的进修学习的次数
                //beyondWealthService.updateAllStudyingNumber(role.getGameSettleInfo().getBeyondWealth());

                break;

            //健康管理
            case CardType.healthManage:

                //记录遇到健康管理的次数
                GameStatisticsManager.getRoleOperateNum(playerId).setMeetHealthNum(GameStatisticsManager.getRoleOperateNum(playerId).getMeetHealthNum() + 1);

                int healthManageCardId = CardType.healthManage;

                Special healthManage = (Special) CardInfo.cardInfo.get(healthManageCardId);

                CardTipInfo.cardTipData(healthManage, CardType.healthManage);

                data.put("cardType", CardType.healthManage);
                data.put("cardInfo", healthManage);

                //增加遇到的健康管理次数
                //beyondWealthService.updateAllHealthManagementNumber(role.getGameSettleInfo().getBeyondWealth());

                break;

            //结账日
            case CardType.closingDate:

                int closingDateCardId = CardType.closingDate;

                Special closingDate = (Special) CardInfo.cardInfo.get(closingDateCardId);

                data.put("cardType", CardType.closingDate);
                data.put("cardInfo", closingDate);

                //判断是内圈结账日还是外圈结账日
                if (outOrIn == 0) {

                    //createWealthService.updateClosingDateNumber(role.getGameSettleInfo().getCreateWealth(), closingDateNumber + 1);

                } else {

                    // manageWealthService.updateClosingDateNumber(role.getGameSettleInfo().getManageWealth(), closingDateNumber + 1);

                }

                break;

            //生孩子
            case CardType.giveChild:

                //获取角色的数据信息
                RoleDataManageInfo roleDataManageInfo = roleInfo.getRoleDataManageInfo();

                //判断玩家当前是否超生，如果超生进行超生处罚，满足超生的条件:孩子数量 > 3

                //获取角色当前的孩子数量
                int haveChildNumber = roleDataManageInfo.getHaveChildNumber();

                logger.debug("当前孩子的数量:" + haveChildNumber);

                //判断是否超生
                if (haveChildNumber >= 3) {

                    //需要罚款
                    data.put("ifFine", 1);

                } else {

                    //记录生孩子数
                    GameStatisticsManager.getRoleOperateNum(playerId).setChildrenNum(GameStatisticsManager.getRoleOperateNum(playerId).getChildrenNum() + 1);

                    data.put("ifFine", 0);

                }

                Special special = (Special) CardInfo.cardInfo.get(CardType.giveChild);

                data.put("cardType", CardType.giveChild);

                data.put("cardInfo", special);

                break;

            //内圈进修学习
            case CardType.innerStudying:

                int innerStudyCardId = CardType.innerStudying;

                Special innerStudying = (Special) CardInfo.cardInfo.get(innerStudyCardId);

                CardTipInfo.cardTipData(innerStudying,CardType.studying);

                data.put("cardType", CardType.innerStudying);

                data.put("cardInfo", innerStudying);

                break;

            //内圈结账日
            case CardType.innerClosingDate:

                int innerClosingDateCardId = CardType.innerClosingDate;

                Special innerClosingDate = (Special) CardInfo.cardInfo.get(innerClosingDateCardId);

                data.put("cardType", CardType.innerClosingDate);

                data.put("cardInfo", innerClosingDate);

                break;

            //内圈健康管理
            case CardType.innerHealthManage:

                int innerHealthManageCardId = CardType.innerHealthManage;

                Special innerHealthManage = (Special) CardInfo.cardInfo.get(innerHealthManageCardId);

                CardTipInfo.cardTipData(innerHealthManage,CardType.healthManage);

                data.put("cardType", CardType.innerHealthManage);

                data.put("cardInfo", innerHealthManage);

                break;

            //有闲有钱
            case CardType.richRelax:

                int richRelaxCardId = getCardFromCardGroup(cardGroup.getInnerRelaxGroup(), CardType.richRelax);

                //记录遇到有钱有闲的次数
                GameStatisticsManager.getRoleOperateNum(playerId).setMeetRichAndIdleNum(GameStatisticsManager.getRoleOperateNum(playerId).getMeetRichAndIdleNum() + 1);

                //查询卡牌信息
                InnerRichRelax innerRichRelax = (InnerRichRelax) CardInfo.cardInfo.get(richRelaxCardId);

                data.put("cardType", CardType.richRelax);

                data.put("cardInfo", innerRichRelax);

                //增加遇到的有闲有钱的次数
                //useWealthService.updateAllRichRelaxNumber(role.getGameSettleInfo().getUseWealth());

                break;

            //品质生活
            case CardType.qualityLife:

                int qualityLifeCardId = getCardFromCardGroup(cardGroup.getInnerQualityLifeGroup(), CardType.qualityLife);

                //记录遇到品质生活的次数
                GameStatisticsManager.getRoleOperateNum(playerId).setMeetQualityLifeNum(GameStatisticsManager.getRoleOperateNum(playerId).getMeetQualityLifeNum() + 1);

                //查询卡牌信息
                InnerQualityLife innerQualityLife = (InnerQualityLife) CardInfo.cardInfo.get(qualityLifeCardId);

                data.put("cardType", CardType.qualityLife);

                data.put("cardInfo", innerQualityLife);

                //增加遇到的品质生活的次数
                //useWealthService.updateAllQualityLifeNumber(role.getGameSettleInfo().getUseWealth());

                break;

            //投资
            case CardType.investment:

                //记录操作遇到投资的次数
                RoleOperateNum roleOperateNum = GameStatisticsManager.getRoleOperateNum(playerId);
                roleOperateNum.setMeetInvestNum(roleOperateNum.getMeetInvestNum() + 1);

                int investmentId = getCardFromCardGroup(cardGroup.getInnerInvestmentGroup(), CardType.investment);

                InnerInvestment investment = (InnerInvestment) CardInfo.cardInfo.get(investmentId);

                //记录投资积分
                GameStatisticsManager.getRoleOperateNum(playerId).setInvestIntegral(GameStatisticsManager.getRoleOperateNum(playerId).getInvestIntegral() + investment.getCardIntegral());

                data.put("cardType", CardType.investment);

                data.put("cardInfo", investment);

                //增加遇到的投资交易次数
                //manageWealthService.updateAllInvestmentNumber(role.getGameSettleInfo().getManageWealth());

                break;

            //内圈命运
            case CardType.inFate:

                int inFateCardId = getCardFromCardGroup(cardGroup.getInnerFateGroup(), CardType.inFate);

                //记录遇到的金融风暴的次数
                if (inFateCardId == CardId.FINANCIAL_CRISIS) {
                    GameStatisticsManager.getRoleOperateNum(playerId).setMeetFinancialCrisisNum(GameStatisticsManager.getRoleOperateNum(playerId).getMeetFinancialCrisisNum() + 1);
                }

                //记录遇到的离婚次数
                if (inFateCardId == CardId.DIVORCE) {
                    GameStatisticsManager.getRoleOperateNum(playerId).setMeetDivorceNum(GameStatisticsManager.getRoleOperateNum(playerId).getMeetDivorceNum() + 1);
                }

                //记录遇到审计的次数
                if (inFateCardId == CardId.ENCOUNTER_AUDIT) {
                    GameStatisticsManager.getRoleOperateNum(playerId).setMeetEncounterAuditNum(GameStatisticsManager.getRoleOperateNum(playerId).getMeetEncounterAuditNum());
                }

                InnerFate innerFate = (InnerFate) CardInfo.cardInfo.get(inFateCardId);

                data.put("cardType", CardType.inFate);

                data.put("cardInfo", innerFate);

                break;

            //内圈自由选择e
            case CardType.freeChoice:

                data.put("cardType", CardType.freeChoice);

                break;
            default:
                throw new RuntimeException("掷骰子出错");
        }

        data.put("placeByTurntable", placeByTurntable);

        logger.debug("当前卡牌类型" + data.get("cardType"));

        logger.debug(playerId + "当前在轮盘上的位置" + placeByTurntable);

        NettyMessage message = BuildResponseMessage.build(ProtoIds.ROLL_THE_DICE, playerId, 0, data);

        ClientConnectionManager.sendMessage(RoomManager.getPlayers(roomId), message);

        //初始化回合结束数据
        GameManager.updateRoundEndData(roomId, initRoundEndData(roomId));

        logger.debug("摇到的总点数为:" + sum);

        logger.debug("弹出的卡牌类型:" + cardType);

        logger.debug("经过结账日的次数:" + closingDateNumber);
    }

    /**
     * 单人回合结束
     *
     * @param roomId
     * @param playerId
     */
    public void roundEnd(String roomId, String playerId) {

        RoleInfo roleInfo = GameManager.getRoleInfo(roomId, playerId);

        Map<String, Object> data = new HashMap<String, Object>();

        int ifPromotion = 1;

        int nonLaborIncome = roleInfo.getRoleDataManageInfo().getNonLaborIncome();

        int totalSpending = roleInfo.getRoleDataManageInfo().getTotalSpending();

        //下一个玩家id
        String nextRoleId = null;

        if (nonLaborIncome > totalSpending) {
            if (roleInfo.getRoleDebtsInfo().getAddNewDebt().size() > 0) {
                ifPromotion = 2;
                nextRoleId = getCurrentRole(roomId);
                data.put("currentRole", nextRoleId);
            } else {
                ifPromotion = 0;
                initInRouletteInfo(roomId, playerId);
            }
        } else {
            ifPromotion = 1;
            nextRoleId = getCurrentRole(roomId);
            data.put("currentRole", nextRoleId);
        }

        //是否添加掉线的人
        addGameing(roomId);

        //是否胜利
        int ifSuccess = 1;

        //获取积分信息
        RoleIntegralRecord roleIntegral = roleInfo.getRoleIntegralRecord();

        if (roleIntegral.getFlowCashTotalIntegral() >= FLOW_CASH_TOTAL_INTEGRAL && roleIntegral.getTimeTotalIntegral() >= TIME_TOTAL_INTEGRAL && roleIntegral.getQualityTotalIntegral() >= QUALITY_TOTAL_INTEGRAL) {
            if (roleInfo.getRoleDebtsInfo().getAddNewDebt().size() > 0) {
                ifSuccess = 2;
            } else {
                SuccessDataManager.getSuccessData(roomId).put(playerId, true);
                List<String> successPlayer = new ArrayList<>();
                List<String> failPlayer = new ArrayList<>();
                for (Map.Entry<String, Boolean> entry : SuccessDataManager.getSuccessData(roomId).entrySet()) {
                    if (entry.getValue()) {
                        successPlayer.add(entry.getKey());
                    }else {
                        failPlayer.add(entry.getKey());
                    }
                }
                if (successPlayer.size() == 3) {
                    ifSuccess = 0;
                    for (String player:successPlayer){
                        //记录单个玩家的记录
                        gameStatisticsHandler.addRecord(player, roomId);
                        //更新玩家的胜利场次
                        gameStatisticsHandler.updateWin(player, roomId);
                    }
                    //更新失败玩家的失败场次
                    gameStatisticsHandler.updateFail(failPlayer.get(0), roomId);
                    gameStatisticsHandler.addRecord(failPlayer.get(0),roomId);
                }
            }
        } else {
            ifSuccess = 1;
        }

        data.put("ifPromotion", ifPromotion);

        data.put("ifSuccess", ifSuccess);

        data.put("roleIntegral", gameStatisticsHandler.getRoleIntegral(roomId));

        //返回游戏胜利的数据
        data.put("successData", SuccessDataManager.getSuccessData(roomId));

        NettyMessage message = BuildResponseMessage.build(ProtoIds.ROUND_END, playerId, MessageStatus.SUCCESS, data);

        ClientConnectionManager.sendMessage(RoomManager.getPlayers(roomId), message);

        if (ifSuccess == 0) {
            logger.debug(roomId + "游戏结束房间解散");

            List<String> players = RoomManager.getPlayers(roomId);

            //更新玩家状态为在线
            for (String id : players) {
                ClientConnectionManager.updateStatus(id, ConnectionStatus.ON_LINE);
            }

            //移除缓存时间开始节点
            GameStatisticsManager.removeTime(roomId);

            //移除玩家的操作记录
            GameStatisticsManager.removeRoleOperateNum(roomId);

            //游戏解散
            GameManager.removeGame(roomId);

            //房间解散
            RoomManager.removeRoom(roomId);
        }
        logger.debug(playerId + "玩家单人回合结束");
    }

    /**
     * 多人回合结束
     *
     * @param roomId
     * @param playerId
     * @param status
     * @param nowPlayerId
     */
    public void allRoundEnd(String roomId, String playerId, int status, String nowPlayerId) {

        GameManager.getRoundEndData(roomId).put(playerId, true);

        ClientConnectionManager.sendMessage(RoomManager.getPlayers(roomId), BuildResponseMessage.build(ProtoIds.ALL_ROUND_END, playerId, MessageStatus.SUCCESS, null));

        //检测是否所有人的回合都已经结束
        logger.debug("房间当前的人数" + GameManager.getRoundEndData(roomId));

        for (Map.Entry<String, Boolean> entry : GameManager.getRoundEndData(roomId).entrySet()) {

            if (!entry.getValue()) {
                return;
            }
        }

        Map<String, Object> data = new HashMap<String, Object>(16);

        int ifPromotion = -1;

        logger.debug("状态参数" + status);

        RoleInfo roleInfo = GameManager.getRoleInfo(roomId, nowPlayerId);

        int nonLaborIncome = roleInfo.getRoleDataManageInfo().getNonLaborIncome();

        int totalSpending = roleInfo.getRoleDataManageInfo().getTotalSpending();

        String nextRoleId = null;
        if (nonLaborIncome > totalSpending) {
            if (roleInfo.getRoleDebtsInfo().getAddNewDebt().size() > 0) {
                ifPromotion = 2;
                nextRoleId = getCurrentRole(roomId);
                data.put("currentRole", nextRoleId);
            } else {
                ifPromotion = 0;
                initInRouletteInfo(roomId, nowPlayerId);
            }
        } else {
            nextRoleId = getCurrentRole(roomId);
            ifPromotion = 1;
            data.put("currentRole", nextRoleId);
        }

        //是否加入断线玩家（在进内圈初始化后加断线玩家）
        addGameing(roomId);

        data.put("nowPlayerId", nowPlayerId);

        data.put("ifPromotion", ifPromotion);

        ClientConnectionManager.sendMessage(RoomManager.getPlayers(roomId), BuildResponseMessage.build(ProtoIds.ALL_ROUND_END, "", 1, data));

        GameManager.getGame(roomId).setCurrentCardType(0);

        //重置所有玩家的回合结束标志
        GameManager.updateRoundEndData(roomId, initRoundEndData(roomId));

        logger.debug(playerId + "多人回合结束");

    }

    /**
     * 获取借贷模块信息
     *
     * @param roomId
     * @param playerId
     */
    public void getLoanModuleInfo(String roomId, String playerId) {

        RoleInfo roleInfo = GameManager.getRoleInfo(roomId, playerId);

        //获取角色贷款信息
        RoleLoanInfo roleLoanInfo = roleInfo.getRoleLoanInfo();

        //获取角色负债信息
        RoleDebtsInfo debtsInfo = roleInfo.getRoleDebtsInfo();

        Map<String, Object> data = new HashMap<String, Object>();

        //角色贷款信息
        data.put("roleLoanInfo", roleLoanInfo);

        //角色负债信息
        data.put("roleBasicDebtInfo", debtsInfo.getBasicDebt().values());

        //角色新增负债信息
        data.put("roleAddNewDebtInfo", debtsInfo.getAddNewDebt().values());

        //贷款记录
        data.put("roleLoanRecordInfo", roleInfo.getRoleLoanRecordInfo());

        NettyMessage message = BuildResponseMessage.build(ProtoIds.GET_LOAN_MODULE_INFO, playerId, MessageStatus.SUCCESS, data);

        ClientConnectionManager.sendMessage(playerId, message);
    }

    /**
     * 获取目标模块信息
     *
     * @param roomId
     * @param playerId
     * @param targetPlayerId
     */
    public void getTargetModuleInfo(String roomId, String playerId, String targetPlayerId) {

        RoleInfo roleInfo = GameManager.getRoleInfo(roomId, targetPlayerId);

        //获取角色数据信息
        RoleDataManageInfo roleDataManageInfo = roleInfo.getRoleDataManageInfo();

        //获取角色积分记录
        RoleIntegralRecord roleIntegralRecord = roleInfo.getRoleIntegralRecord();

        Map<String, Object> data = new HashMap<String, Object>();

        //非劳务收入
        data.put("totalNonLaborIncome", roleDataManageInfo.getNonLaborIncome());

        //积分记录
        data.put("integralRecord", roleIntegralRecord);

        //目标人id
        data.put("targetPlayerId", targetPlayerId);

        NettyMessage message = BuildResponseMessage.build(ProtoIds.GET_TARGET_MODULE_INFO, playerId, MessageStatus.SUCCESS, data);

        ClientConnectionManager.sendMessage(playerId, message);

    }

    /**
     * 获取资产和收入模块
     *
     * @param roomId
     * @param playerId
     * @param targetPlayerId
     */
    public void getAssetAndIncomeModuleInfo(String roomId, String playerId, String targetPlayerId) {

        RoleInfo roleInfo = GameManager.getRoleInfo(roomId, targetPlayerId);

        //角色拥有资产信息
        RoleHaveAssetInfo roleHaveAssetInfo = roleInfo.getRoleHaveAssetInfo();

        //角色收入信息
        RoleIncomeInfo roleIncomeInfo = roleInfo.getRoleIncomeInfo();

        Map<String, Object> data = new HashMap<String, Object>();

        data.put("roleHaveAssetInfo", roleHaveAssetInfo);

        data.put("roleIncomeInfo", roleIncomeInfo);

        //目标人id
        data.put("targetPlayerId", targetPlayerId);

        NettyMessage message = BuildResponseMessage.build(ProtoIds.GET_ASSET_AND_INCOME_MODULE_INFO, playerId, MessageStatus.SUCCESS, data);

        ClientConnectionManager.sendMessage(playerId, message);

    }

    /**
     * 获取负债和支出信息模块
     *
     * @param roomId
     * @param playerId
     * @param targetPlayerId
     */
    public void getDebtAndSpendModuleInfo(String roomId, String playerId, String targetPlayerId) {

        RoleInfo roleInfo = GameManager.getRoleInfo(roomId, targetPlayerId);

        //角色负债信息
        RoleDebtsInfo roleDebtsInfo = roleInfo.getRoleDebtsInfo();

        //角色支出信息
        RoleSpendInfo roleIncomeInfo = roleInfo.getRoleSpendInfo();

        Map<String, Object> data = new HashMap<String, Object>();

        //目标人id
        data.put("targetPlayerId", targetPlayerId);

        data.put("roleBasicDebtInfo", roleDebtsInfo.getBasicDebt().values());

        data.put("roleAddNewDebtInfo", roleDebtsInfo.getAddNewDebt().values());

        data.put("roleBasicSpendInfo", roleIncomeInfo.getBasicSpend().values());

        data.put("roleAddNewSpendInfo", roleIncomeInfo.getAddNewSpend().values());

        NettyMessage message = BuildResponseMessage.build(ProtoIds.GET_DEPT_AND_SPEND_MODULE_INFO, playerId, MessageStatus.SUCCESS, data);

        ClientConnectionManager.sendMessage(playerId, message);

    }

    /**
     * 获取出售记录模块
     *
     * @param roomId
     * @param playerId
     * @param targetPlayerId
     */
    public void getSellRecordModuleInfo(String roomId, String playerId, String targetPlayerId) {

        RoleInfo roleInfo = GameManager.getRoleInfo(roomId, targetPlayerId);

        //获取出售记录
        RoleSellAssetRecord roleSellAssetRecord = roleInfo.getRoleSellAssetRecord();

        Map<String, Object> data = new HashMap<String, Object>();

        //目标人id
        data.put("targetPlayerId", targetPlayerId);

        data.put("roleSellAssetRecord", roleSellAssetRecord);

        NettyMessage message = BuildResponseMessage.build(ProtoIds.GET_SELL_RECORD_MODULE_INFO, playerId, MessageStatus.SUCCESS, data);

        ClientConnectionManager.sendMessage(playerId, message);

    }

    /**
     * 获取结算模块信息
     *
     * @param roomId
     * @param playerId
     * @param targetPlayerId
     */
    public void getClosingModuleInfo(String roomId, String playerId, String targetPlayerId) {

        RoleInfo roleInfo = GameManager.getRoleInfo(roomId, targetPlayerId);

        //获取总收入
        int totalIncome = roleInfo.getRoleDataManageInfo().getTotalIncome();

        //总支出
        int totalSpend = roleInfo.getRoleDataManageInfo().getTotalSpending();

        //结算金额
        int closingDateMoney = roleInfo.getRoleDataManageInfo().getClosingDateMoney();

        Map<String, Object> data = new HashMap<String, Object>();

        data.put("closingDateMoney", closingDateMoney);

        int outOrIn = GameManager.getRoleLocationData(roomId).get(playerId).getOutOrIn();

        if (outOrIn == 0) {

            data.put("totalIncome", totalIncome);

            data.put("totalSpend", totalSpend);

        } else {

//            data.put("initFlowCash",roleAgent.getRole().getRoleDataManageInfo().getInitCashFlow());
//
//            data.put("flowCash",roleAgent.getRole().getRoleDataManageInfo().getCashFlow());

            data.put("totalIncome", roleInfo.getRoleDataManageInfo().getInitCashFlow() + roleInfo.getRoleDataManageInfo().getCashFlow());

            data.put("totalSpend", totalSpend);
        }

        //目标人id
        data.put("targetPlayerId", targetPlayerId);

        NettyMessage message = BuildResponseMessage.build(ProtoIds.GET_CLOSING_MODULE_INFO, playerId, MessageStatus.SUCCESS, data);

        ClientConnectionManager.sendMessage(playerId, message);

    }

    /**
     * 借款
     *
     * @param roomId
     * @param playerId
     * @param loanInfo
     */
    public void loan(String roomId, String playerId, JSONObject loanInfo) {

        RoleInfo roleInfo = GameManager.getRoleInfo(roomId, playerId);

        //获取银行贷款
        int bankMoney = loanInfo.getInteger("bankMoney");

        //获取信用卡贷款
        int creditMoney = loanInfo.getInteger("creditMoney");

        int outOrIn = GameManager.getRoleLocationData(roomId).get(playerId).getOutOrIn();

        roleLoanInfoHandler.loan(roleInfo, bankMoney, creditMoney, outOrIn);

        Map<String, Object> data = new HashMap<String, Object>();

        data.put("roleDataManageInfo", roleInfo.getRoleDataManageInfo());

        data.put("roleLoanInfo", roleInfo.getRoleLoanInfo());

        data.put("roleBasicDebtInfo", roleInfo.getRoleDebtsInfo().getBasicDebt().values());

        data.put("roleAddNewDebtInfo", roleInfo.getRoleDebtsInfo().getAddNewDebt().values());

        data.put("roleLoanRecordInfo", roleInfo.getRoleLoanRecordInfo());

        NettyMessage message = BuildResponseMessage.build(ProtoIds.LOAN, playerId, MessageStatus.SUCCESS, data);

        ClientConnectionManager.sendMessage(RoomManager.getPlayers(roomId), message);

        logger.debug(playerId + "进行了贷款,贷款信息--- 银行贷款:" + bankMoney + "  信用卡贷款:" + creditMoney);
    }

    /**
     * 还款
     *
     * @param roomId
     * @param playerId
     * @param repayInfo
     */
    public void repay(String roomId, String playerId, JSONArray repayInfo) {

        RoleInfo roleInfo = GameManager.getRoleInfo(roomId, playerId);

        int outOrIn = GameManager.getRoleLocationData(roomId).get(playerId).getOutOrIn();

        int status = 0;

        for (int i = 0; i < repayInfo.size(); i++) {

            status = roleLoanInfoHandler.repay(roleInfo, repayInfo.getJSONObject(i).getInteger("repayType"), repayInfo.getJSONObject(i).getString("repayName"), outOrIn);

            if (status != 0) {
                break;
            }
        }

        Map<String, Object> data = new HashMap<String, Object>();

        data.put("roleDataManageInfo", roleInfo.getRoleDataManageInfo());

        data.put("roleLoanInfo", roleInfo.getRoleLoanInfo());

        data.put("roleBasicDebtInfo", roleInfo.getRoleDebtsInfo().getBasicDebt().values());

        data.put("roleAddNewDebtInfo", roleInfo.getRoleDebtsInfo().getAddNewDebt().values());

        NettyMessage message = BuildResponseMessage.build(ProtoIds.REPAY, playerId, status, data);

        ClientConnectionManager.sendMessage(RoomManager.getPlayers(roomId), message);
    }

    /**
     * 购买小机会资产卡牌
     *
     * @param roomId
     * @param playerId
     * @param cardId
     */
    public void buySmallChanceAssetCard(String roomId, String playerId, int cardId) {

        //购买小机会资产卡牌
        int buySmallChanceAssetNum = GameStatisticsManager.getRoleOperateNum(playerId).getBuySmallChanceAssetNum() + 1;
        GameStatisticsManager.getRoleOperateNum(playerId).setBuySmallChanceAssetNum(buySmallChanceAssetNum);

        NettyMessage message = null;

        RoleInfo roleInfo = GameManager.getRoleInfo(roomId, playerId);

        //查询玩家购买的卡牌信息
        OuterSmallChance outerSmallChance = (OuterSmallChance) CardInfo.cardInfo.get(cardId);

        //花费的金钱
        int spendMoney = outerSmallChance.getDownPayment();

        //判断玩家是否可以购买该卡牌
        if (roleInfo.getRoleDataManageInfo().getCash() >= Math.abs(spendMoney)) {

            //roleDataManageInfoService.updateRoleCash(role.getRoleDataManageInfo(),spendMoney);

            roleHaveAssetInfoHandler.addSmallChanceAsset(roleInfo, outerSmallChance);

            Map<String, Object> data = new HashMap<String, Object>();

            data.put("roleDataManageInfo", roleInfo.getRoleDataManageInfo());

            data.put("roleHaveAssetInfo", roleInfo.getRoleHaveAssetInfo());

            message = BuildResponseMessage.build(ProtoIds.BUY_SMALL_CHANCE_ASSET_CARD, playerId, 0, data);

        } else {
            //金币不够
            message = BuildResponseMessage.build(ProtoIds.BUY_SMALL_CHANCE_ASSET_CARD, playerId, -1, null);
        }

        ClientConnectionManager.sendMessage(RoomManager.getPlayers(roomId), message);

        //增加购买小机会的次数
        //createWealthService.updateBuySmallChanceNumber(role.getGameSettleInfo().getCreateWealth());

        logger.debug(playerId + "玩家购买了卡牌,卡牌id:" + cardId);
    }

    /**
     * 购买小机会股票卡牌
     *
     * @param roomId
     * @param playerId
     * @param cardId
     * @param cardNumber
     */
    public void buySmallChanceStockCard(String roomId, String playerId, int cardId, int cardNumber) {

        //购买小机会股票卡牌次数
        int buySmallChanceStockNum = GameStatisticsManager.getRoleOperateNum(playerId).getBuySmallChanceStockNum() + 1;
        GameStatisticsManager.getRoleOperateNum(playerId).setBuySmallChanceStockNum(buySmallChanceStockNum);

        NettyMessage message = null;

        RoleInfo roleInfo = GameManager.getRoleInfo(roomId, playerId);

        //查询玩家购买的卡牌信息
        OuterStock outerStock = (OuterStock) CardInfo.cardInfo.get(cardId);

        outerStock.setStockNumber(cardNumber);

        //花费的金钱
        int spendMoney = outerStock.getTodayPrice() * cardNumber;

        //判断玩家是否可以购买该卡牌
        if (roleInfo.getRoleDataManageInfo().getCash() >= Math.abs(spendMoney)) {

            roleHaveAssetInfoHandler.addStock(roleInfo, outerStock, cardNumber);

            Map<String, Object> data = new HashMap<String, Object>();

            data.put("roleDataManageInfo", roleInfo.getRoleDataManageInfo());

            data.put("roleHaveAssetInfo", roleInfo.getRoleHaveAssetInfo());

            message = BuildResponseMessage.build(ProtoIds.BUY_SMALL_CHANCE_STOCK_CARD, playerId, 0, data);

        } else {
            //金币不够
            message = BuildResponseMessage.build(ProtoIds.BUY_SMALL_CHANCE_STOCK_CARD, playerId, -1, null);
        }

        ClientConnectionManager.sendMessage(RoomManager.getPlayers(roomId), message);

        logger.debug(playerId + "玩家购买了卡牌,卡牌id:" + cardId);
    }

    /**
     * 购买大机会卡牌
     *
     * @param roomId
     * @param playerId
     * @param cardId
     */
    public void buyBigChanceCard(String roomId, String playerId, int cardId) {

        NettyMessage message = null;

        RoleInfo roleInfo = GameManager.getRoleInfo(roomId, playerId);

        //查询玩家购买的卡牌信息
        OuterBigChance outerBigChance = (OuterBigChance) CardInfo.cardInfo.get(cardId);

        //花费的金钱
        int spendMoney = outerBigChance.getDownPayment();

        //判断玩家是否可以购买该卡牌
        if (roleInfo.getRoleDataManageInfo().getCash() > Math.abs(spendMoney)) {

            //购买大机会卡牌的次数
            int buyBigChanceNum = GameStatisticsManager.getRoleOperateNum(playerId).getBuyBigChanceNum() + 1;
            GameStatisticsManager.getRoleOperateNum(playerId).setBuyBigChanceNum(buyBigChanceNum);

            roleHaveAssetInfoHandler.addBigChanceAsset(roleInfo, outerBigChance);

            Map<String, Object> data = new HashMap<String, Object>();

            data.put("roleDataManageInfo", roleInfo.getRoleDataManageInfo());

            data.put("roleHaveAssetInfo", roleInfo.getRoleHaveAssetInfo());

            message = BuildResponseMessage.build(ProtoIds.BUY_BIG_CHANCE_CARD, playerId, 0, data);

        } else {
            //金币不够
            message = BuildResponseMessage.build(ProtoIds.BUY_BIG_CHANCE_CARD, playerId, -1, null);
        }

        ClientConnectionManager.sendMessage(RoomManager.getPlayers(roomId), message);
        //增加购买大机会的次数
        //createWealthService.updateBuyBigChanceNumber(role.getGameSettleInfo().getCreateWealth());

        logger.debug(playerId + "玩家购买了卡牌,卡牌id:" + cardId);
    }

    /**
     * 购买外圈风险卡牌
     *
     * @param roomId
     * @param playerId
     * @param cardId
     * @param freeChoice
     */
    public void buyOuterRiskCard(String roomId, String playerId, int cardId, boolean freeChoice) {

        RoleInfo roleInfo = GameManager.getRoleInfo(roomId, playerId);

        //封装返回信息
        Map<String, Object> data = new HashMap<String, Object>();

        data.put("ifSuspended", 0);

//        //碰见此卡牌需要停赛一轮
//        if (cardId == 10045) {
//
//            roleInfo.setIfSuspension(true);
//
//            data.put("ifSuspended", 1);
//
//            //增加遇到的失业次数
//            //beyondWealthService.updateBuyUnemploymentNumber(role.getGameSettleInfo().getBeyondWealth());
//
//        } else {
//
//            data.put("ifSuspended", 0);
//
//        }

        //获取风险卡牌的信息
        OuterRisk risk = (OuterRisk) CardInfo.cardInfo.get(cardId);

        //花费的资金
        int spendMoney = 0;

        logger.debug("玩家的现金:" + roleInfo.getRoleDataManageInfo().getCash());

        //是否自由选择了
        if (freeChoice) {

            spendMoney = risk.getMoneyOne() + risk.getMoneyTwo();

        } else {

            spendMoney = risk.getMoneyOne();

        }
        logger.debug("所要花费的金币:" + spendMoney);

        if (freeChoice) {

            //更新角色积分 1 时间积分 2品质积分
            if (risk.getIntegralType() == 1) {

                //增加积分记录
                Integral integral = new Integral();
                integral.setName(risk.getName());
                integral.setIntegral(risk.getIntegralValue());
                roleIntegralRecordHandler.addTimeIntegralRecord(roleInfo, integral);

            } else if (risk.getIntegralType() == 2) {
                //增加积分记录
                Integral integral = new Integral();
                integral.setName(risk.getName());
                integral.setIntegral(risk.getIntegralValue());
                roleIntegralRecordHandler.addQualityIntegralRecord(roleInfo, integral);

            }

        }
        //更新玩家的现金
        roleDataManageInfoHandler.updateRoleCash(roleInfo.getRoleDataManageInfo(), spendMoney);

        logger.debug("更新后的金币:" + roleInfo.getRoleDataManageInfo().getCash());

        data.put("roleDataManageInfo", roleInfo.getRoleDataManageInfo());

        //构建消息
        NettyMessage message = BuildResponseMessage.build(ProtoIds.BUY_RISK_CARD, playerId, 0, data);

        ClientConnectionManager.sendMessage(RoomManager.getPlayers(roomId), message);
        logger.debug(playerId + "玩家购买了卡牌,卡牌id:" + cardId);

    }

    /**
     * 购买命运卡牌
     *
     * @param roomId
     * @param playerId
     * @param cardId
     */
    public void buyOuterFateCard(String roomId, String playerId, int cardId) {

//        //获取游戏
//        Game game = Group.gameFightGroup.get(roomId);
//
//        //获取角色代理
//        RoleAgent roleAgent = game.getRoleAgent().get(playerId);
//
//        //获取命运卡牌信息
//        OuterFate fate = (OuterFate) CardInfo.cardInfo.get(cardId);
//
//        //获取非劳务收入的变化
//        int laborIncome = fate.getNonLobarIncomeChange();
//
//        //获取角色的大机会资产信息
//        List<OuterBigChance> bigChances = roleAgent.getRole().getRoleHaveAssetInfo().getBigChances();
//
//        Map<String, Object> data = new HashMap<String, Object>();
//
//        if (bigChances.contains((OuterBigChance) CardInfo.cardInfo.get(fate.getRelevanceId()))) {
//            for (OuterBigChance outerBigChance : bigChances) {
//                if (outerBigChance.getId() == fate.getId()) {
//
//                    roleService.updateNonLaborIncome(roleAgent.getRole(), laborIncome);
//
//                    List<OuterBigChance> bigChanceList = roleAgent.getRole().getRoleIncomeInfo().getBigChanceNonLaborIncome();
//
//                    for (OuterBigChance outerBigChance1 : bigChanceList) {
//                        if (outerBigChance1.getId() == fate.getId()) {
//                            outerBigChance1.setNonLaborIncome(outerBigChance1.getNonLaborIncome() + laborIncome);
//                        }
//                    }
//
//                }
//            }
//            data.put("roleDataManageInfo", roleAgent.getRole().getRoleDataManageInfo());
//
//            data.put("roleHaveAssetInfo", roleAgent.getRole().getRoleHaveAssetInfo());
//
//            data.put("roleIncomeInfo", roleAgent.getRole().getRoleIncomeInfo());
//
//            data.put("spendMoney", laborIncome);
//
//            SendMessage.sendMessageToPlayerGroup(getNeedSendMessagePlayers(game.getRoleAgent()), BuildResponseMessage.build(ProtoIds.BUY_OUTER_FATE_CARD, playerId, 0, data));
//
//        } else {
//            SendMessage.sendMessageToPayer(playerId, BuildResponseMessage.build(ProtoIds.BUY_OUTER_FATE_CARD, playerId, -1, null));
//        }


    }

    /**
     * 购买慈善卡牌
     *
     * @param roomId
     * @param playerId
     * @param cardId
     */
    public void buyCharityCard(String roomId, String playerId, int cardId) {

        NettyMessage message = rollThreeDice(roomId, playerId, cardId, ProtoIds.BUY_CHARITY_CARD);

        ClientConnectionManager.sendMessage(RoomManager.getPlayers(roomId), message);

        logger.debug(playerId + "购买了慈善卡牌");

        //增加购买的慈善事业卡牌
        //useWealthService.updateBuyCharity(role.getGameSettleInfo().getUseWealth());

    }

    /**
     * 购买学习进修卡牌
     *
     * @param roomId
     * @param playerId
     * @param cardId
     */
    public void buyStudyCard(String roomId, String playerId, int cardId) {

        NettyMessage message = rollThreeDice(roomId, playerId, cardId, ProtoIds.BUY_STUDY_CARD);

        ClientConnectionManager.sendMessage(RoomManager.getPlayers(roomId), message);

        logger.debug(playerId + "购买了进修学习卡牌");
    }

    /**
     * 购买健康管理卡牌
     *
     * @param roomId
     * @param playerId
     * @param cardId
     */
    public void buyHealthCard(String roomId, String playerId, int cardId) {

        //记录购买将康管理的次数
        GameStatisticsManager.getRoleOperateNum(playerId).setBuyHealthNum(GameStatisticsManager.getRoleOperateNum(playerId).getBuyHealthNum() + 1);
        NettyMessage message = rollThreeDice(roomId, playerId, cardId, ProtoIds.BUY_HEALTH_CARD);

        ClientConnectionManager.sendMessage(RoomManager.getPlayers(roomId), message);

        logger.debug(playerId + "玩家购买了健康管理卡牌");
    }

    /**
     * 选择机会
     *
     * @param roomId
     * @param playerId
     * @param cardType
     */
    public void choiceChance(String roomId, String playerId, int cardType) {

        //获取卡组
        CardGroup cardGroup = GameManager.getCardGroup(roomId);

        Map<String, Object> data = new HashMap<String, Object>();

        switch (cardType) {

            case CardType.smallChance:

                //从小机会卡组抽取卡牌
                int smallChanceCardId = getCardFromCardGroup(cardGroup.getOuterSmallChanceGroup(), CardType.smallChance);

                //查询卡牌信息
                if (smallChanceCardId > 20000 && smallChanceCardId < 29999) {
                    //遇到小机会资产卡牌
                    int meetSmallChanceAssetNum = GameStatisticsManager.getRoleOperateNum(playerId).getMeetSmallChanceAssetNum() + 1;
                    GameStatisticsManager.getRoleOperateNum(playerId).setMeetSmallChanceAssetNum(meetSmallChanceAssetNum);

                    //资产
                    OuterSmallChance outerSmallChance = (OuterSmallChance) CardInfo.cardInfo.get(smallChanceCardId);

                    //记录卡牌积分
                    GameStatisticsManager.getRoleOperateNum(playerId).setSmallChanceIntegral(GameStatisticsManager.getRoleOperateNum(playerId).getSmallChanceIntegral() + outerSmallChance.getCardIntegral());
                    data.put("cardType", CardType.smallChance_asset);
                    data.put("cardInfo", outerSmallChance);

                } else {

                    //遇到小机会股票次数
                    int meetSmallChanceStockNum = GameStatisticsManager.getRoleOperateNum(playerId).getMeetSmallChanceStockNum() + 1;
                    GameStatisticsManager.getRoleOperateNum(playerId).setMeetSmallChanceStockNum(meetSmallChanceStockNum);

                    //股票
                    OuterStock outerStock = (OuterStock) CardInfo.cardInfo.get(smallChanceCardId);

                    //记录卡牌积分
                    GameStatisticsManager.getRoleOperateNum(playerId).setSmallChanceIntegral(GameStatisticsManager.getRoleOperateNum(playerId).getSmallChanceIntegral() + outerStock.getCardIntegral());
                    data.put("cardType", CardType.smallChance_stock);
                    data.put("cardInfo", outerStock);
                }

                //增加遇到的小机会次数
                //createWealthService.updateAllSmallChanceNumber(role.getGameSettleInfo().getCreateWealth());

                break;

            case CardType.bigChance:

                //遇到大机会的次数
                int meetBigChanceNum = GameStatisticsManager.getRoleOperateNum(playerId).getMeetBigChanceNum() + 1;
                GameStatisticsManager.getRoleOperateNum(playerId).setMeetBigChanceNum(meetBigChanceNum);

                //从大机会卡组抽取卡牌
                int bigChanceCardId = getCardFromCardGroup(cardGroup.getOuterBigChanceGroup(), CardType.bigChance);
                OuterBigChance outerBigChance = (OuterBigChance) CardInfo.cardInfo.get(bigChanceCardId);

                //大机会卡牌
                GameStatisticsManager.getRoleOperateNum(playerId).setBigChanceIntegral(GameStatisticsManager.getRoleOperateNum(playerId).getBigChanceIntegral() + outerBigChance.getCardIntegral());
                data.put("cardType", CardType.bigChance);
                data.put("cardInfo", outerBigChance);

                //增加遇到的大机会次数
                // createWealthService.updateAllBigChanceNumber(role.getGameSettleInfo().getCreateWealth());

                break;

            case CardType.investment:

                int investmentCardId = getCardFromCardGroup(cardGroup.getInnerInvestmentGroup(), CardType.investment);
                InnerInvestment investment = (InnerInvestment) CardInfo.cardInfo.get(investmentCardId);
                data.put("cardType", CardType.investment);
                data.put("cardInfo", investment);
                break;

            case CardType.richRelax:

                int richRelaxCardId = getCardFromCardGroup(cardGroup.getInnerRelaxGroup(), CardType.richRelax);

                InnerRichRelax innerRichRelax = (InnerRichRelax) CardInfo.cardInfo.get(richRelaxCardId);

                data.put("cardType", CardType.richRelax);

                data.put("cardInfo", innerRichRelax);

                break;

            case CardType.qualityLife:

                int qualityLifeCardId = getCardFromCardGroup(cardGroup.getInnerQualityLifeGroup(), CardType.qualityLife);

                InnerQualityLife qualityLife = (InnerQualityLife) CardInfo.cardInfo.get(qualityLifeCardId);

                data.put("cardType", CardType.qualityLife);

                data.put("cardInfo", qualityLife);

                break;

            default:

                logger.error("选择大机会还是小机会错误,未知的卡牌类型");

                break;
        }

        //封装信息
        NettyMessage message = BuildResponseMessage.build(ProtoIds.CHOICE_CHANCE, playerId, 0, data);

        //通知玩家
        ClientConnectionManager.sendMessage(RoomManager.getPlayers(roomId), message);

    }

    /**
     * 有闲有钱卡牌
     *
     * @param roomId
     * @param playerId
     * @param cardId
     */
    public void buyRichRelaxCard(String roomId, String playerId, int cardId) {

        NettyMessage message = null;

        RoleInfo roleInfo = GameManager.getRoleInfo(roomId, playerId);

        //获取卡牌信息
        InnerRichRelax innerRichRelax = (InnerRichRelax) CardInfo.cardInfo.get(cardId);

        //所花费的金钱
        int spendMoney = innerRichRelax.getInvestmentPay();

        //获取流动现金
        int flowCash = innerRichRelax.getFlowCash();

        //获取时间积分
        int timeIntegral = innerRichRelax.getTimeIntegral();

        //获取角色数据信息
        RoleDataManageInfo roleDataManageInfo = roleInfo.getRoleDataManageInfo();

        //是否有足够的金钱购买
        if (roleDataManageInfo.getCash() > Math.abs(spendMoney)) {

            roleDataManageInfoHandler.updateRoleCash(roleDataManageInfo, spendMoney);

            //记录购买有钱有闲的次数
            GameStatisticsManager.getRoleOperateNum(playerId).setBuyRichAndIdleNum(GameStatisticsManager.getRoleOperateNum(playerId).getBuyRichAndIdleNum() + 1);

        } else {
            //金币不够
            message = BuildResponseMessage.build(ProtoIds.BUY_RICH_RELAX_CARD, playerId, -1, null);

            ClientConnectionManager.sendMessage(RoomManager.getPlayers(roomId), message);

            return;
        }

        //是否有流动现金
        if (flowCash != 0) {

            //增加流动现金记录
            Integral integral = new Integral();
            integral.setName(innerRichRelax.getName());
            integral.setIntegral(flowCash);

            roleIntegralRecordHandler.addFlowCashIntegralRecord(roleInfo, integral);

        }

        //是否有时间积分
        if (timeIntegral != 0) {

            //增加时间积分记录
            Integral integral = new Integral();
            integral.setName(innerRichRelax.getName());
            integral.setIntegral(timeIntegral);

            roleIntegralRecordHandler.addTimeIntegralRecord(roleInfo, integral);
        }

        Map<String, Object> data = new HashMap<String, Object>();

        data.put("roleDataManageInfo", roleDataManageInfo);

        message = BuildResponseMessage.build(ProtoIds.BUY_RICH_RELAX_CARD, playerId, 0, data);

        ClientConnectionManager.sendMessage(RoomManager.getPlayers(roomId), message);

        //增加购买的有钱有钱次数
        //useWealthService.updateBuyRichRelaxNumber(role.getGameSettleInfo().getUseWealth());
    }

    /**
     * 购买品质生活卡牌
     *
     * @param roomId
     * @param playerId
     * @param cardId
     */
    public void buyQualityLifeCard(String roomId, String playerId, int cardId) {

        NettyMessage message = null;

        RoleInfo roleInfo = GameManager.getRoleInfo(roomId, playerId);

        //获取角色数据信息
        RoleDataManageInfo roleDataMangeInfo = roleInfo.getRoleDataManageInfo();

        //获取品质生活卡牌
        InnerQualityLife innerQualityLife = (InnerQualityLife) CardInfo.cardInfo.get(cardId);

        //花费金钱
        int spendMoney = innerQualityLife.getConsumeMoney();

        //获取时间积分
        int timeIntegral = innerQualityLife.getTimeIntegral();

        //获取品质积分
        int qualityIntegral = innerQualityLife.getQualityIntegral();

        //是否有足够的金币购买
        if (roleDataMangeInfo.getCash() > Math.abs(spendMoney)) {

            //记录购买品质生活的次数
            GameStatisticsManager.getRoleOperateNum(playerId).setBuyQualityLifeNum(GameStatisticsManager.getRoleOperateNum(playerId).getBuyQualityLifeNum() + 1);

            roleDataManageInfoHandler.updateRoleCash(roleDataMangeInfo, spendMoney);

        } else {
            //金币不够
            message = BuildResponseMessage.build(ProtoIds.BUY_QUALITY_LIFE_CARD, playerId, -1, null);

            ClientConnectionManager.sendMessage(RoomManager.getPlayers(roomId), message);

            return;
        }

        //是否有时间积分
        if (timeIntegral != 0 && (roleDataMangeInfo.getTimeIntegral() - timeIntegral > 0)) {

            Integral integral = new Integral();
            integral.setName(innerQualityLife.getName());
            integral.setIntegral(timeIntegral);

            roleIntegralRecordHandler.addTimeIntegralRecord(roleInfo, integral);

        }

        //是否有品质积分
        if (qualityIntegral != 0) {

            Integral integral = new Integral();
            integral.setName(innerQualityLife.getName());
            integral.setIntegral(qualityIntegral);

            roleIntegralRecordHandler.addQualityIntegralRecord(roleInfo, integral);
        }

        Map<String, Object> data = new HashMap<String, Object>();

        data.put("roleDataManageInfo", roleDataMangeInfo);

        message = BuildResponseMessage.build(ProtoIds.BUY_QUALITY_LIFE_CARD, playerId, 0, data);

        ClientConnectionManager.sendMessage(RoomManager.getPlayers(roomId), message);

        //增加购买的品质生活次数
        //useWealthService.updateBuyQualityLifeNumber(role.getGameSettleInfo().getUseWealth());
    }

    /**
     * 购买投资卡牌
     *
     * @param roomId
     * @param playerId
     * @param cardId
     * @param point
     */
    public void buyInvestmentCard(String roomId, String playerId, int cardId, int point) {

        NettyMessage message = null;

        RoleInfo roleInfo = GameManager.getRoleInfo(roomId, playerId);

        //获取角色数据信息
        RoleDataManageInfo roleDataManageInfo = roleInfo.getRoleDataManageInfo();

        //获取投资卡牌
        InnerInvestment investment = (InnerInvestment) CardInfo.cardInfo.get(cardId);

        //获取花费金钱
        int spendMoney = investment.getInvestmentPay();

        //获取流动资金
        int flowCash = investment.getFlowCash();

        //获取是否需要掷骰子
        int ifRollDice = investment.getIfRollDice();

        //获取掷骰子满足的条件
        int diceConditions = investment.getDiceConditions();

        //获取掷骰子满足的值
        int diceNumber = investment.getDiceNumber();

        //是否有足够的金币购买
        if (roleDataManageInfo.getCash() >= Math.abs(spendMoney)) {

            //记录购买投资的次数
            RoleOperateNum roleOperateNum = GameStatisticsManager.getRoleOperateNum(playerId);
            roleOperateNum.setBuyInsuranceNum(roleOperateNum.getBuyInvestNum() + 1);

            //更新玩家的资金
            roleDataManageInfoHandler.updateRoleCash(roleDataManageInfo, spendMoney);

            //是否需要掷骰子   0不用 1用
            if (ifRollDice == 0) {

                Integral integral = new Integral();
                integral.setName(investment.getName());
                integral.setIntegral(flowCash);

                roleIntegralRecordHandler.addFlowCashIntegralRecord(roleInfo, integral);

                Map<String, Object> data = new HashMap<String, Object>();

                data.put("roleDataManageInfo", roleDataManageInfo);

                message = BuildResponseMessage.build(ProtoIds.BUY_INVESTMENT_CARD, playerId, 0, data);

            } else {

                //1.大于等于  2小于等于
                if (diceConditions == 1) {

                    //如果满足
                    if (point >= diceNumber) {

                        Integral integral = new Integral();
                        integral.setName(investment.getName());
                        integral.setIntegral(flowCash);

                        roleIntegralRecordHandler.addFlowCashIntegralRecord(roleInfo, integral);

                        Map<String, Object> data = new HashMap<String, Object>();

                        data.put("roleDataManageInfo", roleDataManageInfo);

                        message = BuildResponseMessage.build(ProtoIds.BUY_INVESTMENT_CARD, playerId, 0, data);

                    } else {

                        Map<String, Object> data = new HashMap<String, Object>();

                        data.put("roleDataManageInfo", roleDataManageInfo);

                        message = BuildResponseMessage.build(ProtoIds.BUY_INVESTMENT_CARD, playerId, -2, data);
                    }

                } else if (diceConditions == 2) {

                    //如果满足
                    if (point <= diceNumber) {

                        Integral integral = new Integral();
                        integral.setName(investment.getName());
                        integral.setIntegral(flowCash);

                        roleIntegralRecordHandler.addFlowCashIntegralRecord(roleInfo, integral);

                        Map<String, Object> data = new HashMap<String, Object>();

                        data.put("roleDataManageInfo", roleDataManageInfo);

                        message = BuildResponseMessage.build(ProtoIds.BUY_INVESTMENT_CARD, playerId, 0, data);

                    } else {

                        Map<String, Object> data = new HashMap<String, Object>();

                        data.put("roleDataManageInfo", roleDataManageInfo);

                        message = BuildResponseMessage.build(ProtoIds.BUY_INVESTMENT_CARD, playerId, -2, data);
                    }
                }
            }

        } else {
            //金币不够
            message = BuildResponseMessage.build(ProtoIds.BUY_BIG_CHANCE_CARD, playerId, -1, null);

        }

        ClientConnectionManager.sendMessage(RoomManager.getPlayers(roomId), message);
        //增加购买的投资交易次数
        //manageWealthService.updateBuyInvestmentNumber(role.getGameSettleInfo().getManageWealth());
    }

    /**
     * 购买内圈命运卡牌
     *
     * @param roomId
     * @param playerId
     * @param cardId
     * @param point
     */
    public void buyInnerFate(String roomId, String playerId, int cardId, int point) {

        NettyMessage message = null;

        RoleInfo roleInfo = GameManager.getRoleInfo(roomId, playerId);

        //获取角色数据信息
        RoleDataManageInfo roleDataManageInfo = roleInfo.getRoleDataManageInfo();

        //获取命运
        InnerFate innerFate = (InnerFate) CardInfo.cardInfo.get(cardId);

        //命运类型
        int type = innerFate.getType();

        //扣掉多少钱
        int spendMoney = 0;

        //1.需要掷骰子 3,不需要  --如果是女的则赔付资金的一半,男的赔付所有现金
        if (type == 1) {

            //获取条件的值
            int diceNumber = innerFate.getDiceNumber();

            switch (cardId) {

                //游戏软件开发公司股票  掷骰子满足6
                case 90007:

                    //是否满足条件,满足条件奖励50W金币,否则扣掉25W
                    if (point >= diceNumber) {

                        spendMoney = 500000;

                        roleDataManageInfoHandler.updateRoleCash(roleDataManageInfo, spendMoney);

                    } else {
                        spendMoney = -250000;
                        roleDataManageInfoHandler.updateRoleCash(roleDataManageInfo, spendMoney);
                    }

                    break;

                //生物技术环保公司股票  掷骰子满足5
                case 90008:

                    //是否满足条件,满足条件奖励50W金币,否则扣掉5W
                    if (point >= diceNumber) {

                        spendMoney = 500000;

                        roleDataManageInfoHandler.updateRoleCash(roleDataManageInfo, spendMoney);

                    } else {
                        spendMoney = -50000;
                        roleDataManageInfoHandler.updateRoleCash(roleDataManageInfo, spendMoney);
                    }
                    break;
                default:
                    throw new RuntimeException("获取卡牌id出错");
            }

            Map<String, Object> data = new HashMap<String, Object>();

            data.put("roleDataManageInfo", roleDataManageInfo);

            data.put("ifBuyInsurance", 0);

            message = BuildResponseMessage.build(ProtoIds.BUY_INNER_FATE_CARD, playerId, 0, data);

        } else if (type == 3) {

            //玩家是否买了保险
            if (GameManager.getInsuranceData(roomId).get(playerId)) {

                Map<String, Object> data = new HashMap<String, Object>();

                data.put("roleDataManageInfo", roleDataManageInfo);

                data.put("ifBuyInsurance", 1);

                //不扣现金
                message = BuildResponseMessage.build(ProtoIds.BUY_INNER_FATE_CARD, playerId, 0, data);

                //重置保险
                GameManager.getInsuranceData(roomId).put(playerId, false);

            } else {

                switch (cardId) {

                    //官司,赔付一半现金
                    case 90002:

                        spendMoney = (int) (roleDataManageInfo.getCash() * 0.5);

                        roleDataManageInfoHandler.updateRoleCash(roleDataManageInfo, -spendMoney);

                        break;

                    //金融风暴,赔付所有现金
                    case 90004:

                        GameStatisticsManager.getRoleOperateNum(playerId).setFinancialCrisisNum(GameStatisticsManager.getRoleOperateNum(playerId).getFinancialCrisisNum() + 1);

                        spendMoney = roleDataManageInfo.getCash();

                        roleDataManageInfoHandler.updateRoleCash(roleDataManageInfo, -spendMoney);

                        //更新遇到的金融风暴次数
                        //beyondWealthService.updateAllFinancialStormNumber(role.getGameSettleInfo().getBeyondWealth());

                        //更新购买的金融风暴次数
                        //beyondWealthService.updateBuyFinancialStormNumber(role.getGameSettleInfo().getBeyondWealth());

                        break;

                    //遭遇天灾,赔付一半现金
                    case 90005:

                        spendMoney = (int) (roleDataManageInfo.getCash() * 0.5);

                        roleDataManageInfoHandler.updateRoleCash(roleDataManageInfo, -spendMoney);

                        break;

                    //离婚,如果是女的赔付一半,如果是男的则赔付所有
                    case 90006:
                        //记录离婚次数
                        GameStatisticsManager.getRoleOperateNum(playerId).setDivorceNum(GameStatisticsManager.getRoleOperateNum(playerId).getDivorceNum() + 1);

                        //判断角色性别
                        if (roleInfo.getRoleBasicInfo().getGender() == 0) {

                            spendMoney = (int) (roleDataManageInfo.getCash() * 0.5);

                            roleDataManageInfoHandler.updateRoleCash(roleDataManageInfo, -spendMoney);

                        } else {

                            spendMoney = roleDataManageInfo.getCash();

                            roleDataManageInfoHandler.updateRoleCash(roleDataManageInfo, -spendMoney);
                        }

                        //更新遇到的离婚次数
                        //beyondWealthService.updateAllDivorceNumber(role.getGameSettleInfo().getBeyondWealth());

                        //更新购买的离婚次数
                        //beyondWealthService.updateBuyDivorceNumber(role.getGameSettleInfo().getBeyondWealth());
                        break;

                    //审计
                    case 90009:

                        //记录遭遇审计的次数
                        GameStatisticsManager.getRoleOperateNum(playerId).setEncounterAuditNum(GameStatisticsManager.getRoleOperateNum(playerId).getEncounterAuditNum() + 1);

                        spendMoney = (int) (roleDataManageInfo.getCash() * 0.5);

                        roleDataManageInfoHandler.updateRoleCash(roleDataManageInfo, -spendMoney);

                        //更新遇到的审计次数
                        //beyondWealthService.updateBuyAuditNumber(role.getGameSettleInfo().getBeyondWealth());

                        //更新购买的审计次数
                        //beyondWealthService.updateAllAuditNumber(role.getGameSettleInfo().getBeyondWealth());

                        break;
                    default:
                        throw new RuntimeException("获取卡牌id出错");
                }

                Map<String, Object> data = new HashMap<String, Object>();

                data.put("roleDataManageInfo", roleDataManageInfo);

                data.put("ifBuyInsurance", 0);

                //扣现金
                message = BuildResponseMessage.build(ProtoIds.BUY_INNER_FATE_CARD, playerId, 0, data);
            }
        }

        ClientConnectionManager.sendMessage(RoomManager.getPlayers(roomId), message);

    }

    /**
     * 结账日
     *
     * @param roomId
     * @param playerId
     */
    public void closingDate(String roomId, String playerId) {

        int outOrIn = GameManager.getGame(roomId).getRoleLocationData().get(playerId).getOutOrIn();
        if (outOrIn == 0) {
            //外圈结账日次数
            int outPayDayNum = GameStatisticsManager.getRoleOperateNum(playerId).getOutPayDayNum() + 1;
            GameStatisticsManager.getRoleOperateNum(playerId).setOutPayDayNum(outPayDayNum);

        } else if (outOrIn == 1) {

            //内圈结账日次数
            int inPayDayNum = GameStatisticsManager.getRoleOperateNum(playerId).getInPayDayNum() + 1;
            GameStatisticsManager.getRoleOperateNum(playerId).setInPayDayNum(inPayDayNum);
        }

        RoleInfo roleInfo = GameManager.getRoleInfo(roomId, playerId);

        //获取角色数据信息
        RoleDataManageInfo roleDataManageInfo = roleInfo.getRoleDataManageInfo();

        //获取结账日的金额
        int closingDateNumber = roleDataManageInfo.getClosingDateMoney();

        //更新玩家金钱
        roleDataManageInfoHandler.updateRoleCash(roleDataManageInfo, closingDateNumber);

        Map<String, Object> data = new HashMap<String, Object>();

        data.put("roleDataManageInfo", roleInfo.getRoleDataManageInfo());

        NettyMessage message = BuildResponseMessage.build(ProtoIds.CLOSING_DATE, playerId, 0, data);

        ClientConnectionManager.sendMessage(RoomManager.getPlayers(roomId), message);
    }

    /**
     * 晋级内圈成功
     *
     * @param roomId
     * @param playerId
     */
    public void promotionSuccess(String roomId, String playerId) {

        RoleInfo roleInfo = GameManager.getRoleInfo(roomId, playerId);

        Map<String, Object> data = new HashMap<String, Object>();

        data.put("roleDataManageInfo", roleInfo.getRoleDataManageInfo());

        String nextRole = getCurrentRole(roomId);

        data.put("currentRole", nextRole);

        NettyMessage message = BuildResponseMessage.build(ProtoIds.PROMOTION_SUCCESS, playerId, 0, data);
        ClientConnectionManager.sendMessage(RoomManager.getPlayers(roomId), message);
    }

    /**
     * 购买保险
     *
     * @param roomId
     * @param playerId
     */
    public void buyInsurance(String roomId, String playerId) {

        NettyMessage message = null;

        RoleInfo roleInfo = GameManager.getRoleInfo(roomId, playerId);

        //获取角色数据信息
        RoleDataManageInfo roleDataManageInfo = roleInfo.getRoleDataManageInfo();

        //判断是否购买了保险
        if (GameManager.getInsuranceData(roomId).get(playerId)) {

            message = BuildResponseMessage.build(ProtoIds.BUY_INSURANCE, playerId, 1, null);

        } else {

            //记录购买保险的次数
            RoleOperateNum roleOperateNum = GameStatisticsManager.getRoleOperateNum(playerId);
            roleOperateNum.setBuyInsuranceNum(roleOperateNum.getBuyInvestNum() + 1);

            GameManager.getInsuranceData(roomId).put(playerId, true);

            //更新现金
            roleDataManageInfoHandler.updateRoleCash(roleDataManageInfo, -300000);

            Map<String, Object> data = new HashMap<String, Object>();

            data.put("roleDataManageInfo", roleDataManageInfo);

            message = BuildResponseMessage.build(ProtoIds.BUY_INSURANCE, playerId, 0, data);
        }

        ClientConnectionManager.sendMessage(RoomManager.getPlayers(roomId), message);

        //增加购买保险的次数
        //manageWealthService.updateBuyInsuranceNumber(role.getGameSettleInfo().getManageWealth());

    }

    /**
     * 放弃卡牌
     *
     * @param roomId
     * @param playerId
     * @param cardId
     * @param cardType
     */
    public void giveUpCard(String roomId, String playerId, int cardId, int cardType) {

        switch (cardType) {

            //放弃小机会资产
            case CardType.smallChance_asset:
                //放弃小机会资产积分
                OuterSmallChance outerSmallChance = (OuterSmallChance) CardInfo.cardInfo.get(cardId);
                GameStatisticsManager.getRoleOperateNum(playerId).setSmallChanceIntegral(GameStatisticsManager.getRoleOperateNum(playerId).getSmallChanceIntegral() + outerSmallChance.getGiveUpIntegral());
                break;
            case CardType.smallChance_stock:
                //放弃小机会股票积分
                OuterStock outerStock = (OuterStock) CardInfo.cardInfo.get(cardId);
                GameStatisticsManager.getRoleOperateNum(playerId).setSmallChanceIntegral(GameStatisticsManager.getRoleOperateNum(playerId).getSmallChanceIntegral() + outerStock.getGiveUpIntegral());
                break;
            case CardType.bigChance:
                //放弃大机会卡牌积分
                OuterBigChance outerBigChance = (OuterBigChance) CardInfo.cardInfo.get(cardId);
                GameStatisticsManager.getRoleOperateNum(playerId).setBigChanceIntegral(GameStatisticsManager.getRoleOperateNum(playerId).getBigChanceIntegral() + outerBigChance.getGiveUpIntegral());
                break;
            case CardType.charity:
                break;
            case CardType.studying:
                break;
            case CardType.healthManage:
                break;
            case CardType.outFate:
                break;
            case CardType.richRelax:
                break;
            case CardType.qualityLife:
                break;
            case CardType.inFate:
                break;
            case CardType.investment:
                //记录放弃投资卡牌积分
                InnerInvestment innerInvestment = (InnerInvestment) CardInfo.cardInfo.get(cardId);
                GameStatisticsManager.getRoleOperateNum(playerId).setInvestIntegral(GameStatisticsManager.getRoleOperateNum(playerId).getInvestIntegral() + innerInvestment.getGiveUpIntegral());
                break;
            case CardType.innerStudying:
                break;
            case CardType.innerHealthManage:
                break;
            default:
                throw new RuntimeException("放弃卡牌出错");
        }

        Map<String, Object> data = new HashMap<String, Object>();

        data.put("cardType", cardType);

        NettyMessage message = BuildResponseMessage.build(ProtoIds.GIVE_UP_BUY_CARD, playerId, 0, data);

        ClientConnectionManager.sendMessage(RoomManager.getPlayers(roomId), message);
        logger.debug(playerId + "玩家放弃了卡牌,卡牌Id:" + cardId + " 卡牌类型:" + cardType);

    }

    /**
     * 出售资产(外圈命运)
     *
     * @param roomId
     * @param playerId
     * @param cardId
     * @param cardInfo
     */
    public void sellAssets(String roomId, String playerId, int cardId, JSONArray cardInfo) {

        logger.debug("玩家开始出售资产");

        //卖出资产数量
        int sellAssetNum = GameStatisticsManager.getRoleOperateNum(playerId).getSellAssetNum() + 1;
        GameStatisticsManager.getRoleOperateNum(playerId).setSellAssetNum(sellAssetNum);

        RoleInfo roleInfo = GameManager.getRoleInfo(roomId, playerId);

        OuterFate outerFate = (OuterFate) CardInfo.cardInfo.get(cardId);

        //获取处理的资产id
        int processId = outerFate.getRelevanceId();

        //卖价
        int sellPrice = outerFate.getProcessMoney();

        for (int i = 0; i < cardInfo.size(); i++) {

            //需要出售的资产id
            int sellCardId = cardInfo.getJSONObject(i).getInteger("cardId");

            if (sellCardId != processId) {
                return;
            }

            logger.debug("出售资产的id:" + sellCardId);

            int cardType = cardInfo.getJSONObject(i).getInteger("cardType");

            switch (cardType) {

                case CardType.smallChance_asset:

                    logger.debug("出售小资产...");

                    //获取处理的资产的信息
                    OuterSmallChance outerSmallChance = (OuterSmallChance) CardInfo.cardInfo.get(processId);

                    roleHaveAssetInfoHandler.removeSmallChanceAsset(roleInfo, outerSmallChance, sellPrice);

                    break;

                case CardType.bigChance:

                    logger.debug("出售大资产");

                    //获取处理的资产的信息
                    OuterBigChance outerBigChance = (OuterBigChance) CardInfo.cardInfo.get(processId);

                    roleHaveAssetInfoHandler.removeBigChanceAsset(roleInfo, outerBigChance, sellPrice);

                    break;
                default:
                    throw new RuntimeException("获取卡牌类型出错");
            }
        }

        Map<String, Object> data = new HashMap<String, Object>();

        data.put("roleDataManageInfo", roleInfo.getRoleDataManageInfo());

        data.put("roleHaveAssetInfo", roleInfo.getRoleHaveAssetInfo());

        NettyMessage message = BuildResponseMessage.build(ProtoIds.SELL_ASSET, playerId, 0, data);

        ClientConnectionManager.sendMessage(RoomManager.getPlayers(roomId), message);
    }

    /**
     * 出售股票
     *
     * @param roomId
     * @param playerId
     * @param cardId
     * @param cardInfo
     */
    public void sellStocks(String roomId, String playerId, int cardId, JSONArray cardInfo) {

        //记录卖出资产数
        GameStatisticsManager.getRoleOperateNum(playerId).setSellAssetNum(GameStatisticsManager.getRoleOperateNum(playerId).getSellAssetNum() + 1);

        RoleInfo roleInfo = GameManager.getRoleInfo(roomId, playerId);

        //获取卖的股票
        OuterStock outerStock = (OuterStock) CardInfo.cardInfo.get(cardId);

        //卖价
        int todayPrice = outerStock.getTodayPrice();

        for (int i = 0; i < cardInfo.size(); i++) {

            //出售卡牌的id
            int sellCardId = cardInfo.getJSONObject(i).getInteger("cardId");

            //出售数量
            int sellNumber = cardInfo.getJSONObject(i).getInteger("cardNumber");

            //出售卡牌的信息
            OuterStock stock = (OuterStock) CardInfo.cardInfo.get(sellCardId);

            roleHaveAssetInfoHandler.removeStock(roleInfo, stock, sellNumber, Math.abs(todayPrice));
        }

        Map<String, Object> data = new HashMap<String, Object>();

        data.put("roleDataManageInfo", roleInfo.getRoleDataManageInfo());

        data.put("roleHaveAssetInfo", roleInfo.getRoleHaveAssetInfo());


        NettyMessage message = BuildResponseMessage.build(ProtoIds.SELL_STOCKS, playerId, 0, data);

        ClientConnectionManager.sendMessage(RoomManager.getPlayers(roomId), message);

        //增加购买股票或者资产的次数
        //createWealthService.updateSellNumber(roleAgent.getRole().getGameSettleInfo().getCreateWealth());
    }

    /**
     * 慈善,进修,学习通用
     *
     * @param roomId
     * @param playerId
     * @param cardId
     * @param type
     * @return
     */
    private NettyMessage rollThreeDice(String roomId, String playerId, int cardId, int type) {

        NettyMessage message = null;

        RoleInfo roleInfo = GameManager.getRoleInfo(roomId, playerId);

        //获取玩家数据信息
        RoleDataManageInfo roleDataManageInfo = roleInfo.getRoleDataManageInfo();

        logger.debug("玩家的金币:" + roleDataManageInfo.getCash());

        //花费的金钱
        int spendMoney = 0;

        if (GameManager.getRoleLocationData(roomId).get(playerId).getOutOrIn() == 0) {

            spendMoney = (int) (roleDataManageInfo.getTotalIncome() * 0.1);

        } else {
            spendMoney = (int) (roleDataManageInfo.getClosingDateMoney() * 0.1);
        }

        logger.debug("花费的金币:" + spendMoney);

        if (roleDataManageInfo.getCash() > spendMoney) {

            //记录进修学习的次数
            GameStatisticsManager.getRoleOperateNum(playerId).setBuyStudyNum(GameStatisticsManager.getRoleOperateNum(playerId).getBuyStudyNum() + 1);

            //记录购买慈善的次数
            GameStatisticsManager.getRoleOperateNum(playerId).setBuyCharitableNum(GameStatisticsManager.getRoleOperateNum(playerId).getBuyCharitableNum() + 1);

            roleDataManageInfoHandler.updateRoleCash(roleDataManageInfo, -spendMoney);

            logger.debug(playerId + "玩家下次掷骰子3个");

            GameManager.getRoleRollDiceData(roomId).put(playerId, 3);

            Map<String, Object> data = new HashMap<String, Object>();

            data.put("roleDataManageInfo", roleDataManageInfo);

            message = BuildResponseMessage.build(type, playerId, 0, data);


        } else {
            //金币不够
            message = BuildResponseMessage.build(type, playerId, -1, null);
        }
        logger.debug("更新后的金币:" + roleDataManageInfo.getCash());
        return message;

    }

    /**
     * 购买生孩子卡牌
     *
     * @param roomId
     * @param playerId
     * @param ifFine
     */
    public void buyHaveChild(String roomId, String playerId, int ifFine) {

        logger.debug("购买生孩子...");

        //保存当前房间触发发红包的玩家
        GameManager.getGame(roomId).setTriggerRedEnvelopesRole(playerId);

        RoleInfo roleInfo = GameManager.getRoleInfo(roomId, playerId);

        //获取角色数据信息
        RoleDataManageInfo roleDataManageInfo = roleInfo.getRoleDataManageInfo();

        logger.debug("ifFine:" + ifFine);

        logger.debug("当前孩子的数量:" + roleDataManageInfo.getHaveChildNumber());

        NettyMessage message = null;

        //是否超生罚款,还是发送红包
        if (ifFine == 0) {

            if (roleInfo.getRoleDataManageInfo().getHaveChildNumber() < 3) {

                //更新孩子数量
                roleDataManageInfoHandler.updateRoleHaveChildNumber(roleInfo);

            }

            if (RoomManager.getPlayers(roomId).size() == 1) {

                Map<String, Object> data = new HashMap<String, Object>();

                data.put("roleDataManageInfo", roleInfo.getRoleDataManageInfo());

                message = BuildResponseMessage.build(ProtoIds.BUY_HAVE_CHILD, playerId, 1, data);

            } else {
                //请求别的玩家发送红包
                message = BuildResponseMessage.build(ProtoIds.BUY_HAVE_CHILD, playerId, 0, null);
            }
        } else {

            //进行超生罚款
            roleDataManageInfoHandler.updateRoleCash(roleDataManageInfo, -(int) (roleDataManageInfo.getCash() * 0.1));

            Map<String, Object> data = new HashMap<String, Object>();

            data.put("roleDataManageInfo", roleInfo.getRoleDataManageInfo());

            message = BuildResponseMessage.build(ProtoIds.BUY_HAVE_CHILD, playerId, 1, data);
        }

        if (message != null) {
            ClientConnectionManager.sendMessage(RoomManager.getPlayers(roomId), message);
        }

    }

    /**
     * 发红包
     *
     * @param roomId
     * @param playerId
     * @param money
     * @param targetPayerId
     */
    public void sendRedEnvelope(String roomId, String playerId, int money, String targetPayerId) {

        logger.debug(playerId + "发送红包金额:" + money + " 目标id:" + targetPayerId);

        RoleInfo roleInfo = GameManager.getRoleInfo(roomId, playerId);

        //设置玩家已发送红包
        GameManager.getSendRedEnvelopeData(roomId).put(playerId, true);

        //标识玩家发送红包的金额
        GameManager.getSendRedEnvelopeMoney(roomId).put(playerId, money);

        //更新玩家金币
        roleDataManageInfoHandler.updateRoleCash(roleInfo.getRoleDataManageInfo(), money);

        Map<String, Object> data = new HashMap<String, Object>();

        data.put("roleDataManageInfo", roleInfo.getRoleDataManageInfo());

        NettyMessage message = BuildResponseMessage.build(ProtoIds.SEND_RED_ENVELOPE, playerId, 0, data);

        ClientConnectionManager.sendMessage(RoomManager.getPlayers(roomId), message);

        logger.debug("当前房间的发红包的人数" + GameManager.getSendRedEnvelopeData(roomId));

        //检测是否所有玩家都发送完毕
        for (Map.Entry<String, Boolean> entry : GameManager.getSendRedEnvelopeData(roomId).entrySet()) {

            if (entry.getKey().equals(targetPayerId)) {
                continue;
            }

            if (!entry.getValue()) {
                return;
            }
        }

        int totalMoney = 0;

        //获取玩家的金额
        for (Map.Entry<String, Integer> entry : GameManager.getGame(roomId).getSendRedEnvelopeMoney().entrySet()) {

            if (entry.getKey().equals(targetPayerId)) {
                continue;
            }
            totalMoney += entry.getValue();

        }

        //更新收红包玩家的金币
        RoleInfo targetRoleAgent = GameManager.getRoleInfo(roomId, targetPayerId);

        roleDataManageInfoHandler.updateRoleCash(targetRoleAgent.getRoleDataManageInfo(), Math.abs(totalMoney));

        Map<String, Object> targetData = new HashMap<String, Object>();

        targetData.put("roleDataManageInfo", targetRoleAgent.getRoleDataManageInfo());

        targetData.put("redEnvelopeInfo", GameManager.getSendRedEnvelopeMoney(roomId));

        NettyMessage nettyMessage = BuildResponseMessage.build(ProtoIds.SEND_RED_ENVELOPE, targetPayerId, 1, targetData);

        ClientConnectionManager.sendMessage(RoomManager.getPlayers(roomId), nettyMessage);

        //重置角色的红包
        GameManager.updateSendRedEnvelopeData(roomId, initSendRedEnvelope(roomId));

    }

    /**
     * 生成点数
     *
     * @return
     */
    public Map<String, Object> generatorPoint(int number) {

        //封装摇骰子的信息
        Map<String, Object> data = new HashMap<String, Object>();

        //骰子点数的总和
        int sum = 0;

        //封装摇骰子的点数
        int[] point = new int[number];

        for (int i = 0; i < number; i++) {

            //骰子的点数
            point[i] = (int) (Math.random() * 6) + 1;

            logger.debug("摇到的点数:" + point[i]);

            //累加骰子的点数
            sum += point[i];

        }

        data.put("sum", sum);

        data.put("point", point);

        return data;
    }

    /**
     * 从对应卡组获取卡牌
     *
     * @param cardGroup
     * @param cardType
     * @return
     */
    public int getCardFromCardGroup(List<Integer> cardGroup, int cardType) {

        //卡牌
        int cardId = 0;

        //卡组是否还有牌
        if (cardGroup.size() == 0) {

            //卡牌类型
            switch (cardType) {

                //小机会
                case CardType.smallChance:

                    cardGroup.addAll(CardInfo.outerSmallChanceGroup);

                    break;

                //大机会
                case CardType.bigChance:

                    cardGroup.addAll(CardInfo.outerBigChanceGroup);

                    break;

                //外圈命运
                case CardType.outFate:

                    cardGroup.addAll(CardInfo.outerFateGroup);

                    break;

                //风险
                case CardType.risk:

                    cardGroup.addAll(CardInfo.outerRiskGroup);

                    break;

                //内圈命运
                case CardType.inFate:

                    cardGroup.addAll(CardInfo.innerFateGroup);

                    break;

                //投资
                case CardType.investment:

                    cardGroup.addAll(CardInfo.innerInvestmentGroup);

                    break;

                //品质生活
                case CardType.qualityLife:

                    cardGroup.addAll(CardInfo.innerQualityLifeGroup);

                    break;

                //有闲有钱
                case CardType.richRelax:

                    cardGroup.addAll(CardInfo.innerRelaxGroup);

                    break;

                default:

                    logger.debug("未知类型的卡牌");

                    break;
            }

        } else {

            cardId = cardGroup.get(0);

            cardGroup.remove(0);

        }

        //如果卡牌id为，重新抽取卡牌
        if (cardId == 0) {
            return getCardFromCardGroup(cardGroup, cardType);
        }

        return cardId;
    }

    /**
     * 初始化回合结束
     *
     * @param roomId
     * @return
     */
    public Map<String, Boolean> initRoundEndData(String roomId) {

        List<String> playerList = RoomManager.getPlayers(roomId);

        Map<String, Boolean> roundEnd = new HashMap<>();

        for (String playerId : playerList) {

            roundEnd.put(playerId, false);
        }
        return roundEnd;
    }

    /**
     * 初始化发红包
     *
     * @param roomId
     * @return
     */
    public Map<String, Boolean> initSendRedEnvelope(String roomId) {

        List<String> playerList = RoomManager.getPlayers(roomId);

        Map<String, Boolean> sendRedEnvelopeData = new HashMap<>();

        for (String playerId : playerList) {

            sendRedEnvelopeData.put(playerId, false);
        }
        return sendRedEnvelopeData;

    }

    /**
     * 初始化玩家进内圈的角色信息
     *
     * @param roomId
     * @param playerId
     */
    public void initInRouletteInfo(String roomId, String playerId) {

        RoleInfo roleInfo = GameManager.getRoleInfo(roomId, playerId);

        //获取角色数据信息
        RoleDataManageInfo roleDataManageInfo = roleInfo.getRoleDataManageInfo();

        //获取角色负债信息
        RoleDebtsInfo roleDebtsInfo = roleInfo.getRoleDebtsInfo();

        //----------初始化人物内圈的数据

        //玩家晋级内圈
        GameManager.getRoleLocationData(roomId).get(playerId).setOutOrIn(1);

        //初始化在内圈的位置
        GameManager.getRoleLocationData(roomId).get(playerId).setPlaceByTurntable(0);

        //初始化角色数据信息
        roleDataManageInfoHandler.innerInit(roleDataManageInfo);

        //初始化银行贷款信息
        roleLoanInfoHandler.innerInit(roleInfo);

        //初始化角色负债信息
        roleDebtsInfoHandler.innerInit(roleDebtsInfo);

        //初始化角色的积分记录
        roleIntegralRecordHandler.innerInit(roleInfo.getRoleIntegralRecord());

        //重置角色的工资
        roleInfo.getRoleDataManageInfo().setNonLaborIncome(0);

    }

    /**
     * 获取掷骰子的角色
     *
     * @param roomId
     * @return
     */
    public String getCurrentRole(String roomId) {

        Map<String,Boolean> successData = SuccessDataManager.getSuccessData(roomId);

        Queue<CurrentRole> roleOrder = GameManager.getRoleOrder(roomId);

        String currentRole;

        while (true) {
            if (("1".equals(roleOrder.peek().getStatus()))&&!successData.get(roleOrder.peek().getPlayerId())) {
                currentRole = roleOrder.poll().getPlayerId();
                roleOrder.add(new CurrentRole(currentRole, "1"));
                GameManager.updateCurrentRole(roomId, currentRole);
                break;
            } else {
                CurrentRole currentRole1 = roleOrder.poll();
                roleOrder.add(currentRole1);
            }
        }
        return currentRole;
    }

    /**
     * 断线是否加入游戏
     *
     * @param roomId
     */
    public void addGameing(String roomId) {

        //获取断线重连的玩家
        List<String> playerIds = RoomManager.getRoom(roomId).getReconnectPlayerId();

        if (playerIds == null) {
            return;
        }

        Queue<CurrentRole> roleQueue = GameManager.getRoleOrder(roomId);

        for (String playerId : playerIds) {
            for (CurrentRole currentRole : roleQueue) {
                if (currentRole.getPlayerId().equals(playerId) && "2".equals(currentRole.getStatus())) {
                    //1为在线
                    currentRole.setStatus("1");
                    try {
                        //添加到房间中
                        RoomManager.putRoleInRoom(roomId, playerId);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    //添加到多人回合
                    GameManager.putRoleInGame(roomId, playerId);
                    reconnectionHandler.refreshRoleData(roomId, playerId);
                }
            }
        }
    }

    /**
     * 注销账户
     *
     * @param playerId
     */
    public void logoffGame(String playerId) {
        logger.debug("注销");
        ClientConnectionManager.removeConnectionInfo(playerId);

        logger.debug("移除注销玩家缓存信息");
    }

    /**
     * 玩家退出房间
     *
     * @param roomId
     * @param playerId
     */
    public void exitRoom(String roomId, String playerId) {

        Map<String, Boolean> exitRoom = RoomManager.getRoom(roomId).getExitRoom();

        exitRoom.put(playerId, true);

        logger.debug("exitRoom" + exitRoom.size() + exitRoom);

        List<String> players = RoomManager.getOnlinePlayers(roomId);

        CurrentRole connectRole = null;
        Queue<CurrentRole> roleOrder = GameManager.getRoleOrder(roomId);
        for (CurrentRole currentRole : roleOrder) {
            if (currentRole.getPlayerId().equals(playerId)) {
                connectRole = currentRole;
            }
        }

        Map<String, Object> data = new HashMap<>();
        double number = 0;
        int count = 0;
        for (Map.Entry<String, Boolean> entry : exitRoom.entrySet()) {
            if (entry.getValue() && "1".equals(connectRole.getStatus())) {
                count += 1;
                number = (double) count / players.size();
                BigDecimal b = new BigDecimal(number);
                number = b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
            }
        }

        logger.debug("退出房间比例" + number);

        data.put("numberOfPeople", players.size());

        data.put("numberOfPeopleAgree", count);

        if (number > 0.5) {

            for (String player : players) {
                RoomManager.removeRole(roomId, player);
                ClientConnectionManager.updateStatus(player, ConnectionStatus.ON_LINE);
            }

            RoomManager.removeRoom(roomId);
            data.put("exit", 1);
        }

        NettyMessage message = BuildResponseMessage.build(ProtoIds.EXIT_ROOM_INGAME, playerId, MessageStatus.SUCCESS, data);

        Queue<CurrentRole> roleQueue = GameManager.getRoleOrder(roomId);

        List<String> playerIds = new ArrayList<>();

        for (CurrentRole currentRole : roleQueue) {
            if ("2".equals(currentRole.getStatus()) || "1".equals(currentRole.getStatus())) {
                playerIds.add(currentRole.getPlayerId());
            }
        }

        ClientConnectionManager.sendMessage(playerIds, message);

    }

    /**
     * 拒绝退出房间
     *
     * @param roomId
     * @param playerId
     */
    public void refuseExitRoom(String roomId, String playerId) {

        Map<String, Boolean> exitRoom = RoomManager.getRoom(roomId).getExitRoom();

        exitRoom.put(playerId, false);

        List<String> players = RoomManager.getOnlinePlayers(roomId);

        Map<String, Object> data = new HashMap<>();
        CurrentRole connectRole = null;
        Queue<CurrentRole> roleOrder = GameManager.getRoleOrder(roomId);
        for (CurrentRole currentRole : roleOrder) {
            if (currentRole.getPlayerId().equals(playerId)) {
                connectRole = currentRole;
            }
        }

        double number = 0;
        int count = 0;
        for (Map.Entry<String, Boolean> entry : exitRoom.entrySet()) {
            if (!entry.getValue() && "1".equals(connectRole.getStatus())) {
                count += 1;
                number = (double) count / players.size();
                BigDecimal b = new BigDecimal(number);
                number = b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
            }
        }

        if (number >= 0.5) {

            RoomManager.getRoom(roomId).getExitRoom().clear();
            data.put("exit", 0);
        }

        NettyMessage message = BuildResponseMessage.build(ProtoIds.REFUSE_EXIT_ROOM, playerId, MessageStatus.SUCCESS, data);

        ClientConnectionManager.sendMessage(RoomManager.getAllPlayers(roomId), message);
    }

    /**
     * 发起借款
     *
     * @param roomId
     * @param playerId
     */
    public void loanInitiation(String roomId, String playerId) {

        List<String> players = RoomManager.getPlayers(roomId);
        Map<String, Object> data = new HashMap<>();
        data.put("players", players);
        NettyMessage message = BuildResponseMessage.build(ProtoIds.LOAN_INITIATION, playerId, MessageStatus.SUCCESS, data);
        ClientConnectionManager.sendMessage(playerId, message);

    }

    /**
     * 向好友借款
     *
     * @param playerId
     * @param targetPlayerId
     * @param roomId
     * @param cash
     */
    public void borrowMoney(String playerId, String targetPlayerId, String roomId, String cash, String rate) {

        //发送借款人信息
        NettyMessage message = BuildResponseMessage.build(ProtoIds.BORROW_MONEY, playerId, MessageStatus.SUCCESS, null);
        ClientConnectionManager.sendMessage(playerId, message);

        //发送被借款人信息
        Map<String, Object> data1 = new HashMap<>();
        data1.put("playerId", playerId);
        data1.put("roomId", roomId);
        data1.put("cash", cash);
        data1.put("rate", rate);
        NettyMessage message1 = BuildResponseMessage.build(ProtoIds.BORROW_MONEY, targetPlayerId, 1, data1);
        ClientConnectionManager.sendMessage(targetPlayerId, message1);

    }

    /**
     * 确定贷款
     *
     * @param playerId
     * @param targetPlayerId
     * @param roomId
     * @param cash
     * @param rate
     */
    public void definiteLoan(String playerId, String targetPlayerId, String roomId, String cash, String rate) throws Exception {
        List<String> playerIds = new ArrayList<>();
        RoleInfo roleInfo = GameManager.getRoleInfo(roomId, playerId);
        RoleDataManageInfo roleDataManageInfo = roleInfo.getRoleDataManageInfo();
        playerIds.add(playerId);
        playerIds.add(targetPlayerId);

        //更新借款玩家金钱
        roleDataManageInfoHandler.updateRoleCash(roleDataManageInfo, -Integer.parseInt(cash));

        //更新被借款玩家金钱
        RoleInfo roleInfo1 = GameManager.getRoleInfo(roomId, targetPlayerId);
        RoleDataManageInfo roleDataManageInfo1 = roleInfo1.getRoleDataManageInfo();
        roleDataManageInfoHandler.updateRoleCash(roleDataManageInfo1, Integer.parseInt(cash));

        Map<String, Object> data = new HashMap<String, Object>();
        data.put(playerId, roleInfo.getRoleDataManageInfo());
        data.put(targetPlayerId, roleInfo1.getRoleDataManageInfo());
        data.put("playerIds", playerIds);
        data.put("info", playerService.getPlayerName(playerId) + "借给了" + playerService.getPlayerName(targetPlayerId) + Integer.parseInt(cash));
        NettyMessage message = BuildResponseMessage.build(ProtoIds.DEFINITE_LOAN, playerId, 0, data);
        ClientConnectionManager.sendMessage(RoomManager.getPlayers(roomId), message);
    }

}
