package cn.yinjiahui.voa.db.mapper;

import cn.yinjiahui.voa.db.model.Task;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface TaskMapper {


    @Select("SELECT id,project_id,head_id ,title,deadline,task_description,priority,is_finish FROM voa_task WHERE project_id=#{projectId}")
    @Results({
            @Result(column = "project_id", property = "projectId"),
            @Result(column = "head_id", property = "headId"),
            @Result(column = "task_description", property = "description"),
            @Result(column = "is_finish",property = "finish"),
            @Result(column = "head_id",property = "headName",javaType = String.class,
            one=@One(select = "cn.yinjiahui.voa.db.mapper.UserMapper.getUsernameById"))
    })
    List<Task> getTask(Integer projectId);

    @Select("SELECT id,title,deadline,priority,is_finish FROM voa_task WHERE head_id=#{userId} AND project_id=#{projectId}")
    List<Task> getMyTask(Integer projectId,Integer userId);

    @Insert("INSERT  INTO `voa_task`(`project_id`,`head_id`,`title`,`deadline`,`task_description`,`priority`,`is_finish`) VALUES " +
            "(#{projectId},#{headId},#{title},#{deadline},#{description},#{priority},0)")
    void addTask(Task task);

    @Update("UPDATE voa_task SET title=#{title},deadline=#{deadline},task_description=#{description}," +
            "priority=#{priority},is_finish=#{finish} WHERE id=#{id}")
    void updateTask(Task task);

    @Delete("DELETE FROM voa_task WHERE id=#{taskId}")
    void deleteTask(Integer taskId);
}
