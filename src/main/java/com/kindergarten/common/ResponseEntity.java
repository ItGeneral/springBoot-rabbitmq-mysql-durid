package com.kindergarten.common;

import com.kindergarten.utils.ResultStatus;

/**
 * @Date Created on 2017/6/30.
 * @Author SongJiuHua.
 * @description 返回对象
 */
public class ResponseEntity<T> {

    /** 返回信息 */
    private String message;
    /** 返回状态码 */
    private String status = ResultStatus.OK.getCode();
    /** 返回对象 */
    private T result;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }

    public void setErrorMessage(String message, String status){
        this.message = message;
        this.status = status;
    }

}
