package metrcs.controller;

import lombok.extern.slf4j.Slf4j;
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
    //  http:localhost:8989/metric1/a
    // http://127.0.0.1:9998/metrics
    @GetMapping("/a")
    public String a() {
        return Thread.currentThread().getName();
    }
}
