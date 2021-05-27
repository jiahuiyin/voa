package cn.yinjiahui.voa.portal.config;

import com.aliyun.teaopenapi.models.Config;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CodeConfig {

    @Value("${voa.code.accessKeyId}")
    private String accessKeyId;

    @Value("${voa.code.accessKeySecret}")
    private String accessKeySecret;

    @Bean
    public com.aliyun.dysmsapi20170525.Client createClient() throws Exception {
        Config config = new Config()
                // 您的AccessKey ID
                .setAccessKeyId(accessKeyId)
                // 您的AccessKey Secret
                .setAccessKeySecret(accessKeySecret);
        // 访问的域名
        config.endpoint = "dysmsapi.aliyuncs.com";
        return new com.aliyun.dysmsapi20170525.Client(config);
    }
}
