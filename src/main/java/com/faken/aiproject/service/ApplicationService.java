package com.faken.aiproject.service;

import com.faken.aiproject.po.entity.Application;
import com.faken.aiproject.po.entity.PageBean;
import com.faken.aiproject.po.vo.MyApplicationVO;
import com.faken.aiproject.po.vo.ReceivedApplicationVO;

public interface ApplicationService {

    //查询一个用户的各种状态申请的数量
    public int selectApplicationCountByUserIdAndStatus(int userId, int status);


    //通过用户ID查询有关于某个用户的总申请数量
    public int selectAllApplicationCountByUserId(int userId);


    //添加申请消息
    public int addApplication(Application application);

    //分页查询我是申请人的申请
    public PageBean<MyApplicationVO> selectAsApplicantApplicationByPage(int userId, int begin);

    //分页查询我是被申请人的申请
    public PageBean<ReceivedApplicationVO> selectAsRespondentApplicationByPage(int userId, int begin);


    //同意申请
    public boolean passApplication(int applicationId);


}
