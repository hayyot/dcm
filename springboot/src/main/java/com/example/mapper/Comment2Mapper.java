package com.example.mapper;

import com.example.entity.Comment2;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 操作comment2相关数据接口
 */
public interface Comment2Mapper {

    /**
     * 新增
     */
    int insert(Comment2 comment2);

    /**
     * 删除
     */
    int deleteById(Integer id);

    /**
     * 修改
     */
    int updateById(Comment2 comment2);

    /**
     * 根据ID查询
     */
    Comment2 selectById(Integer id);

    /**
     * 查询所有
     */
    List<Comment2> selectAll(Comment2 comment2);

    List<Comment2> selectForUser(Comment2 comment2);

    @Select("select count(*) from comment2 where fid = #{fid} and module = #{module}")
    Integer selectCount(@Param("fid") Integer fid, @Param("module") String module);

}