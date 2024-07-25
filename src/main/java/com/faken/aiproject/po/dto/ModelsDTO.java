package com.faken.aiproject.po.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ModelsDTO {
    private String modelName;
    private String modelUrl;
    private int isAPI;
    private double weight;
}
