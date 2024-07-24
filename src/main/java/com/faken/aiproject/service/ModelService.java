package com.faken.aiproject.service;

import com.faken.aiproject.po.dto.UploadNewModelDTO;
import com.faken.aiproject.po.result.PageBean;
import com.faken.aiproject.po.vo.ModelRankVO;
import com.faken.aiproject.po.vo.MyModelVO;
import com.faken.aiproject.po.vo.PageQueryModelVO;

import java.io.IOException;
import java.util.List;

public interface ModelService {

    List<ModelRankVO> modelRank();

    PageBean<PageQueryModelVO> pageQuery(int userId, int page, String name);
    int uploadModel(UploadNewModelDTO uploadNewModelDTO) throws IOException;

    int deleteModel(String modelId);

    PageBean<MyModelVO> personalCenterPageQuery(int userId, int page);
}
