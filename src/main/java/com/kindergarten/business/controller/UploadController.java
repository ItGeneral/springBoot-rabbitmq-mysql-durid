package com.kindergarten.business.controller;

import com.kindergarten.common.ResponseEntity;
import com.kindergarten.utils.FileUploadUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Date Created on 2017/8/7.
 * @Author SongJiuHua.
 * @description
 */
@Controller
@RequestMapping("")
public class UploadController {


    @ResponseBody
    @RequestMapping(value = "upload", method = RequestMethod.POST)
    public ResponseEntity upload(HttpServletRequest request, HttpServletResponse response){
        ResponseEntity responseEntity = new ResponseEntity();
        FileUploadUtils.upLoadFile(request, response);
        return responseEntity;
    }

}
