package fun.krits.eduservice.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import fun.krits.eduservice.entity.EduCourse;
import fun.krits.eduservice.entity.vo.CoursePublishVo;
import fun.krits.eduservice.entity.vo.front.CourseFrontInfo;

/**
 * <p>
 * 课程 Mapper 接口
 * </p>
 *
 * @author krits
 * @since 2021-02-01
 */
public interface EduCourseMapper extends BaseMapper<EduCourse> {
    CoursePublishVo getPublishCourseInfo(String courseId);
    CourseFrontInfo getCourseFrontInfo(String courseId);
}
