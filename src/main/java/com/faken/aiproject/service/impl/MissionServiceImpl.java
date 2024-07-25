package com.faken.aiproject.service.impl;

import com.faken.aiproject.constant.Constant;
import com.faken.aiproject.mapper.MissionMapper;
import com.faken.aiproject.po.entity.Model;
import com.faken.aiproject.po.entity.ModelAuth;
import com.faken.aiproject.po.entity.ModelUrl;
import com.faken.aiproject.po.vo.UserCanUseModelVO;
import com.faken.aiproject.service.MissionService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MissionServiceImpl implements MissionService {


    @Autowired
    private MissionMapper missionMapper;

    //查询用户可以使用的模型并通过对该模型使用次数的降序
    @Override
    public List<UserCanUseModelVO> selectUserCanModel(int userId) {
        List<ModelAuth> modelAuths = missionMapper.selectUserCanModel(userId);
        List<UserCanUseModelVO> list = new ArrayList<>();
        //遍历集合中的并且进行复制数据到VO
        for (ModelAuth modelAuth : modelAuths) {
            UserCanUseModelVO userCanUseModelVO = new UserCanUseModelVO();
            //设置好模型ID
            userCanUseModelVO.setModelId(modelAuth.getModelId());
            //根据模型ID查找它的名字和是否为大模型的isAPI
            Model model = missionMapper.selectModelById(modelAuth.getModelId());
            userCanUseModelVO.setModelName(model.getModelName());
            userCanUseModelVO.setIsAPI(model.getBigModel());
            //查找该模型的URL
            ModelUrl modelUrl = missionMapper.selectUrlByModelId(modelAuth.getModelId());
            userCanUseModelVO.setModelUrl(modelUrl.getUrl());
            userCanUseModelVO.setWeight(Constant.DEFAULT_WEIGHT); //设置默认权重为1
            list.add(userCanUseModelVO);
        }
        //返回结果
        return list;

    }
}
