package com.kindergarten.business.model;

import com.kindergarten.common.Entity;

/**
 * @Date Created on 2017/6/26.
 * @Author SongJiuHua.
 * @description
 */
public class SysPermission extends Entity {

    /** 权限中文名称 */
    private String permissionCnName;
    /** 权限英文名称 */
    private String permissionEnName;
    /** 是否有效  true：有效 false：无效 */
    private boolean isActive;

    public String getPermissionCnName() {
        return permissionCnName;
    }

    public void setPermissionCnName(String permissionCnName) {
        this.permissionCnName = permissionCnName;
    }

    public String getPermissionEnName() {
        return permissionEnName;
    }

    public void setPermissionEnName(String permissionEnName) {
        this.permissionEnName = permissionEnName;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }
}
