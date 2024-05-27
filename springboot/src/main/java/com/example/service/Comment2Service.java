package com.example.service;

import cn.hutool.core.date.DateUtil;
import com.example.common.enums.RoleEnum;
import com.example.entity.Account;
import com.example.entity.Comment2;
import com.example.mapper.Comment2Mapper;
import com.example.utils.TokenUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 业务处理
 **/
@Service
public class Comment2Service {

    @Resource
    private Comment2Mapper comment2Mapper;

    /**
     * 新增
     */
    public void add(Comment2 comment2) {
        Account currentUser = TokenUtils.getCurrentUser();
        if (RoleEnum.USER.name().equals(currentUser.getRole())){
            comment2.setUserId(currentUser.getId());
        }
        comment2.setTime(DateUtil.now());
        comment2Mapper.insert(comment2);
        if(comment2.getRootId() ==null){
            comment2.setRootId(comment2.getId());
            comment2Mapper.updateById(comment2);
        }

    }

    /**
     * 删除
     */
    public void deleteById(Integer id) {
        comment2Mapper.deleteById(id);
    }

    /**
     * 批量删除
     */
    public void deleteBatch(List<Integer> ids) {
        for (Integer id : ids) {
            comment2Mapper.deleteById(id);
        }
    }

    /**
     * 修改
     */
    public void updateById(Comment2 comment2) {
        comment2Mapper.updateById(comment2);
    }

    /**
     * 根据ID查询
     */
    public Comment2 selectById(Integer id) {
        return comment2Mapper.selectById(id);
    }

    /**
     * 查询所有
     */
    public List<Comment2> selectAll(Comment2 comment2) {
        return comment2Mapper.selectAll(comment2);
    }

    public List<Comment2> selectForUser(Comment2 comment) {
        List<Comment2> commentList = comment2Mapper.selectForUser(comment);  // 查询一级的评论
        for (Comment2 c : commentList) {  // 查询回复列表
            Comment2 param = new Comment2();
            param.setRootId(c.getId());
            List<Comment2> children = this.selectAll(param);
            children = children.stream().filter(child -> !child.getId().equals(c.getId())).collect(Collectors.toList());  // 排除当前查询结果里最外层节点
            c.setChildren(children);
        }
        return commentList;
    }

    /**
     * 分页查询
     */
    public PageInfo<Comment2> selectPage(Comment2 comment2, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Comment2> list = comment2Mapper.selectAll(comment2);
        return PageInfo.of(list);
    }
    public Integer selectCount(Integer fid,String module){
        return comment2Mapper.selectCount(fid ,module);
    }
}