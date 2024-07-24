package com.faken.aiproject.service.impl;

import com.faken.aiproject.mapper.ModelMapper;
import com.faken.aiproject.po.entity.Model;
import com.faken.aiproject.po.vo.ModelRankVO;
import com.faken.aiproject.service.ModelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
            modelRankVO.setCharacterType(model.getCharacterType());
            modelRankVO.setModelName(model.getModelName());
            modelRankVO.setCreateTime(model.getCreateTime());
            listModelRankVO.add(modelRankVO);
        }
        return listModelRankVO;
    }
}
