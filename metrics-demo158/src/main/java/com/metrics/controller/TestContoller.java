package com.metrics.controller;

//import io.prometheus.client.spring.web.PrometheusTimeMethod;

import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import com.google.common.collect.Lists;
import com.netflix.discovery.DiscoveryClient;
import io.micrometer.core.instrument.*;
import io.micrometer.prometheus.PrometheusMeterRegistry;
import io.prometheus.client.Histogram;
import io.prometheus.client.Summary;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by xiedong
 * Date: 2022/7/24
 */
@RestController
@Slf4j
@RequestMapping("/metric1")
public class TestContoller {
    @Value("${spring.application.name}")
    private String appName;
    private Counter userCounter;
    private Histogram user_hist_help;
    @Autowired
    private PrometheusMeterRegistry meterRegistry;


    @Autowired(required = false)
    private DiscoveryClient discoveryClient;

    //
    @PostConstruct
    public void init() {
//        userCounter = Metrics.counter(appName + "user.counter.total", "services", "demo");
//        user_hist_help = Histogram.build(appName + "user_histogram_test", "user_hist_help").buckets(100, 200, 300).create();
//        CollectorRegistry prometheusRegistry = meterRegistry.getPrometheusRegistry();
//        prometheusRegistry.register(user_hist_help);
    }

    //  http:localhost:8989/metric1/a
    @GetMapping("/a")
//    @PrometheusTimeMethod(name = "metric1_a", help = "metric1_a_help")
    public String a() {
//        Metrics.gauge()
        userCounter.increment();
        Double gauge = Metrics.gauge("user.gauge.test", RandomUtil.randomDouble(10, 10000));
        user_hist_help.observe(RandomUtil.randomDouble(100, 300));
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
        return Thread.currentThread().getName() + gauge;
    }


    /**
     * 定义注册器
     */
    @Autowired
    MeterRegistry registry;

    // @Autowired
    //  private PrometheusMeterRegistry registry;//也可以用

    @Autowired
    Histogram histogram;
    @Autowired
    Summary summary;

    @GetMapping("b")
    public String b() {
        Tag tag1 = new ImmutableTag("uri", this.getClass().getName());
        Tag tag2 = null;
        Tag tag3 = null;
        tag2 = new ImmutableTag("code", "code测试" + "");
        tag3 = new ImmutableTag("msg", "msg测试");


        String newAppName = StrUtil.replace(appName, "-", "_");
        newAppName = StrUtil.replace(newAppName, ".", "_");
        summary.labels(newAppName, this.getClass().getName(), "1", 200 + "", "ok").observe(9);
        histogram.labels(newAppName, this.getClass().getName(), "1", 200 + "", "ok").observe(7);
        registry.timer("api_cost_timer", Lists.newArrayList(tag1, tag2, tag3)).record(8, TimeUnit.MILLISECONDS);
        AtomicLong app_reponse_usedtime = registry.gauge("pc_reponse_usedtime", new AtomicLong(0));
        ;
        return Thread.currentThread().getName();
    }


}
