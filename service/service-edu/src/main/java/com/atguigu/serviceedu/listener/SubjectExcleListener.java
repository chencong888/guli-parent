package com.atguigu.serviceedu.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.atguigu.servicebase.exceptionhandler.GuliException;
import com.atguigu.serviceedu.entity.EduSubject;
import com.atguigu.serviceedu.entity.excel.SubjectData;
import com.atguigu.serviceedu.service.EduSubjectService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

public class SubjectExcleListener extends AnalysisEventListener<SubjectData> {
    /**
     * 因为SubjectExcleListener不能交给spring管理，需要自己new。不能注入其他对象
     */
    public EduSubjectService edusubjectservice;


    public SubjectExcleListener() {
    }

    public SubjectExcleListener(EduSubjectService eduSubjectService) {
        this.edusubjectservice = eduSubjectService;
    }


    /**
     * 读取excel内容，一行一行进行读取
     *
     * @param subjectData
     * @param analysisContext
     */
    @Override
    public void invoke(SubjectData subjectData, AnalysisContext analysisContext) {
        if (subjectData == null) {
            throw new GuliException(20001, "文件数据为空");
        }

        //判断一级分类是否重复
        EduSubject oneEduSubject = this.existOneSubject(edusubjectservice, subjectData.getOneSubjectName());
        if(oneEduSubject==null){//没有一级分类，进行添加
            oneEduSubject = new EduSubject();
            oneEduSubject.setTitle(subjectData.getOneSubjectName());
            oneEduSubject.setParentId("0");
            edusubjectservice.save(oneEduSubject);
        }

        String parentid = oneEduSubject.getId();
        EduSubject twoEduSubject = this.existTwoSubject(edusubjectservice, subjectData.getTwoSubjectName(),parentid);
        if(twoEduSubject==null){//没有二级分类，进行添加
            twoEduSubject = new EduSubject();
            twoEduSubject.setTitle(subjectData.getTwoSubjectName());
            twoEduSubject.setParentId(parentid);
            edusubjectservice.save(twoEduSubject);
        }

    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {

    }

    //判断一级分类不能重复添加
    public EduSubject existOneSubject(EduSubjectService eduSubjectService, String name) {
        QueryWrapper<EduSubject> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("title", name);
        queryWrapper.eq("parent_id", 0);
        EduSubject oneSubject = eduSubjectService.getOne(queryWrapper);
        return oneSubject;
    }

    //判断二级分类不能重复添加
    public EduSubject existTwoSubject(EduSubjectService eduSubjectService, String name, String parentid) {
        QueryWrapper<EduSubject> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("title", name);
        queryWrapper.eq("parent_id", parentid);
        EduSubject twoSubject = eduSubjectService.getOne(queryWrapper);
        return twoSubject;
    }
}
