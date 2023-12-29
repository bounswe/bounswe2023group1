package com.groupa1.resq.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("file:${resq.appdir}/resq/conf/appparam.txt")
@Getter
public class ResqAppProperties {

    //If a service wants to reach properties, it should first take it with Dependency Injection @Autowired
    //And then, with the get methods provided by @Getter annotation of lombok, it can reach the properties.

    @Value("${server.port}")
    private String serverPort;

    @Value("${server.servlet.context-path}")
    private String appContextPath;

    @Value("${resq.app.jwtSecret}")
    private String jwtSecret;

    @Value("${resq.app.jwtExpirationMs}")
    private int jwtExpirationMs;
}
