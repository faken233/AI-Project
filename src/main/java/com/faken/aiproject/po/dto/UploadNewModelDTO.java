package com.faken.aiproject.po.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UploadNewModelDTO {
    private String description;
    private String modelName;
    private int modelType;
    private MultipartFile file;
    private int userId;

}
