package cn.yinjiahui.voa.db.model;

import lombok.Data;

import java.util.List;

@Data
public class Project {
    private Integer id;

    private String name;

    private List<Task> tasks;
}
