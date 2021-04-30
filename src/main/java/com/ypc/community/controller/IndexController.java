package com.ypc.community.controller;

import com.ypc.community.mapper.UserMapper;
import com.ypc.community.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

/**
 *@描述
 *@参数
 *@返回值
 *@创建人 yinpengcheng
 *@创建时间 2021/4/27
 */
@Controller
public class IndexController {

    @Autowired
    private UserMapper userMapper;

//    @GetMapping("/hello")
//    public String hello(@RequestParam(name = "name") String name, Model model) {
//        model.addAttribute("name",name);
//        return "index";
//    }
    @GetMapping("/")
    public String index(HttpServletRequest request){

        Cookie[] cookies = request.getCookies();
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals("token")){
                String token = cookie.getValue();
                User user = userMapper.findByToken(token);
                if (user !=null){
                    request.getSession().setAttribute("user",user);
                }
                break;//如果命中就break掉
            }
        }


        return "index";
    }

//    public static void main(String[] args){
//        SpringApplication.run(HelloController.class,args);
//    }

}
