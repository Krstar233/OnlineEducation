package fun.krits.eduservice.controller.front;

import fun.krits.commonutils.Result;
import fun.krits.eduservice.entity.EduCourse;
import fun.krits.eduservice.entity.EduTeacher;
import fun.krits.eduservice.service.EduCourseService;
import fun.krits.eduservice.service.EduTeacherService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/eduservice/teacher-front")
public class TeacherFrontController {
    @Autowired
    private EduTeacherService teacherService;
    @Autowired
    private EduCourseService courseService;

    @ApiOperation(value = "根据教师id获取前台所需的教师信息")
    @GetMapping("{teacherId}")
    public Result getTeacherDetailById(@PathVariable String teacherId){
        EduTeacher teacher = teacherService.getById(teacherId);
        List<EduCourse> courseList = courseService.getTeacherCourse(teacherId);
        return Result.ok().data("teacher", teacher).data("courseList", courseList);
    }
}
