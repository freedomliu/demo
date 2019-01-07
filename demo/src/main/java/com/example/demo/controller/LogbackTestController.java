package com.example.demo.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**日志测试
 * @Auther: liuxiangtao90
 * @Date: 2018/12/28 14:36
 * @Description:logback 测试
 */
@Controller
public class LogbackTestController {

    private final Logger logger = LoggerFactory.getLogger(LogbackTestController.class);

    @GetMapping("/logback")
    @ResponseBody
    public void LogbackTest()
    {
        logger.info("日志测试");
        logger.error("{}","error测试");
        Integer.parseInt("A");
    }
}
