package com.faken.aiproject.po.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ModelUrl {

   private int modelUrlId; //主键ID

   private int modelId; //模型名称

   private String url; //模型文件地址

}
