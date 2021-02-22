package fun.krits.eduservice.service;

import fun.krits.eduservice.entity.EduSubject;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 课程科目 服务类
 * </p>
 *
 * @author krits
 * @since 2021-01-19
 */
public interface EduSubjectService extends IService<EduSubject> {
    void saveSubject(MultipartFile file);
    Map<String, List<EduSubject>> getSubjectsTree();
    Map<String, EduSubject> getSubjectsTable();
}
