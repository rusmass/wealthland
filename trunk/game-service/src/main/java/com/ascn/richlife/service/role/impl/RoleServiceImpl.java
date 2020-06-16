package com.ascn.richlife.service.role.impl;

import com.ascn.richlife.dao.role.RoleDao;
import com.ascn.richlife.model.role.Role;
import com.ascn.richlife.service.role.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 角色信息服务
 *
 * Created by zhangpengxiang on 2017/6/28.
 */
@Service("roleService")
public class RoleServiceImpl implements RoleService{

    @Autowired
    private RoleDao roleDao;

    @Override
    public Role getRoleInfo(int id) {
        return roleDao.getRoleInfo(id);
    }
}
