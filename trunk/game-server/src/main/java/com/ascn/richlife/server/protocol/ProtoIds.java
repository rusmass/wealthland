package com.ascn.richlife.server.protocol;

/**
 * 协议号
 *
 * Created by zhangpengxiang on 17/5/26.
 * @author zhangpengxiang
 */
public class ProtoIds {

    /**
     * 握手请求
     */
    public static final int LOGIN_AUTH = 0;

    /**
     * 帐号在别的地方上线
     */
    public static final int REPEAT_LOGIN = 1;

    /**
     * 心跳
     */
    public static final int HEART_BEAT = 2;

    /**
     * 大厅广播
     */
    public static final int RADIO_CAST = 3;

    /**
     * 房间中聊天
     */
    public static final int ROOM_CHAT = 4;

    /**
     * 游戏分享
     */
    public static final int SHARE_GAME = 5;

    /**
     * 房间分享
     */
    public static final int SHARE_ROOM = 6;

    /**
     * 注销
     */
    public static final int LOG_OFF = 7;

    /**
     * 分享梦想榜
     */
    public static final int SHARE_DREAMBOARD = 8;

    /**
     * 房间中掉线
     */
    public static final int DISCONNECTED_INROOM = 1000;

    /**
     * 选择角色中掉线
     */
    public static final int DISCONNECTED_INCHOOSEROLE = 1001;

    /**
     * 游戏初始化中掉线
     */
    public static final int DISCONNECTED_INGAME = 1002;

    /**
     * 在游戏当中掉线
     */
    public static final int DISCONNECTED_INGAMEING = 1003;

    /**
     * 重连到房间中准备
     */
    public static final int RECONNECTION_READYINROOM = 2001;

    /**
     * 掉线玩家加入游戏中
     */
    public static final int RECONNECTION_INROOM = 2002;

    /**
     * 修改玩家游戏状态
     */
    public static final int RECONNECTION_STATUS = 2003;

    /**
     * 刷线掉线玩家数据
     */
    public static final int REFRESH_DATA = 2004;

    /**
     * 拒绝重连
     */
    public static final int REFUSE_RECONNECTION = 2005;

    /**
     * 创建房间
     */
    public static final int CREATE_ROOM = 3000;

    /**
     * 加入房间
     */
    public static final int JOIN_ROOM = 3001;

    /**
     * 退出房间
     */
    public static final int EXIT_ROOM = 3002;

    /**
     * 踢人
     */
    public static final int KICK_PLAYER = 3003;

    /**
     * 玩家准备
     */
    public static final int PLAYER_READY_ROOM = 3004;

    /**
     * 开始选择角色
     */
    public static final int START_CHOOSE_ROLE = 3005;

    /**
     * 选择角色
     */
    public static final int CHOOSE_ROLE = 4000;

    /**
     * 取消角色
     */
    public static final int CANCEL_ROLE = 4001;

    /**
     * 锁定角色
     */
    public static final int PLAYER_ROLE_READY = 4002;

    /**
     * 开始游戏
     */
    public static final int START_GAME = 4003;

    /**
     * 倒计时选择角色
     */
    public static final int DEFAULT_CHOOSE = 4004;

    /**
     * 游戏是否初始化完成
     */
    public static final int GAME_INIT_OK = 5000;

    /**
     * 获取借贷模块信息
     */
    public static final int GET_LOAN_MODULE_INFO = 5001;

    /**
     * 获取目标模块信息
     */
    public static final int GET_TARGET_MODULE_INFO = 5002;

    /**
     * 获取资产和收入模块
     */
    public static final int GET_ASSET_AND_INCOME_MODULE_INFO = 5003;

    /**
     * 获取负债和住处模块
     */
    public static final int GET_DEPT_AND_SPEND_MODULE_INFO = 5004;

    /**
     * 获取出售记录模块
     */
    public static final int GET_SELL_RECORD_MODULE_INFO = 5005;

    /**
     * 获取结账日模块
     */
    public static final int GET_CLOSING_MODULE_INFO = 5006;

    /**
     * 借款
     */
    public static final int LOAN = 5007;

    /**
     * 还款
     */
    public static final int REPAY = 5008;

    /**
     * 掷骰子
     */
    public static final int ROLL_THE_DICE = 5009;

    /**
     * 回合结束
     */
    public static final int ROUND_END = 5010;

    /**
     * 多人回合结束
     */
    public static final int ALL_ROUND_END = 5011;

    /**
     * 购买小机会卡牌
     */
    public static final int BUY_SMALL_CHANCE_ASSET_CARD = 5012;

    /**
     * 购买股票卡牌
     */
    public static final int BUY_SMALL_CHANCE_STOCK_CARD = 5013;

    /**
     * 购买大机会卡牌
     */
    public static final int BUY_BIG_CHANCE_CARD = 5014;

    /**
     * 购买风险卡牌
     */
    public static final int BUY_RISK_CARD = 5015;

    /**
     * 购买慈善卡牌
     */
    public static final int BUY_CHARITY_CARD = 5016;

    /**
     * 购买进修学习卡牌
     */
    public static final int BUY_STUDY_CARD = 5017;

    /**
     * 购买健康学习卡牌
     */
    public static final int BUY_HEALTH_CARD = 5018;

    /**
     * 选择机会
     */
    public static final int CHOICE_CHANCE = 5019;

    /**
     * 购买有闲有钱卡牌
     */
    public static final int BUY_RICH_RELAX_CARD = 5020;

    /**
     * 购买品质生活卡牌
     */
    public static final int BUY_QUALITY_LIFE_CARD = 5021;

    /**
     * 购买投资卡牌
     */
    public static final int BUY_INVESTMENT_CARD = 5022;

    /**
     * 购买内圈命运卡牌
     */
    public static final int BUY_INNER_FATE_CARD = 5023;

    /**
     * 结账日
     */
    public static final int CLOSING_DATE = 5024;

    /**
     * 晋级内圈成功
     */
    public static final int PROMOTION_SUCCESS = 5025;

    /**
     * 购买保险
     */
    public static final int BUY_INSURANCE = 5026;

    /**
     * 放弃卡牌
     */
    public static final int GIVE_UP_BUY_CARD = 5027;

    /**
     * 出售资产
     */
    public static final int SELL_ASSET = 5028;

    /**
     * 出售股票
     */
    public static final int SELL_STOCKS = 5029;

    /**
     * 生孩子
     */
    public static final int BUY_HAVE_CHILD = 5030;

    /**
     * 发红包
     */
    public static final int SEND_RED_ENVELOPE = 5031;

    /**
     * 退出游戏
     */
    public static final int EXIT_ROOM_INGAME = 5032;

    /**
     * 拒绝退出游戏
     */
    public static final int REFUSE_EXIT_ROOM = 5033;

    /**
     * 发起借款
     */
    public static final int LOAN_INITIATION = 5034;

    /**
     * 向谁借款
     */
    public static final int BORROW_MONEY = 5035;

    /**
     * 确定贷款
     */
    public static final int DEFINITE_LOAN = 5036;

    /**
     * 开始匹配
     */
    public static final int MATCHING_GAME = 6000;

    /**
     * 创建匹配房间
     */
    public static final int MATCHING_CREATE_ROOM = 6001;

    /**
     * 取消匹配
     */
    public static final int CANCEL_MATCHING = 6002;

    /**
     * 进入游戏
     */
    public static final int ENTER_MATCHING_GAME = 6003;

    /**
     * 匹配添加机器人
     */
    public static final int ADD_ROBOT_MATCH = 6004;

    /**
     * 获取游戏统计
     */
    public static final int GAME_STATISTICS = 7000;

    /**
     * 获取游戏统计详情
     */
    public static final int GAME_STATISTICS_DETAIL = 7001;

    /**
     * 测试在线状态
     */
    public static final int TEST_ON_LINE = -1;

    /**
     * 测试在房间中状态
     */
    public static final int TEST_IN_THE_ROOM = -2;

    /**
     * 测试在选择角色中状态
     */
    public static final int TEST_SELECT_ROLE = -3;

    /**
     * 测试在游戏中状态
     */
    public static final int TEST_IN_THE_GAME = -4;

}
