package com.ascn.richlife.dao.test;


import com.ascn.richlife.dao.card.*;
import com.ascn.richlife.dao.gamerecord.GameRecordDao;
import com.ascn.richlife.dao.gamerecord.GameStatisticsDao;
import com.ascn.richlife.dao.login.*;
import com.ascn.richlife.dao.role.RoleDao;
import com.ascn.richlife.model.countInfo.GameStatistics;
import com.ascn.richlife.model.login.Player;
import com.ascn.richlife.model.login.User;
import org.apache.ibatis.session.SqlSession;
import org.junit.Before;
import org.junit.Test;
import org.springframework.transaction.annotation.Transactional;

import java.sql.ResultSet;


/**
 * Created by Administrator on 2017/11/17 0017.
 */
public class TestUser {

    SqlSession sqlSession;

    UserDao userDao;

    PlayerDao playerDao;

    InspirationDao inspirationDao;

    SpecialDao specialDao;

    InnerRichRelaxDao innerRichRelaxDao;

    InnerQualityLifeDao innerQualityLifeDao;

    OuterFateDao outerFateDao;

    OuterBigChanceDao outerBigChanceDao;

    InnerInvestmentDao innerInvestmentDao;

    InnerFateDao innerFateDao;

    OuterStockDao outerStockDao;

    OuterSmallChanceDao outerSmallChanceDao;

    GameStatisticsDao gameStatisticsDao;

    GameRecordDao gameRecordDao;

    NoticeImgDao noticeImgDao;

    GameNoticeDao gameNoticeDao;

    GameDao gameDao;

    CardStudyDao cardStudyDao;

    CardCharityDao cardCharityDao;

    RoleDao roleDao;

    @Before
    public void init() {
        sqlSession = MybatisUtil.getSqlSession(true);

        userDao = sqlSession.getMapper(UserDao.class);

        playerDao = sqlSession.getMapper(PlayerDao.class);

        inspirationDao = sqlSession.getMapper(InspirationDao.class);

        specialDao = sqlSession.getMapper(SpecialDao.class);

        innerRichRelaxDao = sqlSession.getMapper(InnerRichRelaxDao.class);

        innerQualityLifeDao = sqlSession.getMapper(InnerQualityLifeDao.class);

        outerFateDao = sqlSession.getMapper(OuterFateDao.class);

        outerBigChanceDao = sqlSession.getMapper(OuterBigChanceDao.class);

        innerInvestmentDao = sqlSession.getMapper(InnerInvestmentDao.class);

        innerFateDao = sqlSession.getMapper(InnerFateDao.class);

        outerStockDao = sqlSession.getMapper(OuterStockDao.class);

        outerSmallChanceDao = sqlSession.getMapper(OuterSmallChanceDao.class);

        gameStatisticsDao = sqlSession.getMapper(GameStatisticsDao.class);

        gameRecordDao = sqlSession.getMapper(GameRecordDao.class);

        gameDao = sqlSession.getMapper(GameDao.class);

        noticeImgDao = sqlSession.getMapper(NoticeImgDao.class);

        gameNoticeDao = sqlSession.getMapper(GameNoticeDao.class);

        gameDao = sqlSession.getMapper(GameDao.class);

        cardStudyDao = sqlSession.getMapper(CardStudyDao.class);

        cardCharityDao = sqlSession.getMapper(CardCharityDao.class);

        roleDao = sqlSession.getMapper(RoleDao.class);
    }

    @Test
    @Transactional(rollbackFor = Exception.class)
    public void test1() throws Exception {
        User user = new User();
        user.setUser_type("1");
        user.setUser_pwd("123456");
        user.setUser_phone("1234");
        user.setCreate_time("2018-07-02 15:25:00");
        userDao.insertUser(user);
        throw new Exception();
    }
}
