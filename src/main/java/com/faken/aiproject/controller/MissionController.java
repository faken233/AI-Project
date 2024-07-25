package com.faken.aiproject.controller;

import com.faken.aiproject.po.result.Result;
import com.faken.aiproject.po.vo.UserCanUseModelVO;
import com.faken.aiproject.service.MissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/mission")
public class MissionController {

    @Autowired
    private MissionService missionService;


    /**
     * 查询用户可以使用的模型并且根据用户的使用次数排序
     * @param userId
     * @return
     */

    @GetMapping("/queryUserCanUserModel")
    public Result<List<UserCanUseModelVO>> queryUserCanUseModel(@RequestParam("userId") int userId){
        //进行业务查询
        List<UserCanUseModelVO> list = missionService.selectUserCanModel(userId);
        //返回结果
        return Result.success("success", list);
    }



}
