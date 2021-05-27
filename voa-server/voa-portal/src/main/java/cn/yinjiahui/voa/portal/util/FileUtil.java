package cn.yinjiahui.voa.portal.util;

public class FileUtil {
    public static String getSize(long size){

        if(size>=1024*1024*1024){

            return size/1073741824L+"G";

        }else if(size>=1024*1024){

            return size/1048576L+"M";

        }else if(size>=1024){

            return size/1024+"K";

        }else
            return size+"B";

    }
}
