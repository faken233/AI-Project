package com.faken.aiproject.po.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Model {

       private int modelId; //主键ID

       private String modelName; //模型名称

       private int userId; //用户ID

       private int characterType; // 官方/用户

       private int modelType; // 图形/文本

       private String version; //版本

       private String description; //描述

       private int usedTimes; //使用次数

       private String createTime; //上传时间

       private int bigModel; //是否为大模型

}
