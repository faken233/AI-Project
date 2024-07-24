package com.faken.aiproject.po.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageQueryModelVO {

    private int modelId;//

    private String modelName;//

    private int characterType;//

    private int modelType;//

    private String version;//

    private String description;//

    private int usedTimes;//

    private String createdTime;//

    private int sign;

}
