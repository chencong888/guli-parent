package com.atguigu.serviceedu.service.impl;

import com.atguigu.servicebase.exceptionhandler.GuliException;
import com.atguigu.serviceedu.entity.EduCourse;
import com.atguigu.serviceedu.entity.EduCourseDescription;
import com.atguigu.serviceedu.entity.vo.CourseInfoVo;
import com.atguigu.serviceedu.entity.vo.CoursePublishVo;
import com.atguigu.serviceedu.entity.vo.CourseQuery;
import com.atguigu.serviceedu.mapper.EduCourseMapper;
import com.atguigu.serviceedu.service.EduChapterService;
import com.atguigu.serviceedu.service.EduCourseDescriptionService;
import com.atguigu.serviceedu.service.EduCourseService;
import com.atguigu.serviceedu.service.EduVideoService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author atguigu
 * @since 2021-05-10
 */
@Service
public class EduCourseServiceImpl extends ServiceImpl<EduCourseMapper, EduCourse> implements EduCourseService {

    @Autowired
    private EduCourseDescriptionService eduCourseDescriptionService;

    @Autowired
    private EduVideoService eduVideoService;

    @Autowired
    private EduChapterService eduChapterService;

    /**
     * 添加课程基本信息的方法
     *
     * @param courseInfoVo
     * @return
     */
    @Override
    public String saveCourseInfo(CourseInfoVo courseInfoVo) {
        //1.向课程表添加课程基本信息
        //CourseInfoVo对象转换EduCourse对象
        EduCourse eduCourse = new EduCourse();
        BeanUtils.copyProperties(courseInfoVo, eduCourse);
        int insert = baseMapper.insert(eduCourse);
        if (insert <= 0) {
            throw new GuliException(20001, "添加课程信息失败");
        }

        String eduCourseId = eduCourse.getId();
        //2.向课程简介表添加课程简介信息
        EduCourseDescription eduCourseDescription = new EduCourseDescription();
        eduCourseDescription.setId(eduCourseId);
        eduCourseDescription.setDescription(courseInfoVo.getDescription());
        eduCourseDescriptionService.save(eduCourseDescription);
        return eduCourseId;
    }

    @Override
    public CourseInfoVo getCourseInfo(String courseId) {
        EduCourse eduCourse = baseMapper.selectById(courseId);
        CourseInfoVo courseInfoVo = new CourseInfoVo();
        BeanUtils.copyProperties(eduCourse, courseInfoVo);
        EduCourseDescription eduCourseDescription = eduCourseDescriptionService.getById(courseId);
        courseInfoVo.setDescription(eduCourseDescription.getDescription());
        return courseInfoVo;
    }

    @Override
    public boolean updateCourseInfo(CourseInfoVo courseInfoVo) {
        EduCourse eduCourse = new EduCourse();
        BeanUtils.copyProperties(courseInfoVo, eduCourse);
        EduCourseDescription ecd = new EduCourseDescription();
        BeanUtils.copyProperties(courseInfoVo, ecd);
        boolean save1 = this.updateById(eduCourse);
        boolean save = eduCourseDescriptionService.updateById(ecd);
        if (!save1) {
            throw new GuliException(20001, "修改课程信息失败");
        }
        if (!save) {
            throw new GuliException(20001, "修改课程描述失败");
        }
        return save && save1;
    }

    @Override
    public CoursePublishVo getCoursePublishVoInfoById(String id) {
        CoursePublishVo coursePublishVo = baseMapper.selectCoursePublishVoById(id);
        return coursePublishVo;
    }

    @Override
    public void selectCourseList(Page<EduCourse> p, CourseQuery courseQuery) {
        QueryWrapper<EduCourse> queryWrapper = new QueryWrapper();
        String title = courseQuery.getTitle();
        String teacherId = courseQuery.getTeacherId();
        String subjectParentId = courseQuery.getSubjectParentId();
        String subjectId = courseQuery.getSubjectId();

        if (courseQuery == null) {
            baseMapper.selectPage(p, queryWrapper);
        }

        if (!StringUtils.isEmpty(title)) {
            queryWrapper.like("title", courseQuery.getTitle());
        }
        if (!StringUtils.isEmpty(subjectId)) {
            queryWrapper.eq("subject_id", courseQuery.getSubjectId());
        }
        if (!StringUtils.isEmpty(subjectParentId)) {

            queryWrapper.eq("subject_parent_id", courseQuery.getSubjectParentId());
        }
        if (!StringUtils.isEmpty(teacherId)) {
            queryWrapper.eq("teacher_id", courseQuery.getTeacherId());
        }
        baseMapper.selectPage(p, queryWrapper);
    }

    @Override
    public void deleteCourseById(String courseId) {
        eduVideoService.deleteVideoByCourseId(courseId);

        eduChapterService.deleteChapterByCourseId(courseId);

        eduCourseDescriptionService.removeById(courseId);

        int i = baseMapper.deleteById(courseId);
        if (i<=0){
            throw new GuliException(20001,"删除课程失败");
        }

    }


}
