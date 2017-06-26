package com.kindergarten.bootmain.base;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;

/**
 * @Date Created on 2017/3/23.
 * @Author SongJiuHua.
 * @description
 */
public class BaseController {

    private final static Logger logger = LoggerFactory.getLogger(BaseController.class);

    public ResponseResult ok(){
        return new ResponseResult(HttpStatus.OK);
    }

    public ResponseResult badRequest(HttpStatus httpStatus) {
        return new ResponseResult(httpStatus);
    }

    public class ResponseResult {

        private Map<String, Object> map;
        private Object body;
        private HttpStatus httpStatus;

        public ResponseResult(HttpStatus httpStatus) {
            this.httpStatus = httpStatus;
        }

        /**
         * 键值对
         * @param key
         * @param value
         * @return
         */
        public ResponseResult put(String key, Object value) {
            if (map == null) map = new HashMap<>();
            map.put(key, value);
            return this;
        }

        /**
         * 键值对，异常代码
         * @param code
         * @return
         */
        public ResponseResult code(int code) {
            return put("code", code);
        }

        /**
         * 键值对，异常描述
         *
         * @param message
         * @return
         */
        public ResponseResult message(String message) {
            return put("message", message);
        }

        /**
         * 直接返回javaBean
         *
         * @param body
         * @return
         */
        public ResponseResult body(Object body) {
            this.body = body;
            return this;
        }


        public ResponseResult internalServerError() {
            return new ResponseResult(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        public ResponseEntity build() {
            try{
                if (body != null) return ResponseEntity.status(httpStatus).body(body);
                return ResponseEntity.status(httpStatus).body(map);
            } catch (Exception ex) {
                logger.error("", ex);
                return internalServerError().code(0).message("服务器内部错误").build();
            }
        }

    }

}
