package com.songjiuhua.bootmain.mvc.service;

import com.songjiuhua.bootmain.base.BaseService;
import com.songjiuhua.bootmain.mvc.model.User;
import org.springframework.stereotype.Service;

/**
 * @Date Created on 2017/6/26.
 * @Author SongJiuHua.
 * @description
 */
@Service
public class UserService extends BaseService<User> {

    public User getByUserName(String userName){
        return selectOne("queryByUserName", userName);
    }

}
