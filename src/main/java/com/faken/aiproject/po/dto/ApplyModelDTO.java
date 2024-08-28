package com.faken.aiproject.po.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;


//用于申请模型权限的时候
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApplyModelDTO {

   @NotNull(message = "modelId不可为null")
   private Integer modelId; //模型Id

   @NotNull(message = "userId不可为null")
   private Integer userId; //申请人的ID

}
