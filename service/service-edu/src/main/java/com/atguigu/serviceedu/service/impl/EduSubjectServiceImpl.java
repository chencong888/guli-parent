package com.atguigu.serviceedu.service.impl;

import com.alibaba.excel.EasyExcel;
import com.atguigu.serviceedu.entity.EduSubject;
import com.atguigu.serviceedu.entity.excel.SubjectData;
import com.atguigu.serviceedu.entity.subject.OneSubject;
import com.atguigu.serviceedu.entity.subject.TwoSubject;
import com.atguigu.serviceedu.listener.SubjectExcleListener;
import com.atguigu.serviceedu.mapper.EduSubjectMapper;
import com.atguigu.serviceedu.service.EduSubjectService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * <p>
 * 课程科目 服务实现类
 * </p>
 *
 * @author atguigu
 * @since 2021-05-09
 */
@Service
public class EduSubjectServiceImpl extends ServiceImpl<EduSubjectMapper, EduSubject> implements EduSubjectService {

    /**
     * 添加分类课程
     *
     * @param file
     * @param eduSubjectService
     */
    @Override
    public void saveSubject(MultipartFile file, EduSubjectService eduSubjectService) {
        try {
            InputStream inputStream = file.getInputStream();
            EasyExcel.read(inputStream, SubjectData.class, new SubjectExcleListener(eduSubjectService)).sheet().doRead();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<OneSubject> getAllOneTwoSubject() {
        List<OneSubject> finalOneSubject = new ArrayList<>();

        //1.先获取所有的一级分类
        QueryWrapper<EduSubject> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("parent_id", "0");
        List<EduSubject> eduSubjectListOne = baseMapper.selectList(queryWrapper);


        //2.获取所有的二级分类
        QueryWrapper<EduSubject> qw = new QueryWrapper<>();
        qw.ne("parent_id", "0");
        List<EduSubject> eduSubjectListTwo = baseMapper.selectList(qw);
//        System.out.println("eduSubjectListTwo:    " + eduSubjectListTwo);


        //3.

        for (int i = 0; i < eduSubjectListOne.size(); i++) {
            List<TwoSubject> allTwoSubject = new ArrayList<>();
            EduSubject oneSubject = eduSubjectListOne.get(i);
            OneSubject o = new OneSubject();
            BeanUtils.copyProperties(oneSubject, o);

            for (int j = 0; j < eduSubjectListTwo.size(); j++) {
                EduSubject twoSubject = eduSubjectListTwo.get(j);
                if (twoSubject.getParentId().equals(oneSubject.getId())) {
                    TwoSubject t = new TwoSubject();
                    BeanUtils.copyProperties(twoSubject, t);
                    allTwoSubject.add(t);
                }
            }
            o.setChildren(allTwoSubject);

            finalOneSubject.add(o);
        }


        return finalOneSubject;
    }

    @Override
    public OneSubject getOneTwoSubjectById(String id) {
        OneSubject oneSubject = new OneSubject();
        EduSubject eduSubject = this.getById(id);
        TwoSubject twoSubject = new TwoSubject();
        BeanUtils.copyProperties(eduSubject,twoSubject);
        oneSubject.setChildren(Arrays.asList(twoSubject));

        EduSubject eduSubject1 = this.getById(eduSubject.getParentId());
        oneSubject.setId(eduSubject1.getId());
        oneSubject.setTitle(eduSubject1.getTitle());
        return oneSubject;

    }


}
