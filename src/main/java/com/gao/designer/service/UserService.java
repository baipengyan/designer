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

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

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

        //返回结果给前端
        Result result= ResultGenerator.genFailResult(response.getErrmsg());
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("userId", userId);
        resultMap.put("userName", infoResult[0]);
        resultMap.put("moible",infoResult[1]);
        resultMap.put("avatar",infoResult[2]);
        String timeNew= String.valueOf(System.currentTimeMillis());
        System.out.println("时间"+timeNew);
        if (userId!=null&&infoResult[0]!=null){
            String UserSel=userDao.selectByMoible(infoResult[1]);
            if (UserSel==null){
                User user=new User();
                user.setUsername(infoResult[0]);
                user.setDingUserid(userId);
                user.setMoible(infoResult[1]);
                user.setAvatar(infoResult[2]);
                user.setCreate_time(timeNew);
                int newUser=userDao.addUser(user);
                if (newUser>0) {
                    String token = UUID.randomUUID().toString(); //生成token
                    redisService.set(token,infoResult[0],60*60); //存储token到redis里
                    result = ResultGenerator.genSuccessResultToken(resultMap,"第一次登录,存储完毕！登录成功！",token);
                    System.out.println("第一次登录成功打印token"+token);
                }
            }else{
                String token = UUID.randomUUID().toString(); //生成token
                redisService.set(token,infoResult[0],60*60); //存储token到redis里
                result= ResultGenerator.genSuccessResultToken(resultMap,"用户已存在,直接登录成功！",token);
                System.out.println("历史登录成功打印token"+token);
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
            String[] userInfo = {response.getName(),response.getMobile(),response.getAvatar()};
            return userInfo;
        } catch (ApiException e) {
            e.printStackTrace();
            return null;
        }
    }
}
