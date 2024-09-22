package com.faken.aiproject.po.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserCanUseModelVO {

    String modelName; //模型名称
    int modelId; //模型ID
    int Weight; //权重默认为1
    int isAPI; //是否为大模型
    String modelUrl; //模型文件的URL
    String description; //简介

}
