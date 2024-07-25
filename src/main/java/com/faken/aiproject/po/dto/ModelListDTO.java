package com.faken.aiproject.po.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ModelListDTO {
    private int layer;
    private String parallel;
    private List<ModelsDTO> models;
}
