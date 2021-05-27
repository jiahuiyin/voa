package cn.yinjiahui.voa.portal;

import cn.yinjiahui.voa.common.service.RedisService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class VoaPortalApplicationTests {
    @Autowired
    RedisService redisService;

    @Test
    void test(){
    }

}
