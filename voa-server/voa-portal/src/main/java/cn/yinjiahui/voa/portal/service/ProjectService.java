package cn.yinjiahui.voa.portal.service;

import cn.yinjiahui.voa.db.model.Project;


import java.util.List;

public interface ProjectService {


    List<Project> getProjectList(Integer teamId);

    List<Project> getMyProject(Integer teamId);

    void addProject(String projectName,Integer teamId);

}
