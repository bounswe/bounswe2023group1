package com.groupa1.annotation.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("file:${anno.appdir}/anno/conf/appparam.txt")
@Getter
public class AnnoAppProperties {
    @Value("${server.port}")
    private String serverPort;
}