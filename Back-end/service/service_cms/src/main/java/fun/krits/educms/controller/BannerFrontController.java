package fun.krits.educms.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import fun.krits.commonutils.Result;
import fun.krits.educms.entity.CrmBanner;
import fun.krits.educms.service.CrmBannerService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/educms/bannerfront")
public class BannerFrontController {

    @Autowired
    private CrmBannerService bannerService;

    @Cacheable(value = "banner", key = "'selectIndexList'")
    @ApiOperation(value = "获取所有的banner，以列表的方式返回")
    @GetMapping("getAllBanner")
    public Result getAllBanner(){
        QueryWrapper<CrmBanner> wrapper = new QueryWrapper<>();
        wrapper.orderByAsc("id");
        wrapper.last("limit 2");
        List<CrmBanner> banners = bannerService.list(wrapper);
        return Result.ok().data("banners",banners);
    }
}
