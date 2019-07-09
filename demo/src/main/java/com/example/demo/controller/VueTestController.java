package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * @Auther: liuxiangtao90
 * @Date: 2019/5/21 15:26
 * @Description:
 */
@Controller
public class VueTestController {

    @GetMapping("/vue/index")
    public ModelAndView vueIndex(ModelAndView modelAndView) {
        modelAndView.setViewName("vue_index");
        return  modelAndView;
    }
}
