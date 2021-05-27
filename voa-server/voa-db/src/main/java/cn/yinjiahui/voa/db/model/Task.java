package cn.yinjiahui.voa.db.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
public class Task {
    private Integer id;

    private Integer projectId;

    private Integer headId;

    private String headName;

    private String title;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    private Date deadline;

    private String description;

    private Integer priority;
    //(0,1,2,3  3为最高)

    private Integer finish;

}
