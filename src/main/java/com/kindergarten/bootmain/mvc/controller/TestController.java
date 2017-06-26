package com.songjiuhua.bootmain.mvc.controller;

import com.songjiuhua.bootmain.mvc.model.TrainNews;
import com.songjiuhua.bootmain.mvc.service.ProducerService;
import com.songjiuhua.bootmain.mvc.service.TrainNewsService;
import com.songjiuhua.bootmain.properties.MemCachedProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created on 2016/10/14.
 * @Author SongJiuHua.
 * @description
 */
@RequestMapping("/")
@Controller
public class TestController {

    @Autowired
    private MemCachedProperties properties;

    @Autowired
    private TrainNewsService trainNewsService;

    @Autowired
    ProducerService producer;

    @RequestMapping("/hi")
    public ModelAndView test(){
        System.out.println("------------" + properties.getFailover());
        ModelAndView mav = new ModelAndView("displayInfo");
        return mav;
    }

    @RequestMapping(value = "/user")
    public @ResponseBody TrainNews testUser(){
        producer.sender();
        TrainNews user = trainNewsService.queryById(918);
        return user;
    }


}
