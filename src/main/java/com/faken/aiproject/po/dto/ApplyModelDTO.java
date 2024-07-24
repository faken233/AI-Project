package com.faken.aiproject.po.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


//用于申请模型权限的时候
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApplyModelDTO {

   private Integer modelId; //模型Id
   private Integer userId; //申请人的ID

}
