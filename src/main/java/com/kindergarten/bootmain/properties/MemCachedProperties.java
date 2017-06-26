package com.kindergarten.bootmain.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Created on 2016/10/21.
 * @Author SongJiuHua.
 * @description 加载MemCached配置文件
 */
@Configuration
@ConfigurationProperties(prefix = "memcached", locations = "classpath:memcached.properties")
public class MemCachedProperties {

    private String[] services;
    private Integer initConn;
    private Integer minConn;
    private Integer maxConn;
    private Integer maintSleep;
    private Boolean nagle;
    private Integer socketTO;
    private Boolean failover;

    public String[] getServices() {
        return services;
    }

    public void setServices(String[] services) {
        this.services = services;
    }

    public Integer getInitConn() {
        return initConn;
    }

    public void setInitConn(Integer initConn) {
        this.initConn = initConn;
    }

    public Integer getMinConn() {
        return minConn;
    }

    public void setMinConn(Integer minConn) {
        this.minConn = minConn;
    }

    public Integer getMaxConn() {
        return maxConn;
    }

    public void setMaxConn(Integer maxConn) {
        this.maxConn = maxConn;
    }

    public Integer getMaintSleep() {
        return maintSleep;
    }

    public void setMaintSleep(Integer maintSleep) {
        this.maintSleep = maintSleep;
    }

    public Boolean getNagle() {
        return nagle;
    }

    public void setNagle(Boolean nagle) {
        this.nagle = nagle;
    }

    public Integer getSocketTO() {
        return socketTO;
    }

    public void setSocketTO(Integer socketTO) {
        this.socketTO = socketTO;
    }

    public Boolean getFailover() {
        return failover;
    }

    public void setFailover(Boolean failover) {
        this.failover = failover;
    }
}
