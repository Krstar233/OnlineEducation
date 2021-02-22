package fun.krits.eduservice.entity.vo;

import lombok.Data;

/***
 * 课程Table的查询表单数据
 */
@Data
public class CourseQuery {
    private String title;
    private String status;
}