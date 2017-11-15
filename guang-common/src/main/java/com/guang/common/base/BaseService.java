package com.guang.common.base;

import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author huxianguang
 * @create 2017-11-14-上午9:31
 **/
public interface BaseService<Record,Example> {
    int countByExample(Example example);

    int deleteByExample(Example example);

    int deleteByPrimaryKey(Integer permissionId);

    int insert(Record record);

    int insertSelective(Record record);

    List<Record> selectByExample(Example example);

    Record selectByPrimaryKey(Integer permissionId);

    int updateByExampleSelective(@Param("record") Record record, @Param("example") Example example);

    int updateByExample(@Param("record") Record record, @Param("example") Example example);

    int updateByPrimaryKeySelective(Record record);

    int updateByPrimaryKey(Record record);

    List<Record> selectByExampleForOffsetPage(Example example, Integer offset, Integer limit);

    void initMapper();
}
