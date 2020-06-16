package com.ascn.richlife.server.dispatcher;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ascn.richlife.server.group.ClientConnectionManager;
import com.ascn.richlife.server.handler.*;
import com.ascn.richlife.server.message.NettyMessage;
import com.ascn.richlife.server.protocol.ConnectionStatus;
import com.ascn.richlife.server.protocol.ProtoIds;
import io.netty.channel.ChannelHandlerContext;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;


/**
 * 消息分发
 *
 * Created by zhangpengxiang on 17/5/26.
 */
@Component
public class MessageDispatch {

    /**
     * 日志
     */
    private Logger logger = Logger.getLogger(MessageDispatch.class);

    @Autowired
    private LoginAuthHandle loginAuthHandle;

    @Autowired
    private FriendFightHandler friendFightHandler;

    @Autowired
    private ChooseRoleHandler chooseRoleHandler;

    @Autowired
    private GameInitHandler gameInitHandler;

    @Autowired
    private GameHandler gameHandler;

    @Autowired
    private ChatHandler chatHandler;

    @Autowired
    private ShareHandler shareHandler;

    @Autowired
    private HeartBeatHandler heartBeatHandler;

    @Autowired
    private ReconnectionHandler reconnectionHandler;

    @Autowired
    private GameStatisticsHandler gameStatisticsHandler;

    private static final String CHECK_ROOM_ID = "roomId";

    /**
     * 分发消息
     *
     * @param ctx
     * @param message
     * @throws Exception
     */
    public void dispatch(ChannelHandlerContext ctx, NettyMessage message) throws Exception {

        //获取协议类型
        int type = message.getHeader().getType();

        logger.debug("客户端请求协议号:" + type);

        String playerId = message.getHeader().getPlayerId();

        //进行分发
        switch (type) {

            //登录认证
            case ProtoIds.LOGIN_AUTH:
                loginAuthHandle.loginAuth(ctx, message);
                break;

            //心跳验证
            case ProtoIds.HEART_BEAT:
                heartBeatHandler.heartBeat(ctx,message);
                break;

            //大厅广播
            case ProtoIds.RADIO_CAST:
                chatHandler.radioCast(message);
                break;

            //房间聊天
            case ProtoIds.ROOM_CHAT:
                chatHandler.roomChat(message);
                break;

            //游戏分享
            case ProtoIds.SHARE_GAME:
                shareHandler.findGame(playerId);
                break;

            //房间邀请分享
            case ProtoIds.SHARE_ROOM:
                shareHandler.findRoom(playerId);
                break;

            //分享梦想板
            case ProtoIds.SHARE_DREAMBOARD:
                shareHandler.findDreamBoard(playerId);
                break;

            //创建房间
            case ProtoIds.CREATE_ROOM:
                friendFightHandler.createRoom(playerId);
                break;

            //加入房间
            case ProtoIds.JOIN_ROOM:
                friendFightHandler.joinRoom(playerId, message.getBody().get("roomId").toString());
                break;

            //退出房间
            case ProtoIds.EXIT_ROOM:
                friendFightHandler.exitRoom(playerId, message.getBody().get("roomId").toString());
                break;

            //玩家准备
            case ProtoIds.PLAYER_READY_ROOM:
                friendFightHandler.playerReady(playerId, message.getBody().get("roomId").toString());
                break;

            //开始游戏
            case ProtoIds.START_GAME:
                friendFightHandler.autoStartGame(playerId, message.getBody().get("roomId").toString());
                break;

            //选择角色
            case ProtoIds.CHOOSE_ROLE:
                chooseRoleHandler.chooseRole(message.getBody().get("roomId").toString(), playerId, (Integer) message.getBody().get("roleId"));
                break;

            //取消选择
            case ProtoIds.CANCEL_ROLE:
                chooseRoleHandler.cancelRole(message.getBody().get("roomId").toString(), playerId);
                break;

            //玩家锁定角色
            case ProtoIds.PLAYER_ROLE_READY:
                chooseRoleHandler.playerRoleReady(message.getBody().get("roomId").toString(), playerId);
                break;

            //默认选择角色
            case ProtoIds.DEFAULT_CHOOSE:
                chooseRoleHandler.defaultChooseRole(message.getBody().get("roomId").toString(),playerId);
                break;

            //初始化完成
            case ProtoIds.GAME_INIT_OK:
                gameInitHandler.roleInitComplete(message.getBody().get("roomId").toString(), playerId);
                break;

            //注销
            case ProtoIds.LOG_OFF:
                if (checkMessage(message)) {
                    gameHandler.logoffGame( message.getHeader().getPlayerId());
                }

                break;

            //同意退出房间
            case ProtoIds.EXIT_ROOM_INGAME:

                if(checkMessage(message)){
                    if(checkRoomId(message.getBody())){
                        gameHandler.exitRoom(message.getBody().get("roomId").toString(),message.getHeader().getPlayerId());
                    }
                }

                break;

            //拒绝退出房间
            case ProtoIds.REFUSE_EXIT_ROOM:

                if(checkMessage(message)){
                    if (checkRoomId(message.getBody())){
                        gameHandler.refuseExitRoom(message.getBody().get("roomId").toString(),message.getHeader().getPlayerId());
                    }
                }
                break;

            //获取借贷模块信息
            case ProtoIds.GET_LOAN_MODULE_INFO:

                if (checkMessage(message)) {
                    if (checkRoomId(message.getBody())) {
                        gameHandler.getLoanModuleInfo(message.getBody().get("roomId").toString(), message.getHeader().getPlayerId());
                    }
                }

                break;

            //获取目标模块信息
            case ProtoIds.GET_TARGET_MODULE_INFO:

                if (checkMessage(message)) {
                    if (checkRoomId(message.getBody())) {
                        gameHandler.getTargetModuleInfo(message.getBody().get("roomId").toString(), message.getHeader().getPlayerId(), message.getBody().get("targetPlayerId").toString());
                    }
                }

                break;

            //获取资产/收入模块信息
            case ProtoIds.GET_ASSET_AND_INCOME_MODULE_INFO:

                if (checkMessage(message)) {
                    if (checkRoomId(message.getBody())) {
                        gameHandler.getAssetAndIncomeModuleInfo(message.getBody().get("roomId").toString(), message.getHeader().getPlayerId(), message.getBody().get("targetPlayerId").toString());
                    }
                }

                break;

            //获取负债/支出信息
            case ProtoIds.GET_DEPT_AND_SPEND_MODULE_INFO:

                if (checkMessage(message)) {
                    if (checkRoomId(message.getBody())) {
                        gameHandler.getDebtAndSpendModuleInfo(message.getBody().get("roomId").toString(), message.getHeader().getPlayerId(), message.getBody().get("targetPlayerId").toString());
                    }
                }

                break;

            //获取出售记录模块信息
            case ProtoIds.GET_SELL_RECORD_MODULE_INFO:

                if (checkMessage(message)) {
                    if (checkRoomId(message.getBody())) {
                        gameHandler.getSellRecordModuleInfo(message.getBody().get("roomId").toString(), message.getHeader().getPlayerId(), message.getBody().get("targetPlayerId").toString());
                    }
                }

                break;

            //获取结算模块信息
            case ProtoIds.GET_CLOSING_MODULE_INFO:

                if (checkMessage(message)) {
                    if (checkRoomId(message.getBody())) {
                        gameHandler.getClosingModuleInfo(message.getBody().get("roomId").toString(), message.getHeader().getPlayerId(), message.getBody().get("targetPlayerId").toString());
                    }
                }

                break;

            //借款
            case ProtoIds.LOAN:

                if (checkMessage(message)) {
                    if (checkRoomId(message.getBody())) {
                        gameHandler.loan(message.getBody().get("roomId").toString(), message.getHeader().getPlayerId(), (JSONObject) message.getBody().get("loanInfo"));
                    }
                }

                break;

            //还款
            case ProtoIds.REPAY:

                if (checkMessage(message)) {
                    if (checkRoomId(message.getBody())) {
                        gameHandler.repay(message.getBody().get("roomId").toString(), message.getHeader().getPlayerId(), (JSONArray) message.getBody().get("repayInfo"));
                    }
                }

                break;

            //掷骰子
            case ProtoIds.ROLL_THE_DICE:

                if (checkMessage(message)) {
                    if (checkRoomId(message.getBody())) {
                        gameHandler.rollTheDice(message.getBody().get("roomId").toString(), message.getHeader().getPlayerId());
                    }
                }

                break;

            //选择卡牌
            case ProtoIds.CHOICE_CHANCE:

                if (checkMessage(message)) {
                    if (checkRoomId(message.getBody())) {
                        gameHandler.choiceChance(message.getBody().get("roomId").toString(), message.getHeader().getPlayerId(), (Integer) message.getBody().get("cardType"));
                    }
                }

                break;

            //购买小机会资产卡牌
            case ProtoIds.BUY_SMALL_CHANCE_ASSET_CARD:

                if (checkMessage(message)) {
                    if (checkRoomId(message.getBody())) {
                        gameHandler.buySmallChanceAssetCard(message.getBody().get("roomId").toString(), message.getHeader().getPlayerId(), (Integer) message.getBody().get("cardId"));
                    }
                }

                break;

            //购买小机会股票卡牌
            case ProtoIds.BUY_SMALL_CHANCE_STOCK_CARD:

                if (checkMessage(message)) {
                    if (checkRoomId(message.getBody())) {
                        gameHandler.buySmallChanceStockCard(message.getBody().get("roomId").toString(), message.getHeader().getPlayerId(), (Integer) message.getBody().get("cardId"), (Integer) message.getBody().get("cardNumber"));
                    }
                }

                break;

            //购买大机会卡牌
            case ProtoIds.BUY_BIG_CHANCE_CARD:

                if (checkMessage(message)) {
                    if (checkRoomId(message.getBody())) {
                        gameHandler.buyBigChanceCard(message.getBody().get("roomId").toString(), message.getHeader().getPlayerId(), (Integer) message.getBody().get("cardId"));
                    }
                }

                break;

            //购买风险卡牌
            case ProtoIds.BUY_RISK_CARD:

                if (checkMessage(message)) {
                    if (checkRoomId(message.getBody())) {
                        gameHandler.buyOuterRiskCard(message.getBody().get("roomId").toString(), message.getHeader().getPlayerId(), (Integer) message.getBody().get("cardId"), (Boolean) message.getBody().get("freeChoice"));
                    }
                }
                break;

//            case ProtoIds.BUY_OUTER_FATE_CARD:
//
//                gameHandler.buyOuterFateCard(message.getBody().get("roomId").toString(),message.getHeader().getPlayerId(), (Integer) message.getBody().get("cardId"));
//
//                break;

            //购买慈善
            case ProtoIds.BUY_CHARITY_CARD:

                if (checkMessage(message)) {
                    if (checkRoomId(message.getBody())) {
                        gameHandler.buyCharityCard(message.getBody().get("roomId").toString(), message.getHeader().getPlayerId(), (Integer) message.getBody().get("cardId"));
                    }
                }

                break;

            //购买健康管理卡牌
            case ProtoIds.BUY_HEALTH_CARD:

                if (checkMessage(message)) {
                    if (checkRoomId(message.getBody())) {
                        gameHandler.buyHealthCard(message.getBody().get("roomId").toString(), message.getHeader().getPlayerId(), (Integer) message.getBody().get("cardId"));
                    }
                }

                break;

            //购买进修学习卡牌
            case ProtoIds.BUY_STUDY_CARD:

                if (checkMessage(message)) {
                    if (checkRoomId(message.getBody())) {
                        gameHandler.buyStudyCard(message.getBody().get("roomId").toString(), message.getHeader().getPlayerId(), (Integer) message.getBody().get("cardId"));
                    }
                }

                break;

            //单人回合结束
            case ProtoIds.ROUND_END:

                if (checkMessage(message)) {
                    gameHandler.roundEnd(message.getBody().get("roomId").toString(), message.getHeader().getPlayerId());
                }

                break;

            //多人回合结束
            case ProtoIds.ALL_ROUND_END:

                if (checkMessage(message)) {
                    gameHandler.allRoundEnd(message.getBody().get("roomId").toString(), message.getHeader().getPlayerId(), (Integer) message.getBody().get("status"), message.getBody().get("nowPlayerId").toString());
                }

                break;

            //结账日
            case ProtoIds.CLOSING_DATE:

                if (checkMessage(message)) {
                    gameHandler.closingDate(message.getBody().get("roomId").toString(), message.getHeader().getPlayerId());
                }

                break;

            //放弃卡牌
            case ProtoIds.GIVE_UP_BUY_CARD:

                if (checkMessage(message)) {
                    if (checkRoomId(message.getBody())) {
                        gameHandler.giveUpCard(message.getBody().get("roomId").toString(), message.getHeader().getPlayerId(), (Integer) message.getBody().get("cardId"), (Integer) message.getBody().get("cardType"));
                    }
                }

                break;

            //出售资产
            case ProtoIds.SELL_ASSET:

                if (checkMessage(message)) {
                    gameHandler.sellAssets(message.getBody().get("roomId").toString(), message.getHeader().getPlayerId(), (Integer) message.getBody().get("cardId"), (JSONArray) message.getBody().get("cardInfo"));
                }

                break;

            //出售股票
            case ProtoIds.SELL_STOCKS:

                if (checkMessage(message)) {
                    gameHandler.sellStocks(message.getBody().get("roomId").toString(), message.getHeader().getPlayerId(), (Integer) message.getBody().get("cardId"), (JSONArray) message.getBody().get("cardInfo"));
                }

                break;

            //晋级内圈
            case ProtoIds.PROMOTION_SUCCESS:

                if (checkMessage(message)){
                    gameHandler.promotionSuccess(message.getBody().get("roomId").toString(),message.getHeader().getPlayerId());
                }

                break;

            //购买有钱有闲卡牌
            case ProtoIds.BUY_RICH_RELAX_CARD:

                if (checkMessage(message)){
                    gameHandler.buyRichRelaxCard(message.getBody().get("roomId").toString(),message.getHeader().getPlayerId(), (Integer) message.getBody().get("cardId"));
                }

                break;

            //购买品质生活卡牌
            case ProtoIds.BUY_QUALITY_LIFE_CARD:

                if (checkMessage(message)){
                    gameHandler.buyQualityLifeCard(message.getBody().get("roomId").toString(),message.getHeader().getPlayerId(), (Integer) message.getBody().get("cardId"));
                }

                break;

            //购买资产卡牌
            case ProtoIds.BUY_INVESTMENT_CARD:

                if (checkMessage(message)){
                    gameHandler.buyInvestmentCard(message.getBody().get("roomId").toString(),message.getHeader().getPlayerId(),(Integer) message.getBody().get("cardId"), (Integer) message.getBody().get("point"));
                }

                break;

            //购买内圈命运卡牌
            case ProtoIds.BUY_INNER_FATE_CARD:

                if (checkMessage(message)){
                    gameHandler.buyInnerFate(message.getBody().get("roomId").toString(),message.getHeader().getPlayerId(),(Integer) message.getBody().get("cardId"),(Integer) message.getBody().get("point"));
                }

                break;

            //购买保险
            case ProtoIds.BUY_INSURANCE:

                if (checkMessage(message)){
                    gameHandler.buyInsurance(message.getBody().get("roomId").toString(),message.getHeader().getPlayerId());
                }

                break;

            //生孩子卡牌
            case ProtoIds.BUY_HAVE_CHILD:

                if (checkMessage(message)){
                    gameHandler.buyHaveChild(message.getBody().get("roomId").toString(),message.getHeader().getPlayerId(), (Integer) message.getBody().get("ifFine"));
                }

                break;

            //发红包
            case ProtoIds.SEND_RED_ENVELOPE:

                if (checkMessage(message)){
                    gameHandler.sendRedEnvelope(message.getBody().get("roomId").toString(),message.getHeader().getPlayerId(), (Integer) message.getBody().get("money"),message.getBody().get("receivePlayerId").toString());
                }

                break;

            //重连获取数据
            case ProtoIds.RECONNECTION_INROOM:

                if(checkMessage(message)){
                    reconnectionHandler.reconnectionInRoom(message.getBody().get("roomId").toString(),message.getHeader().getPlayerId());
                }

                break;

            //修改重连状态
            case ProtoIds.RECONNECTION_STATUS:

                if(checkMessage(message)){
                    reconnectionHandler.reconnectionInGameing(message.getBody().get("roomId").toString(),message.getHeader().getPlayerId());
                }

                break;

            //拒绝重连
            case ProtoIds.REFUSE_RECONNECTION:

                if(checkMessage(message)){
                    reconnectionHandler.refuseConnection(message.getBody().get("roomId").toString(),message.getHeader().getPlayerId());
                }

                break;

            //发起借款
            case ProtoIds.LOAN_INITIATION:

                if(checkMessage(message)){
                    gameHandler.loanInitiation(message.getBody().get("roomId").toString(),message.getHeader().getPlayerId());
                }

                break;

            //向其余玩家借款
            case ProtoIds.BORROW_MONEY:

                if(checkMessage(message)){
                    gameHandler.borrowMoney(message.getHeader().getPlayerId(),message.getBody().get("targetPlayerId").toString(),message.getBody().get("roomId").toString(),message.getBody().get("cash").toString(),message.getBody().get("rate").toString());
                }

                break;

            //确定贷款
            case ProtoIds.DEFINITE_LOAN:

                if(checkMessage(message)){
                    gameHandler.definiteLoan(message.getHeader().getPlayerId(),message.getBody().get("targetPlayerId").toString(),message.getBody().get("roomId").toString(),message.getBody().get("cash").toString(),message.getBody().get("rate").toString());
                }

                break;

            //游戏记录
            case ProtoIds.GAME_STATISTICS:

                if(checkMessage(message)){
                    gameStatisticsHandler.getGameStatistics(message.getHeader().getPlayerId());
                }

                break;

            //游戏记录详情
            case ProtoIds.GAME_STATISTICS_DETAIL:

                if(checkMessage(message)){
                    gameStatisticsHandler.getGameRecordsRoom(message.getBody().get("roomCode").toString(),message.getHeader().getPlayerId());
                }

                break;

            case ProtoIds.TEST_ON_LINE:

                logger.debug("玩家进入游戏大厅");
                ClientConnectionManager.updateStatus(playerId, ConnectionStatus.ON_LINE);

                break;

            case ProtoIds.TEST_IN_THE_ROOM:

                logger.debug("玩家进入房间中");
                ClientConnectionManager.updateStatus(playerId, ConnectionStatus.IN_THE_ROOM);

                break;

            case ProtoIds.TEST_SELECT_ROLE:

                logger.debug("玩家选择角色中");
                ClientConnectionManager.updateStatus(playerId, ConnectionStatus.CHOOSE_ROLE);

                break;

            case ProtoIds.TEST_IN_THE_GAME:

                logger.debug("玩家进入游戏");
                ClientConnectionManager.updateStatus(playerId, ConnectionStatus.IN_THE_GAME);

                break;

            default:

                logger.debug("未知的协议类型！");

                break;

        }

    }

    /**
     * 检查消息
     *
     * @param message
     * @return
     */
    private boolean checkMessage(NettyMessage message) {

        if (message.getHeader() == null) {
            logger.debug("消息格式不正确");
            return false;
        }
        if (message.getHeader().getPlayerId() == null || message.getHeader().getPlayerId().length() == 0) {
            logger.debug("playerId不能为空");
            return false;
        }
        return true;
    }

    /**
     * 检查roomId
     *
     * @param body
     * @return
     */
    private boolean checkRoomId(Map<String, Object> body) {
        if (body.get(CHECK_ROOM_ID) == null || body.get(CHECK_ROOM_ID).toString().length() == 0) {

            logger.debug("房间号不能为空");
            return false;
        }
        return true;
    }
}
