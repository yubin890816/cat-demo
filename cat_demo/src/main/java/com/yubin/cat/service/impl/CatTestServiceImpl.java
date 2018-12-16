package com.yubin.cat.service.impl;

import com.yubin.cat.aop.CatAnnotation;
import com.yubin.cat.service.CatTestService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * @Author YUBIN
 * @create 2018-12-16
 */
@Slf4j
@Service
public class CatTestServiceImpl implements CatTestService {

    @Override
    @CatAnnotation
    public Map<String, String> testMethodAop() throws InterruptedException {
        Map<String, String> result = new HashMap<>();
        result.put("a", "俞");
        result.put("b", "斌");
        Random random = new Random();
        int time = random.nextInt(1000);
        log.info(String.format("testMethodAop method sleep time: %s ms", time));
        Thread.sleep(time);
        return result;
    }
}
