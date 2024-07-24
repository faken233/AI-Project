package com.faken.aiproject.mapper;

import com.faken.aiproject.po.entity.Model;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface ModelMapper {

    @Select("select * from model order by  used_times DESC limit 10 ")
    List<Model> selectByUsedTimes();
}
