package com.metrics.controller;

//import io.prometheus.client.spring.web.PrometheusTimeMethod;
import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.RandomUtil;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.Gauge;
import io.micrometer.core.instrument.Metrics;
import io.micrometer.prometheus.PrometheusMeterRegistry;
import io.prometheus.client.CollectorRegistry;
import io.prometheus.client.Histogram;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.util.Collections;

/**
 * Created by xiedong
 * Date: 2022/7/24
 */
@RestController
@Slf4j
@RequestMapping("/metric1")
public class TestContoller {
    static final Counter userCounter = Metrics.counter("user.counter.total", "services", "demo");
    Histogram user_hist_help = Histogram.build("user_histogram_test", "user_hist_help").buckets(100, 200, 300).create();
    @Autowired
    private PrometheusMeterRegistry meterRegistry;
    //
    @PostConstruct
    public void init(){
        CollectorRegistry prometheusRegistry = meterRegistry.getPrometheusRegistry();
        prometheusRegistry.register(user_hist_help);
    }

    //  http:localhost:8989/metric1/a
    @GetMapping("/a")
//    @PrometheusTimeMethod(name = "metric1_a", help = "metric1_a_help")
    public String a() {
//        Metrics.gauge()
        userCounter.increment();
        Double gauge = Metrics.gauge("user.gauge.test", RandomUtil.randomDouble(10,10000));
        user_hist_help.observe(RandomUtil.randomDouble(100,300));
//           new Thread(()->{
//           while (true){
//               Double gauge = Metrics.gauge("user.gauge.test", RandomUtil.randomDouble(10,10000));
//               System.out.println(gauge);
//               try {
//                   Thread.sleep(500);
//               } catch (InterruptedException e) {
//                   e.printStackTrace();
//               }
//           }
//        }).start();
        return Thread.currentThread().getName()+gauge;
    }
}
