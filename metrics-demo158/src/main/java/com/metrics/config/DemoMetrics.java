package com.metrics.config;

import io.micrometer.core.instrument.Gauge;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.binder.MeterBinder;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by xiedong
 * Date: 2022/8/3
 */
public class DemoMetrics implements MeterBinder {
    AtomicInteger count = new AtomicInteger(0);


    @Override
    public void bindTo(MeterRegistry meterRegistry) {
        Gauge.builder("demo.count", count, c -> c.incrementAndGet())
                .tags("host", "localhost")
                .description("demo of custom meter binder")
                .register(meterRegistry);
    }
}