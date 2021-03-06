package fun.krits.eduservice.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import fun.krits.eduservice.entity.EduCourse;
import fun.krits.eduservice.entity.vo.CourseInfoVo;
import fun.krits.eduservice.entity.vo.CoursePublishVo;
import fun.krits.eduservice.entity.vo.CourseTableVo;
import fun.krits.eduservice.entity.vo.front.CourseFrontInfo;
import fun.krits.eduservice.entity.vo.front.CourseFrontQueryVo;

import java.util.List;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author krits
 * @since 2021-02-01
 */
public interface EduCourseService extends IService<EduCourse> {
    String saveCourseInfo(CourseInfoVo vo);

    CourseInfoVo getCourseInfo(String courseId);

    void updateCourseInfo(CourseInfoVo vo);

    CoursePublishVo getPublishCourseInfo(String courseId);

    List<CourseTableVo> findAllCourseTableInfo();

    void removeCourseById(String courseId);

    List<EduCourse> getTeacherCourse(String teacherId);

    IPage<EduCourse> frontQuery(CourseFrontQueryVo vo, Long currentPage, Long limit);

    CourseFrontInfo getCourseFrontInfo(String courseId);
}
