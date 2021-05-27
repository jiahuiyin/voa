package cn.yinjiahui.voa.portal.service;

import cn.yinjiahui.voa.db.model.Task;

import java.util.List;

public interface TaskService {

    List<Task> getTask(Integer projectId);

    void addTask(Task task);

    void updateTask(Task task);

    void deleteTask(Integer taskId);
}
