package com.atguigu.serviceedu.service.impl;

import com.atguigu.servicebase.exceptionhandler.GuliException;
import com.atguigu.serviceedu.entity.EduChapter;
import com.atguigu.serviceedu.entity.EduVideo;
import com.atguigu.serviceedu.entity.chapter.Chapter;
import com.atguigu.serviceedu.entity.chapter.Video;
import com.atguigu.serviceedu.mapper.EduChapterMapper;
import com.atguigu.serviceedu.service.EduChapterService;
import com.atguigu.serviceedu.service.EduVideoService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author atguigu
 * @since 2021-05-10
 */
@Service
@Slf4j
public class EduChapterServiceImpl extends ServiceImpl<EduChapterMapper, EduChapter> implements EduChapterService {

    @Autowired
    private EduVideoService eduVideoService;

    @Override
    public List<Chapter> getChapterVideoByCourseId(String courseId) {
        //1.根据课程id查询课程所有的章节
        QueryWrapper<EduChapter> queryWrapperEduChapter = new QueryWrapper<>();
        queryWrapperEduChapter.eq("course_id", courseId);
        List<EduChapter> eduChapterList = this.list(queryWrapperEduChapter);
        //2.根据课程id查询所有的小节
        QueryWrapper<EduVideo> queryWrapperEduVideo = new QueryWrapper<>();
        queryWrapperEduVideo.eq("course_id", courseId);
        List<EduVideo> eduVideoList = eduVideoService.list(queryWrapperEduVideo);

        List<Chapter> finalReturnList = new ArrayList<>();
        //3.遍历查询章节list集合进行封装
        for (int i = 0; i < eduChapterList.size(); i++) {
            EduChapter eduChapter = eduChapterList.get(i);
            Chapter chapter = new Chapter();
            BeanUtils.copyProperties(eduChapter, chapter);

            List<Video> videos = new ArrayList<>();
            for (int m = 0; m < eduVideoList.size(); m++) {
                EduVideo eduVideo = eduVideoList.get(m);
                if (eduVideo.getChapterId().equals(eduChapter.getId())) {
                    Video video = new Video();
                    BeanUtils.copyProperties(eduVideo, video);
                    videos.add(video);
                    chapter.setChildren(videos);
                }
            }

            finalReturnList.add(chapter);
        }
        //4.遍历查询小节list集合进行封装

        return finalReturnList;
    }

    @Override
    public boolean deleteChapterById(String chapterId) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("chapter_id", chapterId);
        int count = eduVideoService.count(queryWrapper);
        if (count<=0){
            int result = baseMapper.deleteById(chapterId);
            return result>0;
        }else {
            throw new GuliException(20001,"章节中有小节不能删除");
        }

    }

    @Override
    public void deleteChapterByCourseId(String courseId) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("course_id",courseId);
        int delete = baseMapper.delete(queryWrapper);
        if (delete<=0){
            throw new GuliException(20001,"根据课程id删除章节失败");
        }
    }
}
