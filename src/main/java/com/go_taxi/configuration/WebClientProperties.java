package com.go_taxi.configuration;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Setter
@Getter
@Component
public class WebClientProperties {
    private String host;
    private String path;
}
