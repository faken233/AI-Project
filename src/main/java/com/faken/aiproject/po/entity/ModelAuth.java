package com.faken.aiproject.po.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ModelAuth {

   private int modelAuthId; // 主键ID

   private int modelId; //模型ID

   private int userId; //用户ID

   private int deletable; //是否有删除的权限

}
