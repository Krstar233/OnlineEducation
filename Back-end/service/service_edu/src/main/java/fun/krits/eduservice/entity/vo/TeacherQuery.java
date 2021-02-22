package fun.krits.eduservice.entity.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel(value = "Teacher查询对象", description = "讲师查询对象封装")
@Data
public class TeacherQuery {

    @ApiModelProperty(value = "讲师姓名")
    private String name;

    @ApiModelProperty(value = "头衔 1高级讲师 2首席讲师")
    private String level;

    @ApiModelProperty(value = "查询开始时间", example = "2019-10-18 10:10:00")
    private String begin;

    @ApiModelProperty(value = "查询结束时间", example = "2019-10-18 10:10:00")
    private String end;

}
