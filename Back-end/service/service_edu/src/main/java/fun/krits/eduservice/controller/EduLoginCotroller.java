package fun.krits.eduservice.controller;

import fun.krits.commonutils.Result;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/eduservice/user")
public class EduLoginCotroller {

    //login
    @PostMapping("login")
    public Result login(){
        return Result.ok().data("token", "admin");
    }

    //info
    @GetMapping("info")
    public Result info(){
        return Result.ok().data("roles","[admin]").data("name", "admin").data("avatar", "https://s1.ax1x.com/2020/10/18/0Ow2zF.png");
    }
}
