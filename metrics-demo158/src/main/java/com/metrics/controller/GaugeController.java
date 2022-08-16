package com.metrics.controller;

import io.micrometer.core.instrument.Metrics;
import io.micrometer.core.instrument.Tags;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.util.Random;
import java.util.concurrent.*;

@RestController
@Slf4j
public class GaugeController {
    private ExecutorService executorService = Executors.newFixedThreadPool(10);//模拟一个10个线程的线程池

    private ScheduledExecutorService scheduled = Executors.newScheduledThreadPool(1);//定时任务用来模拟实现不同时间的并发线程数

    @PostConstruct
    private void init() {

        Metrics.gauge("custom_gauge", Tags.of("class", "micrometerService", "method", "testGauge"), (ThreadPoolExecutor)executorService, ThreadPoolExecutor::getActiveCount);//gauge类型只需创建一次即可，给出一个获取监控值的方法即可。(如何没有合适方法可以通过JUC提供的原子类实现)

        scheduled.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                int r = new Random().nextInt(10);
                for (int i = 0; i < r; i++) {
                    executorService.execute(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                TimeUnit.SECONDS.sleep(10);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                }

            }
        }, 12, 12, TimeUnit.SECONDS);
    }
}
