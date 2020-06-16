package com.ascn.richlife.model;

/**
 * 选择角色元素
 */
public class ChooseRoleElement {

    /**
     * 选择的角色
     */
    private int roleId;

    /**
     * 是否准备
     */
    private Boolean ready;

    public int getRoleId() {
        return roleId;
    }

    public ChooseRoleElement setRoleId(int roleId) {
        this.roleId = roleId;
        return this;
    }

    public Boolean getReady() {
        return ready;
    }

    public ChooseRoleElement setReady(Boolean ready) {
        this.ready = ready;
        return this;
    }
}
