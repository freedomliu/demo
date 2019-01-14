package com.example.demo.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * @Auther: liuxiangtao90
 * @Date: 2019/1/14 14:30
 * @Description:异常统一处理
 */
@ControllerAdvice
public class AppExceptionHandler {

    private final Logger logger = LoggerFactory.getLogger(AppExceptionHandler.class);

    @ExceptionHandler(Exception.class)
    public void exceptionHandler(HttpServletRequest request, Exception e) {
        System.out.println("进入统一异常处理类");
        System.out.println("------------------");
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw, true);
        e.printStackTrace(pw);
        pw.flush();
        sw.flush();
        System.out.println(sw.toString());
        logger.error(sw.toString());
    }
}
