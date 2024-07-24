package com.faken.aiproject.po.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PagedQueryModelVO {
    private String version;
    private String description;
    private int modelId;
    private String modelName;
    private int characterType;
    private int modelType;
    private int usedTimes;
    private Date createdTime;
    private int sign;
}
