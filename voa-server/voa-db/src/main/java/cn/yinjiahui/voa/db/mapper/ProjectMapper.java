package cn.yinjiahui.voa.db.mapper;

import cn.yinjiahui.voa.db.model.Project;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface ProjectMapper {

    @Select("SELECT id,project_name FROM voa_project WHERE team_id=#{teamId}")
    @Result(column = "project_name" ,property = "name")
    List<Project> getProjectList(Integer teamId);


    @Select("SELECT t1.id,t1.project_name FROM voa_project AS t1 INNER JOIN voa_task AS t2" +
            " ON t1.id=t2.project_id WHERE t1.team_id=#{teamId} AND t2.head_id=#{userId} GROUP BY t1.id")
    @Result(column = "project_name",property = "name")
    List<Project> getMyProject(Integer teamId,Integer userId);


    @Insert("INSERT  INTO `voa_project`(`project_name`,`team_id`) VALUES (#{projectName},#{teamId})")
    void addProject(String projectName,Integer teamId);
}
