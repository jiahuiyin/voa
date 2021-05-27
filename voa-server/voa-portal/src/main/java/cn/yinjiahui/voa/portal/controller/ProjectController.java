package cn.yinjiahui.voa.portal.controller;


import cn.yinjiahui.voa.common.api.CommonResult;
import cn.yinjiahui.voa.db.model.Task;
import cn.yinjiahui.voa.portal.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/project/{projectId}")
public class ProjectController {

    @Autowired
    TaskService taskService;

    @GetMapping("/task")
    public CommonResult getTask(@PathVariable("projectId") Integer id){
        List<Task> task = taskService.getTask(id);
        return CommonResult.success(task);
    }

    @PostMapping("/task")
    public CommonResult addTask(@PathVariable("projectId") Integer id,Task task){
        task.setProjectId(id);
        taskService.addTask(task);
        return getTask(id);
    }
    @PutMapping("/task")
    public CommonResult updateTask(@PathVariable("projectId") Integer id,Task task){
        task.setProjectId(id);
        taskService.updateTask(task);
        return getTask(id);
    }

    @DeleteMapping("/task/{taskId}")
    public CommonResult deleteTask(@PathVariable("projectId") Integer id,@PathVariable("taskId")Integer taskId){
        taskService.deleteTask(taskId);
        return getTask(id);
    }


}
