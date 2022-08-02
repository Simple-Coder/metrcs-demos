package com.metrics;

import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.spring.autoconfigure.MeterRegistryCustomizer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

/**
 * Created by xiedong
 * Date: 2022/7/24
 */
@SpringBootApplication
public class MetricsDemoApp158 {
    public static void main(String[] args) {
        SpringApplication.run(MetricsDemoApp158.class, args);
    }
    @Bean
    MeterRegistryCustomizer meterRegistryCustomizer(@Value("${spring.application.name}")String applicationName, MeterRegistry meterRegistry) {
        return meterRegistry1 -> {
            meterRegistry.config().commonTags("application", applicationName);
        };
    }
}
