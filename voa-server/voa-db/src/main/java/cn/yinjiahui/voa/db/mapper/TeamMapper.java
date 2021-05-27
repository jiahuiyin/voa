package cn.yinjiahui.voa.db.mapper;

import cn.yinjiahui.voa.db.model.Team;
import cn.yinjiahui.voa.db.model.UserInfo;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface TeamMapper {

    @Select("SELECT t2.id,t2.team_name FROM voa_team_user AS t1 INNER JOIN " +
            "voa_team AS t2 ON t1.team_id=t2.id WHERE t1.user_id=#{userId}")
    @Result(column = "team_name",property = "teamName")
    List<Team> getDefaultTeam(Integer userId);


    @Select("SELECT COUNT(*) FROM voa_team_user WHERE team_id=#{teamId}")
    Integer getTeamMemberCount(Integer teamId);


    @Select("SELECT t2.id,t2.username,t2.avatar FROM voa_team_user AS t1 INNER JOIN " +
            "voa_user AS t2 ON t1.user_id=t2.id WHERE t1.team_id=#{teamId}")
    List<UserInfo> getTeamMember(Integer teamId);

    @Select("SELECT id FROM voa_team_user WHERE team_id=#{teamId} AND user_id=#{userId}")
    Integer inTeam(Integer teamId,Integer userId);


    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "team.id")
    @Insert("INSERT  INTO `voa_team`(`team_name`,`managerid`) VALUES (#{teamName},#{managerId})")
    void addTeam(Team team);

    @Insert("INSERT INTO voa_team_user(team_id,user_id)VALUES(#{teamId},#{userId})")
    void addTeamMember(Integer teamId,Integer userId);

    @Select("SELECT COUNT(*) FROM voa_team_user WHERE team_id=#{teamId} AND user_id=#{userId}")
    Integer existTeamMember(Integer teamId,Integer userId);

    @Select("SELECT COUNT(*) FROM voa_team WHERE id=#{teamId} AND managerid=#{managerId}")
    Integer isTeamManager(Integer teamId,Integer managerId);
}
