package com.faken.aiproject.mapper;

import com.faken.aiproject.po.dto.LoginDTO;
import com.faken.aiproject.po.dto.RegisterDTO;
import com.faken.aiproject.po.entity.User;
import org.apache.ibatis.annotations.*;

@Mapper
public interface AccountMapper {

    @Select("select * from user where email = #{email} and password = #{password}")
    User selectByEmailAndPassword(LoginDTO loginDTO);

    @Select("select * from user where email = #{email}")
    User selectByEmail(RegisterDTO registerDTO);

    @Options(useGeneratedKeys = true, keyProperty = "userId")
    @Insert("insert into user (password, email, username) VALUES (#{password}, #{email}, #{username})")
    void registerNewUser(User newUser);

    @Select("select * from user where user_id=#{userId}")
    User selectById(String userId);
}
