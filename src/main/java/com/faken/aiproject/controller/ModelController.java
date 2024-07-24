package com.faken.aiproject.controller;

import com.faken.aiproject.po.result.Result;
import com.faken.aiproject.po.vo.ModelRankVO;
import com.faken.aiproject.service.ModelService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/model")
public class ModelController {

    @Autowired
    private ModelService modelService;

    @GetMapping("/modelRank")
    public Result<?> modelRank() {
        List<ModelRankVO> listModelRankVO = modelService.modelRank();
        System.out.println(listModelRankVO);
        String msg = null;
        if (listModelRankVO.size() > 0 && listModelRankVO != null) {//判断查询是否为空
            msg = "获取模型排行成功";
            return Result.success(msg,listModelRankVO);
        }else {
            msg = "获取模型排行失败";
            return Result.error(msg);
        }

    }
}
