package cn.yinjiahui.voa.portal;

import cn.yinjiahui.voa.portal.util.FileUtil;

public class Test {

    @org.junit.jupiter.api.Test
    void test(){


        System.out.println(FileUtil.getSize(1000000));
    }
}
