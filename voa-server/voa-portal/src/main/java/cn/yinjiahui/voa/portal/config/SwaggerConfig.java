package cn.yinjiahui.voa.portal.config;



import cn.yinjiahui.voa.common.config.BaseSwaggerConfig;
import cn.yinjiahui.voa.common.domain.SwaggerProperties;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Swagger2API文档的配置
 * Created by macro on 2018/4/26.
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig extends BaseSwaggerConfig {

    @Override
    public SwaggerProperties swaggerProperties() {
        return SwaggerProperties.builder()
                .apiBasePackage("cn.yinjiahui.voa.portal.controller")
                .title("voa前台系统")
                .description("voa前台相关接口文档")
                .contactName("macro")
                .contactEmail("yinjh2000@qq.com")
                .version("1.0")
                .enableSecurity(false)
                .build();
    }
}
