package fun.krits.vodservice.controller;


import fun.krits.commonutils.Result;
import fun.krits.vodservice.service.VodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@CrossOrigin
@RequestMapping("/eduvod/video")
public class VodController {
    @Autowired
    private VodService vodService;

    @PostMapping("uploadAliyunVideo")
    public Result uploadAliyunVideo(MultipartFile file){
        //返回上传结果
        String videoId = vodService.uploadVideoAliyun(file);
        return Result.ok().data("videoId", videoId);
    }

    @GetMapping("get-play-auth/{videoId}")
    public Result getVideoPlayAuth(@PathVariable String videoId){
        String videoAuth = vodService.getVideoAuth(videoId);
        return Result.ok().data("auth", videoAuth);
    }
}
