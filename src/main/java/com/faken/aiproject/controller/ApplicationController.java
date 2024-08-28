package com.faken.aiproject.controller;

import com.faken.aiproject.constant.Constant;
import com.faken.aiproject.po.dto.ApplyModelDTO;
import com.faken.aiproject.po.dto.HandlerApplicationDTO;
import com.faken.aiproject.po.entity.PageBean;
import com.faken.aiproject.po.result.Result;
import com.faken.aiproject.po.vo.AllApplicationTypeVO;
import com.faken.aiproject.po.vo.MyApplicationVO;
import com.faken.aiproject.po.vo.ReceivedApplicationVO;
import com.faken.aiproject.service.ApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;

@RestController
@RequestMapping("/application")
@Validated
public class ApplicationController {

    @Autowired
    private ApplicationService applicationService;


    //首页查询各种类型的申请数量
    // /application/myApplicationCount?userId=
    @GetMapping ("/myApplicationCount")
    public Result<AllApplicationTypeVO> myApplicationCount (@RequestParam("userId") @NotNull(message = "userId不可为null") int userId){
        //查询所有申请条数
        AllApplicationTypeVO allApplicationTypeVO = new AllApplicationTypeVO();
        //查询所有申请条数
        allApplicationTypeVO.setAllApplication(applicationService.selectAllApplicationCountByUserId(userId));
        //查询未处理的条数
        allApplicationTypeVO.setRemainApplication(applicationService.selectApplicationCountByUserIdAndStatus(userId, Constant.APPLICATION_PENDING));
        //查询拒绝的条数
        allApplicationTypeVO.setRejectedApplication(applicationService.selectApplicationCountByUserIdAndStatus(userId, Constant.APPLICATION_REJECT));
        //查询通过的申请的条数
        allApplicationTypeVO.setPassedApplication(applicationService.selectApplicationCountByUserIdAndStatus(userId, Constant.APPLICATION_ACCEPT));
        return Result.success("success", allApplicationTypeVO);
    }

    //添加申请模型权限
    @PostMapping("/applyModel")
    public Result<Void> applyModel(@RequestBody ApplyModelDTO applyModelDTO){
        int i = applicationService.addApplication(applyModelDTO);
        if(i == 0){
            return Result.error("失败添加申请模型权限");
        }
        return Result.success("成功添加申请模型权限");
    }


    //查询我的申请，我向别人发出申请
    @GetMapping("/myApplication")
    public Result<PageBean<MyApplicationVO>> myApplication(@RequestParam("userId") int userId, @RequestParam("page") int page){
        PageBean<MyApplicationVO> selectAsApplicantApplicationByPage = applicationService.selectAsApplicantApplicationByPage(userId, page);
        //返回结果
        return Result.success("success", selectAsApplicantApplicationByPage);
    }

    //查询我的信息
    @GetMapping("/receivedApplication")
    public Result<PageBean<ReceivedApplicationVO>> receivedApplication(@RequestParam("userId") int userId, @RequestParam("page") int page){
        PageBean<ReceivedApplicationVO> selectAsRespondentApplicationByPage = applicationService.selectAsRespondentApplicationByPage(userId, page);
        //返回结果
        return Result.success("success", selectAsRespondentApplicationByPage);
    }


    //通过申请同意
    @PostMapping("/passApplication")
    public Result<?> passApplication(@RequestBody HandlerApplicationDTO handlerApplicationDTO){

        boolean b = applicationService.passApplication(handlerApplicationDTO.getApplicationId());
        if(b){
            return Result.success("已同意该申请");
        }else {
            return Result.error("操作失败");
        }
    }

    //拒绝该申请
    @PostMapping("/rejectApplication")
    public Result<?> rejectApplication(@RequestBody HandlerApplicationDTO handlerApplicationDTO){

        boolean b = applicationService.rejectApplication(handlerApplicationDTO.getApplicationId());
        if(b){
            return Result.success("已拒绝该申请");
        }else {
            return Result.error("操作失败");
        }
    }



}
