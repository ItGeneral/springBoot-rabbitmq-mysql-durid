package com.kindergarten.business.model;

import java.io.Serializable;
import java.util.Date;

/**
 * @Date Created on 2017/6/26.
 * @Author SongJiuHua.
 * @description
 */
public class SysUser implements Serializable{

    private static final long serialVersionUID = 8834064209490941784L;
    /** id */
    private Long id;
    /** 用户名 */
    private String userName;
    /** 密码 */
    private String password;
    /** 联系电话 */
    private String telPhone;
    /** 姓名 */
    private String realName;
    /** 邮箱地址 */
    private String emailAddress;
    /** 年龄 */
    private Integer age;
    /** 毕业院校 */
    private String graduatedSchool;
    /** 状态 1：在职 2：离职 */
    private Integer status;
    /** 创建时间 */
    private Date createTime;
    /** 更新时间 */
    private Date updateTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getTelPhone() {
        return telPhone;
    }

    public void setTelPhone(String telPhone) {
        this.telPhone = telPhone;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getGraduatedSchool() {
        return graduatedSchool;
    }

    public void setGraduatedSchool(String graduatedSchool) {
        this.graduatedSchool = graduatedSchool;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
