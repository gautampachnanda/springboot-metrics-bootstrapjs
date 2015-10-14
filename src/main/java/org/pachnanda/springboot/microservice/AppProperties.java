package org.pachnanda.springboot.microservice;


import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gautamp on 13/10/2015.
 */
@Component
@ConfigurationProperties(prefix = "my")
public class AppProperties {


    private List<String> servers = new ArrayList<String>();

    /**
     * Sample host.
     */
    private String host;

    /**
     * Sample port.
     */
    private Integer port = 8080;

    public String getHost() {
        return this.host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public Integer getPort() {
        return this.port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public List<String> getServers() {
        return this.servers;
    }
}
