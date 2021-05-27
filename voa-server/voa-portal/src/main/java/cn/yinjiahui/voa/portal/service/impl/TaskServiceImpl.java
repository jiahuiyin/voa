package cn.yinjiahui.voa.portal.service.impl;

import cn.yinjiahui.voa.db.mapper.TaskMapper;
import cn.yinjiahui.voa.db.model.Task;
import cn.yinjiahui.voa.portal.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class TaskServiceImpl implements TaskService {

    @Autowired
    TaskMapper taskMapper;

    @Override
    public List<Task> getTask(Integer projectId) {
        return taskMapper.getTask(projectId);
    }

    @Override
    public void addTask(Task task) {
        taskMapper.addTask(task);
    }

    @Override
    public void updateTask(Task task) {
        taskMapper.updateTask(task);
    }

    @Override
    public void deleteTask(Integer taskId) {
        taskMapper.deleteTask(taskId);
    }
}
