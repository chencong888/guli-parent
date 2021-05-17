package com.atguigu.serviceedu.controller;


import com.atguigu.commonutils.R;
import com.atguigu.serviceedu.entity.EduTeacher;
import com.atguigu.serviceedu.entity.vo.TeacherQuery;
import com.atguigu.serviceedu.service.EduTeacherService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 讲师 前端控制器
 * </p>
 *
 * @author atguigu
 * @since 2021-05-04
 */
@Api(description = "讲师管理")
@RestController
@RequestMapping("/serviceedu/teacher")
@CrossOrigin
public class EduTeacherController {
    @Autowired
    private EduTeacherService eduTeacherService;
    //查询教师表所有数据

    @ApiOperation(value = "讲师列表")
    @GetMapping(value = "/getAll")
    public R getAllTeacher() {

        List<EduTeacher> list = eduTeacherService.list(null);
        return R.ok().data("items", list);
    }

    @ApiOperation(value = "逻辑删除讲师")
    @DeleteMapping("{id}")
    public R deleteTeacher(@ApiParam(name = "id", value = "讲师ID", required = true) @PathVariable("id") String id) {
        boolean b = eduTeacherService.removeById(id);
        if (b) {
            return R.ok();
        } else {
            return R.error();
        }
    }

    @ApiOperation(value = "分页讲师列表")
    @GetMapping("pageTeacher/{page}/{limit}")
    public R pageList(@ApiParam(name = "page", value = "当前页码", required = true) @PathVariable("page") Long page,
                      @ApiParam(name = "limit", value = "每页记录数", required = true) @PathVariable("limit") Long limit) {
        //创建page对象
        Page<EduTeacher> teacherPage = new Page<>(page, limit);
        //调用方法实现分页
        //调用方法的时候底层封装，把分页所有数据封装到teacherPage对象里面
        eduTeacherService.page(teacherPage, null);
        //总记录数
        long total = teacherPage.getTotal();
        //数据list集合
        List<EduTeacher> records = teacherPage.getRecords();
        return R.ok().data("total", total).data("rows", records);
    }


    //使用@RequestBody注解，必须是post请求才能获取到数据
    @ApiOperation(value = "条件查询带分页")
    @PostMapping(value = "pageTeacherCondition/{page}/{limit}")
    public R pageTeacherCondition(@PathVariable("page") Long page,
                                  @PathVariable("limit") Long limit,
                                  @RequestBody (required = false) TeacherQuery teacherQuery) {
        //创建page对象
        Page<EduTeacher> eduTeacherPage = new Page<>(page,limit);

        String name = teacherQuery.getName();
        Integer level = teacherQuery.getLevel();
        String begin = teacherQuery.getBegin();
        String end = teacherQuery.getEnd();
        //构造条件
        QueryWrapper<EduTeacher> queryWrapper = new QueryWrapper<>();
        if (!StringUtils.isEmpty(name)) {
            queryWrapper.like("name", name);
        }

        if (!StringUtils.isEmpty(level)) {
            queryWrapper.eq("level", level);
        }
        if (!StringUtils.isEmpty(begin)) {
            //column传的是数据库字段名
            queryWrapper.ge("gmt_create", begin);
        }
        if (!StringUtils.isEmpty(end)) {
            queryWrapper.le("gmt_modified", end);
        }
        queryWrapper.orderByDesc("gmt_create");
        //调用方法实现条件查询带分页
        eduTeacherService.page(eduTeacherPage, queryWrapper);
        //总记录数
        long total = eduTeacherPage.getTotal();
        //数据list集合
        List<EduTeacher> records = eduTeacherPage.getRecords();
        return R.ok().data("total", total).data("rows", records);
    }

    @ApiOperation("添加讲师方法")
    @PostMapping(value = "addTeacher")
    public R addTeacher(@RequestBody EduTeacher eduTeacher){
        boolean save = eduTeacherService.save(eduTeacher);
        if (save){
            return R.ok();
        }else{
            return R.error();
        }
    }

    @ApiOperation("根据讲师id进行查询")
    @GetMapping(value = "getTeacher/{id}")
    public R getTeacherById(@PathVariable("id") String id){
        EduTeacher eduTeacher = eduTeacherService.getById(id);
       /* 测试自定义异常*/
//       try {
//            int i = 10/0;
//        } catch (Exception e) {
//            e.printStackTrace();
//        throw new GuliException(1111,"GuliExceptopn");
//        }
        return R.ok().data("teacher",eduTeacher);
    }

    @ApiOperation("讲师修改")
    @PostMapping("updateTeacher")
    public R updateTeacher(@RequestBody EduTeacher teacher){
        boolean flag = eduTeacherService.updateById(teacher);
        if (flag){
            return R.ok();
        }else {
            return R.error();
        }
    }



}

