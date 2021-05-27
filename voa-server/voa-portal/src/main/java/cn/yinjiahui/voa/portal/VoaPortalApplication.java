package cn.yinjiahui.voa.portal;


import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
@MapperScan("cn.yinjiahui.voa.db.mapper")
@SpringBootApplication(scanBasePackages = {"cn.yinjiahui.voa.db", "cn.yinjiahui.voa.portal","cn.yinjiahui.voa.common"})
public class VoaPortalApplication {
    public static void main(String[] args) {
        SpringApplication.run(VoaPortalApplication.class, args);
    }
}
