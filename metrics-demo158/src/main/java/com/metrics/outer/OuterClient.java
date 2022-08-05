package com.metrics.outer;

import cn.hutool.json.JSON;
import com.netflix.hystrix.contrib.javanica.annotation.DefaultProperties;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by dongxie on 2022/8/5.
 */
@Component
@Slf4j
@DefaultProperties(
        commandProperties = {
                @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "2000"),
                //请求的失败数目超过这个之后，就会打开熔断器
                @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "5"),
                //熔断器工作时间，默认5秒，超过这个时间便会放流量进去
                @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "3000"),
                //出错率超过75%，启动熔断器，默认50%
                @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "75")
        },
        threadPoolProperties = {
                //核心线程数
                @HystrixProperty(name = "coreSize", value = "10"),
                //最大核心线程数
                @HystrixProperty(name = "maximumSize", value = "150"),
                //超过队列的个数会直接失败
                @HystrixProperty(name = "maxQueueSize", value = "150"),
                //队列小于最大值，拒绝请求
                @HystrixProperty(name = "queueSizeRejectionThreshold", value = "150")
        })
public class OuterClient {
    @Autowired
    private RestTemplate restTemplate;

    @HystrixCommand(
            fallbackMethod = "getHystrixTestFallback",
            groupKey = "getHystrixTestGroup",
            threadPoolKey = "getHystrixTestPool"
    )
    public Object getHystrixTest(int num) {
        String forObject = restTemplate.getForObject("http://127.0.0.1:8989/hystrix/test1?num=" + num, String.class);
        return forObject + restTemplate.toString();
    }

    @SuppressWarnings("unused")
    public Object getHystrixTestFallback(int num,Throwable e) {
        log.error("getHystrixTestFallback errOr", e);
        return "getHystrixTestFallback error";
    }
}
