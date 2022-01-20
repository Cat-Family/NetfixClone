package com.yill.config;

import com.yill.utils.RedisUtils;
import com.yill.utils.Result;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

@Component
@Aspect
public class AopConfig {

    @Autowired
    private RedisUtils redisUtils;

    @Pointcut("execution(public * com.yill.controller..*.*(..))")
    public void logRecordAop() {}

    @Around("logRecordAop()")
    public Object around(ProceedingJoinPoint pjd) throws Throwable {
        String className = pjd.getSignature().getClass().getName();
        String methodName = pjd.getSignature().getName();
        Logger log = LoggerFactory.getLogger(className.getClass());
        Object result = null;
        long start = System.currentTimeMillis();
        Date date = new Date(start);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat logSimpleDateFormat = new SimpleDateFormat("yyyyMMdd");
        String format = simpleDateFormat.format(date);
        String logFormat = logSimpleDateFormat.format(date);
        Object[] args = pjd.getArgs();
        String params = "请求参数:";
        for (Object object : args) {
            params += object.toString() + ",";
        }
        params = params.substring(0, params.length() - 1);
        log.info("接口"+methodName + params);
        result = pjd.proceed();
        String resultString = result.toString();
        redisUtils.setList("log"+logFormat,"请求时间:"+format+",接口"+methodName + params+",返回报文"+resultString+"，接口"+methodName + "执行时长:" + (System.currentTimeMillis() - start+"ms"));
        log.info("接口"+methodName +"返回报文:" + (result instanceof Result ? (Result) result : result));
        log.info("接口"+methodName+"请求时间:"+format);
        log.info("接口"+methodName + "执行时长:" + (System.currentTimeMillis() - start+"ms"));
        return result;
    }
}
