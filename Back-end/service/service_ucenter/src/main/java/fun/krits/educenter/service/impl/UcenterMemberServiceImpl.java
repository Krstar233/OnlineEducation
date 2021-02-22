package fun.krits.educenter.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import fun.krits.commonutils.JwtUtil;
import fun.krits.educenter.entity.UcenterMember;
import fun.krits.educenter.entity.vo.LoginVo;
import fun.krits.educenter.entity.vo.RegisterVo;
import fun.krits.educenter.mapper.UcenterMemberMapper;
import fun.krits.educenter.service.UcenterMemberService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import fun.krits.servicebase.exception.MyException;
import lombok.SneakyThrows;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * 会员表 服务实现类
 * </p>
 *
 * @author krits
 * @since 2021-02-21
 */
@Service
public class UcenterMemberServiceImpl extends ServiceImpl<UcenterMemberMapper, UcenterMember> implements UcenterMemberService {

    @SneakyThrows
    @Override
    public String login(LoginVo member) {
        String mobile = member.getMobile();
        String password = member.getPassword();

        //手机号和密码非空
        if (StringUtils.isEmpty(mobile) || StringUtils.isEmpty(password))
            throw new MyException("登录失败");
        //判断手机号是否正确
        QueryWrapper<UcenterMember> wrapper = new QueryWrapper<>();
        wrapper.eq("mobile", mobile);
        UcenterMember mobileMember = baseMapper.selectOne(wrapper);
        if (mobileMember == null)
            throw new MyException("登陆失败");
        //判断密码
        if (!password.equals(mobileMember.getPassword()))
            throw new MyException("登陆失败");
        //判断是否被禁用
        if (mobileMember.getIsDisabled())
            throw new MyException("登陆失败");
        Map<String, Object> map = new HashMap<>();
        map.put("id", mobileMember.getId());
        map.put("nickname", mobileMember.getNickname());
        return JwtUtil.createToken(map);
    }

    @SneakyThrows
    @Override
    public void register(RegisterVo member) {
        String nickname = member.getNickname();
        String mobile = member.getMobile();
        String password = member.getPassword();
        String code = member.getCode();

        //检查数据库中是否有相同的手机号
        QueryWrapper<UcenterMember> wrapper = new QueryWrapper<>();
        wrapper.eq("mobile", mobile);
        Integer count = baseMapper.selectCount(wrapper);
        if (count > 0){
            throw new MyException("注册失败!");
        }

        UcenterMember registerMember = new UcenterMember();
        registerMember.setNickname(nickname);
        registerMember.setMobile(mobile);
        registerMember.setPassword(password);
        registerMember.setIsDisabled(false);
        registerMember.setGmtCreate(new Date());
        registerMember.setGmtModified(new Date());

        //插入数据
        this.save(registerMember);
    }

    @Override
    public LoginVo getLoginVo(String memberId) {
        UcenterMember member = baseMapper.selectById(memberId);
        if (member == null)
            return null;
        LoginVo loginVo = new LoginVo();
        BeanUtils.copyProperties(member, loginVo);
        return loginVo;
    }
}
