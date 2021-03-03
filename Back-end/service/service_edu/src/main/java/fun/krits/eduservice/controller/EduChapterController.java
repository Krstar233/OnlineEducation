package fun.krits.eduservice.controller;


import fun.krits.commonutils.Result;
import fun.krits.eduservice.entity.EduChapter;
import fun.krits.eduservice.entity.vo.ChapterVo;
import fun.krits.eduservice.service.EduChapterService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author krits
 * @since 2021-02-01
 */
@RestController
@CrossOrigin
@RequestMapping("/eduservice/edu-chapter")
public class EduChapterController {
    @Autowired
    private EduChapterService chapterService;

    @ApiOperation(value = "根据课程id，获取章节小节目录信息")
    @GetMapping("getChapterVideo/{courseId}")
    public Result getChapterVideo(@PathVariable String courseId){
        List<ChapterVo> chapterVoList = chapterService.getChapterVideo(courseId);
        return Result.ok().data("items", chapterVoList);
    }

    @ApiOperation(value = "添加章节")
    @PostMapping("addChapter")
    public Result addChapter(@RequestBody EduChapter chapter){
        chapter.setGmtCreate(new Date());
        chapter.setGmtModified(new Date());
        chapterService.save(chapter);
        return Result.ok().message("章节添加成功！");
    }

    @ApiOperation(value = "根据章节id，获取章节信息")
    @GetMapping("getChapterInfo/{chapterId}")
    public Result getChapterInfo(@PathVariable String chapterId){
        EduChapter chapter = chapterService.getById(chapterId);
        return Result.ok().data("chapter", chapter);
    }

    @ApiOperation(value = "更新章节信息")
    @PostMapping("updateChapter")
    public Result updateChapter(@RequestBody EduChapter chapter){
        chapter.setGmtModified(new Date());
        chapterService.updateById(chapter);
        return Result.ok().message("章节信息跟新成功!");
    }

    @ApiOperation(value = "根据章节id，删除该章节")
    @DeleteMapping("{chapterId}")
    public Result deleteChapter(@PathVariable String chapterId){
        //根据chapterId 查询小节表 有小节则不删除
        Boolean deleted = chapterService.deleteChapter(chapterId);
        if (deleted)
            return Result.ok().message("章节删除成功！");
        return Result.error().message("章节删除失败！");
    }
}

