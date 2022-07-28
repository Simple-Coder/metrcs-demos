package com.metrics.controller;

import com.dtp.core.DtpRegistry;
import com.dtp.core.thread.DtpExecutor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * Created by xiedong
 * Date: 2022/7/24
 */
@RestController
@Slf4j
@RequestMapping("/metric1")
public class TestContoller {
    //  http:localhost:8989/metric1/a
    // http://127.0.0.1:9998/metrics

    @Resource
    private ThreadPoolExecutor dtpExecutor1;

    @GetMapping("/a")
    public String a() {

//        DtpExecutor dtpExecutor = DtpRegistry.getDtpExecutor("dtpExecutor1");
//        dtpExecutor.execute(() -> System.out.println("test"));

        return Thread.currentThread().getName();
    }
}
