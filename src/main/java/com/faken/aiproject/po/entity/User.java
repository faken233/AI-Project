package com.faken.aiproject.po.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {

    private int userId;

    private String  username;

    private String  password;

    private String  email;

    private String  description;

    private String  image;

}
