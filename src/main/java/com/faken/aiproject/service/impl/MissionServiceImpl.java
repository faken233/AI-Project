package com.faken.aiproject.service.impl;

import com.alibaba.fastjson2.JSON;
import com.faken.aiproject.constant.Constant;
import com.faken.aiproject.mapper.MissionMapper;
import com.faken.aiproject.mapper.ModelMapper;
import com.faken.aiproject.po.dto.MissionDTO;
import com.faken.aiproject.po.dto.ModelDTO;
import com.faken.aiproject.po.dto.ModelListDTO;
import com.faken.aiproject.po.entity.Mission;
import com.faken.aiproject.po.entity.Model;
import com.faken.aiproject.po.entity.ModelAuth;
import com.faken.aiproject.po.entity.ModelUrl;
import com.faken.aiproject.po.vo.RecentMissionVO;
import com.faken.aiproject.po.vo.UserCanUseModelVO;
import com.faken.aiproject.service.MissionService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MissionServiceImpl implements MissionService {

    private final MissionMapper missionMapper;
    private final ModelMapper modelMapper;

    public MissionServiceImpl(MissionMapper missionMapper, ModelMapper modelMapper) {
        this.missionMapper = missionMapper;
        this.modelMapper = modelMapper;
    }

    // 查询用户可以使用的模型并通过对该模型使用次数的降序
    @Override
    public List<UserCanUseModelVO> selectUserCanModel(int userId) {
        List<ModelAuth> modelAuths = missionMapper.selectUserCanModel(userId);
        List<UserCanUseModelVO> list = new ArrayList<>();
        //遍历集合中的并且进行复制数据到VO
        for (ModelAuth modelAuth : modelAuths) {
            UserCanUseModelVO userCanUseModelVO = new UserCanUseModelVO();
            // 设置好模型ID
            userCanUseModelVO.setModelId(modelAuth.getModelId());
            // 根据模型ID查找它的名字和是否为大模型的isAPI
            Model model = missionMapper.selectModelById(modelAuth.getModelId());
            userCanUseModelVO.setModelName(model.getModelName());
            userCanUseModelVO.setIsAPI(model.getBigModel());
            // 查找该模型的URL
            ModelUrl modelUrl = missionMapper.selectUrlByModelId(modelAuth.getModelId());
            userCanUseModelVO.setModelUrl(modelUrl.getUrl());
            userCanUseModelVO.setWeight(Constant.DEFAULT_WEIGHT); // 设置默认权重为1
            list.add(userCanUseModelVO);
        }
        //返回结果
        return list;

    }

    @Override
    public int saveMission(MissionDTO missionDTO) {

        Mission mission = new Mission();

        BeanUtils.copyProperties(missionDTO, mission);
        List<ModelListDTO> modelList = missionDTO.getModelList();
        String modelListJsonString = JSON.toJSONString(modelList);
        mission.setModelList(modelListJsonString);


        System.out.println(mission);
        //插入任务列表
        if(missionMapper.insertNewMission(mission) != 0) {
            List<ModelListDTO> modelListDTOList = missionDTO.getModelList();
            for (ModelListDTO modelListDTO : modelListDTOList) {
                List<ModelDTO> modelsDTOList = modelListDTO.getModels();
                for (ModelDTO modelsDTO : modelsDTOList) {
                    String modelUrl = modelsDTO.getModelUrl();
                    //通过模型url查找模型id
                    ModelUrl model= modelMapper.selectModelByModelUrl(modelUrl);
                    int modelId = model.getModelId();
                    //根据模型id增加模型表模型使用次数
                    if (0 != modelMapper.updateUsedTimesByModelId(modelId)){
                        return  modelMapper.updateUsedTimesByModelIdAndUserId(missionDTO.getUserId(),modelId);
                    }else {
                        return 0;
                    }
                }
            }
        }else {
            return 0;
        }
        return 0;
    }

    @Override
    public List<RecentMissionVO> getHomePageRecentMission(int userId) {
        List<Mission> missionList = missionMapper.selectRecentThreeMission(userId);
        List<RecentMissionVO> list = new ArrayList<>();
        for (Mission mission : missionList) {
            RecentMissionVO recentMissionVO = new RecentMissionVO();
            BeanUtils.copyProperties(mission, recentMissionVO);
            list.add(recentMissionVO);
        }
        return list;
    }

    @Override
    public List<MissionDTO> getWorkspaceMission(int userId) {
        List<Mission> missionList = missionMapper.selectMissionByUserId(userId);
        List<MissionDTO> list = new ArrayList<>();
        for (Mission mission : missionList) {
            MissionDTO missionDTO = new MissionDTO();
            BeanUtils.copyProperties(mission, missionDTO);
            List<ModelListDTO> modelListDTOS = JSON.parseArray(mission.getModelList(), ModelListDTO.class);
            missionDTO.setModelList(modelListDTOS);
            list.add(missionDTO);
        }
        return list;
    }
}
