package fun.krits.vodservice.controller;


import fun.krits.commonutils.Result;
import fun.krits.vodservice.service.VodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
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
}
