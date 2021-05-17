package com.atguigu.serviceedu.controller;


import com.atguigu.commonutils.R;
import com.atguigu.serviceedu.entity.EduCourse;
import com.atguigu.serviceedu.entity.vo.CourseInfoVo;
import com.atguigu.serviceedu.entity.vo.CoursePublishVo;
import com.atguigu.serviceedu.entity.vo.CourseQuery;
import com.atguigu.serviceedu.service.EduCourseService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author atguigu
 * @since 2021-05-10
 */
@RestController
@RequestMapping("/serviceedu/course")
@CrossOrigin
public class EduCourseController {

    @Autowired
    private EduCourseService eduCourseService;

    @ApiOperation("添加课程基本信息的方法")
    @PostMapping("/addCourseInfo")
    public R addCourseInfo(@RequestBody CourseInfoVo courseInfoVo) {
        String id = eduCourseService.saveCourseInfo(courseInfoVo);
        return R.ok().data("courseId", id);
    }

    @GetMapping("/getCourseInfoById/{id}")
    public R getCourseInfoById(@PathVariable("id") String id) {
        CourseInfoVo courseInfoVo = eduCourseService.getCourseInfo(id);
        return R.ok().data("courseInfo", courseInfoVo);
    }

    @PostMapping("/updateCourseInfo")
    public R updateCourseInfo(@RequestBody CourseInfoVo courseInfoVo) {
        boolean flag = eduCourseService.updateCourseInfo(courseInfoVo);
        if (flag) {
            return R.ok();
        } else {
            return R.error();
        }
    }

    @GetMapping("/getCoursePublishVoInfo/{id}")
    public R getCoursePublishVoInfo(@PathVariable String id) {
        CoursePublishVo coursePublishVo = eduCourseService.getCoursePublishVoInfoById(id);
        return R.ok().data("coursePublishVo", coursePublishVo);
    }

    @GetMapping("/updateCourseStatus/{id}")
    public R updateCourseStatus(@PathVariable String id) {
        EduCourse eduCourse = new EduCourse();
        eduCourse.setId(id);
        eduCourse.setStatus("Normal");
        eduCourseService.updateById(eduCourse);
        return R.ok();

    }

    @GetMapping("/getPageList/{page}/{limit}")
    public R getPageList(@PathVariable("page") Long page,
                         @PathVariable("limit") Long limit,
                         CourseQuery courseQuery) {
        Page<EduCourse> p = new Page<>(page, limit);
        eduCourseService.selectCourseList(p, courseQuery);
        List<EduCourse> records = p.getRecords();
        long total = p.getTotal();
        return R.ok().data("total", total).data("list", records);
    }

    @DeleteMapping("/deleteCourseById/{id}")
    public R deleteCourseById(@PathVariable("id") String courseId) {
        eduCourseService.deleteCourseById(courseId);

        return R.ok();
    }

}

