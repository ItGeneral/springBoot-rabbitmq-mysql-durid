package com.kindergarten.business.controller;

import com.kindergarten.bootmain.base.BaseController;
import com.kindergarten.business.model.SysBaseInfo;
import com.kindergarten.business.service.SysBaseInfoService;
import com.kindergarten.common.ResponseEntity;
import com.kindergarten.utils.ResultStatus;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @Date Created on 2017/7/14.
 * @Author SongJiuHua.
 * @description 学校基础信息
 */
@Controller
public class SysBaseInfoController extends BaseController {

    private final static Logger logger = LoggerFactory.getLogger(SysBaseInfoController.class);

    @Autowired
    private SysBaseInfoService sysBaseInfoService;

    @ResponseBody
    @RequestMapping(value = "getSchoolBaseInfo", method = RequestMethod.GET)
    public ResponseEntity getSchoolBaseInfo(SysBaseInfo sysBaseInfo){
        ResponseEntity responseEntity = new ResponseEntity();
        try{
            List<SysBaseInfo> sysBaseInfoList = sysBaseInfoService.querySysBaseInfo(sysBaseInfo);
            if (CollectionUtils.isEmpty(sysBaseInfoList)){
                responseEntity.setMessage("没有查到学校基础信息");
                return responseEntity;
            }
            responseEntity.setResult(sysBaseInfoList.get(0));
        }catch (Exception e){
            responseEntity.setErrorMessage("查询学校基础数据异常", ResultStatus.INTERNAL_SERVER_ERROR.getCode());
            logger.error("查询学校基础数据异常", e);
        }
        return responseEntity;
    }


    @ResponseBody
    @RequestMapping(value = "saveSchoolBaseInfo", method = RequestMethod.POST)
    public ResponseEntity saveSchoolBaseInfo(SysBaseInfo sysBaseInfo){
        ResponseEntity responseEntity = new ResponseEntity();
        try{
            if (sysBaseInfo != null && sysBaseInfo.getId() != null){
                sysBaseInfoService.updateById(sysBaseInfo);
            }else{
                sysBaseInfoService.insert(sysBaseInfo);
            }
        }catch (Exception e){
            responseEntity.setErrorMessage("保存学校基础信息异常", ResultStatus.INTERNAL_SERVER_ERROR.getCode());
            logger.error("保存学校基础信息异常", e);
        }
        return responseEntity;
    }


}
