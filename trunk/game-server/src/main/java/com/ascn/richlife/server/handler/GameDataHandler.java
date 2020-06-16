package com.ascn.richlife.server.handler;

import com.ascn.richlife.model.card.*;
import com.ascn.richlife.model.role.Role;
import com.ascn.richlife.server.data.CardInfo;
import com.ascn.richlife.server.data.CardTipInfo;
import com.ascn.richlife.server.data.RoleCode;
import com.ascn.richlife.server.data.RoleData;
import com.ascn.richlife.service.card.*;
import com.ascn.richlife.service.role.RoleService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 游戏数据处理工具类
 *
 * Created by zhangpengxiang on 2017/6/28.
 */
@Component("gameDataHandler")
public class GameDataHandler {

    private Logger logger = Logger.getLogger(GameDataHandler.class);

    @Autowired
    private InnerFateService innerFateService;

    @Autowired
    private InnerInvestmentService innerInvestmentService;

    @Autowired
    private InnerQualityLifeService innerQualityLifeService;

    @Autowired
    private InnerRichRelaxService innerRichRelaxService;

    @Autowired
    private OuterBigChanceService outerBigChanceService;

    @Autowired
    private OuterFateService outerFateService;

    @Autowired
    private OuterRiskService outerRiskService;

    @Autowired
    private OuterSmallChanceService outerSmallChanceService;

    @Autowired
    private OuterStockService outerStockService;

    @Autowired
    private SpecialService specialService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private CardStudyService cardStudyService;

    @Autowired
    private CardCharityService cardCharityService;

    @Autowired
    private CardHealthService cardHealthService;

    /**
     * 初始化所有数据
     */
    public void init() {
        initRoleData();
        initCardGroupData();
        initTipData();
    }

    /**
     * 初始化角色数据
     */
    private void initRoleData() {

        for (Integer integer : RoleCode.roleCode) {

            Role role = roleService.getRoleInfo(integer);

            RoleData.roleData.put(integer,role);
        }

    }

    /**
     * 初始化卡组数据
     */
    private void initCardGroupData(){

        logger.debug("初始化卡牌资源");
        List<InnerFate> innerFate = innerFateService.selectCard();

        for (InnerFate fate : innerFate) {
            CardInfo.innerFateGroup.add(fate.getId());
            CardInfo.cardInfo.put(fate.getId(), fate);
        }

        List<InnerInvestment> innerInvestment = innerInvestmentService.selectCard();

        for (InnerInvestment investment : innerInvestment) {
            CardInfo.innerInvestmentGroup.add(investment.getId());
            CardInfo.cardInfo.put(investment.getId(), investment);
        }

        List<InnerQualityLife> innerQualityLife = innerQualityLifeService.selectCard();

        for (InnerQualityLife qualityLife : innerQualityLife) {
            CardInfo.innerQualityLifeGroup.add(qualityLife.getId());
            CardInfo.cardInfo.put(qualityLife.getId(), qualityLife);
        }

        List<InnerRichRelax> innerRichRelax = innerRichRelaxService.selectCard();

        for (InnerRichRelax richRelax : innerRichRelax) {
            CardInfo.innerRelaxGroup.add(richRelax.getId());
            CardInfo.cardInfo.put(richRelax.getId(), richRelax);
        }

        List<OuterBigChance> outerBigChance = outerBigChanceService.selectCard();

        for (OuterBigChance bigChance : outerBigChance) {
            CardInfo.outerBigChanceGroup.add(bigChance.getId());
            CardInfo.cardInfo.put(bigChance.getId(), bigChance);
        }

        List<OuterFate> outerFate = outerFateService.selectCard();

        for (OuterFate fate : outerFate) {
            CardInfo.outerFateGroup.add(fate.getId());
            CardInfo.cardInfo.put(fate.getId(), fate);
        }

        List<OuterRisk> outerRisk = outerRiskService.selectCard();

        for (OuterRisk risk : outerRisk) {
            CardInfo.outerRiskGroup.add(risk.getId());
            CardInfo.cardInfo.put(risk.getId(), risk);
        }

        List<OuterSmallChance> outerSmallChance = outerSmallChanceService.selectCard();

        for (OuterSmallChance smallChance : outerSmallChance) {
            CardInfo.outerSmallChanceGroup.add(smallChance.getId());
            CardInfo.cardInfo.put(smallChance.getId(), smallChance);
        }

        List<OuterStock> outerStock = outerStockService.selectCard();

        for (OuterStock stock : outerStock) {
            CardInfo.outerSmallChanceGroup.add(stock.getId());
            CardInfo.outerStockGroup.add(stock.getId());
            CardInfo.cardInfo.put(stock.getId(), stock);
        }

        List<Special> special = specialService.selectCard();

        for (Special element : special) {
            CardInfo.specialGroup.add(element.getId());
            CardInfo.cardInfo.put(element.getId(), element);
        }

        logger.debug("卡牌资源加载完毕");
    }

    /**
     * 初始化卡牌数据
     */
    public void initTipData(){
        CardTipInfo.cardStudies.addAll(cardStudyService.findAll());
        CardTipInfo.cardCharities.addAll(cardCharityService.findAll());
        CardTipInfo.cardHealths.addAll(cardHealthService.findAll());
        logger.debug("卡牌提示数据加载完成");
    }
}
