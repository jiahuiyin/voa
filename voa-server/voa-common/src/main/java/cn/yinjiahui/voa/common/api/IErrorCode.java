package cn.yinjiahui.voa.common.api;

/**
 * 封装API的错误码
 * @author yinjiahui
 * @date 2021/4/3
 */
public interface IErrorCode {
    long getCode();

    String getMessage();
}
