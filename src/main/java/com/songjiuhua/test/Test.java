package com.songjiuhua.test;


import java.util.ArrayList;
import java.util.List;

/**
 * Created on 2016/10/11.
 * @Author SongJiuHua.
 * @description
 */
public class Test {


    public static void main(String[] args){
        List<User> list = new ArrayList<>();
        User user = new User();
        user.setEmail("q2whqui");
        user.setUserName("sdjf");
        list.add(user);

        final int[] i = {0};
        list.stream().map((str) -> {
            str.setEmail("jnsdjs");
            return str;
        }).forEach(System.out::println);



        System.out.println("-----" + user.toString());
    }

}
