package com.example.demo.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.WebApplicationContext;

/**日志测试
 * @Auther: liuxiangtao90
 * @Date: 2018/12/28 14:36
 * @Description:logback 测试
 */
@Controller
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class LogbackTestController {

    private final Logger logger = LoggerFactory.getLogger(LogbackTestController.class);

    public LogbackTestController(){
        System.out.println("初始化----------------------------");
    }

    @GetMapping("/logback")
    @ResponseBody
    public void LogbackTest() {
        logger.info("日志测试");
        logger.error("{}","error测试");
        Integer.parseInt("A");
    }
}
