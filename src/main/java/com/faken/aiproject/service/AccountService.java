package com.faken.aiproject.service;

import com.faken.aiproject.po.dto.LoginDTO;
import com.faken.aiproject.po.dto.RegisterDTO;
import com.faken.aiproject.po.vo.HomePageInfoVo;
import com.faken.aiproject.po.vo.PersonalCenterInfoVo;

public interface AccountService {
    String login(LoginDTO loginDTO);

    String generateVerificationCode();

    void sendVerificationCodeAndSaveCode(String to);

    int register(RegisterDTO registerDTO);

    HomePageInfoVo getHomePageInfo(String userId);

    PersonalCenterInfoVo getPersonalCenterInfo(String userId);
}
