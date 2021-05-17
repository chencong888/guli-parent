package com.atguigu.serviceedu.controller;


import com.atguigu.commonutils.R;
import com.atguigu.serviceedu.entity.subject.OneSubject;
import com.atguigu.serviceedu.service.EduSubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 课程科目 前端控制器
 * </p>
 *
 * @author atguigu
 * @since 2021-05-09
 */
@RestController
@RequestMapping("/serviceedu/subject")
@CrossOrigin
public class EduSubjectController {
    @Autowired
    private EduSubjectService eduSubjectService;

    //添加课程分类
    //获取上传过来的文件，把文件内容读出来
    @PostMapping("/addSubject")
    public R addSubject(MultipartFile file) {
        eduSubjectService.saveSubject(file, eduSubjectService);
        return R.ok();
    }

    //课程列表分类（树形）

    /**
     * [{
     *           id: 1,
     *           label: 'Level one 1',
     *           children: [{
     *              id: 5,
     *             label: 'Level two 2-1'
     *           }, {
     *             id: 6,
     *             label: 'Level two 2-2'
     *           }]
     * @return
     */
    @GetMapping(value = "/getAllSubject")
    public R getAllSubject() {
       List<OneSubject> list =  eduSubjectService.getAllOneTwoSubject();
        return R.ok().data("list",list);
    }

    @GetMapping(value = "/getSubjectById/{id}")
    public R getSubjectById(@PathVariable String id) {
        OneSubject oneSubject =  eduSubjectService.getOneTwoSubjectById(id);
        return R.ok().data("list",oneSubject);
    }
}

