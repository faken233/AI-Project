package com.faken.aiproject.service.impl;

import com.faken.aiproject.mapper.ApplicationMapper;
import com.faken.aiproject.po.entity.Application;
import com.faken.aiproject.po.entity.Model;
import com.faken.aiproject.po.entity.PageBean;
import com.faken.aiproject.po.entity.User;
import com.faken.aiproject.po.vo.MyApplicationVO;
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
        pageBean.setCount(count);
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

//    @Override
//    public PageBean<Application> selectAsRespondentApplicationByPage(int userId, int begin) {
//        PageBean<Application> pageBean  = new PageBean<>();
//        //查询总数量
//        int count = applicationMapper.asRespondentApplicationCount(userId);
//        pageBean.setCount(count);
//        //具体东西
//
//
//    }


}
