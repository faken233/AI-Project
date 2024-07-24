package com.faken.aiproject.mapper;

import com.faken.aiproject.po.dto.LoginDTO;
import com.faken.aiproject.po.dto.RegisterDTO;
import com.faken.aiproject.po.entity.User;
import org.apache.ibatis.annotations.*;

@Mapper
public interface AccountMapper {

    @Select("select * from users where email = #{email} and password = #{password}")
    User selectByEmailAndPassword(LoginDTO loginDTO);

    @Select("select * from users where email = #{email}")
    User selectByEmail(RegisterDTO registerDTO);

    @Options(useGeneratedKeys = true, keyProperty = "userId")
    @Insert("insert into users (password, email, user_name) VALUES (#{password}, #{email}, #{userName})")
    void registerNewUser(User newUser);


}
