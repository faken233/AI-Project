package com.faken.aiproject.service.impl;

import com.faken.aiproject.constant.Constant;
import com.faken.aiproject.mapper.ApplicationMapper;
import com.faken.aiproject.mapper.ModelMapper;
import com.faken.aiproject.po.dto.UploadNewModelDTO;
import com.faken.aiproject.po.entity.Model;
import com.faken.aiproject.po.entity.ModelAuth;
import com.faken.aiproject.po.result.PageBean;
import com.faken.aiproject.po.vo.ModelRankVO;
import com.faken.aiproject.po.vo.MyModelVO;
import com.faken.aiproject.po.vo.PageQueryModelVO;
import com.faken.aiproject.service.ModelService;
import com.faken.aiproject.util.HuaweiOBSUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
public class ModelServiceImpl implements ModelService {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private HuaweiOBSUtils huaweiOBSUtils;

    @Autowired
    private ApplicationMapper applicationMapper;


    @Override
    public List<ModelRankVO>  modelRank() {
        List<Model> listModel = modelMapper.selectByUsedTimes();// 获取到根据使用频率前十个模型
        List<ModelRankVO> listModelRankVO = new ArrayList<>();// 包装VO类的list集合
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
        // 生成唯一的文件名
        String originalFilename = uploadNewModelDTO.getFile().getOriginalFilename();
        String fileExtension = null;
        if (originalFilename != null) {
            fileExtension = originalFilename.substring(originalFilename.lastIndexOf(".") + 1);
        }
        String uniqueFilename = UUID.randomUUID() + "." + fileExtension;

        // 转化文件格式为File类型
        File file = new File(uploadNewModelDTO.getFile().getOriginalFilename());
        file.createNewFile();
        FileOutputStream fileOutputStream = new FileOutputStream(file);
        fileOutputStream.write(uploadNewModelDTO.getFile().getBytes());
        fileOutputStream.close();


        huaweiOBSUtils.uploadToOBS(file, uniqueFilename);
        String OBSUrl = generateOBSUrl(huaweiOBSUtils.getEndPoint(), huaweiOBSUtils.getBucketName(), uniqueFilename);

        Model model = new Model();
        BeanUtils.copyProperties(uploadNewModelDTO, model);
        model.setCharacterType(Constant.USER);
        int i  = modelMapper.insertModel(model);// 插入模型表
        if (i == 1){
            // 插入成功
            int modelId = model.getModelId();
            if (modelMapper.insertModelUrl(modelId,OBSUrl) == 1){//插入模型地址表
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

    private String generateOBSUrl(String endPoint, String bucketName, String uniqueFilename) {
        return "https://" + bucketName + "." + endPoint.substring(("https://").length()) + "/Upload/" + uniqueFilename;
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
        int offset = (page - 1) * Constant.DEFAULT_PAGE_SIZE;
        List<Model> myModelVOS = modelMapper.personalCenterPageQuery(userId, offset);
        List<MyModelVO> myModelVOList = new ArrayList<>();
        PageBean<MyModelVO> pageBean = new PageBean<>();

        int total = modelMapper.personalCenterPageQueryTotal(userId);

        for (Model model : myModelVOS) {
            MyModelVO myModelVO = new MyModelVO();
            BeanUtils.copyProperties(model, myModelVO);
            myModelVOList.add(myModelVO);
        }
        pageBean.setTotal((long) total);
        pageBean.setData(myModelVOList);
        return pageBean;
    }

    @Override
    public PageBean<PageQueryModelVO> pageQuery(int userId, int page, String name) {
        int offset = (page - 1) * Constant.DEFAULT_PAGE_SIZE;
        List<Model> models = modelMapper.pageQuery(offset, name);
        List<PageQueryModelVO> pageQueryModelVOS = new ArrayList<>();
        PageBean<PageQueryModelVO> pageBean = new PageBean<>();

        pageBean.setTotal((long) modelMapper.allModelTotal());

        // 进行模型的申请状态判断
        for (Model model : models) {
            int modelId = model.getModelId();
            PageQueryModelVO pageQueryModelVO = new PageQueryModelVO();
           ModelAuth modelAuth = modelMapper.selectByUserId(userId, modelId);

           if (!Objects.isNull(modelAuth)) {
               // 依据用户ID和模型ID可以在权限表中查到模型, 说明用户通过了申请
               // 或者模型是他自己的, 或者他已将官方模型加入自己的库, 为可使用模型
               pageQueryModelVO.setSign(Constant.ACCESSED_MODEL);
           } else {
               // 查不到, 说明模型非用户所有, 或者申请未通过
               // 申请未通过, 则可以在申请表内查询到模型数据
               if (applicationMapper.selectByApplicantIdAndModelId(userId, modelId) != null) {
                   // TODO
                   pageQueryModelVO.setSign(Constant.REMAINING_MODEL);
               } else {
                   // 申请表查询不到数据, 为可申请模型
                   pageQueryModelVO.setSign(Constant.APPLICABLE_MODEL);
               }
           }

           BeanUtils.copyProperties(model, pageQueryModelVO);
           pageQueryModelVOS.add(pageQueryModelVO);
        }
        pageBean.setData(pageQueryModelVOS);
        return pageBean;
    }
}
