package com.ascn.richlife.server.handler;

import com.ascn.richlife.model.countInfo.GameRecord;
import com.ascn.richlife.model.countInfo.GameStatistics;
import com.ascn.richlife.model.role.RoleInfo;
import com.ascn.richlife.model.role.RoleIntegral;
import com.ascn.richlife.model.role.RoleOperateNum;
import com.ascn.richlife.server.group.*;
import com.ascn.richlife.server.message.MessageStatus;
import com.ascn.richlife.server.message.NettyMessage;
import com.ascn.richlife.server.protocol.ProtoIds;
import com.ascn.richlife.server.util.BuildResponseMessage;
import com.ascn.richlife.service.gamerecord.GameRecordService;
import com.ascn.richlife.service.gamerecord.GameStatisticsService;
import com.ascn.richlife.service.login.PlayerService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.NumberFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 游戏数据统计处理工具类
 * <p>
 * Created by Administrator on 2018/1/22 0022.
 */
@Component("gameStatisticsHandler")
public class GameStatisticsHandler {

    Logger logger = Logger.getLogger(GameStatisticsHandler.class);

    @Autowired
    GameStatisticsService gameStatisticsService;

    @Autowired
    GameRecordService gameRecordService;

    @Autowired
    PlayerService playerService;

    /**
     * 获取当前房间中的记录
     *
     * @param roomCode
     * @param playerId
     */
    public void getGameRecordsRoom(String roomCode, String playerId) {

        //获取玩家的游戏记录
        List<GameRecord> gameRecords = gameRecordService.getGameRecordsRoom(roomCode);
        Map<String, Object> data = new HashMap<>();
        data.put("gameRecords", gameRecords);

        NettyMessage message = BuildResponseMessage.build(ProtoIds.GAME_STATISTICS_DETAIL, playerId, MessageStatus.SUCCESS, data);
        ClientConnectionManager.sendMessage(playerId, message);
    }

    /**
     * 获取玩家游戏统计数据
     *
     * @param playerId
     * @return
     */
    public void getGameStatistics(String playerId) {
        Map<String, Object> data = new HashMap<String, Object>(16);

        //添加玩家的对战记录
        data.put("gameRecord", gameRecordService.getGameRecords(playerId));

        //添加玩家的统计信息
        data.put("gameStatistics", gameStatisticsService.getGameStatistics(playerId));

        NettyMessage nettyMessage = BuildResponseMessage.build(ProtoIds.GAME_STATISTICS, playerId, MessageStatus.SUCCESS, data);
        ClientConnectionManager.sendMessage(playerId, nettyMessage);
    }

    /**
     * 更新游戏统计数据
     *
     * @param gameStatistics
     */
    public void updateGameStatistics(GameStatistics gameStatistics) {
        boolean flag = gameStatisticsService.updateGameStatistics(gameStatistics);
        if (flag) {
            logger.info(gameStatistics.getPlayerId() + "更新成功");
        }
    }

    /**
     * 持久化游戏记录
     *
     * @param gameRecord
     */
    public void addGameRecord(GameRecord gameRecord) {
        gameRecordService.addGameRecord(gameRecord);
    }

    /**
     * 计算游戏平均时长
     *
     * @param playerId
     * @param roomId
     * @return
     */
    public String getTime(String roomId, String playerId) {
        GameStatistics gameStatistics = gameStatisticsService.getGameStatistics(playerId);

        //胜利场次
        int win = gameStatistics.getWinScreenings();

        //失败场次
        int fail = gameStatistics.getFailScreenings() + 1;
        int sum = win + fail;
        NumberFormat numberFormat = NumberFormat.getInstance();
        numberFormat.setMaximumFractionDigits(1);
        double endTime = System.currentTimeMillis();
        double minute = (endTime - GameStatisticsManager.getGameStatisticsTime(roomId)) / 60000;
        double average = Double.parseDouble(gameStatistics.getWhenLong());

        //计算平均时长
        String dValue = numberFormat.format(average + (Math.abs(minute - average) / sum));
        return dValue;
    }

    /**
     * 胜利平均时间
     *
     * @param roomId
     * @param playerId
     */
    public String getWinPlayerWhenLong(String roomId, String playerId) {
        GameStatistics gameStatistics = gameStatisticsService.getGameStatistics(playerId);
        int win = gameStatistics.getWinScreenings() + 1;
        int fail = gameStatistics.getFailScreenings();
        NumberFormat numberFormat = NumberFormat.getInstance();
        numberFormat.setMaximumFractionDigits(1);
        double endTime = System.currentTimeMillis();
        double minute = (endTime - GameStatisticsManager.getGameStatisticsTime(roomId)) / 60000;

        double average = Double.parseDouble(gameStatistics.getWhenLong());

        //计算平均时长
        String dValue = numberFormat.format(average + (Math.abs(minute - average) / (win + fail)));
        return dValue;
    }

    /**
     * 获取当前这场的时长
     *
     * @param roomId
     * @return
     */
    public static String getPlayerTime(String roomId) {
        double endTime = System.currentTimeMillis();

        //格式化数据小数点一位
        NumberFormat numberFormat = NumberFormat.getInstance();
        numberFormat.setMaximumFractionDigits(1);
        return numberFormat.format((endTime - GameStatisticsManager.getGameStatisticsTime(roomId)) / 60000);
    }

    /**
     * 统计游戏胜利玩家的数据
     *
     * @param playerId
     */
    public void updateWin(String playerId, String roomId) {

        GameStatistics gameStatistics = gameStatisticsService.getGameStatistics(playerId);

        //更新胜利玩家数据
        gameStatistics.setWinScreenings(gameStatistics.getWinScreenings() + 1);

        //更新游戏平均时长
        gameStatistics.setWhenLong(getWinPlayerWhenLong(roomId, playerId));

        //更新胜利玩家数据胜率
        gameStatistics.setWinrate(updateGameStatisticsWin(playerId));

        //持久化玩家最新数据
        updateGameStatistics(gameStatistics);
    }

    /**
     * 统计失败玩家的数据
     *
     * @param playerId
     * @param roomId
     */
    public void updateFail(String playerId, String roomId) {
        List<String> players = RoomManager.getAllPlayers(roomId);
        players.remove(playerId);

        GameStatistics gameStatistics = gameStatisticsService.getGameStatistics(playerId);

        //更新玩家失败数据
        gameStatistics.setFailScreenings(gameStatistics.getFailScreenings() + 1);

        //更新玩家游戏平均时长
        gameStatistics.setWhenLong(getTime(roomId, playerId));

        //更新胜利玩家数据胜率
        gameStatistics.setWinrate(updateGameStatisticsFail(playerId));

        //持久化失败数据
        updateGameStatistics(gameStatistics);
    }

    /**
     * 添加游戏中每个玩家的记录
     *
     * @param roomId
     */
    public void addRecord(String playerID, String roomId) {

        String time = String.valueOf(System.currentTimeMillis());
        RoleInfo roleInfo = GameManager.getRoleInfo(roomId, playerID);
        String increasedCash = String.valueOf(roleInfo.getRoleDataManageInfo().getCashFlow() + roleInfo.getRoleDataManageInfo().getInitCashFlow());
        int inOrOut = GameManager.getRoleLocationData(roomId).get(playerID).getOutOrIn();
        try {
            //玩家是否进入内圈
            if (playerID.equals(playerID)) {
                updateGameRecord(playerService.getPlayerName(playerID), roleInfo.getRoleModelInfo().getHeadImgInfo(), roleInfo.getRoleBasicInfo().getProfessional(), playerID, String.valueOf(roleInfo.getRoleDataManageInfo().getCashFlow()), increasedCash, 2, String.valueOf(roleInfo.getRoleIntegralRecord().getTimeTotalIntegral()), String.valueOf(roleInfo.getRoleIntegralRecord().getQualityTotalIntegral()), roomId, time, sumCreateWealthScore(playerID), manageWealth(playerID), useOfWealth(playerID), surpassWealth(playerID), comprehensiveScore(playerID));
            } else {
                updateGameRecord(playerService.getPlayerName(playerID), roleInfo.getRoleModelInfo().getHeadImgInfo(), roleInfo.getRoleBasicInfo().getProfessional(), playerID, String.valueOf(roleInfo.getRoleDataManageInfo().getCashFlow()), increasedCash, inOrOut, String.valueOf(roleInfo.getRoleIntegralRecord().getTimeTotalIntegral()), String.valueOf(roleInfo.getRoleIntegralRecord().getQualityTotalIntegral()), roomId, time, sumCreateWealthScore(playerID), manageWealth(playerID), useOfWealth(playerID), surpassWealth(playerID), comprehensiveScore(playerID));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /***
     * 更新玩家游戏统计数据胜率
     * @param playerId
     */
    public String updateGameStatisticsWin(String playerId) {
        GameStatistics gameStatistics = gameStatisticsService.getGameStatistics(playerId);
        float win = gameStatistics.getWinScreenings() + 1;
        float fail = gameStatistics.getFailScreenings();
        float sum = win + fail;
        NumberFormat numberFormat = NumberFormat.getInstance();
        numberFormat.setMaximumFractionDigits(2);
        String winRate = numberFormat.format((win / sum) * 100) + "%";
        return winRate;
    }

    /**
     * 计算失败玩家胜率
     *
     * @param playerId
     * @return
     */
    public String updateGameStatisticsFail(String playerId) {
        GameStatistics gameStatistics = gameStatisticsService.getGameStatistics(playerId);
        float win = gameStatistics.getWinScreenings();
        float fail = gameStatistics.getFailScreenings() + 1;
        float sum = win + fail;
        NumberFormat numberFormat = NumberFormat.getInstance();
        numberFormat.setMaximumFractionDigits(2);
        String winRate = numberFormat.format((win / sum) * 100) + "%";
        return winRate;
    }

    /**
     * 统计玩家每一场游戏记录
     *
     * @param job
     * @param playerId
     * @param flowCash
     * @param timeIntegral
     * @param qualityIntegral
     */
    public void updateGameRecord(String name, String job, String occupation, String playerId, String flowCash, String increasedCash, int inOrOut, String timeIntegral, String qualityIntegral, String roomId, String time, double createWealth, double manageWealth, double useOfWealth, double surpassWealth, double comprehensiveScore) {
        GameRecord gameRecord = new GameRecord();

        //统计玩家记录
        gameRecord.setName(name);
        gameRecord.setJob(job);
        gameRecord.setOccupation(occupation);
        gameRecord.setPlayerId(playerId);
        gameRecord.setFlowCash(flowCash);
        gameRecord.setIncreasedCash(increasedCash);
        gameRecord.setInOrOut(inOrOut);
        gameRecord.setRoomCode(roomId + time);
        gameRecord.setTimeIntegral(timeIntegral);
        gameRecord.setQualityIntegral(qualityIntegral);
        gameRecord.setWhenLong(getPlayerTime(roomId));
        gameRecord.setCreateWealth((int) createWealth);
        gameRecord.setManageWealth((int) manageWealth);
        gameRecord.setUseOfWealth((int) useOfWealth);
        gameRecord.setSurpassWealth((int) surpassWealth);
        gameRecord.setComprehensiveScore((int) comprehensiveScore);

        //持久化玩家游戏记录
        addGameRecord(gameRecord);

    }

    /**
     * 获取玩家的角色积分
     *
     * @param roomId
     */
    public Map<String, RoleIntegral> getRoleIntegral(String roomId) {
        Map<String, RoleIntegral> data = new HashMap<>();
        for (String playerId : RoomManager.getPlayers(roomId)) {
            RoleIntegral roleIntegral = new RoleIntegral();
            roleIntegral.setPlayerId(playerId);
            roleIntegral.setBigChanceIntegral((int) bigChanceIntegral(playerId));
            roleIntegral.setSmallChanceIntegral((int) smallChanceIntegral(playerId));
            roleIntegral.setSellAssetIntegral((int) sellAssetIntegral(playerId));
            roleIntegral.setOutPayDayIntegral((int) outPayDayIntegral(playerId));
            roleIntegral.setInvestIntegral((int) investIntegral(playerId));
            roleIntegral.setInsuranceIntegral((int) insuranceIntegral(playerId));
            roleIntegral.setInPayDayIntegral((int) inPayDayIntegral(playerId));
            roleIntegral.setRichAndIdleIntegral((int) richAndIdleIntegral(playerId));
            roleIntegral.setQualityLifeIntegral((int) qualityLifeIntegral(playerId));
            roleIntegral.setCharitableIntegral((int) charitableIntegral(playerId));
            roleIntegral.setStudyIntegral((int) studyIntegral(playerId));
            roleIntegral.setHealthIntegral((int) healthIntegral(playerId));
            roleIntegral.setUnemploymentIntegral((int) unemploymentIntegral(playerId));
            roleIntegral.setEncounterAudiIntegral((int) encounterAuditIntegral(playerId));
            roleIntegral.setDivorce((int) divorceIntegral(playerId));
            roleIntegral.setFinancialCrisisIntegral((int) financialCrisisIntegral(playerId));
            roleIntegral.setCreateWealth((int) sumCreateWealthScore(playerId));
            roleIntegral.setManageWealth((int) manageWealth(playerId));
            roleIntegral.setUseOfWealth((int) useOfWealth(playerId));
            roleIntegral.setSurpassWealth((int) surpassWealth(playerId));
            roleIntegral.setComprehensiveScore((int) comprehensiveScore(playerId));
            data.put(playerId, roleIntegral);
        }
        return data;
    }

    /**
     * 大机会积分
     *
     * @param playerId
     * @return
     */
    public double bigChanceIntegral(String playerId) {
        RoleOperateNum roleOperateNum = GameStatisticsManager.getRoleOperateNum(playerId);

        //获取玩家操作的不同卡牌次数
        int buyBigChanceNum = roleOperateNum.getBuyBigChanceNum();
        int meetBigChanceNum = roleOperateNum.getMeetBigChanceNum();

        //计算大机会得分
        double bigChanceScore = 0;
        if (meetBigChanceNum != 0) {
            bigChanceScore = buyBigChanceNum * (1.1 + buyBigChanceNum / meetBigChanceNum);
        }

        return bigChanceScore;
    }

    /**
     * 小机会积分
     *
     * @param playerId
     * @return
     */
    public double smallChanceIntegral(String playerId) {
        RoleOperateNum roleOperateNum = GameStatisticsManager.getRoleOperateNum(playerId);
        int buySmallChanceStockNum = roleOperateNum.getBuySmallChanceStockNum();
        int buySmallChanceAssetNum = roleOperateNum.getBuySmallChanceAssetNum();
        int meetSmallChanceStockNum = roleOperateNum.getMeetSmallChanceStockNum();
        int meetSmallChanceAssetNum = roleOperateNum.getMeetSmallChanceAssetNum();

        //计算小机会得分
        double smallChanceScore = 0;
        if (meetSmallChanceAssetNum != 0 && meetSmallChanceStockNum != 0) {
            smallChanceScore = (buySmallChanceAssetNum + buySmallChanceStockNum) * (1.1 + (buySmallChanceAssetNum + buySmallChanceStockNum) / (meetSmallChanceAssetNum + meetSmallChanceStockNum));
        }
        return smallChanceScore;
    }

    /**
     * 卖出资产积分
     *
     * @param playerId
     * @return
     */
    public double sellAssetIntegral(String playerId) {
        RoleOperateNum roleOperateNum = GameStatisticsManager.getRoleOperateNum(playerId);
        int sellAssetNum = roleOperateNum.getSellAssetNum();
        //卖出资产得分
        double sellAssetScore = sellAssetNum * 2.1;
        return sellAssetScore;
    }

    /**
     * 外圈结账日积分
     *
     * @param playerId
     * @return
     */
    public double outPayDayIntegral(String playerId) {
        RoleOperateNum roleOperateNum = GameStatisticsManager.getRoleOperateNum(playerId);
        int outPayDayNum = roleOperateNum.getOutPayDayNum();
        //外圈结账日得分
        double outPayDayScore = outPayDayNum * 2.1;
        return outPayDayScore;
    }

    /**
     * 计算单个玩家的创建财富积分
     *
     * @param playerId
     */
    public double sumCreateWealthScore(String playerId) {

        //创造财富得分
        double createWealth = bigChanceIntegral(playerId) + smallChanceIntegral(playerId) + sellAssetIntegral(playerId) + outPayDayIntegral(playerId);
        return createWealth;
    }

    /**
     * 投资积分
     *
     * @param playerId
     * @return
     */
    public double investIntegral(String playerId) {
        RoleOperateNum roleOperateNum = GameStatisticsManager.getRoleOperateNum(playerId);
        int buyInvestNum = roleOperateNum.getBuyInvestNum();
        int meetInvestNum = roleOperateNum.getMeetInvestNum();
        double investScore = 0;
        if (meetInvestNum != 0) {
            investScore = buyInvestNum * (1.2 + buyInvestNum / meetInvestNum);
        }
        return investScore;
    }

    /**
     * 保险积分
     *
     * @param playerId
     * @return
     */
    public double insuranceIntegral(String playerId) {
        RoleOperateNum roleOperateNum = GameStatisticsManager.getRoleOperateNum(playerId);
        int buyInsuranceNum = roleOperateNum.getBuyInsuranceNum();
        double insuranceScore = buyInsuranceNum * 2.2;
        return insuranceScore;
    }

    /**
     * 内圈结账日积分
     *
     * @param playerId
     * @return
     */
    public double inPayDayIntegral(String playerId) {
        RoleOperateNum roleOperateNum = GameStatisticsManager.getRoleOperateNum(playerId);
        int inPayDayNum = roleOperateNum.getInPayDayNum();
        double inPayDayScore = inPayDayNum * 2.2;
        return inPayDayScore;
    }

    /**
     * 管理财富积分
     *
     * @param playerId
     * @return
     */
    public double manageWealth(String playerId) {

        //管理财富积分
        double manageWealth = investIntegral(playerId) + insuranceIntegral(playerId) + inPayDayIntegral(playerId);
        return manageWealth;
    }

    /**
     * 有闲有钱积分
     *
     * @param playerId
     * @return
     */
    public double richAndIdleIntegral(String playerId) {
        RoleOperateNum roleOperateNum = GameStatisticsManager.getRoleOperateNum(playerId);
        int meetRichAndIdleNum = roleOperateNum.getMeetRichAndIdleNum();
        int buyRichAndIdleNum = roleOperateNum.getBuyRichAndIdleNum();
        double richAndIdle = 0;
        if (meetRichAndIdleNum != 0) {
            richAndIdle = buyRichAndIdleNum * (1.3 + buyRichAndIdleNum / meetRichAndIdleNum);
        }
        return richAndIdle;
    }

    /**
     * 品质生活积分
     *
     * @param playerId
     * @return
     */
    public double qualityLifeIntegral(String playerId) {
        RoleOperateNum roleOperateNum = GameStatisticsManager.getRoleOperateNum(playerId);
        int meetQualityLifeNum = roleOperateNum.getMeetQualityLifeNum();
        int buyQualityLifeNum = roleOperateNum.getBuyQualityLifeNum();
        double qualityLife = 0;
        if (meetQualityLifeNum != 0) {
            qualityLife = buyQualityLifeNum * (1.3 + buyQualityLifeNum / meetQualityLifeNum);
        }
        return qualityLife;
    }

    /**
     * 慈善积分
     *
     * @param playerId
     * @return
     */
    public double charitableIntegral(String playerId) {
        RoleOperateNum roleOperateNum = GameStatisticsManager.getRoleOperateNum(playerId);
        int meetCharitableNum = roleOperateNum.getMeetCharitableNum();
        int buyCharitableNum = roleOperateNum.getBuyCharitableNum();
        double charitable = 0;
        if (meetCharitableNum != 0) {
            charitable = buyCharitableNum * (1.3 + buyCharitableNum / meetCharitableNum);
        }
        return charitable;
    }


    /**
     * 运用财富积分
     *
     * @param playerId
     * @return
     */
    public double useOfWealth(String playerId) {

        double useOfWealth = richAndIdleIntegral(playerId) + qualityLifeIntegral(playerId) + charitableIntegral(playerId);
        return useOfWealth;
    }

    /**
     * 学习积分
     *
     * @param playerId
     * @return
     */
    public double studyIntegral(String playerId) {
        RoleOperateNum roleOperateNum = GameStatisticsManager.getRoleOperateNum(playerId);
        int meetStudyNum = roleOperateNum.getMeetStudyNum();
        int buyStudyNum = roleOperateNum.getBuyStudyNum();
        double studyScore = 0;
        if (meetStudyNum != 0) {
            studyScore = buyStudyNum * (1.4 + buyStudyNum / meetStudyNum);
        }
        return studyScore;
    }

    /**
     * 健康积分
     *
     * @param playerId
     * @return
     */
    public double healthIntegral(String playerId) {
        RoleOperateNum roleOperateNum = GameStatisticsManager.getRoleOperateNum(playerId);
        int meetHealthNum = roleOperateNum.getMeetHealthNum();
        int buyHealthNum = roleOperateNum.getBuyHealthNum();
        double healthScore = 0;
        if (meetHealthNum != 0) {
            healthScore = buyHealthNum * (1.4 + buyHealthNum / meetHealthNum);
        }
        return healthScore;
    }

    /**
     * 失业积分
     *
     * @param playerId
     * @return
     */
    public double unemploymentIntegral(String playerId) {
        int unemploymentNum = GameStatisticsManager.getRoleOperateNum(playerId).getUnemploymentNum();
        double unemployment = unemploymentNum * 2.4;
        return unemployment;
    }

    /**
     * 审计积分
     *
     * @param playerId
     * @return
     */
    public double encounterAuditIntegral(String playerId) {
        RoleOperateNum roleOperateNum = GameStatisticsManager.getRoleOperateNum(playerId);
        int meetEncounterAuditNum = roleOperateNum.getMeetEncounterAuditNum();
        int encounterAuditNum = roleOperateNum.getEncounterAuditNum();
        double encounterAudi = 0;
        if (meetEncounterAuditNum != 0) {
            encounterAudi = encounterAuditNum * (1.4 + encounterAuditNum / meetEncounterAuditNum);
        }
        return encounterAudi;
    }

    /**
     * 离婚积分
     *
     * @param playerId
     * @return
     */
    public double divorceIntegral(String playerId) {
        RoleOperateNum roleOperateNum = GameStatisticsManager.getRoleOperateNum(playerId);
        int meetDivorceNum = roleOperateNum.getMeetDivorceNum();
        int divorceNum = roleOperateNum.getDivorceNum();
        double divorce = 0;
        if (meetDivorceNum != 0) {
            divorce = divorceNum * (1.4 + divorceNum / meetDivorceNum);
        }
        return divorce;
    }

    /**
     * 金融风暴积分
     *
     * @param playerId
     * @return
     */
    public double financialCrisisIntegral(String playerId) {
        RoleOperateNum roleOperateNum = GameStatisticsManager.getRoleOperateNum(playerId);
        int meetFinancialCrisisNum = roleOperateNum.getMeetFinancialCrisisNum();
        int financialCrisisNum = roleOperateNum.getFinancialCrisisNum();
        double financialCrisis = 0;
        if (meetFinancialCrisisNum != 0) {
            financialCrisis = financialCrisisNum * (1.4 + financialCrisisNum / meetFinancialCrisisNum);
        }
        return financialCrisis;
    }


    /**
     * 超越财富积分
     *
     * @param playerId
     * @return
     */
    public double surpassWealth(String playerId) {

        double surpassWealth = studyIntegral(playerId) + unemploymentIntegral(playerId) + encounterAuditIntegral(playerId) + divorceIntegral(playerId) + financialCrisisIntegral(playerId) + healthIntegral(playerId);
        return surpassWealth;
    }

    /**
     * 综合积分
     *
     * @param playerId
     * @return
     */
    public double comprehensiveScore(String playerId) {
        RoleOperateNum roleOperateNum = GameStatisticsManager.getRoleOperateNum(playerId);

        //积分1
        int scoreOne = roleOperateNum.getBuyCharitableNum() + roleOperateNum.getChildrenNum() + roleOperateNum.getBuyRichAndIdleNum() + roleOperateNum.getBuyQualityLifeNum();

        //积分2
        int scoreTwo = 20;

        //积分3
        int scoreThree = roleOperateNum.getUnemploymentNum() + roleOperateNum.getEncounterAuditNum() + roleOperateNum.getDivorceNum();

        //积分4
        int scoreFour = roleOperateNum.getBigChanceIntegral() + roleOperateNum.getSmallChanceIntegral() + roleOperateNum.getInvestIntegral() + roleOperateNum.getOutPayDayNum() + roleOperateNum.getInPayDayNum();
        double comprehensiveScore = scoreOne * 20 + scoreTwo * 30 + scoreThree * 5 + scoreFour * 40;
        return comprehensiveScore;
    }

}
