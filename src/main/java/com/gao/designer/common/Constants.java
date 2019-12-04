package com.gao.designer.common;

public class Constants {

    /*
    设置接口返回参数
     */
    public static final int RESULT_CODE_SUCCESS = 200;  // 成功处理请求
    public static final int RESULT_CODE_BAD_REQUEST = 412;  // 请求错误
    public static final int RESULT_CODE_NOT_LOGIN = 402;  // 未登录
    public static final int RESULT_CODE_PARAM_ERROR = 406;  // 传参错误
    public static final int RESULT_CODE_SERVER_ERROR = 500;  // 服务器错误
    public static final int RESULT_CODE_TOKEN_ERROR=101; //token异常


    /*
    钉钉微应用KEY以及SECRET常量参数设置
     */
    public static final String APP_KEY = "ding4ce7unidhfqoffws";
    public static final String APP_SECRET="iKAcM9nap1M_cJMWQnSVBVJ1OaD7Co5w_SW-V6qdBEUlZmMdkzQjo3sAmNjxpY9V";

    /**
     * 钉钉网关gettoken地址
     */
    public static final String URL_GET_TOKKEN = "https://oapi.dingtalk.com/gettoken";

    /**
     *获取用户在企业内userId的接口URL
     */
    public static final String URL_GET_USER_INFO = "https://oapi.dingtalk.com/user/getuserinfo";

    /**
     *获取用户姓名的接口url
     */
    public static final String URL_USER_GET = "https://oapi.dingtalk.com/user/get";
}
