package com.kindergarten.business.model;

import com.kindergarten.common.Entity;

import java.util.Date;

/**
 * @Date Created on 2017/7/14.
 * @Author SongJiuHua.
 * @description
 */
public class SysBaseInfo extends Entity {

    /** 学校简介 */
    private String schoolIntroduction;
    /** 校园文化 */
    private String schoolCulture;
    /** 师资力量 */
    private String schoolTeacherInfo;
    /** 学校联系人姓名 */
    private String schoolContactName;
    /** 学校联系人电话 */
    private String schoolContactPhone;
    /** 学校所在地址 */
    private String schoolAddress;

    @Override
    public Long getId() {
        return super.getId();
    }

    @Override
    public void setId(Long id) {
        super.setId(id);
    }

    @Override
    public Date getCreateTime() {
        return super.getCreateTime();
    }

    @Override
    public void setCreateTime(Date createTime) {
        super.setCreateTime(createTime);
    }

    @Override
    public Date getUpdateTime() {
        return super.getUpdateTime();
    }

    @Override
    public void setUpdateTime(Date updateTime) {
        super.setUpdateTime(updateTime);
    }

    public String getSchoolIntroduction() {
        return schoolIntroduction;
    }

    public void setSchoolIntroduction(String schoolIntroduction) {
        this.schoolIntroduction = schoolIntroduction;
    }

    public String getSchoolCulture() {
        return schoolCulture;
    }

    public void setSchoolCulture(String schoolCulture) {
        this.schoolCulture = schoolCulture;
    }

    public String getSchoolTeacherInfo() {
        return schoolTeacherInfo;
    }

    public void setSchoolTeacherInfo(String schoolTeacherInfo) {
        this.schoolTeacherInfo = schoolTeacherInfo;
    }

    public String getSchoolContactName() {
        return schoolContactName;
    }

    public void setSchoolContactName(String schoolContactName) {
        this.schoolContactName = schoolContactName;
    }

    public String getSchoolContactPhone() {
        return schoolContactPhone;
    }

    public void setSchoolContactPhone(String schoolContactPhone) {
        this.schoolContactPhone = schoolContactPhone;
    }

    public String getSchoolAddress() {
        return schoolAddress;
    }

    public void setSchoolAddress(String schoolAddress) {
        this.schoolAddress = schoolAddress;
    }
}
