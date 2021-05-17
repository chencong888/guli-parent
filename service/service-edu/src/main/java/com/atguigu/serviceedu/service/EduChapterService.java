package com.atguigu.serviceedu.service;

import com.atguigu.serviceedu.entity.EduChapter;
import com.atguigu.serviceedu.entity.chapter.Chapter;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author atguigu
 * @since 2021-05-10
 */
public interface EduChapterService extends IService<EduChapter> {

    List<Chapter> getChapterVideoByCourseId(String courseId);

    boolean deleteChapterById(String chapterId);

    void deleteChapterByCourseId(String courseId);
}
