package com.faken.aiproject.service;

import com.faken.aiproject.po.entity.Model;
import com.faken.aiproject.po.vo.ModelRankVO;

import java.util.List;

public interface ModelService {

    List<ModelRankVO> modelRank();
    int uploadModel(Model model,String url);
}
