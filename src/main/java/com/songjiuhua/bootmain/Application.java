package com.songjiuhua.bootmain;

import com.songjiuhua.example.memcached.MemCached;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created on 2016/9/28.
 * @Author SongJiuHua.
 * @description
 */
@RestController
@SpringBootApplication
public class Application extends SpringBootServletInitializer {


    @RequestMapping("/test")
    public String test(){
        MemCached memCached = new MemCached();
        memCached.testMemCached();
        return MemCached.client.get("key").toString();
    }

    public static void main(String[] args){
        SpringApplication.run(Application.class ,args);
    }


}
