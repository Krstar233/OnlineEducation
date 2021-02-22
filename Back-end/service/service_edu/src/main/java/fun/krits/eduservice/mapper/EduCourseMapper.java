package fun.krits.eduservice.mapper;

import fun.krits.eduservice.entity.EduCourse;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import fun.krits.eduservice.entity.vo.CourseInfoVo;
import fun.krits.eduservice.entity.vo.CoursePublishVo;

import java.util.List;

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
    List<CourseInfoVo> findAllCourseInfo();
}
