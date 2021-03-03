package fun.krits.eduservice.controller;


import fun.krits.commonutils.Result;
import fun.krits.eduservice.entity.EduVideo;
import fun.krits.eduservice.service.EduVideoService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

/**
 * <p>
 * 课程视频 前端控制器
 * </p>
 *
 * @author krits
 * @since 2021-02-01
 */
@RestController
@CrossOrigin
@RequestMapping("/eduservice/edu-video")
public class EduVideoController {
    @Autowired
    private EduVideoService videoService;

    //根据Id获取小节
    @ApiOperation(value = "根据Id获取小节")
    @GetMapping("{videoId}")
    public Result getVideoById(@PathVariable String videoId){
        EduVideo video = videoService.getById(videoId);
        return Result.ok().data("video", video);
    }

    //添加小节
    @ApiOperation(value = "添加小节")
    @PostMapping("addVideo")
    public Result addVideo(@RequestBody EduVideo video){
        video.setGmtCreate(new Date());
        video.setGmtModified(new Date());
        videoService.save(video);
        return Result.ok().message("添加成功！");
    }

    //删除小节
    @ApiOperation(value = "删除小节")
    @DeleteMapping("{videoId}")
    public Result deleteVideo(@PathVariable String videoId){
        videoService.removeById(videoId);
        return Result.ok().message("删除成功!");
    }

    //修改小节
    @ApiOperation(value = "修改小节")
    @PostMapping("updateVideo")
    public Result updateVideo(@RequestBody EduVideo video){
        video.setGmtModified(new Date());
        videoService.updateById(video);
        return Result.ok().message("小节修改成功!");
    }
}

