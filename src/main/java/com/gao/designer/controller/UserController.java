package com.gao.designer.controller;

import com.gao.designer.common.RedisService;
import com.gao.designer.common.Result;
import com.gao.designer.common.ResultGenerator;
import com.gao.designer.dao.UserDao;
import com.gao.designer.entity.User;
import com.gao.designer.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


@RestController
@CrossOrigin(origins = "*",maxAge = 3600)
@Api(value = "用户接口",tags = "用户操作接口",description = "登录/用户增删改查接口")
@RequestMapping(value = "/User")
public class UserController {
    @Autowired
    private UserDao userDao;
    @Autowired
    private UserService userService;
    @Autowired
    private RedisService redisService;
    /**
     * 并没有什么卵用的一个接口
     * @return
     */
    @RequestMapping(value = "/welcome",method = RequestMethod.GET)
    @ApiOperation(value = "欢迎",notes = "没什么用，就是想说welcome")
    public String welcome(){
        return "welcome!";
    }


    /**
     * 钉钉用户根据前端传递的authCode获取信息
     * @param requestAuthCode
     * @return
     */
    @RequestMapping(value = "/login",method = RequestMethod.POST)
    @ApiOperation(value = "钉钉免验证登录",notes = "前端传值authCode，后台进行身份验证")
    @ResponseBody
    public Result login(@RequestParam(value = "authCode") String requestAuthCode) {
        return userService.dingLogin(requestAuthCode);
    }


    /**
     * 获取所有的用户
     * @return
     */
    @RequestMapping(value = "/getAllUser",method = RequestMethod.GET)
    @ApiOperation(value = "获取所有用户",notes ="一次性获取所有用户" )
    public Result getAllUser(){
        List<User> alluser=new ArrayList<>();
        Result result=ResultGenerator.genFailResult("获取失败");
        alluser=userDao.selectAll();
        if (alluser!=null)
        {
            result=ResultGenerator.genSuccessResult(alluser);
        }
        return result;
    }

    /**
     * 退出登录，删除redis里存储的token值
     * @return
     */
    @RequestMapping(value = "/logout",method = RequestMethod.POST)
    @ApiOperation(value = "退出登录",notes = "用户退出登录，后台删除token值")
    public Result logout(@RequestParam(value = "token") String requestToken){
        return userService.logout(requestToken);
    }



}



