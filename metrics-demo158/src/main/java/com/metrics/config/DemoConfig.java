package com.metrics.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by xiedong
 * Date: 2022/8/3
 */
@Configuration
public class DemoConfig {
    @Bean
    public DemoMetrics demoMetrics(){
        return new DemoMetrics();
    }
}
