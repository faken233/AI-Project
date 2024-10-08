package com.faken.aiproject.service.impl;

import com.faken.aiproject.constant.Constant;
import com.faken.aiproject.mapper.ApplicationMapper;
import com.faken.aiproject.po.dto.ApplyModelDTO;
import com.faken.aiproject.po.entity.*;
import com.faken.aiproject.po.vo.MyApplicationVO;
import com.faken.aiproject.po.vo.ReceivedApplicationVO;
import com.faken.aiproject.service.ApplicationService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ApplicationServiceImpl implements ApplicationService {

    private final ApplicationMapper applicationMapper;

    public ApplicationServiceImpl(ApplicationMapper applicationMapper) {
        this.applicationMapper = applicationMapper;
    }

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
    public int addApplication(ApplyModelDTO applyModelDTO) {
        Model model = applicationMapper.selectModelByModelId(applyModelDTO.getModelId());
        if (model.getCharacterType() == 0){
            //该模型是官方的
            ModelAuth modelAuth = new ModelAuth();
            modelAuth.setUserId(applyModelDTO.getUserId());
            modelAuth.setModelId(applyModelDTO.getModelId());
            modelAuth.setDeletable(Constant.CANNOT_DELETE);
            //申请官方模型成功
            return applicationMapper.addModelAuth(modelAuth);
        }else if (model.getCharacterType() == 1){
            Application application = new Application();
            application.setApplicantId(applyModelDTO.getUserId());
            application.setModelId(model.getModelId());
            application.setRespondentId(model.getUserId());
            application.setStatus(Constant.APPLICATION_PENDING);
            return applicationMapper.addApplication(application);
        }
        return 0;
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

    //通过申请
    @Override
    public boolean passApplication(int applicationId) {
        Application application = applicationMapper.selectByApplicationId(applicationId);
        applicationMapper.changeApplicationStatusPass(applicationId); //更改为同意的状态码
        ModelAuth modelAuth = new ModelAuth();
        modelAuth.setModelId(application.getModelId()); //模型id
        modelAuth.setUserId(application.getApplicantId());
        int i = applicationMapper.addModelAuth(modelAuth);
        return i != 0;
    }

    //拒绝申请
    @Override
    public boolean rejectApplication(int applicationId) {
        applicationMapper.changeApplicationStatusReject(applicationId); //更改为拒绝的状态码
        return true;
    }

}
