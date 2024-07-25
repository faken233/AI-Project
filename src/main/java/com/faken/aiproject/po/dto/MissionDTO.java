package com.faken.aiproject.po.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MissionDTO {
    private int userId;
    private String content;
    private String image;
    private String answer;
    private String missionName;
    private List<ModelListDTO> modelList;
}
