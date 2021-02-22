package fun.krits.eduservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import fun.krits.eduservice.entity.EduChapter;
import fun.krits.eduservice.entity.EduVideo;
import fun.krits.eduservice.entity.vo.ChapterVo;
import fun.krits.eduservice.entity.vo.VideoVo;
import fun.krits.eduservice.mapper.EduChapterMapper;
import fun.krits.eduservice.service.EduChapterService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import fun.krits.eduservice.service.EduVideoService;
import fun.krits.servicebase.exception.MyException;
import lombok.SneakyThrows;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
public class EduChapterServiceImpl extends ServiceImpl<EduChapterMapper, EduChapter> implements EduChapterService {
    @Autowired
    private EduVideoService videoService;
    //课程大纲列表，根据id查询
    @Override
    public List<ChapterVo> getChapterVideo(String courseId) {
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.eq("course_id",courseId);
        List<EduChapter> chapters = baseMapper.selectList(wrapper);
        List<ChapterVo> resList = new ArrayList<>();

        // 多次查询 封装数据列表
        for (EduChapter chapter : chapters){
            ChapterVo chapterVo = new ChapterVo();
            chapterVo.setId(chapter.getId());
            chapterVo.setTitle(chapter.getTitle());
            QueryWrapper videoWrapper = new QueryWrapper();
            videoWrapper.eq("course_id", courseId);
            videoWrapper.eq("chapter_id", chapter.getId());
            List<EduVideo> videos = videoService.list(videoWrapper);
            List<VideoVo> videoVos = new ArrayList<>();
            for (EduVideo video : videos){
                VideoVo videoVo = new VideoVo();
                BeanUtils.copyProperties(video, videoVo);
                videoVos.add(videoVo);
            }
            chapterVo.setChildren(videoVos);
            resList.add(chapterVo);
        }
        return resList;
    }

    @SneakyThrows
    @Override
    public Boolean deleteChapter(String chapterId) {
        QueryWrapper<EduVideo> wrapper = new QueryWrapper<>();
        wrapper.eq("chapter_id", chapterId);
        int count = videoService.count(wrapper);

        if (count > 0)
            throw new MyException("无法删除带有小节的章节!");
        else
            return baseMapper.deleteById(chapterId) > 0;
    }
}
