package com.kindergarten.bootmain;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created on 2016/9/28.
 * @Author SongJiuHua.
 * @description
 */
@RestController
@ComponentScan(value = "com.kindergarten")
@Configuration
@EnableAutoConfiguration
public class Application extends SpringBootServletInitializer {

    public static void main(String[] args){
        SpringApplication.run(Application.class ,args);
    }


}
