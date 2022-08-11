package com.metrics.config;


import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.Gauge;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;
import io.micrometer.core.instrument.simple.SimpleMeterRegistry;
import io.micrometer.prometheus.PrometheusMeterRegistry;
import io.prometheus.client.Histogram;
import io.prometheus.client.Summary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Supplier;

@Configuration
public class PrometheusMetricsConfig {

    @Autowired
    private PrometheusMeterRegistry registry;

    @Autowired
    private MeterRegistry meterRegistry;

    /**
     * 只增不减的计数器
     * @return
     */
    @Bean
    Counter getCounter(){
        return Counter.builder("pc_test_counter").tag("key", "value").register(meterRegistry);
    }

    /**
     * 只增不减的计数器
     * @return
     */
    @Bean
    Counter createCounter(){
        return Counter.builder("name")  //名称
                .baseUnit("unit") //基础单位
                .description("desc") //描述
                .tag("tagKey", "tagValue")//标签
                .tags("a","A","b","B","c","C") //标签list
                .register(new SimpleMeterRegistry());//绑定的MeterRegistry
    }

    /**
     * 可增可减的仪表盘
     * @return
     */
    @Bean
    Gauge getGauge(){
        return Gauge.builder("test_builder", new Supplier<Number>() {
            @Override
            public Number get() {
                return new Random().nextDouble();
            }
        }).register(meterRegistry);
    }

    AtomicInteger atomicInteger = new AtomicInteger();
    /**
     * 可增可减的仪表盘
     * @return
     */
    @Bean
    Gauge createGauge(){
        return Gauge
                .builder("gauge",atomicInteger, AtomicInteger::get)
                .description("a description of what this gauge does") // 可选
                .tags("region", "test") // 可选
                .register(registry);
    }

    /**
     * 柱状图
     * @return
     */
    @Bean
    Histogram getHistogram(){
        return Histogram.build().labelNames("application","uri", "accessType", "code","msg")
                .name("pc_cost_histogram").help("请求耗时histogram")
                .buckets(100,500,1000,3000).register(registry.getPrometheusRegistry());
    }

    /**
     * timmer也属于summary，默认单位sencond,并统计max值
     * @return
     */
    @Bean
    Timer getTimer(){
        return Timer
                .builder("my.timer")
                .description("a description of what this timer does") // 可选
                .tags("region", "test") // 可选
                .register(registry);
    }

    /**
     * 摘要分析
     * @return
     */
    @Bean
    Summary getSummary(){
        return  Summary.build().labelNames("application","uri", "accessType", "code","msg")
                .name("pc_cost_summary").help("请求耗时summary")
                .quantile(0.5, 0.05)
                .quantile(0.9, 0.01)
                .register(registry.getPrometheusRegistry());
    }
}
