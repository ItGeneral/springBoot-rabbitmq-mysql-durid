package com.songjiuhua.bootmain.mvc.model;

/**
 * Created on 2016/10/31.
 * @Author SongJiuHua.
 * @description
 */
public class TrainNews {

    private Integer id;
    private String newType;
    private String cityName;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNewType() {
        return newType;
    }

    public void setNewType(String newType) {
        this.newType = newType;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }
}
