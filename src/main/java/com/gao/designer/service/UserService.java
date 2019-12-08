package com.gao.designer.service;

import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.DingTalkClient;
import com.dingtalk.api.request.OapiUserGetRequest;
import com.dingtalk.api.request.OapiUserGetuserinfoRequest;
import com.dingtalk.api.response.OapiUserGetResponse;
import com.dingtalk.api.response.OapiUserGetuserinfoResponse;
import com.gao.designer.common.*;
import com.gao.designer.dao.UserDao;
import com.gao.designer.entity.User;
import com.taobao.api.ApiException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
public class UserService {

    @Autowired
    private RedisService redisService;

    @Autowired
    private UserDao userDao;

    public Result dingLogin(String authCode){
        //获取钉钉accessToken
        String accessToken = AccessTokenUtil.getToken();

        //获取用户信息
        DingTalkClient client = new DefaultDingTalkClient(Constants.URL_GET_USER_INFO);
        OapiUserGetuserinfoRequest request = new OapiUserGetuserinfoRequest();
        request.setCode(authCode);
        request.setHttpMethod("GET");
        OapiUserGetuserinfoResponse response;
        try {
            response = client.execute(request, accessToken);
        } catch (ApiException e) {
            e.printStackTrace();
            return null;
        }
        //获取用户的userId
        String userId = response.getUserid();
        String[] infoResult = getUserInfo(accessToken,userId);

        Result result= ResultGenerator.genFailResult(response.getErrmsg());
        assert infoResult != null;
        Map<String, Object> resultMap = new HashMap<String,Object>(){{put("userId", "userId");put("userName", infoResult[0]);put("moible",infoResult[1]);put("avatar",infoResult[2]);}};
        resultMap.put("roles",infoResult[3]);
        String timeNew= String.valueOf(System.currentTimeMillis());
        Date date=new Date();
        if (userId!=null&&infoResult[0]!=null){
            String UserSel=userDao.selectByMoible(infoResult[1]);
            if (UserSel==null){
                User user=new User();
                user.setAll(infoResult[0],userId,infoResult[1],infoResult[2],timeNew);
                int newUser=userDao.addUser(user);
                if (newUser>0) {
                    String token = UUID.randomUUID().toString(); //生成token
                    redisService.set(token,infoResult[0],1L, TimeUnit.DAYS); //存储token到redis里
                    result = ResultGenerator.genSuccessResultToken(resultMap,"第一次登录,存储完毕！登录成功！",token);
                    System.out.println("时间："+date+"====="+infoResult[0]+"用户第一次登陆！，保存token："+token);
                }
            }else{
                String token = UUID.randomUUID().toString(); //生成token
                redisService.set(token,infoResult[0],1L, TimeUnit.DAYS); //存储token到redis里
                result= ResultGenerator.genSuccessResultToken(resultMap,"用户已存在,直接登录成功！",token);
                System.out.println("时间："+date+"====="+infoResult[0]+"老用户登录！，保存token："+token);
            }

        }
        return result;
    }

    public Result logout(String token){
        Result result=ResultGenerator.genFailResult("未知错误");
        Boolean delete = redisService.delete(token);
        if (!delete) {
            result=ResultGenerator.genFailResult("注销失败,您可能暂未登录");
        }else{
            result=ResultGenerator.genSuccessResult("注销成功");
        }
        return result;
    }

    /**x
     * 获取当前AuthCode用户信息
     * @param accessToken
     * @param userId
     * @return
     */
    private String[] getUserInfo(String accessToken, String userId) {
        try {
            DingTalkClient client = new DefaultDingTalkClient(Constants.URL_USER_GET);
            OapiUserGetRequest request = new OapiUserGetRequest();
            request.setUserid(userId);
            request.setHttpMethod("GET");
            OapiUserGetResponse response = client.execute(request, accessToken);
            String[] userInfo = {response.getName(),response.getMobile(),response.getAvatar(), String.valueOf(response.getRoles())};
            return userInfo;
        } catch (ApiException e) {
            e.printStackTrace();
            return null;
        }
    }
}
