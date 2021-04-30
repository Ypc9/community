package com.ypc.community.controller;

import com.ypc.community.dto.AccessTokenDTO;
import com.ypc.community.dto.GithubUser;
import com.ypc.community.mapper.UserMapper;
import com.ypc.community.model.User;
import com.ypc.community.provider.GithubProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
//import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

/**
 * @创建人 yinpengcheng
 * @创建时间 2021/4/28
 */
@Controller
public class AuthorizeController {

    @Autowired
    private GithubProvider githubProvider;

//    @Value("${github.client.id}")
    @Value("${github.client.id}")
    private String clientId;

//    @Value("${github.client.secret}")
    @Value("${github.client.secret}")
    private String clientSecret;

//    @Value("${github.redirect.uri}")
    @Value("${github.redirect.uri}")
    private String redirectUri;

    @Autowired
    private UserMapper userMapper;

    @GetMapping("/callback")
    public String callback(@RequestParam(name = "code") String code,
                           @RequestParam(name = "state") String state,
//                           HttpServletRequest request,
                           HttpServletResponse response) {
        AccessTokenDTO accessTokenDTO = new AccessTokenDTO();
//        accessTokenDTO.setClient_id("Iv1.9b1dc2338293f93c");
        accessTokenDTO.setClient_id(clientId);
//        accessTokenDTO.setClient_secret("bcca9591f71d8e137fe26d54d242585a9be5fec3");
        accessTokenDTO.setClient_secret(clientSecret);
        accessTokenDTO.setCode(code);
//        accessTokenDTO.setRedirect_uri("http://localhost:8887/callback");
        accessTokenDTO.setRedirect_uri(redirectUri);
        accessTokenDTO.setState(state);
        String accessToken = githubProvider.getAccessToken(accessTokenDTO);
        GithubUser githubUser = githubProvider.githubUser(accessToken);//拿到user
//        System.out.println(user.getName());//打印github中的Pubilic profile
        //注意：个人的GitHub中如果没有name和bio信息，就会报错
        if (githubUser != null) {
            User user = new User();
            String token = UUID.randomUUID().toString();
            user.setToken(token);
            user.setName(githubUser.getName());
            user.setAccountId(String.valueOf(githubUser.getId()));
            user.setGmtCreate(System.currentTimeMillis());//当前时间计算
            user.setGmtModified(user.getGmtModified());
            userMapper.insert(user);//插入数据库的过程相当于写入session
            response.addCookie(new Cookie("token",token));//自动写入cookie
            //登陆成功，写cookie 和session
//            request.getSession().setAttribute("user", githubUser);
//            return "redirect:index";//重定向页面  找不到地址报错
            return "redirect:/";//  redirect:/ 意思是它会在本地再请求一次   手动写入cookie
        } else {
            //登陆失败，重新登陆
            return "redirect:/";
        }
//        return "index";//完成参数的接收
    }
}
