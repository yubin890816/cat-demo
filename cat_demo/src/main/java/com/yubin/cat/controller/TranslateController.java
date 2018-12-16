package com.yubin.cat.controller;

import com.dianping.cat.Cat;
import com.dianping.cat.message.Message;
import com.dianping.cat.message.Transaction;
import com.yubin.cat.service.CatTestService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * @author YUBIN
 */
@RestController
@RequestMapping("/translate")
public class TranslateController {

    private static final Logger LOGGER = LoggerFactory.getLogger(TranslateController.class);

    @Autowired
    private CatTestService catTestService;

    @RequestMapping(value = "/getVoice", produces = "application/json")
    public Object getVoice(HttpServletRequest request, HttpServletResponse response) {
        Transaction t = Cat.newTransaction("URL", "Translate/GetVoice");
        Map<String, String> result = new HashMap<>();
        try{
            //do your business
            result.put("a", "a");
            result.put("b", "b");
            Random random = new Random();
            int time = random.nextInt(1000);
            LOGGER.info(String.format("sleep time: %s ms", time));
            Thread.sleep(time);
            t.setStatus(Message.SUCCESS);
        } catch (Exception e) {
            Cat.getProducer().logError(e);
            t.setStatus(e);
        } finally {
            t.complete();
        }
        return result;
    }

    @RequestMapping(value = "/testCat", produces = "application/json")
    public Object testCat(HttpServletRequest request, HttpServletResponse response) {
        Transaction t = Cat.newTransaction("TEST", "testCat");
        Map<String, String> result = new HashMap<>();
        try{
            result.put("a", "俞");
            result.put("b", "斌");
            Random random = new Random();
            int time = random.nextInt(1000);
            LOGGER.info(String.format("testCat method sleep time: %s ms", time));
            Thread.sleep(time);
            int i = 1 / 0;
            t.addData("testCase接口业务代码执行完成","值");
            t.setStatus(Message.SUCCESS);
        } catch (Exception e) {
            t.setStatus(e);
            LOGGER.error("接口异常",e);
            //Cat.logEvent("TEST","testCase", Event.SUCCESS, ExceptionUtils.getStackTrace(e));
            //Cat.logError(e);
        } finally {
            t.complete();
        }
        return result;
    }

    @RequestMapping(value = "/testFilter")
    public Object testFilter(HttpServletRequest request, HttpServletResponse response) throws InterruptedException {
        Map<String, String> result = new HashMap<>();
        result.put("a", "俞");
        result.put("b", "斌");
        Random random = new Random();
        int time = random.nextInt(1000);
        LOGGER.info(String.format("testFilter method sleep time: %s ms", time));
        Thread.sleep(time);
        return result;
    }

    @RequestMapping(value = "/testMethodAop", produces = "application/json")
    public Object testMethodAop(HttpServletRequest request, HttpServletResponse response) throws InterruptedException {
        Map<String, String> result = catTestService.testMethodAop();
        return result;
    }
}
