package com.faken.aiproject.po.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MyModelVO {
    private String version;
    private String description;
    private int modelId;
    private String modelName;
    private int modelType;
    private int usedTimes;
    private String createTime;
}
