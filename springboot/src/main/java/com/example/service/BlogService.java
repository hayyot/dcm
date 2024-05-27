package com.example.service;

import cn.hutool.core.date.DateUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONUtil;
import com.example.common.enums.LikesModuleEnum;
import com.example.common.enums.RoleEnum;
import com.example.entity.*;
import com.example.mapper.BlogMapper;
import com.example.utils.TokenUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 博客信息业务处理
 **/
@Service
public class BlogService {

    @Resource
    private BlogMapper blogMapper;
    @Resource
    LikesService likesService;

    /**
     * 新增
     */
    public void add(Blog blog) {
        blog.setDate(DateUtil.today());

        blogMapper.insert(blog);
    }

    /**
     * 删除
     */
    public void deleteById(Integer id) {
        blogMapper.deleteById(id);
    }

    /**
     * 批量删除
     */
    public void deleteBatch(List<Integer> ids) {
        for (Integer id : ids) {
            blogMapper.deleteById(id);
        }
    }

    /**
     * 修改
     */
    public void updateById(Blog blog) {
        blogMapper.updateById(blog);
    }

    /**
     * 根据ID查询
     */
    public Blog selectById(Integer id) {
        Blog blog = blogMapper.selectById(id);
        int likesCount = likesService.selectByFidAndModule(id, LikesModuleEnum.BLOG.getValue());
        blog.setLikesCount(likesCount);
        Likes userLikes = likesService.selectUserLikes(id, LikesModuleEnum.BLOG.getValue());
        blog.setUserLike(userLikes != null);
        return blog;
    }

    /**
     * 查询所有
     */
    public List<Blog> selectAll(Blog blog) {
        return blogMapper.selectAll(blog);
    }

    /**
     * 分页查询
     */
    public PageInfo<Blog> selectPage(Blog blog, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Blog> list = blogMapper.selectAll(blog);
        return PageInfo.of(list);
    }

}