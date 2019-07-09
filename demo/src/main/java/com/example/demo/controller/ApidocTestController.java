package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * apidoc使用方法在云笔记里
 * <p>Title: ApidocTestController</p>  
 * <p>Description: </p>  
 * @author liuxiangtao90  
 * @date 2019年7月9日 下午4:01:06
 */
@Controller
public class ApidocTestController {

    /**
     * @Decription: apidocc 测试
     * @Author: liuxiangtao90
     * @CreateDate: Created in 2018/12/28 13:05
     * @param:
     * @param null
     * @Return:
     */
    /**
     * @api {get} /user/:id Request User information [标题]
     * @apiDescription api描述
     * @apiName GetUser
     * @apiGroup User
     *
     * @apiParam {Number} id Users unique ID.
     *
     * @apiSuccess {String} firstname Firstname of the User.
     * @apiSuccess {String} lastname  Lastname of the User.
     *
     * @apiSuccessExample Success-Response:
     *     HTTP/1.1 200 OK
     *     {
     *       "firstname": "John",
     *       "lastname": "Doe"
     *     }
     *
     * @apiError UserNotFound The id of the User was not found.
     *
     * @apiErrorExample Error-Response:
     *     HTTP/1.1 404 Not Found
     *     {
     *       "error": "UserNotFound"
     *     }
     */
    @GetMapping("/apidoc")
    public String roleList(){
        return "apidoc";
    }
}
