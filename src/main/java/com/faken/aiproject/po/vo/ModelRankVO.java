package com.faken.aiproject.po.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ModelRankVO {
    private int characterType;
    private String modelName;
    private String createTime;

}
