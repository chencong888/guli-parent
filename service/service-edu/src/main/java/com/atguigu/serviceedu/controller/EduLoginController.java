package com.atguigu.serviceedu.controller;

import com.atguigu.commonutils.R;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.*;

@Api(description = "讲师登录")
@RestController
@RequestMapping("/serviceedu/user")
@CrossOrigin
public class EduLoginController {
    @PostMapping(value = "/login")
    public R login() {
        return R.ok().data("token", "admin");
    }

    @GetMapping(value = "/info")
    public R info() {
        return R.ok().data("roles","[admin]").data("name","admin").data("avatar","https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif");
    }

}
