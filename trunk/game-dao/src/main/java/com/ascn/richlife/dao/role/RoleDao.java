package com.ascn.richlife.dao.role;

import com.ascn.richlife.model.role.Role;
import org.springframework.stereotype.Repository;

/**
 * 角色信息
 */
@Repository("roleDao")
public interface RoleDao {

    /**
     * 获取角色信息
     *
     * @param id
     * @return
     */
    Role getRoleInfo(int id);

}
