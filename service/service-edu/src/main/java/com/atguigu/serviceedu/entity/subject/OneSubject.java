package com.atguigu.serviceedu.entity.subject;

import com.atguigu.serviceedu.entity.EduSubject;
import lombok.Data;

import java.util.List;

@Data
public class OneSubject {
    private String id;
    private String title;
    //一个一级分类有多个二级分类
    private List<TwoSubject> children;

}
