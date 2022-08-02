package com.metrics;

//import io.prometheus.client.spring.boot.EnablePrometheusEndpoint;
//import io.prometheus.client.spring.boot.EnableSpringBootMetricsCollector;

//import io.prometheus.client.exporter.HTTPServer;
import com.metrics.config.PrometheusMetricsInterceptor;
//import io.prometheus.client.hotspot.DefaultExports;
//import io.prometheus.client.spring.boot.EnablePrometheusEndpoint;
//import io.prometheus.client.spring.boot.EnableSpringBootMetricsCollector;
//import io.prometheus.client.spring.web.EnablePrometheusTiming;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.io.IOException;

/**
 * Created by xiedong
 * Date: 2022/7/24
 */
//@EnablePrometheusEndpoint
//@EnableSpringBootMetricsCollector
@SpringBootApplication
//public class MetricsDemoApp158 extends WebMvcConfigurerAdapter implements CommandLineRunner {
public class MetricsDemoApp158 {
//    @Override
//    public void addInterceptors(InterceptorRegistry registry) {
//        registry.addInterceptor(new PrometheusMetricsInterceptor()).addPathPatterns("/**");
//    }


    public static void main(String[] args) throws IOException {
//        HTTPServer server = new HTTPServer.Builder()
//                .withPort(8088)
//                .build();
        SpringApplication.run(MetricsDemoApp158.class, args);
    }

//    @Override
//    public void run(String... strings) throws Exception {
//        DefaultExports.initialize();
//    }
//    @Bean
//    MeterRegistryCustomizer meterRegistryCustomizer(@Value("${spring.application.name}")String applicationName, MeterRegistry meterRegistry) {
//        return meterRegistry1 -> {
//            meterRegistry.config().commonTags("application", applicationName);
//        };
//    }
}
