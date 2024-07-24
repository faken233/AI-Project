package com.faken.aiproject.po.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageBean<T> {

   private long total; //总条数
   private List<T> data;

}
