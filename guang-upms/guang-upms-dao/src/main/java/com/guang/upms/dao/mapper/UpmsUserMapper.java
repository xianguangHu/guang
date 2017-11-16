package com.guang.upms.dao.mapper;

import com.guang.upms.dao.model.UpmsUser;
import com.guang.upms.dao.model.UpmsUserExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

/**
 * Mybatis generator自动生成
 */
public interface UpmsUserMapper {
    /**
     * 根据条件查询数量
     * @param example
     * @return
     */
    int countByExample(UpmsUserExample example);

    /**
     * 根据条件删除多条
     * @param example
     * @return
     */
    int deleteByExample(UpmsUserExample example);

    /**
     * 根据条件删除单条
     * @param userId
     * @return
     */
    int deleteByPrimaryKey(Integer userId);

    /**
     * 插入数据
     * @param record
     * @return
     */
    int insert(UpmsUser record);

    /**
     * 插入数据 只插入不为null的数据
     * @param record
     * @return
     */
    int insertSelective(UpmsUser record);

    /**
     * 根据条件查询数据
     * @param example
     * @return
     */
    List<UpmsUser> selectByExample(UpmsUserExample example);

    /**
     * 根据主键查询数据
     * @param userId
     * @return
     */
    UpmsUser selectByPrimaryKey(Integer userId);

    /**
     * 按条件更新值不为null的字段
     * @param record
     * @param example
     * @return
     */
    int updateByExampleSelective(@Param("record") UpmsUser record, @Param("example") UpmsUserExample example);

    /**
     * 按条件更新
     * @param record
     * @param example
     * @return
     */
    int updateByExample(@Param("record") UpmsUser record, @Param("example") UpmsUserExample example);

    /**
     * 按条件跟新  如果字段为null 就不跟新
     * @param record
     * @return
     */
    int updateByPrimaryKeySelective(UpmsUser record);

    /**
     * 按主键跟新
     * @param record
     * @return
     */
    int updateByPrimaryKey(UpmsUser record);
}