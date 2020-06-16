package com.ascn.richlife.dao.login;

import com.ascn.richlife.model.login.Inspiration;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 梦想板
 */
@Repository
public interface InspirationDao {

    /**
     * 玩家感悟分享
     * @param content
     * @param nickName
     * @param avatar
     * @return
     */
    int Inspiration(@Param("content")String content,@Param("nickName")String nickName,@Param("avatar")String avatar) throws Exception;

    /**
     * 分页查询感悟
     * @param start
     * @param end
     * @return
     */
    List<Inspiration> findPage(@Param("start")int start,@Param("end")int end);

    /**
     * 查询所有的记录
     * @param nickName
     * @return
     */
    int findCount(@Param("nickName")String nickName);

    /**
     * 查询个人发布感悟
     * @param nickName
     * @return
     */
    List<Inspiration> findByNickName(@Param("nickName")String nickName,@Param("start")int start,@Param("end")int end);
}
