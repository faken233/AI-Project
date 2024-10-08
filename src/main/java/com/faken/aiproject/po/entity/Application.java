package com.faken.aiproject.po.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Application {

   private int applicationId; //主键ID

   private int applicantId; //申请人ID

   private int respondentId; //被申请人ID

   private int modelId; //申请的模型的ID

   private String applyTime; //申请时间

   private int status; //消息状态，

}
