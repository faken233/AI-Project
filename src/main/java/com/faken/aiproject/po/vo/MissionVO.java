package com.faken.aiproject.po.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MissionVO {
    private int userId;
    private String content;
    private String image;
    private String answer;
    private String missionName;
    private String modelList;
}