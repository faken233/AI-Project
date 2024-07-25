package com.faken.aiproject.service;

import com.faken.aiproject.po.vo.UserCanUseModelVO;

import java.util.List;

public interface MissionService {

    //查询用户可以使用的模型并通过对该模型使用次数的降序
    public List<UserCanUseModelVO> selectUserCanModel(int userId);

}
