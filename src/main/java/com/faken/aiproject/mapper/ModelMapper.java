package com.faken.aiproject.mapper;

import com.faken.aiproject.po.entity.Model;
import com.faken.aiproject.po.entity.ModelAuth;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface ModelMapper {

    @Select("select * from model order by  used_times DESC limit 10 ")
    List<Model> selectByUsedTimes();


    @Options(useGeneratedKeys = true,keyProperty = "modelId")
    @Insert("insert into model(user_id,model_name,model_type,description,character_type) " +
            "values (#{userId},#{modelName},#{modelType},#{description},#{characterType})")
    int insertModel(Model model);

    @Insert("insert into model_url(model_id,url) values (#{modelId},#{url})")
    int insertModelUrl(@Param("modelId")int modelId,@Param("url") String url);

    @Insert("insert into model_auth(model_id,user_id,deletable) values (#{modelId},#{userId},#{deletable})")
    int insertModelAuth(ModelAuth modelAuth);

    @Delete("delete from model where model_id = #{modelId} ")
    int deleteModelByModelId(String modelId);

    @Delete("delete from model_auth where model_id = #{modelId}")
    int deleteModelAuthByModelId(String modelId);

    @Delete("delete from model_url where model_id = #{modelId}")
    int deleteModelUrlByModelId(String modelId);


    List<Model> pageQuery(@Param("offset") int offset, @Param("name") String name);

    @Select("select * from model_auth where user_id = #{userId} and model_id = #{modelId}")
    ModelAuth selectByUserId(int userId, int modelId);

    @Select("select * from model where user_id = #{userId} limit #{offset}, 6")
    List<Model> personalCenterPageQuery(int userId, int offset);
}
