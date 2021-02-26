package fun.krits.eduservice.entity.vo.front;

import lombok.Data;

@Data
public class CourseFrontQueryVo {
    private String subjectParentId;
    private String subjectId;
    private String sortBy;
}
