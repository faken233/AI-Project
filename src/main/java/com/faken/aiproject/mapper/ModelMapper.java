package com.faken.aiproject.mapper;

import com.faken.aiproject.po.entity.Model;
import com.faken.aiproject.po.entity.ModelAuth;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface ModelMapper {

    @Select("select * from model order by  used_times DESC limit 10 ")
    List<Model> selectByUsedTimes();

    List<Model> pageQuery(@Param("offset") int offset, @Param("name") String name);

    @Select("select * from model_auth where user_id = #{userId} and model_id = #{modelId}")
    ModelAuth selectByUserId(int userId, int modelId);
}
