package com.faken.aiproject.service.impl;

import com.faken.aiproject.constant.Constant;
import com.faken.aiproject.mapper.ModelMapper;
import com.faken.aiproject.po.entity.Model;
import com.faken.aiproject.po.entity.ModelAuth;
import com.faken.aiproject.po.result.PageBean;
import com.faken.aiproject.po.vo.ModelRankVO;
import com.faken.aiproject.po.vo.PageQueryModelVO;
import com.faken.aiproject.service.ModelService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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
//            modelRankVO.setCharacterType(model.getCharacterType());
//            modelRankVO.setModelName(model.getModelName());
//            modelRankVO.setCreateTime(model.getCreateTime());
            BeanUtils.copyProperties(model, modelRankVO);
            listModelRankVO.add(modelRankVO);
        }
        return listModelRankVO;
    }

    @Override
    public PageBean<PageQueryModelVO> pageQuery(int userId, int page, String name) {
        int offset = (page - 1) * 6;
        List<Model> models = modelMapper.pageQuery(offset, name);
        List<PageQueryModelVO> pageQueryModelVOS = new ArrayList<>();
        PageBean<PageQueryModelVO> pageBean = new PageBean<>();

        pageBean.setTotal((long) models.size());

        for (Model model : models) {
           PageQueryModelVO pageQueryModelVO = new PageQueryModelVO();
           ModelAuth modelAuth = modelMapper.selectByUserId(userId, model.getModelId());
           if (!Objects.isNull(modelAuth)) {
               // 依据用户ID和模型ID可以在权限表中查到模型, 说明用户通过了申请
               // 或者模型是他自己的, 或者他已将官方模型加入自己的库, 为可使用模型
               pageQueryModelVO.setSign(Constant.ACCESSED_MODEL);
           } else {
               // 查不到, 说明模型非用户所有, 或者申请未通过, 为可申请模型
               pageQueryModelVO.setSign(Constant.APPLICABLE_MODEL);
           }
           BeanUtils.copyProperties(model, pageQueryModelVO);
           pageQueryModelVOS.add(pageQueryModelVO);
       }
       return pageBean;
    }
}
