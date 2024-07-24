package com.faken.aiproject.service;

import com.faken.aiproject.po.dto.LoginDTO;
import com.faken.aiproject.po.dto.RegisterDTO;
import com.faken.aiproject.po.entity.UserAndToken;
import com.faken.aiproject.po.vo.HomePageInfoVO;
import com.faken.aiproject.po.vo.PersonalCenterInfoVO;

public interface AccountService {
    UserAndToken login(LoginDTO loginDTO);

    String generateVerificationCode();

    void sendVerificationCodeAndSaveCode(String to);

    int register(RegisterDTO registerDTO);

    HomePageInfoVO getHomePageInfo(String userId);

    PersonalCenterInfoVO getPersonalCenterInfo(String userId);
}
