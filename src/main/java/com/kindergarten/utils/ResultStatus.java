package com.kindergarten.utils;

/**
 * @Date Created on 2017/6/30.
 * @Author SongJiuHua.
 * @description
 */
public enum ResultStatus {

    OK("200", "成功");

    private String code;

    private String description;

    ResultStatus(String code, String description){
        this.code = code;
        this.description = description;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
