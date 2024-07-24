package com.faken.aiproject.po.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UploadNewModelDTO {
    private String characterType;
    private String modelName;
    private String createTime;

}
