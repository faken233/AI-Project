package com.faken.aiproject.po.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AllApplicationTypeVO {

   private Integer allApplication; //关于我的全部申请数量
   private Integer remainApplication; //没有被处理的关于我的申请数量
   private Integer rejectedApplication; //拒绝状态的申请的数量
   private Integer passedApplication; //通过状态的申请的数量


}
