package com.faken.aiproject.controller;

import com.faken.aiproject.po.dto.EmailDTO;
import com.faken.aiproject.po.dto.LoginDTO;
import com.faken.aiproject.po.dto.RegisterDTO;
import com.faken.aiproject.po.entity.UserAndToken;
import com.faken.aiproject.po.result.Result;
import com.faken.aiproject.po.vo.HomePageInfoVO;
import com.faken.aiproject.po.vo.PersonalCenterInfoVO;
import com.faken.aiproject.service.AccountService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;


@RestController
@Slf4j
public class AccountController {

    @Autowired
    private AccountService accountService;

    @PostMapping("/account/register")
    public Result<?> register(@RequestBody RegisterDTO registerDTO) {

        int res = accountService.register(registerDTO);
        if (res == 1) {
            return Result.success("注册成功,赶紧登陆吧!");
        } else if (res == 0) {
            return Result.error("验证码不正确!");
        } else if (res == -1) {
            return Result.error("出现未知异常...");
        }
        return Result.error("出现未知异常...");
    }

    @PostMapping("/account/register/sendVerificationCode")
    public Result<?> sendVerificationCode(@RequestBody EmailDTO emailDTO) {
        try {
            accountService.sendVerificationCodeAndSaveCode(emailDTO.getEmail());
            return Result.success("发送成功");
        } catch (Exception e) {
            return Result.error("邮箱验证码发送失败");
        }
    }

    @PostMapping("/account/login")
    public Result<UserAndToken> login(@RequestBody LoginDTO loginDTO) {

        UserAndToken res = accountService.login(loginDTO);
        if (Objects.isNull(res)) {
            return Result.error("登陆失败，请检查你的账号和密码是否输入正确");
        }else {
            return Result.success("ok", res);
        }
    }

    @GetMapping("/account/homePageInfo")
    public Result<?> getHomePageInfo(@RequestParam("userId") String userId){
        HomePageInfoVO res = accountService.getHomePageInfo(userId);
        if(Objects.isNull(res)){
            return Result.error("查找失败，请检查你的用户id");
        }else{
            return Result.success("成功",res);
        }
    }

    @GetMapping("/account/personalCenterInfo")
    public Result<?> getPersonalCenterInfo(@RequestParam("userId") String userId){
        PersonalCenterInfoVO res = accountService.getPersonalCenterInfo(userId);
        if(Objects.isNull(res)){
            return Result.error("查找失败，请检查你的用户id");
        }else{
            return Result.success("成功",res);
        }
    }
}
