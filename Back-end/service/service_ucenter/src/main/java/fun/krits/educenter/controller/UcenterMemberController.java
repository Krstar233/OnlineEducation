package fun.krits.educenter.controller;


import fun.krits.commonutils.JwtUtil;
import fun.krits.commonutils.Result;
import fun.krits.educenter.entity.UcenterMember;
import fun.krits.educenter.entity.vo.LoginVo;
import fun.krits.educenter.entity.vo.RegisterVo;
import fun.krits.educenter.service.UcenterMemberService;
import fun.krits.educenter.utils.TokenUtil;
import fun.krits.servicebase.exception.MyException;
import io.swagger.annotations.ApiOperation;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * <p>
 * 会员表 前端控制器
 * </p>
 *
 * @author krits
 * @since 2021-02-21
 */
@RestController
@CrossOrigin
@RequestMapping("/educenter/member")
public class UcenterMemberController {
    @Autowired
    private UcenterMemberService memberService;

    //登录
    @ApiOperation(value = "用户登陆")
    @PostMapping("login")
    public Result loginUer(@RequestBody LoginVo member, HttpServletResponse response){
        String token = memberService.login(member);
        TokenUtil.setToken(token, response);
        return Result.ok().data("token", token);
    }

    //注册
    @ApiOperation(value = "用户注册")
    @PostMapping("register")
    public Result register(@RequestBody RegisterVo member){
        memberService.register(member);
        return Result.ok().message("注册成功！");
    }

    @SneakyThrows
    @ApiOperation(value = "根据token获取登录信息")
    @GetMapping("getLoginInfo")
    public Result getLoginInfo(HttpServletRequest rq){
        String token = rq.getHeader("token");
        if (token == null)
            throw new MyException("获取失败！");
        Map<String, Object> tokenBody = JwtUtil.parseToken(token);
        String memberId = (String) tokenBody.get("id");
        UcenterMember member = memberService.getById(memberId);
        return Result.ok().data("userInfo", member);
    }
}