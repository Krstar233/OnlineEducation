package fun.krits.eduservice.controller;


import fun.krits.commonutils.Result;
import fun.krits.eduservice.entity.EduSubject;
import fun.krits.eduservice.entity.vo.QuickMap;
import fun.krits.eduservice.entity.vo.SubjectTreeDfsVO;
import fun.krits.eduservice.service.EduSubjectService;
import fun.krits.servicebase.exception.MyException;
import io.swagger.annotations.ApiOperation;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 课程科目 前端控制器
 * </p>
 *
 * @author krits
 * @since 2021-01-19
 */
@RestController
@CrossOrigin
@RequestMapping("/eduservice/edu-subject")
public class EduSubjectController {
    @Autowired
    private EduSubjectService subjectService;

    @SneakyThrows
    @ApiOperation(value = "添加科目")
    @PostMapping("addSubject")
    public Result addResult(MultipartFile file){
        String contentType = file.getContentType();
        if (!contentType.equals("application/vnd.ms-excel") && !contentType.equals("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))
            throw new MyException("上传文件的格式有误!");
        subjectService.saveSubject(file);
        return Result.ok().message("数据读取成功，并已存入数据库!");
    }

    @ApiOperation(value = "获取所有科目信息，返回树形结构")
    @GetMapping("findAll")
    public Result findAll(){
        // 复杂度O(N^2)，有待优化
        Map<String, List<EduSubject>> tree = subjectService.getSubjectsTree();
        Map<String, EduSubject> table = subjectService.getSubjectsTable();
        SubjectTreeDfsVO vo = new SubjectTreeDfsVO(tree, table);
        dfs("0", vo);
        return Result.ok().data("subjectTree", vo.getJsonMap());
    }

    //dfs遍历课程树，并整合成合适的Map作为返回数据
    private void dfs(String nodeId, SubjectTreeDfsVO vo){
        if (vo.checkVisit(nodeId))
            return;
        vo.setVisit(nodeId, true);
        QuickMap map = null;
        List<EduSubject> subNodes = vo.getSubNodesById(nodeId);
        if (subNodes == null)
            return;
        if (!nodeId.equals("0")){
            map = QuickMap.map()
                    .pair("label", vo.getSubject(nodeId).getTitle())
                    .pair("id", nodeId);
            vo.getJsonMap().add(map);
        }
        for (EduSubject node : subNodes){
            if (map != null)
                map.pairWithList("children",
                        QuickMap.map()
                        .pair("label", node.getTitle())
                        .pair("id", node.getId()));
            dfs(node.getId(), vo);
        }
    }
}

