package com.faken.aiproject.mapper;

import com.faken.aiproject.po.dto.MissionDTO;
import com.faken.aiproject.po.entity.Mission;
import com.faken.aiproject.po.entity.Model;
import com.faken.aiproject.po.entity.ModelAuth;
import com.faken.aiproject.po.entity.ModelUrl;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface MissionMapper {

    //查询用户可以使用的模型并且根据用户使用该模型的次数进行降序排序
    @Select("select * from model_auth where user_id = #{userId} order by user_use_times DESC")
    public List<ModelAuth> selectUserCanModel(int userId);


    //根据模型ID查找该模型
    @Select("select * from model where model_id = #{modelId}")
    public Model selectModelById(int modelId);


    //根据模型ID查找该模型的URL
    @Select("select * from model_url where model_id = #{modelId}")
    public ModelUrl selectUrlByModelId(int modelId);

    @Insert("insert into mission(model_list,content,image,user_id,mission_name,answer) values(#{modelList},#{content},#{image},#{userId},#{missionName},#{answer})")
    public int insertNewMission(Mission mission);
}
