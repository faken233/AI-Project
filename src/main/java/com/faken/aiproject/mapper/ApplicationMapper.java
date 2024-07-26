package com.faken.aiproject.mapper;

import com.faken.aiproject.po.entity.Application;
import com.faken.aiproject.po.entity.Model;
import com.faken.aiproject.po.entity.ModelAuth;
import com.faken.aiproject.po.entity.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface ApplicationMapper {

    //通过用户ID查询有关于某个用户的总申请数量
    @Select("select count(*) from application where applicant_id = #{userId} or respondent_id = #{userId}")
    int selectAllApplicationCountByUserId(int userId);

    //查询一个用户的各种状态申请的数量
    @Select("select count(*) from application where (applicant_id = #{userId} or respondent_id = #{userId}) and status = #{status}")
    int selectApplicationCountByUserIdAndStatus(@Param("userId") int userId, @Param("status") int status);


    //添加申请权限
    @Insert("insert into application (applicant_id, respondent_id, model_id, status) values (#{applicantId},#{respondentId},#{modelId},#{status})")
    int addApplication(Application application);


    // TODO Mapper位置?
    //通过模型id查找所拥有人的id
    @Select("select user_id from model where model_id = #{modelId}")
    int selectRespondentIdByModelId(int modelId);


    //查询我是被申请人的申请的总条数
    @Select("select count(*) from application where respondent_id = #{userId}")
    int asRespondentApplicationCount(int userId);

    //查询我是申请人的申请的总条数
    @Select("select count(*) from application where applicant_id = #{userId}")
    int asApplicantApplicationCount(int userId);

    //分页查询我是被申请人的申请
    @Select("select * from application where respondent_id = #{userId} order by status limit #{begin}, #{size} ")
    List<Application> selectAsRespondentApplicationByPage(@Param("userId") int userId, @Param("begin") int begin, @Param("size") int size);

    //分页查询我是申请人的申请
    @Select("select * from application where applicant_id = #{userId} order by status limit #{begin}, #{size} ")
    List<Application> selectAsApplicantApplicationByPage(@Param("userId") int userId, @Param("begin") int begin, @Param("size") int size);

    // TODO Mapper位置?
    //暂时，通过模型ID查找模型
    @Select("select * from model where model_id = #{modelId}")
    Model selectModelByModelId(int modelId);


    // TODO Mapper位置?
    //暂时，通过id查找一个人
    @Select("select * from user where user_id = #{id}")
    User selectUserById(int id);

    //根据申请的id查询这条申请
    @Select("select * from application where application_id = #{applicationId}")
    Application selectByApplicationId(int applicationId);

    @Update("update application set status = 1 where application_id = #{applicationId}")
    void changeApplicationStatusPass(int applicationId);

    // TODO Mapper位置?
    //添加一个用户申请通过的权限信息
    @Insert("insert into model_auth (model_id, user_id, deletable) values (#{modelId}, #{userId}, #{deletable})")
    int addModelAuth(ModelAuth modelAuth);

    //拒绝申请
    @Update("update application set status = 2 where application_id = #{applicationId}")
    void changeApplicationStatusReject(int applicationId);

    @Select("select * from application where applicant_id = #{userId} and model_id = #{modelId}")
    Application selectByApplicantIdAndModelId(int userId, int modelId);
}
