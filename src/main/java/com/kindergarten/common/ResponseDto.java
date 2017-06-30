package com.kindergarten.common;

import org.springframework.http.HttpStatus;

/**
 * @Date Created on 2017/6/30.
 * @Author SongJiuHua.
 * @description
 */
public class ResponseDto<T> {

    /** 返回信息 */
    private String message;
    /** 返回状态码 */
    private HttpStatus code = HttpStatus.OK;
    /** 返回对象 */
    private T result;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public HttpStatus getCode() {
        return code;
    }

    public void setCode(HttpStatus code) {
        this.code = code;
    }

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }

    public void setErrorMessage(String message){
        this.message = message;
        this.code = HttpStatus.INTERNAL_SERVER_ERROR;
    }

}
