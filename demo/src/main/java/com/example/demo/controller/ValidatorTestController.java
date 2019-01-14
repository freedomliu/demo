package com.example.demo.controller;

import com.example.demo.entity.User;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.List;

/**
 * @Auther: liuxiangtao90
 * @Date: 2019/1/14 09:04
 * @Description:字段校验测试类
 */
@Controller
public class ValidatorTestController {

    /**
     * @Decription:
     * @Author: liuxiangtao90
     * @CreateDate: Created in 2019/1/14 9:18
     * @param:
     * @param
     * @Return: java.lang.String
     */
    @GetMapping (name = "validator")
    public String validator() {
        return "validator";
    }

    /**
     * @Decription:
     * @Author: liuxiangtao90
     * @CreateDate: Created in 2019/1/14 9:21
     * @param: 
     * @param user
     * @param result
     * @Return: java.lang.String
     */
    @PostMapping(name = "validator")
    public void validator(@Valid User user, BindingResult result) {
        if (result.hasErrors()){
            List<ObjectError> errorList = result.getAllErrors();
            for(ObjectError error : errorList){
                System.out.println(error.getDefaultMessage());
            }
        }
    }
}
