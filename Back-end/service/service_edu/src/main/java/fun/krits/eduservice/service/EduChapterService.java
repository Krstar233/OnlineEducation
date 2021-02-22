package fun.krits.eduservice.service;

import fun.krits.eduservice.entity.EduChapter;
import com.baomidou.mybatisplus.extension.service.IService;
import fun.krits.eduservice.entity.vo.ChapterVo;

import java.util.List;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author krits
 * @since 2021-02-01
 */
public interface EduChapterService extends IService<EduChapter> {

    List<ChapterVo> getChapterVideo(String courseId);

    Boolean deleteChapter(String chapterId);
}
