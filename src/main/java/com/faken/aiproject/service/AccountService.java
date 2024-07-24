package com.faken.aiproject.service;

import com.faken.aiproject.po.dto.LoginDTO;
import com.faken.aiproject.po.dto.RegisterDTO;

public interface AccountService {
    String login(LoginDTO loginDTO);

    String generateVerificationCode();

    void sendVerificationCodeAndSaveCode(String to);

    int register(RegisterDTO registerDTO);
}
