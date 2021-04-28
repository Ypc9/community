package com.ypc.community.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *@描述
 *@参数
 *@返回值
 *@创建人 yinpengcheng
 *@创建时间 2021/4/27
 */
@Controller
public class IndexController {

//    @GetMapping("/hello")
//    public String hello(@RequestParam(name = "name") String name, Model model) {
//        model.addAttribute("name",name);
//        return "index";
//    }
    @GetMapping("/")
    public String index(){ return "index"; }

//    public static void main(String[] args){
//        SpringApplication.run(HelloController.class,args);
//    }

}
