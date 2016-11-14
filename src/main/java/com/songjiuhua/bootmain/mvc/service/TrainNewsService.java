package com.songjiuhua.bootmain.mvc.service;

import com.songjiuhua.bootmain.mvc.model.TrainNews;
import com.songjiuhua.bootmain.utils.BaseService;
import org.springframework.stereotype.Service;

/**
 * Created on 2016/11/4.
 *
 * @Author SongJiuHua.
 * @description
 */
@Service
public class TrainNewsService extends BaseService<TrainNews>{

    public TrainNews queryById(Integer id) {
        return selectOne("queryById", id);
    }
}
