package com.metrics.config;

import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.actuate.autoconfigure.metrics.MeterRegistryCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by xiedong
 * Date: 2022/7/24
 */
@Configuration
public class MetricConfig {
    @Bean
    MeterRegistryCustomizer meterRegistryCustomizer(@Value("${spring.application.name}") String applicationName, MeterRegistry meterRegistry) {
        return meterRegistry1 -> {
            meterRegistry.config().commonTags("application", applicationName);
        };
    }
}
