package com.atguigu.serviceedu.controller;


import com.atguigu.commonutils.R;
import com.atguigu.serviceedu.entity.EduVideo;
import com.atguigu.serviceedu.service.EduVideoService;
import org.apache.xmlbeans.impl.xb.xsdschema.Public;
import org.apache.xmlbeans.impl.xb.xsdschema.impl.PublicImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 课程视频 前端控制器
 * </p>
 *
 * @author atguigu
 * @since 2021-05-10
 */
@RestController
@RequestMapping("/serviceedu/video")
@CrossOrigin
public class EduVideoController {
    @Autowired
    private EduVideoService eduVideoService;

    @PostMapping("/addvideo")
    public R addVideo(@RequestBody EduVideo eduVideo) {
        eduVideoService.save(eduVideo);
        return R.ok();
    }

    @GetMapping("/deleteVideoById/{id}")
    public R deleteVideoById(@PathVariable("id") String videoId){
        boolean b = eduVideoService.removeById(videoId);
        return R.ok();
    }

    @PostMapping("/updateVideo")
    public R updateVideo(@RequestBody EduVideo eduVideo) {
        eduVideoService.updateById(eduVideo);
        return R.ok();
    }

    @GetMapping("/getVideoById/{id}")
    public R getVideoById(@PathVariable("id") String videoId){
        EduVideo video = eduVideoService.getById(videoId);
        return R.ok().data("video",video);
    }

}

