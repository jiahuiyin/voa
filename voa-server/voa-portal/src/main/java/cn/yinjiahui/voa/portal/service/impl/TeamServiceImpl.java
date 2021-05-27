package cn.yinjiahui.voa.portal.service.impl;

import cn.yinjiahui.voa.db.mapper.TeamMapper;
import cn.yinjiahui.voa.db.model.Team;
import cn.yinjiahui.voa.db.model.UserInfo;
import cn.yinjiahui.voa.portal.repository.SessionRepository;
import cn.yinjiahui.voa.portal.service.TeamService;
import cn.yinjiahui.voa.portal.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class TeamServiceImpl implements TeamService {

    @Autowired
    TeamMapper teamMapper;

    @Autowired
    UserService userService;

    @Autowired
    SessionRepository sessionRepository;

    @Override
    public List<Team> getDefaultTeam() {
        Integer userId=userService.getCurrentUserId();
        return teamMapper.getDefaultTeam(userId);

    }

    @Override
    public Map getTeamInfo(Integer teamId) {
        Integer count=teamMapper.getTeamMemberCount(teamId);
        List<UserInfo> teamMember = teamMapper.getTeamMember(teamId);
        for(UserInfo user:teamMember){
            if(sessionRepository.contains(user.getId())){
                user.setOnline(1);
            }else{
                user.setOnline(0);
            }
        }
        Map map=new HashMap(2);
        map.put("count",count);
        map.put("teamMember",teamMember);
        return map;
    }

    @Override
    public boolean inTeam(Integer teamId) {
        Integer userId=userService.getCurrentUserId();
        Integer id=teamMapper.inTeam(teamId,userId);
        return id==null?false:true;
    }

    @Override
    public void addTeam(String teamName) {
        Integer userId=userService.getCurrentUserId();
        Team team=new Team();
        team.setTeamName(teamName);
        team.setManagerId(userId);
        teamMapper.addTeam(team);
        teamMapper.addTeamMember(team.getId(),userId);
    }

    @Override
    public boolean addTeamMember(Integer teamId, Integer userId) {
        Integer managerId = userService.getCurrentUserId();
        Integer isTeamManager = teamMapper.isTeamManager(teamId, managerId);
        if(isTeamManager==0){
            return false;
        }
        Integer existMember = teamMapper.existTeamMember(teamId, userId);
        if(existMember>0){
            return false;
        }
        teamMapper.addTeamMember(teamId,userId);
        return true;
    }
}
