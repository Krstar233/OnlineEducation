package fun.krits.eduservice.entity.vo;

import lombok.Data;

import java.util.Date;

@Data
public class CourseTableVo {
    private String id;
    private String status;
    private String title;
    private Integer lessonNum;
    private Date gmtCreate;
    private Long viewCount;
}
