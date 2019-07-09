package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * @Auther: liuxiangtao90
 * @Date: 2019/1/21 13:29
 * @Description:
 */
@Controller
public class MyThreadTestController {

    @GetMapping("/threadTest")
    @ResponseBody
    public String threadTest(HttpServletRequest request) {
        final String name = request.getParameter("name");
        int number = 0;
        for (int i = 0 ; i < 100 ; i++) {
            inThread(number++);
        }
        return name;
    }

    private void inThread (Integer number) {
        new Thread() {
            public void run() {
                System.out.print(number);
            }
        }.start();
    }
}
