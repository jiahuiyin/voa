package cn.yinjiahui.voa.portal.service.impl;

import cn.yinjiahui.voa.db.mapper.ProjectMapper;
import cn.yinjiahui.voa.db.mapper.TaskMapper;
import cn.yinjiahui.voa.db.model.Project;
import cn.yinjiahui.voa.db.model.Task;
import cn.yinjiahui.voa.portal.service.ProjectService;

import cn.yinjiahui.voa.portal.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class ProjectServiceImpl implements ProjectService {

    @Autowired
    ProjectMapper projectMapper;

    @Autowired
    UserService userService;

    @Autowired
    TaskMapper taskMapper;

    @Override
    public List<Project> getProjectList(Integer teamId) {
        return projectMapper.getProjectList(teamId);
    }

    @Override
    public List<Project> getMyProject(Integer teamId) {
        Integer userId=userService.getCurrentUserId();
        List<Project> myProject = projectMapper.getMyProject(teamId, userId);
        for(Project p:myProject){
            List<Task> myTask = taskMapper.getMyTask(p.getId(), userId);
            p.setTasks(myTask);

        }
        return myProject;
    }

    @Override
    public void addProject(String projectName, Integer teamId) {
        projectMapper.addProject(projectName,teamId);
    }
}
