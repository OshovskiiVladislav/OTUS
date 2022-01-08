package com.oshovskii.otus.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties(prefix = "test")
@Component
@Getter
@Setter
public class ConfigApp {

    private String filePath;
    private String filePathEn;
    private int numberToCompleteTest;
}
