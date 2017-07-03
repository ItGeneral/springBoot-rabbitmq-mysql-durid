package com.kindergarten.utils;

/**
 * @Date Created on 2017/6/30.
 * @Author SongJiuHua.
 * @description 状态码
 */
public enum ResultStatus {

    OK("200", "成功"),
    INTERNAL_SERVER_ERROR("500", "系统服务异常"),
    PARAMETER_EXCEPTION("601", "参数校验异常");

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
