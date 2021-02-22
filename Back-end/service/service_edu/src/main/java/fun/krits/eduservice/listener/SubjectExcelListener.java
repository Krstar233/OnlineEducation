package fun.krits.eduservice.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import fun.krits.eduservice.entity.EduSubject;
import fun.krits.eduservice.entity.excel.SubjectData;
import fun.krits.eduservice.service.EduSubjectService;
import fun.krits.servicebase.exception.MyException;
import lombok.SneakyThrows;

public class SubjectExcelListener extends AnalysisEventListener<SubjectData> {
    private final EduSubjectService subjectService;

    public SubjectExcelListener(EduSubjectService subjectService){
        this.subjectService = subjectService;
    }

    @SneakyThrows
    @Override
    public void invoke(SubjectData subjectData, AnalysisContext analysisContext) {
        if (subjectData == null)
            throw new MyException("文件为空！");
        EduSubject subject = getIfExitOneSubject(subjectData.getOneSubjectName());
        if (subject == null){
            saveSubject(subjectData.getOneSubjectName(), "0");
            subject = getIfExitOneSubject(subjectData.getOneSubjectName());
        }
        saveSubject(subjectData.getTwoSubjectName(), subject.getId());
    }

    private void saveSubject(String title, String parentId){
        EduSubject subjectToSave = new EduSubject();
        subjectToSave.createDate();
        subjectToSave.setParentId(parentId);
        subjectToSave.setTitle(title);
        subjectService.save(subjectToSave);
    }

    //根据title名字，获取一级分类
    private EduSubject getIfExitOneSubject(String title){
        QueryWrapper<EduSubject> wrapper = new QueryWrapper<>();
        wrapper.eq("title", title);
        wrapper.eq("parent_id", "0");
        EduSubject subject = subjectService.getOne(wrapper);
        return subject;
    }
    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {

    }
}
