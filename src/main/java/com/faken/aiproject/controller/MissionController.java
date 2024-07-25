package com.faken.aiproject.controller;

import com.faken.aiproject.po.dto.MissionDTO;
import com.faken.aiproject.po.result.Result;
import com.faken.aiproject.po.vo.RecentMissionVO;
import com.faken.aiproject.po.vo.UserCanUseModelVO;
import com.faken.aiproject.service.MissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/mission")
public class MissionController {

    @Autowired
    private MissionService missionService;


    @GetMapping("/queryUserCanUseModel")
    public Result<List<UserCanUseModelVO>> queryUserCanUseModel(@RequestParam("userId") int userId){
        //进行业务查询
        List<UserCanUseModelVO> list = missionService.selectUserCanModel(userId);
        //返回结果
        return Result.success("success", list);
    }

    @PostMapping("/saveMission")
    public Result<?> saveMission(@RequestBody MissionDTO missionDTO) {
        if (missionService.saveMission(missionDTO) != 0){
            return Result.success("任务上传成功");
        }else {
            return Result.error("任务上传失败");
        }
    }

    @GetMapping("/homePageRecentMission")
    public Result<List<RecentMissionVO>> homePageRecentMission(@RequestParam("userId") int userId) {
        List<RecentMissionVO> list = missionService.getHomePageRecentMission(userId);

        return Result.success("", list);
    }



}
