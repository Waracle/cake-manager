package com.waracle.cakemgr;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.net.URL;

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "data")
public class ConfigProperties {
    private URL path;
}