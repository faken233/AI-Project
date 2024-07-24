package com.faken.aiproject.po.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    private String  userId;
    private String  userName;
    private String  password;
    private String  email;
    private Integer gender;
}
