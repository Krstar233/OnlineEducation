package fun.krits.eduservice.controller.front;

import fun.krits.commonutils.Result;
import fun.krits.eduservice.entity.EduCourse;
import fun.krits.eduservice.entity.EduTeacher;
import fun.krits.eduservice.service.EduCourseService;
import fun.krits.eduservice.service.EduTeacherService;
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

    @GetMapping("{teacherId}")
    public Result getTeacherDetailById(@PathVariable String teacherId){
        EduTeacher teacher = teacherService.getById(teacherId);
        List<EduCourse> courseList = courseService.getTeacherCourse(teacherId);
        return Result.ok().data("teacher", teacher).data("courseList", courseList);
    }
}
