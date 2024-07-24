package com.faken.aiproject.po.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PersonalCenterInfoVO {
    private String email;
    private String username;
    private String description;
    private String image;
}
