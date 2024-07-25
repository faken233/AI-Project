package com.faken.aiproject.service;

import com.faken.aiproject.po.dto.MissionDTO;
import com.faken.aiproject.po.vo.RecentMissionVO;
import com.faken.aiproject.po.vo.UserCanUseModelVO;

import java.util.List;

public interface MissionService {

    //查询用户可以使用的模型并通过对该模型使用次数的降序
    public List<UserCanUseModelVO> selectUserCanModel(int userId);

    //保存用户生成任务列表
    int saveMission(MissionDTO missionDTO);

    List<RecentMissionVO> getHomePageRecentMission(int userId);
}
