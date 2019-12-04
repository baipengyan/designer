package com.gao.designer.common;

import com.alibaba.fastjson.JSON;
import com.taobao.api.internal.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Objects;

public class AuthInterceptor implements HandlerInterceptor {

    @Autowired
    private RedisService redisService;
    Result result=new Result();
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = request.getHeader("token");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");
        if (StringUtils.isEmpty(token)) {
            Result result=ResultGenerator.genFailTokenResult("用户未登录，请登录后操作！");
            response.getWriter().print(JSON.toJSONString(result));
            return false;
        }
        Object loginStatus = redisService.get(token);
            if( Objects.isNull(loginStatus)){
                Result result=ResultGenerator.genFailTokenResult("token错误");
                response.getWriter().print(JSON.toJSONString(result));
            return false;
        }
            /*
            更新token有效期预留代码
             */
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
