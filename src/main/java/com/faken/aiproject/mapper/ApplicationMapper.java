package com.faken.aiproject.mapper;

import com.faken.aiproject.po.entity.Application;
import com.faken.aiproject.po.entity.Model;
import com.faken.aiproject.po.entity.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface ApplicationMapper {

    //通过用户ID查询有关于某个用户的总申请数量
    @Select("select count(*) from application where applicant_id = #{userId} or respondent_id = #{userId}")
    public int selectAllApplicationCountByUserId(int userId);

    //查询一个用户的各种状态申请的数量
    @Select("select count(*) from application where (applicant_id = #{userId} or respondent_id = #{userId}) and status = #{status}")
    public int selectApplicationCountByUserIdAndStatus(@Param("userId") int userId, @Param("status") int status);


    //添加申请权限
    @Insert("insert into application (applicant_id, respondent_id, model_id, status) values (#{applicantId},#{respondentId},#{modelId},#{status})")
    public int addApplication(Application application);


    //暂时的
    //通过模型id查找所拥有人的id
    @Select("select user_id from model where model_id = #{modelId}")
    public int selectRespondentIdByModelId(int modelId);


    //查询我是被申请人的申请的总条数
    @Select("select count(*) from application where respondent_id = #{userId}")
    public int asRespondentApplicationCount(int userId);

    //查询我是申请人的申请的总条数
    @Select("select count(*) from application where applicant_id = #{userId}")
    public int asApplicantApplicationCount(int userId);

    //分页查询我是被申请人的申请
    @Select("select * from application where respondent_id = #{userId}  limit #{begin}, #{size} ")
    public List<Application> selectAsRespondentApplicationByPage(@Param("userId") int userId, @Param("begin") int begin,@Param("size") int size);

    //分页查询我是申请人的申请
    @Select("select * from application where applicant_id = #{userId}  limit #{begin}, #{size} ")
    public List<Application> selectAsApplicantApplicationByPage(@Param("userId") int userId, @Param("begin") int begin,@Param("size") int size);

    //暂时，通过模型ID查找模型
    @Select("select * from model where model_id = #{modelId}")
    public Model selectModelByModelId(int modelId);


    //暂时，通过id查找一个人
    @Select("select * from user where user_id = #{id}")
    public User selectUserById(int id);

}
