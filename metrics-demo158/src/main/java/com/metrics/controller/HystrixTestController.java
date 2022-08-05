package com.metrics.controller;

import com.metrics.outer.OuterClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by dongxie on 2022/8/5.
 */
@RequestMapping("/hystrix")
@RestController
@Slf4j
public class HystrixTestController {
    @Autowired
    private OuterClient outerClient;

    // http://127.0.0.1:8989/hystrix/test?num=12
    @GetMapping("/test")
    public Object test(@RequestParam int num) {
        return outerClient.getHystrixTest(num);
    }

    @GetMapping("/test1")
    // http://127.0.0.1:8989/hystrix/test1?num=12
    public Object test1(@RequestParam int num) {
        if (num % 10 == 0) {
            throw new RuntimeException("num % 10 ==0");
        }
        return Thread.currentThread().getName();
    }

}
