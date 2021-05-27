package cn.yinjiahui.voa.portal.service;

import cn.yinjiahui.voa.db.model.Team;

import java.util.List;
import java.util.Map;

public interface TeamService {

    List<Team> getDefaultTeam();

    Map getTeamInfo(Integer teamId);

    boolean inTeam(Integer teamId);

    void addTeam(String teamName);

    boolean addTeamMember(Integer teamId,Integer userId);
}
