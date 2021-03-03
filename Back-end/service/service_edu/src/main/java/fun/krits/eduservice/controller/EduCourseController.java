package fun.krits.eduservice.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import fun.krits.commonutils.Result;
import fun.krits.eduservice.entity.EduCourse;
import fun.krits.eduservice.entity.vo.CourseInfoVo;
import fun.krits.eduservice.entity.vo.CoursePublishVo;
import fun.krits.eduservice.entity.vo.CourseQuery;
import fun.krits.eduservice.entity.vo.CourseTableVo;
import fun.krits.eduservice.service.EduCourseService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
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
@RequestMapping("/eduservice/course")
public class EduCourseController {
    @Autowired
    private EduCourseService courseService;

    @ApiOperation(value = "查诈所有课程信息，填装后台管理系统的表格")
    @GetMapping("findAllCourseTableInfo")
    public Result findAllCourseTableInfo(){
        List<CourseTableVo> list = courseService.findAllCourseTableInfo();
        return Result.ok()
                .data("total", list.size())
                .data("rows", list);
    }

    @ApiOperation(value = "根据id删除课程")
    @DeleteMapping("{courseId}")
    public Result deleteCourseById(@PathVariable String courseId){
        courseService.removeCourseById(courseId);
        return Result.ok().message("课程删除成功！");
    }

    @ApiOperation(value = "添加课程信息")
    @PostMapping("addCourseInfo")
    public Result addCourseInfo(@RequestBody CourseInfoVo vo){
        String id = courseService.saveCourseInfo(vo);
        return Result.ok().message("课程信息添加成功!").data("courseId", id);
    }

    @ApiOperation(value = "修改课程信息")
    @PostMapping("updateCourseInfo")
    public Result updateCourseInfo(@RequestBody CourseInfoVo vo){
        courseService.updateCourseInfo(vo);
        return Result.ok().message("课程信息修改成功！");
    }

    @ApiOperation(value = "根据id获取课程信息")
    @GetMapping("getCourseInfo/{courseId}")
    public Result getCourseInfo(@PathVariable String courseId){
        CourseInfoVo courseInfoVo = courseService.getCourseInfo(courseId);
        return Result.ok().data("courseInfo", courseInfoVo);
    }

    //根据课程id查询课程确认信息
    @ApiOperation(value = "根据课程id查询课程确认信息")
    @GetMapping("getPublishCourseInfo/{courseId}")
    public Result getPublishCourseInfo(@PathVariable String courseId){
        CoursePublishVo vo = courseService.getPublishCourseInfo(courseId);
        return Result.ok().data("coursePublishInfo", vo);
    }

    //课程的最终发布
    //修改课程状态
    @ApiOperation(value = "根据id发布课程，课程的最终发布---修改课程状态")
    @PostMapping("publishCourse/{courseId}")
    public Result publishCourse(@PathVariable String courseId){
        EduCourse course = new EduCourse();
        course.setGmtModified(new Date());
        course.setId(courseId);
        course.setStatus("Normal");
        courseService.updateById(course);
        return Result.ok().message("课程发布成功！");
    }

    //课程表格表单数据 --- 根据查询条件查询课程信息
    @ApiOperation(value = "课程表格表单数据 --- 根据查询条件查询课程信息")
    @PostMapping("tableQuery")
    public Result tableQuery(@RequestBody CourseQuery queryVo){
        QueryWrapper<EduCourse> wrapper = new QueryWrapper<>();
        if (queryVo.getTitle() != null)
            wrapper.like("title", queryVo.getTitle());
        if (queryVo.getStatus() != null)
            wrapper.eq("status", queryVo.getStatus());
        List<EduCourse> courseList = courseService.list(wrapper);
        List<CourseTableVo> courseTableVos = new ArrayList<>();
        for (EduCourse course : courseList){
            CourseTableVo vo = new CourseTableVo();
            BeanUtils.copyProperties(course, vo);
            courseTableVos.add(vo);
        }
        return Result.ok().data("total", courseTableVos.size())
                .data("rows", courseTableVos);
    }
}

