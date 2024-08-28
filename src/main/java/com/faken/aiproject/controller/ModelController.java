package com.faken.aiproject.controller;

import com.faken.aiproject.po.dto.UploadNewModelDTO;
import com.faken.aiproject.po.result.PageBean;
import com.faken.aiproject.po.result.Result;
import com.faken.aiproject.po.vo.ModelRankVO;
import com.faken.aiproject.po.vo.MyModelVO;
import com.faken.aiproject.po.vo.PageQueryModelVO;
import com.faken.aiproject.service.ModelService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@Slf4j
@RequestMapping("/model")
public class ModelController {


    @Autowired
    private ModelService modelService;

    @GetMapping("/modelRank")
    public Result<List<ModelRankVO>> modelRank() {
        List<ModelRankVO> listModelRankVO = modelService.modelRank();
        System.out.println(listModelRankVO);
        String msg;
        if (!listModelRankVO.isEmpty()) {//判断查询是否为空
            msg = "获取模型排行成功";
            return Result.success(msg,listModelRankVO);
        }else {
            msg = "获取模型排行失败";
            return Result.error(msg);
        }
    }

    @GetMapping("/allModels")
    public Result<PageBean<PageQueryModelVO>> allModels(@RequestParam("userId") int userId, @RequestParam("page") int page, @RequestParam("name") String name) {

        PageBean<PageQueryModelVO> pageBean = modelService.pageQuery(userId, page, name);

        return Result.success("", pageBean);
    }

    @PostMapping("/uploadNewModel")
    public Result<Void> uploadNewModel(UploadNewModelDTO uploadNewModelDTO) {
        try {
            int count =modelService.uploadModel(uploadNewModelDTO);//进行模型数据插入数据库
            if (count == 1){
                //添加成功
                return Result.success("模型上传成功");

            }else {
                return Result.error("模型上传失败");
            }
        } catch (IOException e) {
            return Result.error("模型上传异常，请稍后再试");
        }
    }


    @DeleteMapping("/deleteModel")
    public Result<Void> deleteModel(@RequestParam("modelId") String modelId) {
        int i = modelService.deleteModel(modelId);
        if (i != 0){
            return Result.success("删除模型成功");
        }else {
            return Result.error("删除模型失败，请稍后再试");
        }
    }

    @GetMapping("/myModel")
    public Result<PageBean<MyModelVO>> myModel(@RequestParam("userId") int userId, @RequestParam("page") int page) {

        PageBean<MyModelVO> pageBean = modelService.personalCenterPageQuery(userId, page);

        return Result.success("", pageBean);
    }






}
