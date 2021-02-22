package fun.krits.eduservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import fun.krits.eduservice.entity.EduChapter;
import fun.krits.eduservice.entity.EduCourse;
import fun.krits.eduservice.entity.EduCourseDescription;
import fun.krits.eduservice.entity.EduVideo;
import fun.krits.eduservice.entity.vo.CourseInfoVo;
import fun.krits.eduservice.entity.vo.CoursePublishVo;
import fun.krits.eduservice.entity.vo.CourseTableVo;
import fun.krits.eduservice.mapper.EduCourseMapper;
import fun.krits.eduservice.service.EduChapterService;
import fun.krits.eduservice.service.EduCourseDescriptionService;
import fun.krits.eduservice.service.EduCourseService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import fun.krits.eduservice.service.EduVideoService;
import fun.krits.servicebase.exception.MyException;
import lombok.SneakyThrows;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author krits
 * @since 2021-02-01
 */
@Service
public class EduCourseServiceImpl extends ServiceImpl<EduCourseMapper, EduCourse> implements EduCourseService {
    @Autowired
    private EduCourseDescriptionService courseDescriptionService;

    @Autowired
    private EduVideoService videoService;

    @Autowired
    private EduChapterService chapterService;

    //添加课程基本信息
    @SneakyThrows
    @Override
    public String saveCourseInfo(CourseInfoVo vo){
        //1. 课程表中添加课程基本信息
        EduCourse course = new EduCourse();
        BeanUtils.copyProperties(vo, course);
        course.setGmtCreate(new Date()); course.setGmtModified(new Date());
        int insert = baseMapper.insert(course);
        if (insert <= 0){
            //添加失败
            throw new MyException("添加课程信息失败");
        }

        //2. 向课程表添加课程简介
        EduCourseDescription description = new EduCourseDescription();
        description.setId(course.getId());
        description.setDescription(vo.getDescription());
        description.setGmtCreate(new Date()); description.setGmtModified(new Date());
        courseDescriptionService.save(description);
        return course.getId();
    }

    @Override
    public CourseInfoVo getCourseInfo(String courseId) {
        // 查询课程信息
        CourseInfoVo retVo = new CourseInfoVo();
        EduCourse course = baseMapper.selectById(courseId);
        BeanUtils.copyProperties(course, retVo);

        // 查询描述信息
        EduCourseDescription description = courseDescriptionService.getById(courseId);
        retVo.setDescription(description.getDescription());
        return retVo;
    }

    @SneakyThrows
    @Override
    public void updateCourseInfo(CourseInfoVo vo) {
        // 修改课程表
        EduCourse course = new EduCourse();
        BeanUtils.copyProperties(vo, course);
        course.setGmtModified(new Date());
        int update = baseMapper.updateById(course);
        if (update <= 0)
            throw new MyException("修改课程信息失败！");
        // 修改描述表
        EduCourseDescription description = new EduCourseDescription();
        description.setGmtModified(new Date());
        description.setId(vo.getId());
        description.setDescription(vo.getDescription());
        courseDescriptionService.updateById(description);
    }

    @Override
    public CoursePublishVo getPublishCourseInfo(String courseId) {
        CoursePublishVo vo = baseMapper.getPublishCourseInfo(courseId);
        return vo;
    }

    @Override
    public List<CourseTableVo> findAllCourseTableInfo() {
        List<CourseTableVo> retList = new ArrayList<>();
        List<EduCourse> courseList = baseMapper.selectList(null);
        for (EduCourse course : courseList){
            CourseTableVo vo = new CourseTableVo();
            BeanUtils.copyProperties(course, vo);
            retList.add(vo);
        }
        return retList;
    }

    // TODO: 此多表操作有风险，可能需要回滚，或者加锁操作
    @Override
    public void removeCourseById(String courseId) {
        //删除课程表
        baseMapper.deleteById(courseId);
        //删除描述表
        courseDescriptionService.removeById(courseId);
        //删除章节表
        QueryWrapper<EduChapter> wrapper1 = new QueryWrapper<>();
        wrapper1.eq("course_id", courseId);
        chapterService.remove(wrapper1);
        //删除小节表
        QueryWrapper<EduVideo> wrapper2 = new QueryWrapper<>();
        wrapper2.eq("course_id", courseId);
        videoService.remove(wrapper2);
    }
}