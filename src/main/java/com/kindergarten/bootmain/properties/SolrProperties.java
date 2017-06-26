package com.kindergarten.bootmain.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Created on 2016/10/21.
 * @Author SongJiuHua.
 * @description
 */
@Configuration
@ConfigurationProperties(prefix = "solr", locations = "classpath:solr.properties")
public class SolrProperties {

    private String url;
    private String maxRetries;
    private String connectionTimeout;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getMaxRetries() {
        return maxRetries;
    }

    public void setMaxRetries(String maxRetries) {
        this.maxRetries = maxRetries;
    }

    public String getConnectionTimeout() {
        return connectionTimeout;
    }

    public void setConnectionTimeout(String connectionTimeout) {
        this.connectionTimeout = connectionTimeout;
    }
}
