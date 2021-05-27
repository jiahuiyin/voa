package cn.yinjiahui.voa.portal.controller;

import cn.yinjiahui.voa.common.api.CommonResult;
import cn.yinjiahui.voa.db.model.Project;
import cn.yinjiahui.voa.db.model.Team;
import cn.yinjiahui.voa.portal.service.ProjectService;
import cn.yinjiahui.voa.portal.service.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/team")
public class TeamController {

    @Autowired
    TeamService teamService;

    @Autowired
    ProjectService projectService;

    @GetMapping("")
    public CommonResult getDefaultTeam(){
        List<Team> defaultTeam = teamService.getDefaultTeam();
        return CommonResult.success(defaultTeam);
    }

    @GetMapping("/{teamId}/member")
    public CommonResult getTeamInfo(@PathVariable("teamId") Integer id){
        Map teamInfo = teamService.getTeamInfo(id);
        return CommonResult.success(teamInfo);
    }


    @GetMapping("/{teamId}/project")
    public CommonResult getProject(@PathVariable("teamId") Integer teamId){
        List<Project> projectList = projectService.getProjectList(teamId);
        return CommonResult.success(projectList);
    }

    @GetMapping("/{teamId}/myproject")
    public CommonResult getMyProject(@PathVariable("teamId") Integer teamId){
        List<Project> myProject = projectService.getMyProject(teamId);
        return CommonResult.success(myProject);
    }

    @PostMapping("/{teamId}/project")
    public CommonResult addProject(@PathVariable("teamId") Integer teamId,
                                   @RequestParam String projectName){
        projectService.addProject(projectName,teamId);
        return getProject(teamId);
    }

    @PostMapping("")
    public CommonResult addTeam(String teamName){
        teamService.addTeam(teamName);
        return getDefaultTeam();
    }

    @PostMapping("/{teamId}/member")
    public CommonResult addTeamMember(@PathVariable("teamId") Integer teamId,Integer userId){
        boolean success = teamService.addTeamMember(teamId, userId);
        if(success){
            return CommonResult.success(getTeamInfo(teamId));
        }else{
            return CommonResult.failed();
        }
    }
}
