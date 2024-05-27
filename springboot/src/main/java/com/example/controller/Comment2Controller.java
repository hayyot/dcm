package com.example.controller;

import com.example.common.Result;
import com.example.entity.Comment2;
import com.example.service.Comment2Service;
import com.github.pagehelper.PageInfo;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import java.util.List;

/**
 * 前端操作接口
 **/
@RestController
@RequestMapping("/comment2")
public class Comment2Controller {

    @Resource
    private Comment2Service comment2Service;

    /**
     * 新增
     */
    @PostMapping("/add")
    public Result add(@RequestBody Comment2 comment2) {
        comment2Service.add(comment2);
        return Result.success();
    }

    /**
     * 删除
     */
    @DeleteMapping("/delete/{id}")
    public Result deleteById(@PathVariable Integer id) {
        comment2Service.deleteById(id);
        return Result.success();
    }

    /**
     * 批量删除
     */
    @DeleteMapping("/delete/batch")
    public Result deleteBatch(@RequestBody List<Integer> ids) {
        comment2Service.deleteBatch(ids);
        return Result.success();
    }

    /**
     * 修改
     */
    @PutMapping("/update")
    public Result updateById(@RequestBody Comment2 comment2) {
        comment2Service.updateById(comment2);
        return Result.success();
    }

    /**
     * 根据ID查询
     */
    @GetMapping("/selectById/{id}")
    public Result selectById(@PathVariable Integer id) {
        Comment2 comment2 = comment2Service.selectById(id);
        return Result.success(comment2);
    }

    /**
     * 查询所有
     */
    @GetMapping("/selectAll")
    public Result selectAll(Comment2 comment2) {
        List<Comment2> list = comment2Service.selectAll(comment2);
        return Result.success(list);
    }

    /**
     * 分页查询
     */
    @GetMapping("/selectPage")
    public Result selectPage(Comment2 comment2,
                             @RequestParam(defaultValue = "1") Integer pageNum,
                             @RequestParam(defaultValue = "10") Integer pageSize) {
        PageInfo<Comment2> page = comment2Service.selectPage(comment2, pageNum, pageSize);
        return Result.success(page);
    }
    @GetMapping("/selectForUser")
    public Result selectForUser(Comment2 comment2) {
        List<Comment2> list = comment2Service.selectForUser(comment2);
        return Result.success(list);
    }
    @GetMapping("/selectCount")
    public Result selectCount(@RequestParam Integer fid, @RequestParam String module) {
        Integer count = comment2Service.selectCount(fid, module);
        return Result.success(count);
    }

}