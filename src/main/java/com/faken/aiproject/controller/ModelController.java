package com.faken.aiproject.controller;

import com.faken.aiproject.constant.Constant;
import com.faken.aiproject.po.dto.UploadNewModelDTO;
import com.faken.aiproject.po.entity.Model;
import com.faken.aiproject.po.result.Result;
import com.faken.aiproject.po.vo.ModelRankVO;
import com.faken.aiproject.service.ModelService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@RestController
@Slf4j
@RequestMapping("/model")
public class ModelController {


    @Autowired
    private ModelService modelService;

    /**
     * 模型排行
     * @return
     */
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

    /**
     * 上传新模型
     * @param uploadNewModelDTO
     * @return
     */
    @PostMapping("/uploadNewModel")
    public Result<?> uploadNewModel(UploadNewModelDTO uploadNewModelDTO) {
        String url = "D:\\QG_project\\files\\";//设置本地地址，后面更改为服务器地址
        try {
            byte[] bytes = uploadNewModelDTO.getFile().getBytes();
            Path path = Paths.get(url + uploadNewModelDTO.getFile().getOriginalFilename());
            url = path.toString();
            Files.write(path,bytes);
            Model model = new Model();
            model.setModelName(uploadNewModelDTO.getModelName());
            model.setModelType(uploadNewModelDTO.getModelType());
            model.setDescription(uploadNewModelDTO.getDescription());
            model.setUserId(uploadNewModelDTO.getUserId());
            model.setCharacterType(Constant.USER);
            int count =modelService.uploadModel(model,url);//进行模型数据插入数据库
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
    public Result<?> deleteModel(@RequestParam("modelId") String modelId) {
        int i = modelService.deleteModel(modelId);
        if (i != 0){
            return Result.success("删除模型成功");
        }else {
            return Result.error("删除模型失败，请稍后再试");
        }
    }


}
