package fun.krits.eduservice.service.impl;

import com.alibaba.excel.EasyExcel;
import fun.krits.eduservice.entity.EduSubject;
import fun.krits.eduservice.entity.excel.SubjectData;
import fun.krits.eduservice.listener.SubjectExcelListener;
import fun.krits.eduservice.mapper.EduSubjectMapper;
import fun.krits.eduservice.service.EduSubjectService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 课程科目 服务实现类
 * </p>
 *
 * @author krits
 * @since 2021-01-19
 */
@Service
public class EduSubjectServiceImpl extends ServiceImpl<EduSubjectMapper, EduSubject> implements EduSubjectService {

    //从excel文件中读取数据并存入数据库
    @Override
    public void saveSubject(MultipartFile file) {
        try{
            InputStream in = file.getInputStream();
            EasyExcel.read(in, SubjectData.class, new SubjectExcelListener(this)).sheet().doRead();
        }catch (Exception e){

        }
    }
    //获取课程对象的树形结构（邻接表）
    @Override
    public Map<String, List<EduSubject>> getSubjectsTree(){
        Map<String,List<EduSubject>> tree = new HashMap<>();
        List<EduSubject> subjectList = this.list(null);
        for (EduSubject subject : subjectList){
            tree.putIfAbsent(subject.getParentId(), new ArrayList<>());
            tree.get(subject.getParentId()).add(subject);
        }
        return tree;
    }
    //获取课程id和课程对象的映射
    @Override
    public Map<String, EduSubject> getSubjectsTable() {
        Map<String, EduSubject> table = new HashMap<>();
        List<EduSubject> subjectList = this.list(null);
        for (EduSubject subject: subjectList){
            table.putIfAbsent(subject.getId(), subject);
        }
        return table;
    }
}
