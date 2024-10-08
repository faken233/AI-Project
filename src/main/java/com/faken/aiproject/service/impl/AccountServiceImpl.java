package com.faken.aiproject.service.impl;

import com.faken.aiproject.mapper.AccountMapper;
import com.faken.aiproject.po.dto.LoginDTO;
import com.faken.aiproject.po.dto.RegisterDTO;
import com.faken.aiproject.po.entity.User;
import com.faken.aiproject.po.entity.UserAndToken;
import com.faken.aiproject.po.vo.HomePageInfoVO;
import com.faken.aiproject.po.vo.PersonalCenterInfoVO;
import com.faken.aiproject.properties.MailProperties;
import com.faken.aiproject.service.AccountService;
import com.faken.aiproject.util.JwtUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Random;
import java.util.concurrent.TimeUnit;

@Service
public class AccountServiceImpl implements AccountService {

    private final AccountMapper accountMapper;

    private final MailSender mailSender;

    private final MailProperties mailProperties;

    private final RedisTemplate<String, String> redisTemplate;

    public AccountServiceImpl(AccountMapper accountMapper, MailSender mailSender, MailProperties mailProperties, RedisTemplate<String, String> redisTemplate) {
        this.accountMapper = accountMapper;
        this.mailSender = mailSender;
        this.mailProperties = mailProperties;
        this.redisTemplate = redisTemplate;
    }


    @Override
    public UserAndToken login(LoginDTO loginDTO) {
        Map<String, Object> claims = new HashMap<>();
        UserAndToken userAndToken = new UserAndToken();
        User user = accountMapper.selectByEmailAndPassword(loginDTO);

        // 判断是否根据DTO找到用户信息
        if (Objects.isNull(user)) {
            return null;
        }

        // 找到用户信息, 构建JWT
        int userId = user.getUserId();
        claims.put("userId", userId);
        String token = JwtUtils.generateToken(claims);

        userAndToken.setToken(token);
        userAndToken.setUserId(userId);
        return userAndToken;
    }

    @Override
    public String generateVerificationCode() {
        Random random = new Random();
        int code = 100000 + random.nextInt(900000);
        return String.valueOf(code);
    }

    @Override
    public void sendVerificationCodeAndSaveCode(String to) {

        // 初始化消息体
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        // 生成随机验证码
        String code = generateVerificationCode();


        // 存入邮箱和验证码的键值对 三分钟内有效
        redisTemplate.opsForValue().set(
                "CodeOf" + to,
                code,
                3,
                TimeUnit.MINUTES
        );


        // 拼接消息体
        mailMessage.setFrom(mailProperties.getUsername());
        mailMessage.setTo(to);
        mailMessage.setSubject("Verification Code");
        mailMessage.setText("Your verification code is: " + code);

        mailSender.send(mailMessage);
    }

    @Override
    public int register(RegisterDTO registerDTO) {
        // 首先验证是否是已有账户
        User user = accountMapper.selectByEmail(registerDTO);
        if (!Objects.isNull(user)) {
            return -1;
        }

        // 新邮箱, 进行邮箱验证
        String code = redisTemplate.opsForValue().get("CodeOf" + registerDTO.getEmail());
        if (code != null && code.equals(registerDTO.getVerificationCode())) {
            // 认证通过
            User newUser = new User();
            BeanUtils.copyProperties(registerDTO, newUser);
            accountMapper.registerNewUser(newUser);
            redisTemplate.delete("CodeOf" + registerDTO.getEmail());
            return 1;
        } else if (code != null) {
            // 认证不通过
            return 0;
        }
        return -1;
    }

    @Override
    public HomePageInfoVO getHomePageInfo(String userId) {
        User user=accountMapper.selectById(userId);
        if(user==null){
            return null;
        }else{
            HomePageInfoVO homePageInfoVo = new HomePageInfoVO();
            BeanUtils.copyProperties(user,homePageInfoVo);
            return homePageInfoVo;
        }
    }

    @Override
    public PersonalCenterInfoVO getPersonalCenterInfo(String userId) {
        User user = accountMapper.selectById(userId);
        if(user==null){
            return null;
        }else{
            PersonalCenterInfoVO personalCenterInfoVo = new PersonalCenterInfoVO();
            BeanUtils.copyProperties(user,personalCenterInfoVo);
            return personalCenterInfoVo;
        }
    }
}
