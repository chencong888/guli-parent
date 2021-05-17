package com.atguigu.serviceedu.service;

import com.atguigu.serviceedu.entity.EduCourse;
import com.atguigu.serviceedu.entity.vo.CourseInfoVo;
import com.atguigu.serviceedu.entity.vo.CoursePublishVo;
import com.atguigu.serviceedu.entity.vo.CourseQuery;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author atguigu
 * @since 2021-05-10
 */
public interface EduCourseService extends IService<EduCourse> {

    String saveCourseInfo(CourseInfoVo courseInfoVo);


    CourseInfoVo getCourseInfo(String id);

    boolean updateCourseInfo(CourseInfoVo courseInfoVo);

    CoursePublishVo getCoursePublishVoInfoById(String id);

    void selectCourseList(Page<EduCourse> p, CourseQuery courseQuery);

    void deleteCourseById(String courseId);
}
