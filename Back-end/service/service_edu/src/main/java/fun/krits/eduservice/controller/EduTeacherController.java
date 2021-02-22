package fun.krits.eduservice.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import fun.krits.commonutils.Result;
import fun.krits.eduservice.entity.EduTeacher;
import fun.krits.eduservice.entity.vo.TeacherQuery;
import fun.krits.eduservice.service.EduTeacherService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

/**
 * <p>
 * 讲师 前端控制器
 * </p>
 *
 * @author krits
 * @since 2021-01-03
 */
@RestController
@RequestMapping("/eduservice/teacher")
@CrossOrigin
public class EduTeacherController {

    @Autowired
    private EduTeacherService teacherService;

    @GetMapping("findAll")
    @ApiOperation(value = "所有讲师列表")
    public Result findAll(){
        List<EduTeacher> teachers = teacherService.list(null);
        return Result.ok().data("items", teachers);
    }

    //逻辑删除
    @GetMapping("{id}")
    @ApiOperation(value = "根据id查询讲师")
    public Result getTeacherById(@ApiParam(name = "id", value = "讲师ID", required = true) @PathVariable String id){
        EduTeacher teacher = teacherService.getById(id);
        return Result.ok().data("teacher", teacher);
    }

    //逻辑删除
    @DeleteMapping("{id}")
    @ApiOperation(value = "根据id逻辑删除讲师")
    public Result removeTeacher(@ApiParam(name = "id", value = "讲师ID", required = true) @PathVariable String id){
        boolean ok = teacherService.removeById(id);
        if (ok){
            return Result.ok();
        }
        return Result.error();
    }

    //分页查询
    @GetMapping("pageTeacher/{current}/{limit}")
    @ApiOperation(value = "分页查询讲师信息")
    public Result pageListTeacher(@PathVariable Long current, @PathVariable Long limit){
        //创建page对象
        Page<EduTeacher> pageTeacher = new Page<>(current, limit);
        teacherService.page(pageTeacher, null);
        return Result.ok().data("total", pageTeacher.getTotal()).data("rows", pageTeacher.getRecords());
    }

    //分页条件查询
    @PostMapping("pageTeacherCondition/{current}/{limit}")
    public Result pageTeacherCondition(@PathVariable Long current, @PathVariable Long limit,@RequestBody TeacherQuery teacherQuery){
        // 创建page对象
        Page<EduTeacher> pageTeacher = new Page<>(current, limit);
        QueryWrapper<EduTeacher> wrapper = new QueryWrapper<>();
        //多条件组合查询
        //判断条件值是否为空
        String name = teacherQuery.getName();
        String level = teacherQuery.getLevel();
        String begin = teacherQuery.getBegin();
        String end = teacherQuery.getEnd();
        if (!StringUtils.isEmpty(name)){
            wrapper.like("name", name);
        }
        if (!StringUtils.isEmpty(level)){
            wrapper.eq("level", level);
        }
        if (!StringUtils.isEmpty(begin)){
            wrapper.ge("gmt_create", begin);
        }
        if (!StringUtils.isEmpty(end)){
            wrapper.le("gmt_create", end);
        }
        wrapper.orderByDesc("gmt_create");

        teacherService.page(pageTeacher, wrapper);
        return Result.ok().data("total", pageTeacher.getTotal()).data("rows", pageTeacher.getRecords());
    }

    @ApiOperation(value = "新增讲师")
    @PostMapping()
    public Result addTeacher(@RequestBody EduTeacher eduTeacher){
        eduTeacher.setGmtCreate(new Date());
        eduTeacher.setGmtModified(new Date());
        if (teacherService.save(eduTeacher)){
            return Result.ok();
        }
        return Result.error();
    }

    @ApiOperation(value = "根据id修改讲师")
    @PutMapping("{id}")
    public Result updateTeacher(
            @ApiParam(name = "id", value = "讲师id", required = true)
            @PathVariable String id,

            @ApiParam(name = "teacher", value = "讲师对象", required = true)
            @RequestBody EduTeacher teacher){

        teacher.setId(id);
        teacher.setGmtModified(new Date());
        if (teacherService.updateById(teacher))
            return Result.ok();
        return Result.error();
    }
}

