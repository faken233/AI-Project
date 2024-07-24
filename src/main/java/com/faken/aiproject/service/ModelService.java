package com.faken.aiproject.service;

import com.faken.aiproject.po.result.PageBean;
import com.faken.aiproject.po.entity.Model;
import com.faken.aiproject.po.vo.ModelRankVO;
import com.faken.aiproject.po.vo.PageQueryModelVO;

import java.util.List;

public interface ModelService {

    List<ModelRankVO> modelRank();

    PageBean<PageQueryModelVO> pageQuery(int userId, int page, String name);
    int uploadModel(Model model,String url);

    int deleteModel(String modelId);
}
