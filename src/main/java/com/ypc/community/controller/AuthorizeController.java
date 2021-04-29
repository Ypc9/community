package com.ypc.community.controller;

import com.ypc.community.dto.AccessTokenDTO;
import com.ypc.community.dto.GithubUser;
import com.ypc.community.provider.GithubProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *@创建人 yinpengcheng
 *@创建时间 2021/4/28
 */
@Controller
public class AuthorizeController {

    @Autowired
    private GithubProvider githubProvider;

    @Value("${github.client.id}")
    private String clientId;
    @Value("${github.client.secret}")
    private String clientSecret;
    @Value("${github.redirect.uri}")
    private String redirectUri;

    @GetMapping("/callback")
        public  String callback(@RequestParam(name="code") String code,
                                @RequestParam(name = "state") String state){
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
        GithubUser user = githubProvider.githubUser(accessToken);//拿到user
        System.out.println(user.getName());//打印github中的Pubilic profile
        //注意：个人的GitHub中如果没有name和bio信息，就会报错
        return "index";//完成参数的接收
    }
}
