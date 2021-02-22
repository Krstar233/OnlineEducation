package fun.krits.educenter.service;

import fun.krits.educenter.entity.UcenterMember;
import com.baomidou.mybatisplus.extension.service.IService;
import fun.krits.educenter.entity.vo.LoginVo;
import fun.krits.educenter.entity.vo.RegisterVo;

/**
 * <p>
 * 会员表 服务类
 * </p>
 *
 * @author krits
 * @since 2021-02-21
 */
public interface UcenterMemberService extends IService<UcenterMember> {

    String login(LoginVo member);

    void register(RegisterVo member);

    LoginVo getLoginVo(String memberId);
}
