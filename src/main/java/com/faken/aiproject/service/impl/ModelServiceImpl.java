package com.faken.aiproject.service.impl;

import com.faken.aiproject.constant.Constant;
import com.faken.aiproject.mapper.ModelMapper;
import com.faken.aiproject.po.entity.Model;
import com.faken.aiproject.po.entity.ModelAuth;
import com.faken.aiproject.po.vo.ModelRankVO;
import com.faken.aiproject.service.ModelService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class ModelServiceImpl implements ModelService {
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public List<ModelRankVO>  modelRank() {
        List<Model> listModel = modelMapper.selectByUsedTimes();//获取到根据使用频率前十个模型
        List<ModelRankVO> listModelRankVO = new ArrayList<ModelRankVO>();//包装VO类的list集合
        ModelRankVO modelRankVO = null;
        for (Model model : listModel) {//包装VO类
            modelRankVO = new ModelRankVO();
            BeanUtils.copyProperties(model, modelRankVO);
            listModelRankVO.add(modelRankVO);
        }
        return listModelRankVO;
    }


    @Override
    @Transactional
    public int uploadModel(Model model,String url) {
        int i  = modelMapper.insertModel(model);//插入模型表
        if (i == 1){
            //插入成功
            int modelId = model.getModelId();
            if (modelMapper.insertModelUrl(modelId,url) ==1 ){//插入模型地址表
                ModelAuth modelAuth = new ModelAuth();
                BeanUtils.copyProperties(model,modelAuth);
                modelAuth.setDeletable(Constant.CAN_DELETE);
                return modelMapper.insertModelAuth(modelAuth);//插入用户模型权利表
            }else {
                return 0;
            }
        }else {
            return 0;
        }
    }

    @Override
    public int deleteModel(String modelId) {
        if (modelMapper.deleteModelByModelId(modelId) != 0){
            if (modelMapper.deleteModelUrlByModelId(modelId) != 0) {
                return modelMapper.deleteModelAuthByModelId(modelId);
            }else {
                return 0;
            }
        }else
        {
            return 0;
        }

    }
}
