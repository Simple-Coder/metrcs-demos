package com.metrics.controller;

//import io.prometheus.client.spring.web.PrometheusTimeMethod;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.Metrics;
import io.micrometer.prometheus.PrometheusMeterRegistry;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by xiedong
 * Date: 2022/7/24
 */
@RestController
@Slf4j
@RequestMapping("/metric1")
public class TestContoller {
    static final Counter userCounter = Metrics.counter("user.counter.total", "services", "demo");

    @Autowired
    private PrometheusMeterRegistry meterRegistry;
    //  http:localhost:8989/metric1/a
    @GetMapping("/a")
//    @PrometheusTimeMethod(name = "metric1_a", help = "metric1_a_help")
    public String a() {
//        Metrics.gauge()
        userCounter.increment();
        return Thread.currentThread().getName();
    }
}
