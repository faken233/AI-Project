package com.faken.aiproject.service.impl;

import com.faken.aiproject.constant.Constant;
import com.faken.aiproject.mapper.ModelMapper;
import com.faken.aiproject.po.dto.UploadNewModelDTO;
import com.faken.aiproject.po.entity.Model;
import com.faken.aiproject.po.entity.ModelAuth;
import com.faken.aiproject.po.result.PageBean;
import com.faken.aiproject.po.vo.ModelRankVO;
import com.faken.aiproject.po.vo.MyModelVO;
import com.faken.aiproject.po.vo.PageQueryModelVO;
import com.faken.aiproject.service.ModelService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
public class ModelServiceImpl implements ModelService {
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public List<ModelRankVO>  modelRank() {
        List<Model> listModel = modelMapper.selectByUsedTimes();//获取到根据使用频率前十个模型
        List<ModelRankVO> listModelRankVO = new ArrayList<>();//包装VO类的list集合
        ModelRankVO modelRankVO;
        for (Model model : listModel) {//包装VO类
            modelRankVO = new ModelRankVO();
            BeanUtils.copyProperties(model, modelRankVO);
            listModelRankVO.add(modelRankVO);
        }
        return listModelRankVO;
    }


    @Override
    @Transactional
    public int uploadModel(UploadNewModelDTO uploadNewModelDTO) throws IOException {
        // TODO 设置云端oss

        String url = "D:\\QG_project\\files\\";//设置本地地址，后面更改为服务器地址
        byte[] bytes = uploadNewModelDTO.getFile().getBytes();
        // 生成唯一的文件名
        String originalFilename = uploadNewModelDTO.getFile().getOriginalFilename();
        String fileExtension = originalFilename.substring(originalFilename.lastIndexOf(".") + 1);
        String uniqueFilename = UUID.randomUUID().toString() + "." + fileExtension;

        //设置文件路径，并下载到对应路径
        Path path = Paths.get(url + uniqueFilename);
        Files.write(path,bytes);
        url = path.toString();

        Model model = new Model();
//        model.setModelName(uploadNewModelDTO.getModelName());
//        model.setModelType(uploadNewModelDTO.getModelType());
//        model.setDescription(uploadNewModelDTO.getDescription());
//        model.setUserId(uploadNewModelDTO.getUserId());
        BeanUtils.copyProperties(uploadNewModelDTO, model);
        model.setCharacterType(Constant.USER);
        int i  = modelMapper.insertModel(model);//插入模型表
        if (i == 1){
            //插入成功
            int modelId = model.getModelId();
            if (modelMapper.insertModelUrl(modelId,url) == 1){//插入模型地址表
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

    @Override
    public PageBean<MyModelVO> personalCenterPageQuery(int userId, int page) {
        int offset = (page - 1) * 6;
        List<Model> myModelVOS = modelMapper.personalCenterPageQuery(userId, offset);
        List<MyModelVO> myModelVOList = new ArrayList<>();
        PageBean<MyModelVO> pageBean = new PageBean<>();

        long total = myModelVOS.size();

        for (Model model : myModelVOS) {
            MyModelVO myModelVO = new MyModelVO();
            BeanUtils.copyProperties(model, myModelVO);
            myModelVOList.add(myModelVO);
        }
        pageBean.setTotal(total);
        pageBean.setData(myModelVOList);
        return pageBean;
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
