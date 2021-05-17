package com.atguigu.serviceedu.controller;


import com.atguigu.commonutils.R;
import com.atguigu.serviceedu.entity.EduChapter;
import com.atguigu.serviceedu.entity.chapter.Chapter;
import com.atguigu.serviceedu.entity.vo.CourseInfoVo;
import com.atguigu.serviceedu.service.EduChapterService;
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
@RequestMapping("/serviceedu/chapter")
@CrossOrigin
public class EduChapterController {
    @Autowired
    private EduChapterService eduChapterService;

    @GetMapping("/getChapterVideo/{courseId}")
    public R getChapterVideo(@PathVariable String courseId) {
        List<Chapter> list = eduChapterService.getChapterVideoByCourseId(courseId);
        return R.ok().data("allChapterVideo", list);
    }

    @GetMapping("/getChapter/{id}")
    public R getChapterById(@PathVariable("id") String chapterId) {
        EduChapter chapter = eduChapterService.getById(chapterId);
        return R.ok().data("chapter", chapter);
    }

    @PostMapping("addChapter")
    public R addChapter(@RequestBody EduChapter eduChapter) {
        eduChapterService.save(eduChapter);
        return R.ok();
    }

    @PostMapping("updateChapter")
    public R updateChapter(@RequestBody EduChapter eduChapter) {
        eduChapterService.updateById(eduChapter);
        return R.ok();
    }

    @GetMapping("deleteChapterById/{id}")
    public R deleteChapterById(@PathVariable("id") String chapterId) {
        boolean flag = eduChapterService.deleteChapterById(chapterId);
        return flag?R.ok():R.error();

    }


}

