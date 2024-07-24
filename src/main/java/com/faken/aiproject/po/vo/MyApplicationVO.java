package com.faken.aiproject.po.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MyApplicationVO {
   private Integer applicationId; //申请记录的ID
   private String modelName; //模型名称
   private String respondentName; //被申请人的名字
   private String applyTime; //申请的时间
   private int status; //此申请的状态

}
