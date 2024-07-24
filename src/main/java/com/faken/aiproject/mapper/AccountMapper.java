package com.faken.aiproject.mapper;

import com.faken.aiproject.po.dto.LoginDTO;
import com.faken.aiproject.po.dto.RegisterDTO;
import com.faken.aiproject.po.entity.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface AccountMapper {

    @Select("select * from users where email = #{email} and password = #{password}")
    User selectByEmailAndPassword(LoginDTO loginDTO);

    @Select("select * from users where email = #{email}")
    User selectByEmail(RegisterDTO registerDTO);

    @Options(useGeneratedKeys = true, keyProperty = "userId")
    @Insert("insert into users (password, email, username) VALUES (#{password}, #{email}, #{userName})")
    void registerNewUser(User newUser);
}
