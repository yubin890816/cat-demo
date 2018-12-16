package com.yubin.cat.service;

import com.yubin.cat.aop.CatAnnotation;

import java.util.Map;

/**
 * ${DESCRIPTION}
 *
 * @Author Administrator
 * @create 2018-12-16
 */
public interface CatTestService {

    Map<String,String> testMethodAop() throws InterruptedException;
}
