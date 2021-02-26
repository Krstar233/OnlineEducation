package fun.krits.eduservice.controller.front;

import com.baomidou.mybatisplus.core.metadata.IPage;
import fun.krits.commonutils.Result;
import fun.krits.eduservice.entity.EduCourse;
import fun.krits.eduservice.entity.vo.front.CourseFrontInfo;
import fun.krits.eduservice.entity.vo.front.CourseFrontQueryVo;
import fun.krits.eduservice.service.EduCourseService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/eduservice/course-front")
public class CourseFrontController {
    @Autowired
    private EduCourseService courseService;

    @PostMapping("query/{currentPage}/{limit}")
    public Result query(@RequestBody CourseFrontQueryVo vo, @PathVariable Long currentPage, @PathVariable Long limit){
        IPage<EduCourse> page = courseService.frontQuery(vo, currentPage, limit);
        return Result.ok()
                .data("total", page.getTotal())
                .data("courseList", page.getRecords());
    }

    @ApiOperation(value = "查询前台课程详细页面信息")
    @GetMapping("detail/{courseId}")
    public Result getCourseDetail(@PathVariable String courseId){
        CourseFrontInfo courseFrontInfo = courseService.getCourseFrontInfo(courseId);
        return Result.ok()
                .data("courseInfo", courseFrontInfo);
    }
}
