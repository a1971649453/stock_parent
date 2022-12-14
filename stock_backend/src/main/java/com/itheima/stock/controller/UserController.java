package com.itheima.stock.controller;

import com.itheima.stock.service.UserService;
import com.itheima.stock.vo.req.LoginReqVo;
import com.itheima.stock.vo.resp.LoginRespVo;
import com.itheima.stock.vo.resp.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @author 金宗文
 * @version 1.0
 */
@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    private UserService userService;

//    @GetMapping("/test")
//    public String test(){
//        return "itheima";
//    }

    @PostMapping("/login")
    public R<LoginRespVo> login(@RequestBody LoginReqVo vo){

        return userService.login(vo);
    }

    /**
     * 生成登录校验码
     * @return
     */
    @GetMapping("/captcha")
    public R<Map> generateCaptcha(){
        return userService.generateCaptcha();
    }






















}
