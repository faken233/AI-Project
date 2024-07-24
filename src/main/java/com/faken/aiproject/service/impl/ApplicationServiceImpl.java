package com.faken.aiproject.service.impl;

import com.faken.aiproject.mapper.ApplicationMapper;
import com.faken.aiproject.po.entity.Application;
import com.faken.aiproject.po.entity.Model;
import com.faken.aiproject.po.entity.PageBean;
import com.faken.aiproject.po.entity.User;
import com.faken.aiproject.po.vo.MyApplicationVO;
import com.faken.aiproject.po.vo.ReceivedApplicationVO;
import com.faken.aiproject.service.ApplicationService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ApplicationServiceImpl implements ApplicationService {

    @Autowired
    private ApplicationMapper applicationMapper;

    //查询一个用户的各种状态申请的数量
    @Override
    public int selectApplicationCountByUserIdAndStatus(int userId, int status) {
        return applicationMapper.selectApplicationCountByUserIdAndStatus(userId, status);
    }

    //通过用户ID查询有关于某个用户的总申请数量
    @Override
    public int selectAllApplicationCountByUserId(int userId) {
        return applicationMapper.selectAllApplicationCountByUserId(userId);
    }

    //添加一个申请
    @Override
    public int addApplication(Application application) {
        application.setStatus(0);
        int i = applicationMapper.selectRespondentIdByModelId(application.getModelId());
        application.setRespondentId(i);
        //不为0则表示添加成功
        return applicationMapper.addApplication(application);

    }

    //分页查询用户的我的申请
    @Override
    public PageBean<MyApplicationVO> selectAsApplicantApplicationByPage(int userId, int begin) {
        PageBean<MyApplicationVO> pageBean  = new PageBean<>();
        //查询总数量
        int count = applicationMapper.asApplicantApplicationCount(userId);
        pageBean.setTotal(count);
        begin = (begin - 1) * 6;
        //具体东西
        List<MyApplicationVO> list = new ArrayList<>();
        List<Application> applications = applicationMapper.selectAsApplicantApplicationByPage(userId, begin, 6);
        for (Application application : applications) {
            MyApplicationVO myApplicationVO = new MyApplicationVO();
            BeanUtils.copyProperties(application, myApplicationVO);
            //获取申请模型的名字
            Model model = applicationMapper.selectModelByModelId(application.getModelId());
            myApplicationVO.setModelName(model.getModelName());
            //获取被申请人的名字
            User user = applicationMapper.selectUserById(application.getRespondentId());
            myApplicationVO.setRespondentName(user.getUsername());
            list.add(myApplicationVO);
        }
        pageBean.setData(list);
        return pageBean;
    }


    // 查询我的信息，也就是别人对我的申请
    @Override
    public PageBean<ReceivedApplicationVO> selectAsRespondentApplicationByPage(int userId, int begin) {
        PageBean<ReceivedApplicationVO> pageBean  = new PageBean<>();
        //查询总数量
        int count = applicationMapper.asRespondentApplicationCount(userId);
        pageBean.setTotal(count);
        begin = (begin - 1) * 6;
        //具体东西
        List<ReceivedApplicationVO> list = new ArrayList<>();
        List<Application> applications = applicationMapper.selectAsRespondentApplicationByPage(userId, begin, 6);
        for (Application application : applications) {
            ReceivedApplicationVO receivedApplicationVO = new ReceivedApplicationVO();
            BeanUtils.copyProperties(application, receivedApplicationVO);
            //获取申请模型的名字
            Model model = applicationMapper.selectModelByModelId(application.getModelId());
            receivedApplicationVO.setModelName(model.getModelName());
            //获取申请人的名字
            User user = applicationMapper.selectUserById(application.getApplicantId());
            receivedApplicationVO.setApplicantName(user.getUsername());
            list.add(receivedApplicationVO);
        }
        pageBean.setData(list);
        return pageBean;
    }



}
