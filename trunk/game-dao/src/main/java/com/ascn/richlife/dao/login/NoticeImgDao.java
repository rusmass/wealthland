package com.ascn.richlife.dao.login;

import com.ascn.richlife.model.login.NoticeImg;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * 公告图
 */
@Repository
public interface NoticeImgDao {

    /**
     * 获取图片
     * @param id
     * @return
     */
    NoticeImg getImg(@Param("id")int id);

}
