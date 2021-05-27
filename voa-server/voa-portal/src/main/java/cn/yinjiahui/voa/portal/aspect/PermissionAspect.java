package cn.yinjiahui.voa.portal.aspect;

import cn.yinjiahui.voa.common.api.CommonResult;
import cn.yinjiahui.voa.portal.service.TeamService;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Order(1)
@Slf4j
public class PermissionAspect {



    @Autowired
    TeamService teamService;

    @Around("execution(cn.yinjiahui.voa.common.api.CommonResult cn.yinjiahui.voa.portal.controller.TeamController.*(Integer,..)) && args(teamId)")
    public CommonResult doAround(ProceedingJoinPoint pjp, Integer teamId) throws Throwable {

        if(!teamService.inTeam(teamId)){
            return CommonResult.forbidden(null);
        }
        System.out.println(teamId);
        return (CommonResult) pjp.proceed();
    }

    @Around("execution(* cn.yinjiahui.voa.portal.controller.*.*(..))")
    public CommonResult doException(ProceedingJoinPoint pjp) throws Throwable {
        Object proceed=null;
        try{
            proceed = pjp.proceed();
        }catch (Exception e){
            log.error(e.toString());
            return CommonResult.failed();
        }
        return (CommonResult)proceed;
    }

}
