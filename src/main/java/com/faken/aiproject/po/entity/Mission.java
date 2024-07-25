package com.faken.aiproject.po.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Mission {
    private int missionId;
    private String missionName;
    private String content;
    private int userId;
    private String image;
    private String answer;
    private String createTime;
    private String modelList;
}
