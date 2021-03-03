package fun.krits.eduservice.controller;

import fun.krits.commonutils.Result;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/eduservice/user")
public class EduLoginCotroller {

    //login
    @ApiOperation(value = "后台管理系统登入接口")
    @PostMapping("login")
    public Result login(){
        return Result.ok().data("token", "admin");
    }

    //info
    @ApiOperation(value = "后台管理系统主页信息")
    @GetMapping("info")
    public Result info(){
        return Result.ok().data("roles","[admin]").data("name", "admin").data("avatar", "https://s1.ax1x.com/2020/10/18/0Ow2zF.png");
    }
}
