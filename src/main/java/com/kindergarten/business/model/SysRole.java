package com.kindergarten.business.model;

import com.kindergarten.common.Entity;


/**
 * @Date Created on 2017/6/26.
 * @Author SongJiuHua.
 * @description
 */
public class SysRole extends Entity {

    /** 角色中文名称 */
    private String roleCnName;
    /** 角色英文名称 */
    private String roleEnName;
    /** 角色类型 (1:业务角色;2:管理角色 ;3:系统内置角色) */
    private Integer roleType;
    /** 是否有效  true：有效 false：无效 */
    private boolean isActive;
    /** 备注 */
    private String remark;
    /** 创建人 */
    private String creator;
    /** 更新人 */
    private String updater;


    public String getRoleCnName() {
        return roleCnName;
    }

    public void setRoleCnName(String roleCnName) {
        this.roleCnName = roleCnName;
    }

    public String getRoleEnName() {
        return roleEnName;
    }

    public void setRoleEnName(String roleEnName) {
        this.roleEnName = roleEnName;
    }

    public Integer getRoleType() {
        return roleType;
    }

    public void setRoleType(Integer roleType) {
        this.roleType = roleType;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public String getUpdater() {
        return updater;
    }

    public void setUpdater(String updater) {
        this.updater = updater;
    }


}
